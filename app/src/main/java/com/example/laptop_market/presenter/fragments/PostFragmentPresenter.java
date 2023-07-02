package com.example.laptop_market.presenter.fragments;

import android.content.Context;

import com.example.laptop_market.contracts.IAccountContract;
import com.example.laptop_market.model.account.AccountModel;

public class PostFragmentPresenter implements IAccountContract.Presenter.PostFragmentPresenter {
    private IAccountContract.View.PostFragmentView postFragmentView;
    private IAccountContract.Model accountModel;

    public PostFragmentPresenter(Context context, IAccountContract.View.PostFragmentView postFragmentView) {
        this.postFragmentView = postFragmentView;
        accountModel = new AccountModel(context);
    }

    @Override
    public void OnNewPostClickedEnable() {
        accountModel.CheckSignedInAccount(isLogin -> {
            if(!isLogin)
                postFragmentView.OnDisableNewPostButton();
        });
    }
}
