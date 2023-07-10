package com.example.laptop_market.presenter.activities;

import android.content.Context;

import com.example.laptop_market.contracts.IAccountContract;
import com.example.laptop_market.model.account.AccountModel;
import com.example.laptop_market.view.activities.SavedPostActivity;
import com.example.laptop_market.view.adapters.PostSearchResult.PostSearchResult;

import java.util.ArrayList;

public class SavedPostActivityPresenter implements IAccountContract.Presenter.SavedPostActivityPresenter {
    private IAccountContract.View.SavedPostActivityView savedPostActivityView;
    private IAccountContract.Model accountModel;

    public SavedPostActivityPresenter() {

    }
    public SavedPostActivityPresenter(IAccountContract.View.SavedPostActivityView view, Context context) {
        this.savedPostActivityView = view;
        accountModel = new AccountModel(context);
    }

    @Override
    public void LoadSavedPosts(String accountID) {
        accountModel.LoadYourSavedPosts(accountID, new IAccountContract.Model.OnFinishLoadYourSavedPosts() {
            @Override
            public void OnFinishLoadYourSavedPosts(boolean isSuccess, ArrayList<PostSearchResult> postSearchResults, Exception error) {
                if (isSuccess) {
                    savedPostActivityView.DisplaySavedPosts(postSearchResults);
                } else {
                    error.printStackTrace();
                }
            }
        });
    }
}
