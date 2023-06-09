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
import com.example.laptop_market.presenter.fragments.SellFragmentPresenter;
import com.example.laptop_market.view.adapters.Sell.SellCancelAdapter;
import com.example.laptop_market.view.adapters.Sell.SellOrder;
import com.example.laptop_market.view.adapters.Sell.SellDeliveringAdapter;

import java.util.ArrayList;
import java.util.List;


public class SellDeliveringFragment extends Fragment implements IOrderContract.View.SellDeliveringFragmentView {
    private RecyclerView rcvSellDelivering;
    private IOrderContract.Presenter.SellFragmentPresenter sellFragmentPresenter;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout layoutNotSellDelivering;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        sellFragmentPresenter = new SellFragmentPresenter(this, getContext());
        View view = inflater.inflate(R.layout.fragment_sell_delivering, container, false);
        rcvSellDelivering = view.findViewById(R.id.rcvSellDelivering);
        layoutNotSellDelivering = view.findViewById(R.id.layoutNotSellDelivering);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayoutSellDelivering);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sellFragmentPresenter.LoadSellDeliveringOrder();
                layoutNotSellDelivering.setVisibility(View.GONE);
                rcvSellDelivering.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        //Hiển thị các đơn bán - đang xử lý
        progressBar = view.findViewById(R.id.progressBarSellDelivering);
        GridLayoutManager gridLayoutManagerSellDelivering = new GridLayoutManager(requireContext(),1);
        rcvSellDelivering.setLayoutManager(gridLayoutManagerSellDelivering);
        sellFragmentPresenter.LoadSellDeliveringOrder();
        return view;
    }

    @Override
    public void DisplaySellDeliveringOrder(ArrayList<SellOrder> orders) {
        if (orders == null || orders.size() == 0) {
            progressBar.setVisibility(View.GONE);
            layoutNotSellDelivering.setVisibility(View.VISIBLE);
            return;
        }
        SellDeliveringAdapter sellDeliveringAdapter = new SellDeliveringAdapter(orders);
        rcvSellDelivering.setAdapter(sellDeliveringAdapter);
        layoutNotSellDelivering.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        rcvSellDelivering.setVisibility(View.VISIBLE);
    }
}