package com.example.laptop_market.model.account;

import static com.example.laptop_market.presenter.fragments.SignUpFragmentPresenter.SIGNUP_FAILED;
import static com.example.laptop_market.presenter.fragments.SignUpFragmentPresenter.SIGNUP_SUCCESS;

import android.content.Context;

import com.example.laptop_market.contracts.IAccountContract;
import com.example.laptop_market.utils.tables.AccountTable;
import com.example.laptop_market.utils.tables.Constants;
import com.example.laptop_market.utils.elses.PreferenceManager;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AccountModel implements IAccountContract.Model {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private PreferenceManager preferenceManager;
    private FirebaseUser firebaseUser;

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
            String temp = preferenceManager.getString(Constants.KEY_USER_NAME);
            if(temp != null && !temp.isEmpty()){
                account.setAccountName(preferenceManager.getString(Constants.KEY_USER_NAME));
                account.setEmail(preferenceManager.getString(Constants.KEY_USER_EMAIL));
                listener.OnLoadingListener(true,account);
            }
            else{
                db.collection(AccountTable.TABLE_NAME).document(firebaseUser.getUid()).get().addOnSuccessListener(
                        documentSnapshot -> {
                            account.setAccountName(documentSnapshot.getString(AccountTable.ACCOUNT_NAME));
                            account.setEmail(documentSnapshot.getString(AccountTable.EMAIL));
                            preferenceManager.putString(Constants.KEY_USER_NAME, account.getAccountName());
                            preferenceManager.putString(Constants.KEY_USER_EMAIL, account.getEmail());
                            listener.OnLoadingListener(true,account);
                        }
                );
            }
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
                                account.setCartItems(new ArrayList<>());
                                account.setSavedPosts(new ArrayList<>());
                                account.setRating(0d);
                                Map<String, Object> user = new HashMap<>();
                                user.put(AccountTable.EMAIL, account.getEmail());
                                user.put(AccountTable.PASSWORD, account.getPassword());
                                user.put(AccountTable.ACCOUNT_NAME, account.getAccountName());
                                user.put(AccountTable.PUBLISH_POSTS,new ArrayList<String>());
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
    //endregion
}
