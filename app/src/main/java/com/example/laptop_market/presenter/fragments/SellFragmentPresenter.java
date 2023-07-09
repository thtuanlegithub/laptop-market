package com.example.laptop_market.presenter.fragments;

import android.content.Context;

import com.example.laptop_market.contracts.IAccountContract;
import com.example.laptop_market.contracts.IOrderContract;
import com.example.laptop_market.model.account.AccountModel;
import com.example.laptop_market.model.order.OrderModel;
import com.example.laptop_market.view.adapters.Buy.BuyOrder;
import com.example.laptop_market.view.adapters.Sell.SellOrder;

import java.util.ArrayList;

public class SellFragmentPresenter implements IOrderContract.Presenter.SellFragmentPresenter {
    private IOrderContract.View.SellFragmentView sellFragmentView;
    private IOrderContract.View.SellProcessingFragmentView sellProcessingFragmentView;
    private IOrderContract.View.SellDeliveringFragmentView sellDeliveringFragmentView;
    private IOrderContract.View.SellFinishFragmentView sellFinishFragmentView;
    private IOrderContract.View.SellCancelFragmentView sellCancelFragmentView;
    private IAccountContract.Model accountModel;
    private IOrderContract.Model orderModel;

    public SellFragmentPresenter(IOrderContract.View.SellFragmentView view, Context context) {
        this.sellFragmentView = view;
        accountModel = new AccountModel(context);
        orderModel = new OrderModel(context);
    }
    public SellFragmentPresenter(IOrderContract.View.SellProcessingFragmentView view, Context context) {
        this.sellProcessingFragmentView = view;
        accountModel = new AccountModel(context);
        orderModel = new OrderModel(context);
    }
    public SellFragmentPresenter(IOrderContract.View.SellDeliveringFragmentView view, Context context) {
        this.sellDeliveringFragmentView = view;
        accountModel = new AccountModel(context);
        orderModel = new OrderModel(context);
    }
    public SellFragmentPresenter(IOrderContract.View.SellFinishFragmentView view, Context context) {
        this.sellFinishFragmentView = view;
        accountModel = new AccountModel(context);
        orderModel = new OrderModel(context);
    }
    public SellFragmentPresenter(IOrderContract.View.SellCancelFragmentView view, Context context) {
        this.sellCancelFragmentView = view;
        accountModel = new AccountModel(context);
        orderModel = new OrderModel(context);
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

    @Override
    public void LoadSellProcessingOrder() {
        orderModel.GetSellProcessingOrders(new IOrderContract.Model.OnGetSellProcessingOrdersListener() {
            @Override
            public void OnFinishGetSellProcessingOrders(boolean isSuccess, ArrayList<SellOrder> orders, Exception error) {
                if (isSuccess) {
                    sellProcessingFragmentView.DisplaySellProcessingOrder(orders);
                } else {
                    error.printStackTrace();
                }
            }
        });
    }

    @Override
    public void LoadSellDeliveringOrder() {
        orderModel.GetSellDeliveringOrders(new IOrderContract.Model.OnGetSellDeliveringOrdersListener() {
            @Override
            public void OnFinishGetSellDeliveringOrders(boolean isSuccess, ArrayList<SellOrder> orders, Exception error) {
                if (isSuccess)
                    sellDeliveringFragmentView.DisplaySellDeliveringOrder(orders);
                else
                    error.printStackTrace();
            }
        });
    }

    @Override
    public void LoadSellFinishedOrder() {
        orderModel.GetSellFinishedOrders(new IOrderContract.Model.OnGetSellFinishedOrdersListener() {
            @Override
            public void OnGetSellFinishedOrders(boolean isSuccess, ArrayList<SellOrder> orders, Exception error) {
                if (isSuccess)
                    sellFinishFragmentView.DisplaySellFinishedOrder(orders);
                else
                    error.printStackTrace();
            }
        });
    }

    @Override
    public void LoadSellCancelOrder() {
        orderModel.GetSellCanceledOrders(new IOrderContract.Model.OnGetSellCanceledOrdersListener() {
            @Override
            public void OnFinishGetSellCanceledOrders(boolean isSuccess, ArrayList<SellOrder> orders, Exception error) {
                if (isSuccess)
                    sellCancelFragmentView.DisplaySellFinishedOrder(orders);
                else
                    error.printStackTrace();
            }
        });
    }
}
