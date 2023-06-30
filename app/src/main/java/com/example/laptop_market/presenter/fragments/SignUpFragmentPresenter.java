package com.example.laptop_market.presenter.fragments;

import android.content.Context;

import com.example.laptop_market.contracts.IAccountContract;
import com.example.laptop_market.model.account.Account;
import com.example.laptop_market.model.account.AccountModel;
import com.example.laptop_market.utils.PreferenceManager;

public class SignUpFragmentPresenter implements IAccountContract.Presenter.SignUpFragmentPresenter {

    private IAccountContract.View.SignUpFragmentView view = null;
    private IAccountContract.Model accountModel = null;
    public static final int SIGNUP_SUCCESS = 1;
    public static final int SIGNUP_FAILED = 2;

    public SignUpFragmentPresenter(IAccountContract.View.SignUpFragmentView view, Context context) {
        this.view = view;
        accountModel = new AccountModel(context);
    }

    @Override
    public void createAccountClicked(Account account) {
        accountModel.CreateAccount(account,(type, message) -> {
            if(type == SIGNUP_SUCCESS)
            {
                view.RegisterSuccess();
            }
            else
                view.ShowError(message);
        });
    }
}
