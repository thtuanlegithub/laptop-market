package com.example.laptop_market.presenter.fragments;

import android.content.Context;

import com.example.laptop_market.contracts.IAccountContract;
import com.example.laptop_market.model.account.AccountModel;
import com.example.laptop_market.utils.PreferenceManager;

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
}
