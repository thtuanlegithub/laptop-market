package com.example.laptop_market.presenter.activities;

import com.example.laptop_market.contracts.IAccountContract;
import com.example.laptop_market.model.account.Account;

public class AccountSettingActivityPresenter implements IAccountContract.Presenter.AccountSettingActivityPresenter {
    private IAccountContract.View.AccountSettingActivityView view;
    private IAccountContract.Model model;
    public AccountSettingActivityPresenter(IAccountContract.View.AccountSettingActivityView view)
    {
        this.view = view;
    }
    @Override
    public void LoadAccountSetting() {
        model.LoadAccountSetting((account, e) -> {
            if(e!=null)
            {
                view.Error(e);
            }
            else
                view.LoadProfileData(account);
        });
    }

    @Override
    public void UpdateProfileOnClick(Account account) {
        model.UpdateAccountInformation(account, e -> {
            if(e!=null)
                view.Error(e);
        });
    }
}
