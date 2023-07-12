package com.example.laptop_market.presenter.fragments;

import com.example.laptop_market.contracts.IAccountContract;
import com.example.laptop_market.model.account.AccountModel;

public class ForgotPasswordFragmentPresenter implements IAccountContract.Presenter.ForgotPasswordFragmentPresenter {
    private IAccountContract.View.ForgotPasswordFragmentView view;
    private IAccountContract.Model accountModel;
    public ForgotPasswordFragmentPresenter(IAccountContract.View.ForgotPasswordFragmentView view)
    {
        this.view = view;
        accountModel = new AccountModel();
    }
    @Override
    public void SendEmailForgot(String email) {
        accountModel.SendEmailForgotPassword(email,isSuccess -> {
            if(isSuccess)
                view.SendEmailForgotPasswordSuccess();
            else
                view.SendEmailForgotPasswordFailed();
        });
    }
}
