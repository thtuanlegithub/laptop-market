package com.example.laptop_market.model.account;

import static com.example.laptop_market.presenter.fragments.SignUpFragmentPresenter.SIGNUP_FAILED;
import static com.example.laptop_market.presenter.fragments.SignUpFragmentPresenter.SIGNUP_SUCCESS;

import android.content.Context;
import android.icu.lang.UCharacter;
import android.net.Uri;

import com.example.laptop_market.contracts.IAccountContract;
import com.example.laptop_market.model.order.OrderStatus;
import com.example.laptop_market.utils.tables.AccountTable;
import com.example.laptop_market.utils.tables.Constants;
import com.example.laptop_market.utils.elses.PreferenceManager;
import com.example.laptop_market.utils.tables.LaptopTable;
import com.example.laptop_market.utils.tables.OrderTable;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AccountModel implements IAccountContract.Model {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private PreferenceManager preferenceManager;
    private FirebaseUser firebaseUser;
    private FirebaseStorage firebaseStorage;
    public AccountModel()
    {
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseStorage = FirebaseStorage.getInstance();
    }

    public AccountModel(Context context)
    {
        this.preferenceManager= new PreferenceManager(context);
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
    }
    // region Login account function
    @Override
    public void Login(Account account, OnLoginFinishListener listener) {
        firebaseAuth.signInWithEmailAndPassword(account.getEmail(), account.getPassword()).addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        firebaseUser = task.getResult().getUser();
                        String UId = task.getResult().getUser().getUid();
                        db.collection(AccountTable.TABLE_NAME).document(UId).get().addOnCompleteListener(
                                task1 -> {
                                    if(task1.isSuccessful()) {
                                        String username = task1.getResult().getString(AccountTable.ACCOUNT_NAME);
                                        preferenceManager.putString(Constants.KEY_USER_EMAIL, account.getEmail());
                                        preferenceManager.putString(Constants.KEY_USER_NAME, username);
                                    }
                                }
                        ).addOnFailureListener(e -> {e.printStackTrace();});
                        listener.OnLoginListener(true,"Success");
                    }
                    else{
                        listener.OnLoginListener(false,"Thông tin đăng nhập không chính xác. Vui lòng kiểm tra lại!");
                    }
                });
    }
    //endregion
    //region On Loading account information
    @Override
    public void LoadAccount(OnLoadingAccountListener listener) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null)
        {
            Account account = new Account();
            account.setAccountID(firebaseUser.getUid());
            db.collection(AccountTable.TABLE_NAME).document(firebaseUser.getUid()).get().addOnSuccessListener(
                    documentSnapshot -> {
                        account.setAccountName(documentSnapshot.getString(AccountTable.ACCOUNT_NAME));
                        account.setEmail(documentSnapshot.getString(AccountTable.EMAIL));
                        account.setAvatar(documentSnapshot.getString(AccountTable.AVARTAR));
                        listener.OnLoadingListener(true,account);
                    }).addOnFailureListener(e -> {e.printStackTrace();});
        }
        else{
            listener.OnLoadingListener(false,null);
        }
    }
    //endregion
    //region Log out account
    @Override
    public void LogoutAccount(OnLogOutAccountListener listener) {
        if(firebaseUser!=null)
        {
            firebaseAuth.signOut();
            firebaseUser=firebaseAuth.getCurrentUser();
            preferenceManager.putString(Constants.KEY_USER_EMAIL,"");
            preferenceManager.putString(Constants.KEY_USER_NAME,"");
            listener.OnLogoutListener();
        }
    }


