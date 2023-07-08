package com.example.laptop_market.contracts;

import com.example.laptop_market.model.order.Order;

public interface IOrderContract {
    interface Model {
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
    }

    interface Presenter{
        interface BuyFragmentPresenter {
            void LoadManageBuyOrder();
        }
        interface SellFragmentPresenter {
            void LoadManageSellOrder();
        }

        interface BuyOrderDetailActivityPresenter {
            void OnConfirmBuyingClicked(Order order, String sellerID, String buyerID);
        }
    }
}
