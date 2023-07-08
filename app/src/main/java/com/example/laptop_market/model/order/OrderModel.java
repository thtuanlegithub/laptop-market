package com.example.laptop_market.model.order;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.example.laptop_market.contracts.IOrderContract;
import com.example.laptop_market.utils.elses.PreferenceManager;
import com.example.laptop_market.utils.tables.AccountTable;
import com.example.laptop_market.utils.tables.OrderTable;
import com.example.laptop_market.utils.tables.PostTable;
import com.example.laptop_market.view.adapters.Buy.BuyOrder;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;


public class OrderModel implements IOrderContract.Model {
    private PreferenceManager preferenceManager;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private FirebaseUser firebaseUser;
    public OrderModel (Context context)
    {
        this.preferenceManager= new PreferenceManager(context);
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
    }

    @Override
    public void CreateNewOrder(Order order, String sellerID, String buyerID, OnCreateOrderListener listener) {
        db.collection(OrderTable.TABLE_NAME).add(order).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentReference documentReference = task.getResult();
                if (documentReference != null) {
                    String orderID = documentReference.getId();
                    documentReference.update("orderID", orderID);
                    // Save order to sellerOrders of account Seller
                    db.collection(AccountTable.TABLE_NAME).document(sellerID).update(AccountTable.SELL_ORDERS, FieldValue.arrayUnion(orderID));
                    // Save order to buyerOrders of account Seller
                    db.collection(AccountTable.TABLE_NAME).document(buyerID).update(AccountTable.BUY_ORDERS, FieldValue.arrayUnion(orderID));
                    listener.OnFinishCreateOrder(true, null);
                } else {
                    listener.OnFinishCreateOrder(false, task.getException());
                }
            } else {
                listener.OnFinishCreateOrder(false, task.getException());
            }
        });
    }

    @Override
    public void GetProcessingOrders(OnGetProcessingOrdersListener listener) {
        String userID = firebaseUser.getUid();
        // Nếu có người đang đăng nhập
        if (userID != null) {
            DocumentReference accountRef = db.collection(AccountTable.TABLE_NAME).document(userID);
            // Lấy cái BuyOrder từ bảng Account
            accountRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        ArrayList<String> buyOrdersID = (ArrayList<String>) documentSnapshot.get(AccountTable.BUY_ORDERS);
                        if (buyOrdersID != null && !buyOrdersID.isEmpty()) {
                            Query query = db.collection(OrderTable.TABLE_NAME)
                                    .whereEqualTo(OrderTable.ORDER_STATUS, OrderStatus.PROCESSING)
                                    .whereIn("orderID", buyOrdersID);

                            query.get().addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()) {
                                    ArrayList<Task<DocumentSnapshot>> loadProductTasks = new ArrayList<>(); // Danh sách các nhiệm vụ tải dữ liệu sản phẩm
                                    ArrayList<BuyOrder> processingOrders = new ArrayList<>();

                                    for (QueryDocumentSnapshot orderDoc : task1.getResult()) {
                                        BuyOrder buyOrder = new BuyOrder();
                                        buyOrder.setOrderId(orderDoc.getString("orderID"));
                                        buyOrder.setSellerId(orderDoc.getString(OrderTable.SELLER_ID));
                                        String postID = orderDoc.getString(OrderTable.POST_ID);
                                        Task<DocumentSnapshot> getProductTask = db.collection(PostTable.TABLE_NAME)
                                                .document(postID)
                                                .get()
                                                .addOnSuccessListener(productSnapshot -> {
                                                    if (productSnapshot.exists()) {
                                                        buyOrder.setLaptopName(productSnapshot.getString(PostTable.TITLE));
                                                        buyOrder.setImage(getBitMapFromString(productSnapshot.getString(PostTable.POST_MAIN_IMAGE)));
                                                    }
                                                });
                                        loadProductTasks.add(getProductTask);
                                        buyOrder.setPrice(orderDoc.getString(OrderTable.TOTAL_AMOUNT));
                                        buyOrder.setAddress(orderDoc.getString(OrderTable.SHIP_ADDRESS));
                                        processingOrders.add(buyOrder);
                                    }

                                    // Chờ cho tất cả các nhiệm vụ tải dữ liệu sản phẩm hoàn thành
                                    Tasks.whenAllSuccess(loadProductTasks)
                                            .addOnSuccessListener(results -> {
                                                listener.OnFinishGetProcessingOrders(true, processingOrders, null);
                                            })
                                            .addOnFailureListener(exception -> {
                                                listener.OnFinishGetProcessingOrders(false, null, exception);
                                            });
                                } else {
                                    listener.OnFinishGetProcessingOrders(false, null, task1.getException());
                                }
                            });
                        }
                    }
                }
            });
        } else {
            // Trả về list rỗng, nhưng vẫn thông báo thành công chứ không phải bug
            listener.OnFinishGetProcessingOrders(true, null, null);
        }
    }

    @Override
    public void GetDeliveringOrders(OnGetDeliveringOrdersListener listener) {

    }

    @Override
    public void GetFinishedOrders(OnGetFinishedOrdersListener listener) {

    }

    @Override
    public void GetCanceledOrders(OnGetCanceledOrdersListener listener) {

    }

    private Bitmap getBitMapFromString(String encodedImage)
    {
        if(encodedImage!=null) {
            byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        else
            return  null;
    }
}
