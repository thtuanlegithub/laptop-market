package com.example.laptop_market.contracts;

import com.example.laptop_market.model.order.Order;
import com.example.laptop_market.model.post.Post;
import com.example.laptop_market.view.adapters.Buy.BuyOrder;
import com.example.laptop_market.view.adapters.Sell.SellOrder;

import java.util.ArrayList;

public interface IOrderContract {
    interface Model {
        // region Buy order
        void GetBuyProcessingOrders (OnGetBuyProcessingOrdersListener listener);
        interface OnGetBuyProcessingOrdersListener {
            void OnFinishGetBuyProcessingOrders (boolean isSuccess, ArrayList<BuyOrder> orders, Exception error);
        }
        void GetBuyDeliveringOrders (OnGetBuyDeliveringOrdersListener listener);
        interface OnGetBuyDeliveringOrdersListener {
            void OnFinishGetBuyDeliveringOrders (boolean isSuccess, ArrayList<BuyOrder> orders, Exception error);
        }
        void GetBuyFinishedOrders (OnGetBuyFinishedOrdersListener listener);
        interface OnGetBuyFinishedOrdersListener {
            void OnGetBuyFinishedOrdersOrders (boolean isSuccess, ArrayList<BuyOrder> orders, Exception error);
        }
        void GetBuyCanceledOrders (OnGetBuyCanceledOrdersListener listener);
        interface OnGetBuyCanceledOrdersListener {
            void OnFinishGetBuyCanceledOrders (boolean isSuccess, ArrayList<BuyOrder> orders, Exception error);
        }
        // endregion

        // region Sell order
        void GetSellProcessingOrders (OnGetSellProcessingOrdersListener listener);
        interface OnGetSellProcessingOrdersListener {
            void OnFinishGetSellProcessingOrders (boolean isSuccess, ArrayList<SellOrder> orders, Exception error);
        }
        void GetSellDeliveringOrders (OnGetSellDeliveringOrdersListener listener);
        interface OnGetSellDeliveringOrdersListener {
            void OnFinishGetSellDeliveringOrders (boolean isSuccess, ArrayList<SellOrder> orders, Exception error);
        }
        void GetSellFinishedOrders (OnGetSellFinishedOrdersListener listener);
        interface OnGetSellFinishedOrdersListener {
            void OnGetSellFinishedOrders (boolean isSuccess, ArrayList<SellOrder> orders, Exception error);
        }
        void GetSellCanceledOrders (OnGetSellCanceledOrdersListener listener);
        interface OnGetSellCanceledOrdersListener {
            void OnFinishGetSellCanceledOrders (boolean isSuccess, ArrayList<SellOrder> orders, Exception error);
        }
        // endregion
        void CreateNewOrder (Order order, String sellerID, String buyerID, OnCreateOrderListener listener);
        interface OnCreateOrderListener {
            void OnFinishCreateOrder(boolean isSuccess, Exception error);
        }
        void LoadOrderDetails(String OrderID, String PostID, OnLoadOrderDetailsListener listener);
        interface  OnLoadOrderDetailsListener {
            void OnFinishLoadOrderDetails(boolean isSuccess, Order orderInfo, Post postInfo, Exception error);
        }

        void UpdateOrderInfo(String OrderID, String changeOrderStatusTo, String postServiceName, String postServiceCode, String finishedTime, OnUpdateOrderInfo listener);
        interface OnUpdateOrderInfo {
            void OnFinishUpdateOrderInfo(boolean isSuccess, Exception error);
        }
    }
    interface View {
        interface BuyFragmentView {
            void DisplayRequireLoginView();
            void DisplayManageOrderView();
        }
        interface SellFragmentView {
            void DisplayRequireLoginView();
            void DisplayManageOrderView();
        }
        interface BuyOrderDetailActivityView {
            void DisplayBuySucessful();
        }
        interface OrderDetailsActivityView {
            void DisplayOrderDetailInformation(Order orderInfo, Post postInfo);
            void DisplayUpdatePostStatus(String currentStatus);
        }
        interface BuyProcessingFragmentView {
            void DisplayBuyProcessingOrder(ArrayList<BuyOrder> orders);
        }
        interface BuyDeliveringFragmentView {
            void DisplayBuyDeliveringOrder(ArrayList<BuyOrder> orders);
        }
        interface BuyFinishFragmentView {
            void DisplayBuyFinishedOrder(ArrayList<BuyOrder> orders);
        }
        interface BuyCancelFragmentView {
            void DisplayCancelFinishedOrder(ArrayList<BuyOrder> orders);
        }
        interface SellProcessingFragmentView {
            void DisplaySellProcessingOrder(ArrayList<SellOrder> orders);
        }
        interface SellDeliveringFragmentView {
            void DisplaySellDeliveringOrder(ArrayList<SellOrder> orders);
        }
        interface SellFinishFragmentView {
            void DisplaySellFinishedOrder(ArrayList<SellOrder> orders);
        }
        interface SellCancelFragmentView {
            void DisplaySellFinishedOrder(ArrayList<SellOrder> orders);
        }
    }

    interface Presenter{
        interface BuyFragmentPresenter {
            void LoadManageBuyOrder();
            void LoadBuyProcessingOrder();
            void LoadBuyDeliveringOrder();
            void LoadBuyFinishedOrder();
            void LoadBuyCancelOrder();
        }
        interface SellFragmentPresenter {
            void LoadManageSellOrder();
            void LoadSellProcessingOrder();
            void LoadSellDeliveringOrder();
            void LoadSellFinishedOrder();
            void LoadSellCancelOrder();
        }

        interface BuyOrderDetailActivityPresenter {
            void OnConfirmBuyingClicked(Order order, String sellerID, String buyerID);
        }
        interface OrderDetailActivityPresenter {
            void UpdateOrderInfo(String orderID, String changeOrderStatusTo, String postServiceName, String postServiceCode, String finishedTime);
            void LoadOrderInfo(String orderID, String postID);
            void UpdatePostStatus(String postID, String changePostStatusTo);
        }
    }
}
