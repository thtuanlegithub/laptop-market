package com.example.laptop_market.presenter.fragments;

import android.content.Context;

import com.example.laptop_market.contracts.IAccountContract;
import com.example.laptop_market.contracts.IOrderContract;
import com.example.laptop_market.model.account.AccountModel;
import com.example.laptop_market.model.order.Order;
import com.example.laptop_market.model.order.OrderModel;
import com.example.laptop_market.view.adapters.Buy.BuyOrder;

import java.util.ArrayList;

public class BuyFragmentPresenter implements IOrderContract.Presenter.BuyFragmentPresenter {
    private IOrderContract.View.BuyFragmentView buyFragmentView;
    IOrderContract.View.BuyProcessingFragmentView buyProcessingFragmentView;
    IOrderContract.View.BuyDeliveringFragmentView buyDeliveringFragmentView;
    IOrderContract.View.BuyFinishFragmentView buyFinishFragmentView;
    IOrderContract.View.BuyCancelFragmentView buyCancelFragmentView;
    private IAccountContract.Model accountModel;
    private IOrderContract.Model orderModel;

    public BuyFragmentPresenter(){

    }
    public BuyFragmentPresenter(IOrderContract.View.BuyProcessingFragmentView view, Context context){
        this.buyProcessingFragmentView = view;
        accountModel = new AccountModel(context);
        orderModel = new OrderModel(context);
    }
    public BuyFragmentPresenter(IOrderContract.View.BuyDeliveringFragmentView view, Context context){
        this.buyDeliveringFragmentView = view;
        accountModel = new AccountModel(context);
        orderModel = new OrderModel(context);
    }
    public BuyFragmentPresenter(IOrderContract.View.BuyFinishFragmentView view, Context context){
        this.buyFinishFragmentView = view;
        accountModel = new AccountModel(context);
        orderModel = new OrderModel(context);
    }
    public BuyFragmentPresenter(IOrderContract.View.BuyCancelFragmentView view, Context context){
        this.buyCancelFragmentView = view;
        accountModel = new AccountModel(context);
        orderModel = new OrderModel(context);
    }

    public BuyFragmentPresenter(IOrderContract.View.BuyFragmentView view, Context context){
        this.buyFragmentView = view;
        accountModel = new AccountModel(context);
        orderModel = new OrderModel(context);
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

    @Override
    public void LoadBuyProcessingOrder() {
        orderModel.GetBuyProcessingOrders(new IOrderContract.Model.OnGetBuyProcessingOrdersListener() {
            @Override
            public void OnFinishGetBuyProcessingOrders(boolean isSuccess, ArrayList<BuyOrder> orders, Exception error) {
                if (isSuccess) {
                    buyProcessingFragmentView.DisplayBuyProcessingOrder(orders);
                } else {
                    error.printStackTrace();
                }
            }
        });
    }

    @Override
    public void LoadBuyDeliveringOrder() {
        orderModel.GetBuyDeliveringOrders(new IOrderContract.Model.OnGetBuyDeliveringOrdersListener() {
            @Override
            public void OnFinishGetBuyDeliveringOrders(boolean isSuccess, ArrayList<BuyOrder> orders, Exception error) {
                if (isSuccess) {
                    buyDeliveringFragmentView.DisplayBuyDeliveringOrder(orders);
                } else {
                    error.printStackTrace();
                }
            }
        });
    }

    @Override
    public void LoadBuyFinishedOrder() {
        orderModel.GetBuyFinishedOrders(new IOrderContract.Model.OnGetBuyFinishedOrdersListener() {
            @Override
            public void OnGetBuyFinishedOrdersOrders(boolean isSuccess, ArrayList<BuyOrder> orders, Exception error) {
                if (isSuccess) {
                    buyFinishFragmentView.DisplayBuyFinishedOrder(orders);
                } else {
                    error.printStackTrace();
                }
            }
        });
    }

    @Override
    public void LoadBuyCancelOrder() {
        orderModel.GetBuyCanceledOrders(new IOrderContract.Model.OnGetBuyCanceledOrdersListener() {
            @Override
            public void OnFinishGetBuyCanceledOrders(boolean isSuccess, ArrayList<BuyOrder> orders, Exception error) {
                if (isSuccess) {
                    buyCancelFragmentView.DisplayCancelFinishedOrder(orders);
                } else {
                    error.printStackTrace();
                }
            }
        });
    }
}
