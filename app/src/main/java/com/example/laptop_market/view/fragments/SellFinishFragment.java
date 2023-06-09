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
import com.example.laptop_market.view.adapters.Sell.SellFinishAdapter;
import com.example.laptop_market.view.adapters.Sell.SellOrder;

import java.util.ArrayList;
import java.util.List;


public class SellFinishFragment extends Fragment implements IOrderContract.View.SellFinishFragmentView{
    private RecyclerView rcvSellFinish;
    private IOrderContract.Presenter.SellFragmentPresenter sellFragmentPresenter;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout layoutNotSellFinish;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        sellFragmentPresenter = new SellFragmentPresenter(this, getContext());
        View view = inflater.inflate(R.layout.fragment_sell_finish, container, false);
        rcvSellFinish = view.findViewById(R.id.rcvSellFinish);
        layoutNotSellFinish = view.findViewById(R.id.layoutNotSellFinish);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayoutSellFinish);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sellFragmentPresenter.LoadSellFinishedOrder();
                layoutNotSellFinish.setVisibility(View.GONE);
                rcvSellFinish.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        //Hiển thị các đơn bán - đang xử lý
        progressBar = view.findViewById(R.id.progressBarSellFinish);
        GridLayoutManager gridLayoutManagerSellFinish = new GridLayoutManager(requireContext(),1);
        rcvSellFinish.setLayoutManager(gridLayoutManagerSellFinish);
        sellFragmentPresenter.LoadSellFinishedOrder();
        return view;
    }

    @Override
    public void DisplaySellFinishedOrder(ArrayList<SellOrder> orders) {
        if (orders == null || orders.size() == 0){
            progressBar.setVisibility(View.GONE);
            layoutNotSellFinish.setVisibility(View.VISIBLE);
            return;
        }
        SellFinishAdapter sellFinishAdapter = new SellFinishAdapter(orders);
        rcvSellFinish.setAdapter(sellFinishAdapter);
        layoutNotSellFinish.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        rcvSellFinish.setVisibility(View.VISIBLE);
    }
}