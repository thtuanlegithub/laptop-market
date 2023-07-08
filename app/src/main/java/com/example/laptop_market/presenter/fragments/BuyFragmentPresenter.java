package com.example.laptop_market.presenter.fragments;

import android.content.Context;

import com.example.laptop_market.contracts.IAccountContract;
import com.example.laptop_market.contracts.IOrderContract;
import com.example.laptop_market.model.account.AccountModel;

public class BuyFragmentPresenter implements IOrderContract.Presenter.BuyFragmentPresenter {
    private IOrderContract.View.BuyFragmentView buyFragmentView;
    private IAccountContract.Model accountModel;

    public BuyFragmentPresenter(){

    }

    public BuyFragmentPresenter(IOrderContract.View.BuyFragmentView view, Context context){
        this.buyFragmentView = view;
        accountModel = new AccountModel(context);
    }

    @Override
    public void LoadManageBuyOrder() {
        accountModel.CheckSignedInAccount(isLogin -> {
            if(!isLogin)
                buyFragmentView.DisplayRequireLoginView();
            else
                buyFragmentView.DisplayManageOrderView();
        });
    }
}
