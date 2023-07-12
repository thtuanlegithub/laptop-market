package com.example.laptop_market.presenter.fragments;

import com.example.laptop_market.contracts.IAccountContract;
import com.example.laptop_market.model.account.AccountModel;

public class AuthenticationFragmentPresenter implements IAccountContract.Presenter.AuthenticationFragmentPresenter {
    private IAccountContract.Model accountModel;
    private IAccountContract.View.AuthenticationFragmentView view;
    public AuthenticationFragmentPresenter(IAccountContract.View.AuthenticationFragmentView view)
    {
        this.view = view;
        accountModel = new AccountModel();
    }
    @Override
    public void SendEmail() {
        accountModel.SendEmailVerified(()->{
            view.sendEmailSuccess();
        });
    }

    @Override
    public void CheckVerifiedEmail() {
        accountModel.GetEmailVerified(()->{
            view.CheckVerifiedEmailSuccess();
        });
    }
}
