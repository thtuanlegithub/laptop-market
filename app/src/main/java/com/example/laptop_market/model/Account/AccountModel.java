package com.example.laptop_market.model.Account;

import static com.example.laptop_market.presenter.fragment.SignUpFragmentPresenter.SIGNUP_FAILED;
import static com.example.laptop_market.presenter.fragment.SignUpFragmentPresenter.SIGNUP_SUCCESS;

import com.example.laptop_market.contracts.IAccountContract;
import com.example.laptop_market.ultilities.Constants;
import com.example.laptop_market.ultilities.PreferenceManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AccountModel implements IAccountContract.Model {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private PreferenceManager preferenceManager;
    private FirebaseUser firebaseUser;

    public AccountModel(PreferenceManager preferenceManager)
    {
        this.preferenceManager=preferenceManager;
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
                        db.collection(Account.KEY_TABLE_ACCOUNT).document(UId).get().addOnCompleteListener(
                                task1 -> {
                                    if(task1.isSuccessful()) {
                                        String username = task1.getResult().getString(Account.KEY_ACCOUNT_NAME);
                                        preferenceManager.putString(Constants.KEY_USER_EMAIL, account.getEmail());
                                        preferenceManager.putString(Constants.KEY_USER_NAME, username);
                                    }
                                }
                        ).addOnFailureListener(e -> {e.printStackTrace();});
                        listener.OnLoginListener(true,"Success");
                    }
                    else{
                        db.collection(Account.KEY_TABLE_ACCOUNT).whereEqualTo(Account.KEY_EMAIL,account.getEmail()).get().addOnSuccessListener(
                                queryDocumentSnapshots -> {
                                    if(queryDocumentSnapshots.getDocuments().size()==0)
                                    {
                                        listener.OnLoginListener(false,"Sai địa chỉ email");
                                    }
                                    else
                                        listener.OnLoginListener(false,"Sai password");
                                });
                    }

                });
    }
    //endregion
    //region On Loading account information
    @Override
    public void LoadAccount(OnLoadingAccountListener listener) {
        if(firebaseUser!=null)
        {
            Account account = new Account();
            account.setID_Account(firebaseUser.getUid());
            String temp =preferenceManager.getString(Constants.KEY_USER_NAME);
            if(!(preferenceManager.getString(Constants.KEY_USER_NAME).isEmpty())) {
                account.setName(preferenceManager.getString(Constants.KEY_USER_NAME));
                account.setEmail(preferenceManager.getString(Constants.KEY_USER_EMAIL));
                listener.OnLoadingListener(true,account);
            }
            else{
                db.collection(Account.KEY_TABLE_ACCOUNT).document(firebaseUser.getUid()).get().addOnSuccessListener(
                        documentSnapshot -> {
                            account.setName(documentSnapshot.getString(Account.KEY_ACCOUNT_NAME));
                            account.setEmail(documentSnapshot.getString(Account.KEY_EMAIL));
                            account.setName(preferenceManager.getString(Constants.KEY_USER_NAME));
                            account.setEmail(preferenceManager.getString(Constants.KEY_USER_EMAIL));
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

                                Map<String, Object> user = new HashMap<>();
                                user.put(Account.KEY_EMAIL, account.getEmail());
                                user.put(Account.KEY_PASSWORD, account.getPassword());
                                user.put(Account.KEY_ACCOUNT_NAME, account.getName());
                                user.put(Account.KEY_LIST_PUBLISH_POST,new ArrayList<String>());
                                db.collection(Account.KEY_TABLE_ACCOUNT).document(task.getResult().getUser().getUid()).set(user);
                                listener.OnCreateAccountResult(SIGNUP_SUCCESS,"Create account success");
                            }
                        })
                .addOnFailureListener(e -> {
                    if (e instanceof FirebaseAuthUserCollisionException) {
                        listener.OnCreateAccountResult(SIGNUP_FAILED,"Account already existed");
                    } else {
                        listener.OnCreateAccountResult(SIGNUP_FAILED,"Sign up fail");
                    }
                });
    }
    //endregion




}