package com.example.laptop_market.presenter.activities;

import android.net.Uri;

import com.example.laptop_market.contracts.IAccountContract;
import com.example.laptop_market.model.account.Account;
import com.example.laptop_market.model.account.AccountModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class ProfileActivityPresenter implements IAccountContract.Presenter.ProfileActivityPresenter {
    private IAccountContract.View.ProfileActivityView view;
    private IAccountContract.Model model;

    public ProfileActivityPresenter(IAccountContract.View.ProfileActivityView view) {
        this.view = view;
        model = new AccountModel();
    }

    @Override
    public void LoadProfile(String accountID) {
        model.LoadAccountWithId(accountID, (account, e) -> {
            if(e!=null)
                view.ExceptionCatch(e);
            else
                view.LoadProfile(account);
        });
    }

    @Override
    public void UploadImageClicked(Account account,Uri uri) {
        model.UploadAvatar(account, uri,e -> {
            if(e!=null)
                view.ExceptionCatch(e);
            else
                view.FinishUploadImage();
        });
    }
}
