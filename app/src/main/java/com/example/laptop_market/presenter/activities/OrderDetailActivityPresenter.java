package com.example.laptop_market.presenter.activities;

import android.content.Context;

import com.example.laptop_market.contracts.IOrderContract;
import com.example.laptop_market.contracts.IPostContract;
import com.example.laptop_market.model.order.Order;
import com.example.laptop_market.model.order.OrderModel;
import com.example.laptop_market.model.post.Post;
import com.example.laptop_market.model.post.PostModel;
import com.example.laptop_market.view.activities.BuyOrderDetailActivity;
import com.example.laptop_market.view.activities.SellOrderDetailActivity;

public class OrderDetailActivityPresenter implements IOrderContract.Presenter.OrderDetailActivityPresenter {
    private IOrderContract.View.OrderDetailsActivityView orderDetailsActivityView;
    private IOrderContract.Model orderModel;
    private IPostContract.Model postModel;
    public OrderDetailActivityPresenter () {

    }

    public OrderDetailActivityPresenter (IOrderContract.View.OrderDetailsActivityView orderDetailsActivityView, Context context) {
        this.orderDetailsActivityView = orderDetailsActivityView;
        orderModel = new OrderModel(context);
        postModel = new PostModel(context);
    }

    @Override
    public void UpdateOrderInfo(String orderID, String changeOrderStatusTo, String postServiceName, String postServiceCode, String finishTime) {
        orderModel.UpdateOrderInfo(orderID, changeOrderStatusTo, postServiceName, postServiceCode, finishTime, new IOrderContract.Model.OnUpdateOrderInfo() {
            @Override
            public void OnFinishUpdateOrderInfo(boolean isSuccess, Exception error) {
                if (isSuccess) {
                    // Do nothing
                } else {
                    error.printStackTrace();
                }
            }
        });
    }

    @Override
    public void LoadOrderInfo(String orderID, String postID) {
        orderModel.LoadOrderDetails(orderID, postID, new IOrderContract.Model.OnLoadOrderDetailsListener() {
            @Override
            public void OnFinishLoadOrderDetails(boolean isSuccess, Order orderInfo, Post postInfo, Exception error) {
                if (isSuccess) {
                    orderDetailsActivityView.DisplayOrderDetailInformation(orderInfo, postInfo);
                } else {
                    error.printStackTrace();
                }
            }
        });
    }

    @Override
    public void UpdatePostStatus(String postID, String changePostStatusTo) {
        postModel.UpdatePostStatus(postID, changePostStatusTo, new IPostContract.Model.OnUpdatePostStatusListener() {
            @Override
            public void OnFinishUpdatePostStatus(boolean isSuccess, String currentStatus, Exception error) {
                if (isSuccess) {
                    orderDetailsActivityView.DisplayUpdatePostStatus(currentStatus);
                } else {
                    error.printStackTrace();
                }
            }
        });
    }
}