//endregion

    //region Create Account function
    @Override
    public void CreateAccount(Account account, OnCreateAccountListener listener) {
        firebaseAuth.createUserWithEmailAndPassword(account.getEmail(), account.getPassword()).addOnCompleteListener(
                        task -> {
                            if (task.isSuccessful()) {
                                account.setFinishData(false);
                                account.setAccountID(task.getResult().getUser().getUid());
                                account.setPublishPosts(new ArrayList<>());
                                account.setRatingOrders(new ArrayList<>());
                                account.setSavedPosts(new ArrayList<>());
                                account.setBuyOrders(new ArrayList<>());
                                account.setSellOrders(new ArrayList<>());
                                account.setRating(0d);
                                db.collection(AccountTable.TABLE_NAME).document(task.getResult().getUser().getUid()).set(account);
                                listener.OnCreateAccountResult(SIGNUP_SUCCESS,"Tạo tài khoản thành công");
                            }
                        })
                .addOnFailureListener(e -> {
                    if (e instanceof FirebaseAuthUserCollisionException) {
                        listener.OnCreateAccountResult(SIGNUP_FAILED,"Đã tồn tại tài khoản này");
                    } else {
                        listener.OnCreateAccountResult(SIGNUP_FAILED,"Đăng ký thất bại");
                    }
                });
    }

    @Override
    public void LoadAccountWithId(String accountId, OnLoadingAccountWithIdListener listener) {
        db.collection(AccountTable.TABLE_NAME).document(accountId).get().addOnSuccessListener(documentSnapshot -> {
            Account account = documentSnapshot.toObject(Account.class);
            account.setAccountID(documentSnapshot.getId());
            listener.OnFinishLoadingAccountWithId(account,null);
        }).addOnFailureListener(e -> {
            listener.OnFinishLoadingAccountWithId(null,e);
        });
    }

    @Override
    public void CheckSignedInAccount(OnCheckingSignInAccountListener listener) {
        firebaseAuth = FirebaseAuth.getInstance();
        boolean isLogin = firebaseAuth.getCurrentUser() != null;
        listener.OnFinishCheckingSignInAccount(isLogin);
    }
    //endregion

    //region Save post
    @Override
    public void ClickSavePost(String postID, OnFinishSavePostListener listener) {
        String userID = firebaseUser.getUid();
        db.collection(AccountTable.TABLE_NAME).document(userID).update(AccountTable.SAVED_POSTS, FieldValue.arrayUnion(postID)).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                listener.OnFinishSavePost(true, true, null);
            }
            else {
                listener.OnFinishSavePost(false, false, task.getException());
            }
        });
    }

    @Override
    public void ClickRemoveSavePost(String postID, OnFinishSavePostListener listener) {
        String userID = firebaseUser.getUid();
        db.collection(AccountTable.TABLE_NAME).document(userID).update(AccountTable.SAVED_POSTS, FieldValue.arrayRemove(postID)).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                listener.OnFinishSavePost(true, false, null);
            }
            else {
                listener.OnFinishSavePost(false, false, task.getException());
            }
        });
    }

    @Override
    public void LoadSavePostButton(String postID, OnFinishSavePostListener listener) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userID = firebaseUser.getUid();
        db.collection(AccountTable.TABLE_NAME).document(userID).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                DocumentSnapshot documentSnapshot = task.getResult();
                ArrayList<String> savedPostList = (ArrayList<String>) documentSnapshot.get(AccountTable.SAVED_POSTS);
                boolean isExisted = savedPostList.contains(postID);
                listener.OnFinishSavePost(true, isExisted, null);
            }
            else{
                listener.OnFinishSavePost(false, false, task.getException());
            }
        });
    }

    @Override
    public void LoadAccountSetting(OnFinishLoadingProfileListener listener) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        db.collection(AccountTable.TABLE_NAME).document(firebaseUser.getUid()).get().addOnCompleteListener(task -> {
            if(!task.isSuccessful())
            {
                listener.OnFinishLoadingProfile(null,task.getException());
            }
            else
            {
                Account account = task.getResult().toObject(Account.class);
                listener.OnFinishLoadingProfile(account,null);
            }
        });
    }

    @Override
    public void UpdateAccountInformation(Account account, OnFinishUpdateAccountInformationListener listener) {
        db.collection(AccountTable.TABLE_NAME).document(firebaseUser.getUid()).update(AccountTable.ACCOUNT_NAME,account.getAccountName()
                , AccountTable.ADDRESS, account.getAddress()
                , AccountTable.PHONE_NUMBER, account.getPhoneNumber()
                , AccountTable.DESCRIPTION, account.getDescription()).addOnCompleteListener(task -> {
                    if(!task.isSuccessful())
                        listener.OnFinishUpdateAccountInformation(task.getException());
                    else
                        listener.OnFinishUpdateAccountInformation(null);
        });

    }

    @Override
    public void UpdateAccountPassword(String oldPassword, String newPassword, OnFinishUpdateAccountPasswordListener listener) {
        db.collection(AccountTable.TABLE_NAME).document(firebaseUser.getUid()).get().addOnSuccessListener(documentSnapshot -> {
            if(!documentSnapshot.getString(AccountTable.PASSWORD).equals(oldPassword))
                listener.OnFinishUpdateAccountPassword(false, null);
            else
                firebaseUser.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        db.collection(AccountTable.TABLE_NAME).document(firebaseUser.getUid()).update(AccountTable.PASSWORD,newPassword);
                        listener.OnFinishUpdateAccountPassword(true, null);
                    }
                }).addOnFailureListener(Throwable::printStackTrace);
        }).addOnFailureListener(Throwable::printStackTrace);
    }


    //endregion
    @Override
    public void UploadAvatar(Account account, Uri uri, OnFinishUpdateAvatarListener listener) {
        String extension = "jpg";
        StorageReference imageRef = firebaseStorage.getReference().child(LaptopTable.TABLE_NAME).child( firebaseUser.getUid() + "." + extension);
        imageRef.putFile(uri).addOnCompleteListener(task -> {
            if(!task.isSuccessful())
                listener.OnFinishUpdateAvatar(task.getException());
            else
            {
                imageRef.getDownloadUrl().addOnCompleteListener(task1 -> {
                    if(!task1.isSuccessful())
                        listener.OnFinishUpdateAvatar(task1.getException());
                    else
                    {
                        Uri imageLink = task1.getResult();
                        db.collection(AccountTable.TABLE_NAME).document(firebaseUser.getUid()).update(AccountTable.AVARTAR, imageLink.toString()).addOnFailureListener(listener::OnFinishUpdateAvatar)
                                .addOnSuccessListener(unused -> {
                            listener.OnFinishUpdateAvatar(null);
                        });
                    }
                });
            }
        });
    }

    @Override
    public void GetCurrentAccountInformation(OnGetCurrentAccountInfoListener listener) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null){
            String userID = firebaseUser.getUid();
            db.collection(AccountTable.TABLE_NAME).document(userID).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()){
                        CurrentBuyer currentBuyer = new CurrentBuyer();
                        currentBuyer.setAccountID(firebaseUser.getUid());
                        currentBuyer.setAccountName(documentSnapshot.getString(AccountTable.ACCOUNT_NAME));
                        currentBuyer.setPhoneNumber(documentSnapshot.getString(AccountTable.PHONE_NUMBER));
                        currentBuyer.setAddress(documentSnapshot.getString(AccountTable.ADDRESS));
                        listener.OnFinishGetCurrentAccountInfo(true, currentBuyer, null);
                    }
                } else {
                    listener.OnFinishGetCurrentAccountInfo(false, null, task.getException());
                }
            });
        }
    }

    @Override
    public void LoadStatisticInformation(String AccountID, Boolean isBuy, OnFinishLoadStatisticInformation listener) {
        DocumentReference accountRef = db.collection(AccountTable.TABLE_NAME).document(AccountID);
        accountRef.get().addOnCompleteListener(task -> {
           if (task.isSuccessful())  {
               DocumentSnapshot documentSnapshot = task.getResult();
               if (documentSnapshot != null && documentSnapshot.exists()) {
                    ArrayList<String> youFinishOrders = new ArrayList<String>();
                    if (isBuy)
                        youFinishOrders = (ArrayList) documentSnapshot.get(AccountTable.BUY_ORDERS);
                    else
                        youFinishOrders = (ArrayList) documentSnapshot.get(AccountTable.SELL_ORDERS);
                    if (youFinishOrders != null && !youFinishOrders.isEmpty()){
                        Query query = db.collection(OrderTable.TABLE_NAME)
                                .whereEqualTo(OrderTable.ORDER_STATUS, OrderStatus.FINISHED)
                                .whereIn("orderID", youFinishOrders);
                        query.get().addOnCompleteListener(task1 -> {
                           if (task1.isSuccessful()) {
                               int NoOrders = 0;
                               double revenue = 0;
                               for (QueryDocumentSnapshot orderDoc :  task1.getResult()) {
                                    String rawPrice = orderDoc.getString(OrderTable.TOTAL_AMOUNT);
                                    String formattedStr = rawPrice.replaceAll("[^\\d.]", "");
                                    revenue += (Double.parseDouble(formattedStr));
                                    ++NoOrders;
                               }
                               listener.OnFinishLoadStatistic(true, NoOrders, revenue, null);
                           } else{
                               listener.OnFinishLoadStatistic(false, 0, 0, task1.getException());
                           }
                        });
                    } else {
                        listener.OnFinishLoadStatistic(true, 0, 0, null);
                    }
               }
           }
        });
    }
}
