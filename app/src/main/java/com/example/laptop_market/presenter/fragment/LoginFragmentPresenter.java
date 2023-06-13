package com.example.laptop_market.presenter.fragment;

import android.util.Patterns;

import com.example.laptop_market.contracts.IAccountContract;
import com.example.laptop_market.model.Account.Account;
import com.example.laptop_market.model.Account.AccountModel;
import com.example.laptop_market.ultilities.PreferenceManager;

public class LoginFragmentPresenter implements IAccountContract.Presenter.LoginFragmentPresenter {

    private PreferenceManager preferenceManager = null;
    private IAccountContract.View.LoginFragmentView view = null;
    private IAccountContract.Model accountModel = null;


    public LoginFragmentPresenter(IAccountContract.View.LoginFragmentView view, PreferenceManager preferenceManager) {
        this.preferenceManager = preferenceManager;
        this.view = view;
        accountModel = new AccountModel(preferenceManager);
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
