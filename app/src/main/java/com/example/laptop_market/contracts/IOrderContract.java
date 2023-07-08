package com.example.laptop_market.contracts;

import com.example.laptop_market.model.order.Order;
import com.example.laptop_market.view.adapters.Buy.BuyOrder;

import java.util.ArrayList;

public interface IOrderContract {
    interface Model {
        void GetProcessingOrders (OnGetProcessingOrdersListener listener);
        interface OnGetProcessingOrdersListener {
            void OnFinishGetProcessingOrders (boolean isSuccess, ArrayList<BuyOrder> orders, Exception error);
        }
        void GetDeliveringOrders (OnGetDeliveringOrdersListener listener);
        interface OnGetDeliveringOrdersListener {
            void OnFinishGetDeliveringOrders (boolean isSuccess, ArrayList<BuyOrder> orders, Exception error);
        }
        void GetFinishedOrders (OnGetFinishedOrdersListener listener);
        interface OnGetFinishedOrdersListener {
            void OnGetFinishedOrdersOrders (boolean isSuccess, ArrayList<BuyOrder> orders, Exception error);
        }
        void GetCanceledOrders (OnGetCanceledOrdersListener listener);
        interface OnGetCanceledOrdersListener {
            void OnFinishGetCanceledOrders (boolean isSuccess, ArrayList<BuyOrder> orders, Exception error);
        }
        void CreateNewOrder (Order order, String sellerID, String buyerID, OnCreateOrderListener listener);
        interface OnCreateOrderListener {
            void OnFinishCreateOrder(boolean isSuccess, Exception error);
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
        }

        interface BuyOrderDetailActivityPresenter {
            void OnConfirmBuyingClicked(Order order, String sellerID, String buyerID);
        }
    }
}
