package com.example.laptop_market.contracts;

public interface IOrderContract {
    interface Model {

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
    }

    interface Presenter{
        interface BuyFragmentPresenter {
            void LoadManageBuyOrder();
        }
        interface SellFragmentPresenter {
            void LoadManageSellOrder();
        }
    }
}
