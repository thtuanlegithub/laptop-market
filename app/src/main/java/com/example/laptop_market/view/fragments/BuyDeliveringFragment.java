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
import com.example.laptop_market.view.adapters.Buy.BuyCancelAdapter;
import com.example.laptop_market.view.adapters.Buy.BuyOrder;
import com.example.laptop_market.view.adapters.Buy.BuyDeliveringAdapter;

import java.util.ArrayList;
import java.util.List;


public class BuyDeliveringFragment extends Fragment implements IOrderContract.View.BuyDeliveringFragmentView{
    private RecyclerView rcvBuyDelivering;
    private IOrderContract.Presenter.BuyFragmentPresenter buyFragmentPresenter;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout layoutNotBuyDelivering;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        buyFragmentPresenter = new BuyFragmentPresenter(this, getContext());
        View view = inflater.inflate(R.layout.fragment_buy_delivering, container, false);

        //Hiển thị các đơn bán - đang xử lý
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayoutBuyDelivering);
        layoutNotBuyDelivering = view.findViewById(R.id.layoutNotBuyDelivering);
        rcvBuyDelivering = view.findViewById(R.id.rcvBuyDelivering);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                buyFragmentPresenter.LoadBuyDeliveringOrder();
                layoutNotBuyDelivering.setVisibility(View.GONE);
                rcvBuyDelivering.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        progressBar = view.findViewById(R.id.progressBarBuyDelivering);
        GridLayoutManager gridLayoutManagerBuyDelivering = new GridLayoutManager(requireContext(),1);
        rcvBuyDelivering.setLayoutManager(gridLayoutManagerBuyDelivering);
        buyFragmentPresenter.LoadBuyDeliveringOrder();
        return view;
    }

    @Override
    public void DisplayBuyDeliveringOrder(ArrayList<BuyOrder> orders) {
        if (orders == null || orders.size() == 0){
            progressBar.setVisibility(View.GONE);
            layoutNotBuyDelivering.setVisibility(View.VISIBLE);
            return;
        }
        BuyDeliveringAdapter BuyDeliveringAdapter = new BuyDeliveringAdapter(orders);
        rcvBuyDelivering.setAdapter(BuyDeliveringAdapter);
        layoutNotBuyDelivering.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        rcvBuyDelivering.setVisibility(View.VISIBLE);
    }
}