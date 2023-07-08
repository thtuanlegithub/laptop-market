package com.example.laptop_market.presenter.activities;

import android.content.Context;

import com.example.laptop_market.contracts.IOrderContract;
import com.example.laptop_market.model.order.Order;
import com.example.laptop_market.model.order.OrderModel;

public class BuyOrderDetailActivityPresenter implements IOrderContract.Presenter.BuyOrderDetailActivityPresenter {
    private IOrderContract.View.BuyOrderDetailActivityView buyOrderDetailActivityView;
    private IOrderContract.Model orderModel;

    public BuyOrderDetailActivityPresenter () {

    }
    public BuyOrderDetailActivityPresenter (IOrderContract.View.BuyOrderDetailActivityView view, Context context) {
        this.buyOrderDetailActivityView = view;
        this.orderModel = new OrderModel(context);
    }

    @Override
    public void OnConfirmBuyingClicked(Order order, String sellerID, String buyerID) {
        // Model sẽ thực hiện query các kiểu + lưu data
        // Sau đó thì
        orderModel.CreateNewOrder(order, sellerID, buyerID, new IOrderContract.Model.OnCreateOrderListener() {
            @Override
            public void OnFinishCreateOrder(boolean isSuccess, Exception error) {
                if (isSuccess) {
                    buyOrderDetailActivityView.DisplayBuySucessful();
                }
                else {
                    error.printStackTrace();
                }
            }
        });
    }
}
