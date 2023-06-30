package com.example.laptop_market.presenter.fragments;

import android.content.Context;

import com.example.laptop_market.contracts.IAccountContract;
import com.example.laptop_market.model.account.Account;
import com.example.laptop_market.model.account.AccountModel;
import com.example.laptop_market.utils.PreferenceManager;

public class LoginFragmentPresenter implements IAccountContract.Presenter.LoginFragmentPresenter {

    private IAccountContract.View.LoginFragmentView view = null;
    private IAccountContract.Model accountModel = null;


    public LoginFragmentPresenter(IAccountContract.View.LoginFragmentView view, Context context) {
        this.view = view;
        accountModel = new AccountModel(context);
    }
    @Override
    public void LoginButtonClicked(Account account) {
        accountModel.Login(account, (isSuccess, message) -> {
            if (isSuccess)
                view.LoginSuccess();
            else
                view.LoginFailed(message);
        });

    }
}
