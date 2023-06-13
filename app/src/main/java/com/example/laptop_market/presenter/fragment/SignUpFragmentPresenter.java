package com.example.laptop_market.presenter.fragment;

import com.example.laptop_market.contracts.IAccountContract;
import com.example.laptop_market.model.Account.Account;
import com.example.laptop_market.model.Account.AccountModel;
import com.example.laptop_market.ultilities.PreferenceManager;

public class SignUpFragmentPresenter implements IAccountContract.Presenter.SignUpFragmentPresenter {

    private PreferenceManager preferenceManager = null;
    private IAccountContract.View.SignUpFragmentView view = null;
    private IAccountContract.Model accountModel = null;
    public static final int SIGNUP_SUCCESS = 1;
    public static final int SIGNUP_FAILED = 2;

    public SignUpFragmentPresenter(IAccountContract.View.SignUpFragmentView view, PreferenceManager preferenceManager) {
        this.preferenceManager = preferenceManager;
        this.view = view;
        accountModel = new AccountModel(preferenceManager);
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
