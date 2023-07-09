package com.example.laptop_market.model.post;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.algolia.search.saas.AlgoliaException;
import com.algolia.search.saas.Client;
import com.algolia.search.saas.CompletionHandler;
import com.algolia.search.saas.Index;
import com.algolia.search.saas.Query;
import com.example.laptop_market.contracts.IPostContract;
import com.example.laptop_market.model.laptop.Laptop;
import com.example.laptop_market.model.order.OrderStatus;
import com.example.laptop_market.utils.tables.AccountTable;
import com.example.laptop_market.utils.tables.LaptopTable;
import com.example.laptop_market.utils.tables.OrderTable;
import com.example.laptop_market.utils.tables.PostTable;
import com.example.laptop_market.utils.tables.SearchFilterPost;
import com.example.laptop_market.view.adapters.PostSearchResult.PostSearchResult;
import com.example.laptop_market.view.adapters.Sell.SellOrder;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostModel implements IPostContract.Model {
    private FirebaseAuth firebaseAuth;
    private int count;
    private FirebaseFirestore db;
    private FirebaseUser firebaseUser;
    private FirebaseStorage firebaseStorage;
    private Client client;
    private Context context;
    private int i;
    private int j =0;
    private int totalhits = 0;
    public PostModel(Context context)
    {
        this.context = context;
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseStorage = FirebaseStorage.getInstance();
        client = new Client("K1ZCG3UK74","29a7a669b13aee0c13fc00a6e714bd22");
    }
    //region Create new Post
    @Override
    public void CreateNewPost(Post post, Laptop laptop, OnCreatePostListener listener) {
        post.setAccountID(firebaseUser.getUid());
        post.setPushlishTime(new Date());
        db.collection(PostTable.TABLE_NAME).add(post).addOnCompleteListener(task -> {
            if(task.isSuccessful())
            {
                DocumentReference documentReference = task.getResult();
                if (documentReference != null) {
                    String postID = documentReference.getId();
                    documentReference.update("postID", postID);
                    Client client = new Client("K1ZCG3UK74", "f7e2dae123a6156b7f7177b5fe321618");
                    Index index = client.getIndex(PostTable.TABLE_NAME);
                    try
                    {
                        JSONObject object = new JSONObject();
                        object.put(PostTable.POST_ID, documentReference.getId());
                        object.put(PostTable.TITLE, post.getTitle().toLowerCase());
                        object.put(LaptopTable.BRAND_ID, laptop.getBrandID());
                        object.put(LaptopTable.PRICE, laptop.getPrice());
                        object.put(LaptopTable.CPU, laptop.getCpu());
                        object.put(LaptopTable.RAM, laptop.getRam());
                        object.put(LaptopTable.HARD_DRIVE, laptop.getHardDrive());
                        object.put(LaptopTable.HARD_DRIVE_SIZE, laptop.getHardDriveSize());
                        object.put(LaptopTable.GRAPHICS, laptop.getGraphics());
                        object.put(LaptopTable.SCREEN_SIZE, laptop.getScreenSize());
                        object.put(LaptopTable.GUARANTEE, laptop.getGuarantee());
                        index.addObjectAsync(object, ((jsonObject, e) -> {
                            if (e != null)
                                listener.OnFinishCreatePostListener(false, e);
                            else{
                                db.collection(AccountTable.TABLE_NAME).document(firebaseUser.getUid()).update(AccountTable.PUBLISH_POSTS, FieldValue.arrayUnion(task.getResult().getId()))
                                        .addOnSuccessListener(unused -> {
                                            listener.OnFinishCreatePostListener(true,null);
                                        })
                                        .addOnFailureListener(e1 -> {
                                            listener.OnFinishCreatePostListener(false,e1);
                                        });

                            }
                        }));
                    }
                    catch (JSONException ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }
            else{
                listener.OnFinishCreatePostListener(false,task.getException());
            }
        });
    }
    //endregion
    //region On Searching post
    @Override
    public void LoadSearchPost(String searchPost, OnLoadingSearchPostListener listener) {
        j=0;
        Index index = client.getIndex(PostTable.TABLE_NAME);
        Query query = new Query(searchPost);
        // Chỉ định các trường tìm kiếm
        JSONObject settings = new JSONObject();
        try {
            settings.put("searchableAttributes", PostTable.TITLE);
            settings.put("searchableAttributes", LaptopTable.BRAND_ID);
        }
        catch (JSONException ex)
        {
            ex.printStackTrace();
        }
        index.setSettingsAsync(settings, null);
/*        JSONObject settings = new JSONObject();
        settings.put("searchableAttributes", new String[] {"title", "brandId"});*/

// Cập nhật các thiết lập mới cho chỉ mục Algolia Search
        index.searchAsync(query,(jsonObject, e)->{
                    if (e == null) {
                        assert jsonObject != null;
                        JSONArray hits = jsonObject.optJSONArray("hits");
                        List<Task<DocumentSnapshot>> downloadTasks = new ArrayList<>();
                        count=0;
                        for (int i = 0; i < hits.length(); i++) {
                            JSONObject hit = hits.optJSONObject(i);
                            String id = hit.optString(PostTable.POST_ID);
                            Task<DocumentSnapshot> task = db.collection(PostTable.TABLE_NAME).document(id).get();
                            downloadTasks.add(task);
                        }

                        Tasks.whenAllSuccess(downloadTasks).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                List<Object> documentSnapshots = task.getResult();
                                ArrayList<PostSearchResult> listPost = new ArrayList<>();
                                for (Object obj : documentSnapshots) {
                                    DocumentSnapshot documentSnapshot = (DocumentSnapshot)obj;
                                    PostSearchResult post = new PostSearchResult();
                                    post.setPostId(documentSnapshot.getId());
                                    post.setAddress(documentSnapshot.getString(PostTable.SELLER_ADDRESS));
                                    post.setTitle(documentSnapshot.getString(PostTable.TITLE));
                                    post.setLaptopId(documentSnapshot.getString(PostTable.LAPTOP_ID));
                                    post.setAccountId(documentSnapshot.getString(PostTable.ACCOUNT_ID));
                                    post.setImage(getBitMapFromString(documentSnapshot.getString((PostTable.POST_MAIN_IMAGE))));
                                    listPost.add(post);
                                }
                                listener.OnFinishLoadingSearchPostListener(listPost,null);
                            } else {
                                Exception error = task.getException();
                                listener.OnFinishLoadingSearchPostListener(null, error);
                            }
                        });
                    } else {
                        totalhits=0;
                        ArrayList<PostSearchResult> postSearchResultArrayList = new ArrayList<>();
                        listener.OnFinishLoadingSearchPostListener(postSearchResultArrayList,null);

                    }

                }
        );
    }

    @Override
    public void LoadSearchPostByFilter(SearchFilterPost searchFilterPost, OnLoadingSearchPostListener listener) {
            Index index = client.getIndex(PostTable.TABLE_NAME);
            Query query = new Query(searchFilterPost.getSearchPost());
            String filterString = "";

        // Thêm điều kiện lọc giá
        if (searchFilterPost.getMinimumPrice() >= 0 && searchFilterPost.getMaximumPrice() > 0) {
            String priceFilter = LaptopTable.PRICE + " >= " + searchFilterPost.getMinimumPrice() + " AND " + LaptopTable.PRICE + " <= " + searchFilterPost.getMaximumPrice();
            filterString += priceFilter  + " AND ";
        }

// Thêm điều kiện lọc thương hiệu
        if (searchFilterPost.getListBrandName().size() > 0) {
            String brandFilter = "";
            for (String brandName : searchFilterPost.getListBrandName()) {
                brandFilter += " " + LaptopTable.BRAND_ID + ":" + brandName + " OR ";
            }
            brandFilter ="( " + brandFilter.substring(0, brandFilter.length() - 4) + " ) ";
            filterString+=brandFilter + " AND ";
        }


        if (searchFilterPost.getListGuarantee().size() > 0) {
            String guaranteeFilters = "";
            for (String guarantee : searchFilterPost.getListGuarantee()) {
                guaranteeFilters += " " + LaptopTable.GUARANTEE + ":\"" + guarantee + "\" OR ";
            }
            guaranteeFilters ="( " + guaranteeFilters.substring(0, guaranteeFilters.length() - 4)+ " ) ";
            filterString+=guaranteeFilters + " AND ";

        }

// Thêm điều kiện lọc CPU
        if (searchFilterPost.getListCPU().size() > 0) {
            String cpuFilter = "";
            for (String cpu : searchFilterPost.getListCPU()) {
                cpuFilter += " " + LaptopTable.CPU + ":" + cpu + " OR ";
            }
            cpuFilter = "( " + cpuFilter.substring(0, cpuFilter.length() - 4) + " ) ";
            filterString+=cpuFilter + " AND ";
        }

// Thêm điều kiện lọc RAM
        if (searchFilterPost.getListRam().size() > 0) {
            String ramFilter = "";
            for (String ram : searchFilterPost.getListRam()) {
                ramFilter += " " + LaptopTable.RAM + ":" + ram + " OR ";
            }
            ramFilter = "( " + ramFilter.substring(0, ramFilter.length() - 4) + " ) ";
            filterString+=ramFilter + " AND ";
        }

// Thêm điều kiện lọc ổ cứng
        if (searchFilterPost.getListHardDrive().size() > 0) {
            String hardDriveFilter = "";
            for (String hardDrive : searchFilterPost.getListHardDrive()) {
                hardDriveFilter += " " + LaptopTable.HARD_DRIVE + ":" + hardDrive + " OR ";
            }
            hardDriveFilter = "( " + hardDriveFilter.substring(0, hardDriveFilter.length() - 4) + " ) ";
            filterString += hardDriveFilter + " AND ";
        }

// Thêm điều kiện lọc dung lượng ổ cứng
        if (searchFilterPost.getListHardDriveSize().size() > 0) {
            String hardDriveSizeFilter = "";
            for (String hardDriveSize : searchFilterPost.getListHardDriveSize()) {
                hardDriveSizeFilter += " " + LaptopTable.HARD_DRIVE_SIZE + ":" + hardDriveSize + " OR ";
            }
            hardDriveSizeFilter = "( " + hardDriveSizeFilter.substring(0, hardDriveSizeFilter.length() - 4) + " ) ";
            filterString += hardDriveSizeFilter + " AND ";
        }

// Thêm điều kiện lọc card đồ họa
        if (searchFilterPost.getListGraphics().size() > 0) {
            String graphicsFilter = "";
            for (String graphics : searchFilterPost.getListGraphics()) {
                graphicsFilter += " " + LaptopTable.GRAPHICS + ":" + graphics + " OR ";
            }
            graphicsFilter = "( " + graphicsFilter.substring(0, graphicsFilter.length() - 4) + " ) ";
            filterString += graphicsFilter + " AND ";
        }

// Thêm điều kiện lọc kích thước màn hình
        if (searchFilterPost.getListScreenSize().size() > 0) {
            String screenSizeFilter = "";
            for (String screenSize : searchFilterPost.getListScreenSize()) {
                screenSizeFilter += " " + LaptopTable.SCREEN_SIZE + ":" + screenSize + " OR ";
            }
            screenSizeFilter = "( " + screenSizeFilter.substring(0, screenSizeFilter.length() - 4) + " ) ";
            filterString += screenSizeFilter + " AND ";;
        }
        filterString = filterString.substring(0,filterString.length()-5);
        query.setFilters(filterString);
            index.searchAsync(query, new CompletionHandler() {
                @Override
                public void requestCompleted(JSONObject jsonObject, AlgoliaException e) {
                    if (e == null) {
                        assert jsonObject != null;
                        JSONArray hits = jsonObject.optJSONArray("hits");
                        List<Task<DocumentSnapshot>> downloadTasks = new ArrayList<>();
                        count=0;
                        for (int i = 0; i < hits.length(); i++) {
                            JSONObject hit = hits.optJSONObject(i);
                            String id = hit.optString(PostTable.POST_ID);
                            Task<DocumentSnapshot> task = db.collection(PostTable.TABLE_NAME).document(id).get();
                            downloadTasks.add(task);
                        }

                        Tasks.whenAllSuccess(downloadTasks).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                List<Object> documentSnapshots = task.getResult();
                                ArrayList<PostSearchResult> listPost = new ArrayList<>();
                                for (Object obj : documentSnapshots) {
                                    DocumentSnapshot documentSnapshot = (DocumentSnapshot)obj;
                                    PostSearchResult post = new PostSearchResult();
                                    post.setPostId(documentSnapshot.getId());
                                    post.setAddress(documentSnapshot.getString(PostTable.SELLER_ADDRESS));
                                    post.setTitle(documentSnapshot.getString(PostTable.TITLE));
                                    post.setLaptopId(documentSnapshot.getString(PostTable.LAPTOP_ID));
                                    post.setAccountId(documentSnapshot.getString(PostTable.ACCOUNT_ID));
                                    post.setImage(getBitMapFromString(documentSnapshot.getString((PostTable.POST_MAIN_IMAGE))));
                                    listPost.add(post);
                                }
                                listener.OnFinishLoadingSearchPostListener(listPost,null);
                            } else {
                                Exception error = task.getException();
                                listener.OnFinishLoadingSearchPostListener(null, error);
                            }
                        });
                    } else {
                        totalhits=0;
                        ArrayList<PostSearchResult> postSearchResultArrayList = new ArrayList<>();
                        listener.OnFinishLoadingSearchPostListener(postSearchResultArrayList,null);

                    }

                }
            });
    }
    private String getFilterString(String field, List<String> values) {
        if (values.isEmpty()) {
            return "";
        }
        StringBuilder filterString = new StringBuilder();
        filterString.append(" AND (");
        for (String value : values) {
            filterString.append(field).append(":'").append(value).append("' OR ");
        }
        return filterString.substring(0, filterString.length() - 4) + ")";
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
    //endregion
    @Override
    public void LoadingPostInPostDetail(String postID, OnLoadingPostInPostDetailListener listener) {
        db.collection(PostTable.TABLE_NAME).document(postID).get().addOnSuccessListener(documentSnapshot -> {
            Post post = documentSnapshot.toObject(Post.class);
            listener.OnFinishLoadingPostInPostDetail(post,null);
        }).addOnFailureListener(e -> {
            listener.OnFinishLoadingPostInPostDetail(null,e);
        });
    }
    //region GetSellerPhoneNumber
    @Override
    public void GetSellerPhoneNumber(String postID, OnLoadSellerPhoneNumber listener) {
        db.collection(PostTable.TABLE_NAME).document(postID).get().addOnCompleteListener(task -> {
           if (task.isSuccessful()) {
               DocumentSnapshot documentSnapshot = task.getResult();
               if (documentSnapshot.exists()){
                   String phoneNumber = documentSnapshot.getString(PostTable.SELLER_PHONE_NUMBER);
                   listener.OnFinishLoadSellerPhoneNumber(true, phoneNumber, null);
               }
           }
           else {
               listener.OnFinishLoadSellerPhoneNumber(false, null, task.getException());
           }
        });
    }
    //endregion

    @Override
    public void LoadPostActive(OnLoadPostActiveListener listener) {
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            String userID = firebaseUser.getUid();
            // Nếu có người đang đăng nhập
            if (userID != null) {
                DocumentReference accountRef = db.collection(AccountTable.TABLE_NAME).document(userID);
                // Lấy cái BuyOrder từ bảng Account
                accountRef.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot != null && documentSnapshot.exists()) {
                            ArrayList<String> postActiveListID = (ArrayList<String>) documentSnapshot.get(AccountTable.PUBLISH_POSTS);
                            if (postActiveListID != null && !postActiveListID.isEmpty()) {
                                com.google.firebase.firestore.Query query = db.collection(PostTable.TABLE_NAME)
                                        .whereEqualTo(PostTable.POST_STATUS, PostStatus.AVAILABLE)
                                        .whereIn("postID", postActiveListID);

                                query.get().addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        ArrayList<PostSearchResult> postSearchResults = new ArrayList<>();
                                        for (QueryDocumentSnapshot postDoc : task1.getResult()) {
                                            PostSearchResult postSearchResult = new PostSearchResult ();
                                            postSearchResult.setPostId(postDoc.getString(PostTable.POST_ID));
                                            postSearchResult.setLaptopId(documentSnapshot.getString(PostTable.LAPTOP_ID));
                                            postSearchResult.setAccountId(postDoc.getString(PostTable.ACCOUNT_ID));
                                            postSearchResult.setTitle(documentSnapshot.getString(PostTable.TITLE));
                                            postSearchResult.setAddress(postDoc.getString(PostTable.SELLER_ADDRESS));
                                            postSearchResult.setImage(getBitMapFromString(postDoc.getString(PostTable.POST_MAIN_IMAGE)));
                                            postSearchResults.add(postSearchResult);
                                        }
                                        listener.OnFinishLoadPostActive(true, postSearchResults, null);
                                    } else {
                                        listener.OnFinishLoadPostActive(false, null, task1.getException());
                                    }
                                });
                            } else {
                                listener.OnFinishLoadPostActive(true, null, null);
                            }
                        }
                    }
                });
            }
        } else {
            // Trả về list rỗng, nhưng vẫn thông báo thành công chứ không phải bug
            listener.OnFinishLoadPostActive(true, null, null);
        }
    }

    @Override
    public void LoadPostInActive(OnLoadPostInactiveListener listener) {
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            String userID = firebaseUser.getUid();
            // Nếu có người đang đăng nhập
            if (userID != null) {
                DocumentReference accountRef = db.collection(AccountTable.TABLE_NAME).document(userID);
                // Lấy cái BuyOrder từ bảng Account
                accountRef.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot != null && documentSnapshot.exists()) {
                            ArrayList<String> postActiveListID = (ArrayList<String>) documentSnapshot.get(AccountTable.PUBLISH_POSTS);
                            if (postActiveListID != null && !postActiveListID.isEmpty()) {
                                com.google.firebase.firestore.Query query = db.collection(PostTable.TABLE_NAME)
                                        .whereEqualTo(PostTable.POST_STATUS, PostStatus.NOT_AVAILABLE)
                                        .whereIn("postID", postActiveListID);

                                query.get().addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        ArrayList<PostSearchResult> postSearchResults = new ArrayList<>();
                                        for (QueryDocumentSnapshot postDoc : task1.getResult()) {
                                            PostSearchResult postSearchResult = new PostSearchResult ();
                                            postSearchResult.setPostId(postDoc.getString(PostTable.POST_ID));
                                            postSearchResult.setLaptopId(documentSnapshot.getString(PostTable.LAPTOP_ID));
                                            postSearchResult.setAccountId(postDoc.getString(PostTable.ACCOUNT_ID));
                                            postSearchResult.setTitle(documentSnapshot.getString(PostTable.TITLE));
                                            postSearchResult.setAddress(postDoc.getString(PostTable.SELLER_ADDRESS));
                                            postSearchResult.setImage(getBitMapFromString(postDoc.getString(PostTable.POST_MAIN_IMAGE)));
                                            postSearchResults.add(postSearchResult);
                                        }
                                        listener.OnFinishLoadPostInactive(true, postSearchResults, null);
                                    } else {
                                        listener.OnFinishLoadPostInactive(false, null, task1.getException());
                                    }
                                });
                            } else {
                                listener.OnFinishLoadPostInactive(true, null, null);
                            }
                        }
                    }
                });
            }
        } else {
            // Trả về list rỗng, nhưng vẫn thông báo thành công chứ không phải bug
            listener.OnFinishLoadPostInactive(true, null, null);
        }
    }
}
