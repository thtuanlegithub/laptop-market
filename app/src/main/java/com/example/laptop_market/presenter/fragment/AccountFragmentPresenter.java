package com.example.laptop_market.presenter.fragment;

import com.example.laptop_market.contracts.IAccountContract;
import com.example.laptop_market.model.Account.AccountModel;
import com.example.laptop_market.ultilities.PreferenceManager;

public class AccountFragmentPresenter implements IAccountContract.Presenter.AccountFragmentPresenter {

    private PreferenceManager preferenceManager = null;
    private IAccountContract.View.AccountFragmentView view = null;
    private IAccountContract.Model accountModel = null;

    public AccountFragmentPresenter(IAccountContract.View.AccountFragmentView view, PreferenceManager preferenceManager)
    {
        this.preferenceManager = preferenceManager;
        this.view = view;
        accountModel = new AccountModel(preferenceManager);
    }

    @Override
    public void LoadingAccount() {
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
