package com.example.laptop_market.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.laptop_market.R;
import com.example.laptop_market.contracts.IOrderContract;
import com.example.laptop_market.presenter.fragments.BuyFragmentPresenter;
import com.example.laptop_market.view.adapters.Buy.BuyFinishAdapter;
import com.example.laptop_market.view.adapters.Buy.BuyOrder;
import com.google.firestore.admin.v1.IndexOrBuilder;

import java.util.ArrayList;
import java.util.List;


public class BuyFinishFragment extends Fragment implements IOrderContract.View.BuyFinishFragmentView {
    private RecyclerView rcvBuyFinish;
    private IOrderContract.Presenter.BuyFragmentPresenter buyFragmentPresenter;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout layoutNotBuyFinish;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        buyFragmentPresenter = new BuyFragmentPresenter(this, getContext());
        View view = inflater.inflate(R.layout.fragment_buy_finish, container, false);

        //Hiển thị các đơn bán - đang xử lý
        rcvBuyFinish = view.findViewById(R.id.rcvBuyFinish);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayoutBuyFinish);
        layoutNotBuyFinish = view.findViewById(R.id.layoutNotBuyFinish);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                buyFragmentPresenter.LoadBuyFinishedOrder();
                layoutNotBuyFinish.setVisibility(View.GONE);
                rcvBuyFinish.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        progressBar = view.findViewById(R.id.progressBarBuyFinish);
        GridLayoutManager gridLayoutManagerBuyFinish = new GridLayoutManager(requireContext(),1);
        rcvBuyFinish.setLayoutManager(gridLayoutManagerBuyFinish);
        buyFragmentPresenter.LoadBuyFinishedOrder();
        return view;
    }
    @Override
    public void DisplayBuyFinishedOrder(ArrayList<BuyOrder> orders) {
        if (orders == null || orders.size() == 0){
            progressBar.setVisibility(View.GONE);
            layoutNotBuyFinish.setVisibility(View.VISIBLE);
            return;
        }
        BuyFinishAdapter BuyFinishAdapter = new BuyFinishAdapter(orders);
        rcvBuyFinish.setAdapter(BuyFinishAdapter);
        layoutNotBuyFinish.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        rcvBuyFinish.setVisibility(View.VISIBLE);
    }
}