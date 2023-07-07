package com.example.laptop_market.presenter.fragments;

import android.content.Context;

import com.example.laptop_market.contracts.IAccountContract;
import com.example.laptop_market.contracts.IOrderContract;
import com.example.laptop_market.model.account.AccountModel;

public class SellFragmentPresenter implements IOrderContract.Presenter.SellFragmentPresenter {
    private IOrderContract.View.SellFragmentView sellFragmentView;
    private IAccountContract.Model accountModel;

    public SellFragmentPresenter(IOrderContract.View.SellFragmentView view, Context context) {
        this.sellFragmentView = view;
        accountModel = new AccountModel(context);
    }

    public SellFragmentPresenter (){

    }

    @Override
    public void LoadManageSellOrder() {
        accountModel.CheckSignedInAccount(isLogin -> {
            if(!isLogin)
                sellFragmentView.DisplayRequireLoginView();
            else
                sellFragmentView.DisplayManageOrderView();
        });
    }
}
