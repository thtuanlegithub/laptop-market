package com.example.laptop_market.presenter.fragments;

import android.content.Context;

import com.example.laptop_market.contracts.IAccountContract;
import com.example.laptop_market.contracts.IPostContract;
import com.example.laptop_market.model.account.AccountModel;

public class PostFragmentPresenter implements IPostContract.Presenter.PostFragmentPresenter {
    private IPostContract.View.PostFragmentView postFragmentView;
    private IAccountContract.Model accountModel;

    public PostFragmentPresenter(Context context, IPostContract.View.PostFragmentView postFragmentView) {
        this.postFragmentView = postFragmentView;
        accountModel = new AccountModel(context);
    }

    @Override
    public void CreateNewPost() {
        accountModel.CheckSignedInAccount(isLogin -> {
            if(isLogin)
                postFragmentView.CreateNewPost();
            else
                postFragmentView.LoginAccount();
        });
    }
}
