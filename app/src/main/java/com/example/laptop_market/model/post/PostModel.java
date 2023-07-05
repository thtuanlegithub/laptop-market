package com.example.laptop_market.model.post;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.algolia.search.saas.Client;
import com.algolia.search.saas.Index;
import com.algolia.search.saas.Query;
import com.example.laptop_market.contracts.IPostContract;
import com.example.laptop_market.utils.tables.AccountTable;
import com.example.laptop_market.utils.tables.LaptopTable;
import com.example.laptop_market.utils.tables.PostTable;
import com.example.laptop_market.view.adapters.PostSearchResult.PostSearchResult;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
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
    }
    //region Create new Post
    @Override
    public void CreateNewPost(Post post, OnCreatePostListener listener) {
        post.setAccountID(firebaseUser.getUid());
        post.setPushlishTime(new Date());
        db.collection(PostTable.TABLE_NAME).add(post).addOnCompleteListener(task -> {
            if(task.isSuccessful())
            {
                Client client = new Client("K1ZCG3UK74", "f7e2dae123a6156b7f7177b5fe321618");
                Index index = client.getIndex(PostTable.TABLE_NAME);
                DocumentReference documentReference = task.getResult();
                db.collection(LaptopTable.TABLE_NAME).document(post.getLaptopID()).get().addOnSuccessListener(documentSnapshot -> {
                    try
                    {
                        JSONObject object = new JSONObject();
                        object.put(PostTable.POST_ID, documentReference.getId());
                        object.put(PostTable.TITLE, post.getTitle().toLowerCase());
                        object.put(LaptopTable.BRAND_ID, documentSnapshot.getString(LaptopTable.BRAND_ID));
                        index.addObjectAsync(object, ((jsonObject, e) -> {
                            if (e != null)
                                listener.OnFinishCreatePostListener(false, e);
                        }));
                    }
                    catch (JSONException ex)
                    {
                        ex.printStackTrace();
                    }
                });

                db.collection(AccountTable.TABLE_NAME).document(firebaseUser.getUid()).update(AccountTable.PUBLISH_POSTS, FieldValue.arrayUnion(task.getResult().getId()))
                        .addOnFailureListener(e -> {
                            listener.OnFinishCreatePostListener(false,task.getException());
                        });
                listener.OnFinishCreatePostListener(true,null);
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
        Client client = new Client("K1ZCG3UK74","29a7a669b13aee0c13fc00a6e714bd22");
        j=0;
        Index index = client.getIndex(PostTable.TABLE_NAME);
        Query query = new Query(searchPost);
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
}
