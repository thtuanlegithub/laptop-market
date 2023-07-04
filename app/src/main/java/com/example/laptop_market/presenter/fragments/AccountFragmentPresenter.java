package com.example.laptop_market.presenter.fragments;

import android.content.Context;

import com.example.laptop_market.contracts.IAccountContract;
import com.example.laptop_market.model.account.AccountModel;
import com.example.laptop_market.utils.elses.PreferenceManager;

public class AccountFragmentPresenter implements IAccountContract.Presenter.AccountFragmentPresenter {

    private IAccountContract.View.AccountFragmentView view = null;
    private IAccountContract.Model accountModel = null;

    public AccountFragmentPresenter(IAccountContract.View.AccountFragmentView view, Context context)
    {
        this.view = view;
        accountModel = new AccountModel(context);
    }

    @Override
    public void LoadAccountStatus() {
        accountModel.LoadAccount((isSignIn, account) -> {
            if(isSignIn)
                view.LoadAccount(account);
            else
                view.LoadNotLoginAccount();
        });
    }

    @Override
    public void LogoutAccount() {
        accountModel.LogoutAccount(()->{
            view.LogoutAccount();
        });
    }

    @Override
    public void ClickSellOrderStatistic() {
        accountModel.CheckSignedInAccount(isLogin -> {
            if (isLogin)
                view.LoadSellOrder();
            else
                view.LoginAccount();
        });
    }

    @Override
    public void ClickBuyOrderStatistic() {
        accountModel.CheckSignedInAccount(isLogin -> {
            if (isLogin)
                view.LoadBuyOrder();
            else
                view.LoginAccount();
        });
    }

    @Override
    public void ClickSavedPost() {
        accountModel.CheckSignedInAccount(isLogin -> {
            if (isLogin)
                view.LoadSavedPost();
            else
                view.LoginAccount();
        });
    }

    @Override
    public void ClickYourRating() {
        accountModel.CheckSignedInAccount(isLogin -> {
            if (isLogin)
                view.LoadYourRating();
            else
                view.LoginAccount();
        });
    }

    @Override
    public void ClickAccountSettings() {
        accountModel.CheckSignedInAccount(isLogin -> {
            if (isLogin)
                view.LoadAccountSettings();
            else
                view.LoginAccount();
        });
    }
}
