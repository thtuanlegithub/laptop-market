package com.example.laptop_market.presenter.activities;

import android.content.Context;

import com.example.laptop_market.contracts.IAccountContract;
import com.example.laptop_market.model.account.AccountModel;

public class StatisticActivityPresenter implements IAccountContract.Presenter.StatisticActivityPresenter {
    private IAccountContract.View.StatisticActivityView statisticActivityView;
    private IAccountContract.Model accountModel;

    public StatisticActivityPresenter() {

    }

    public StatisticActivityPresenter(IAccountContract.View.StatisticActivityView view, Context context) {
        this.statisticActivityView = view;
        accountModel = new AccountModel(context);
    }

    @Override
    public void LoadStatisticInformation(String accountID, boolean isBuy) {
        accountModel.LoadStatisticInformation(accountID, isBuy, new IAccountContract.Model.OnFinishLoadStatisticInformation() {
            @Override
            public void OnFinishLoadStatistic(boolean isSuccess, int NoOrders, double revenue, Exception error) {
                if (isSuccess) {
                    statisticActivityView.DisplayStatistic(NoOrders, revenue);
                }
                else {
                    error.printStackTrace();
                }
            }
        });
    }
}
