package com.example.laptop_market.presenter.activities;

import com.example.laptop_market.contracts.IAccountContract;
import com.example.laptop_market.model.account.AccountModel;

public class AccountPasswordActivityPresenter implements IAccountContract.Presenter.AccountPasswordActivityPresenter {
    private IAccountContract.View.AccountPasswordActivityView view;
    private IAccountContract.Model model;

    public AccountPasswordActivityPresenter(IAccountContract.View.AccountPasswordActivityView view) {
        this.view = view;
        model = new AccountModel();
    }

    @Override
    public void UpdatePassword(String oldPassword, String newPassword) {
        model.UpdateAccountPassword(oldPassword,newPassword, (isSuccess, e) -> {
            if(e!=null)
                e.printStackTrace();
            else{
                if(isSuccess)
                    view.UpdatePasswordSuccess();
                else
                    view.WrongOldPassword("Sai mật khẩu cũ");
            }
        });
    }
}
