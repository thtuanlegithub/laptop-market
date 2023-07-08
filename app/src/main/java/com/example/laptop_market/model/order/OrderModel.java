package com.example.laptop_market.model.order;
import android.content.Context;

import com.example.laptop_market.contracts.IOrderContract;
import com.example.laptop_market.utils.elses.PreferenceManager;
import com.example.laptop_market.utils.tables.AccountTable;
import com.example.laptop_market.utils.tables.OrderTable;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;


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
}
