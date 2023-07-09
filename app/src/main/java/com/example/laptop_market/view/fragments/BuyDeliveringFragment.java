package com.example.laptop_market.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        progressBar = view.findViewById(R.id.progressBarBuyDelivering);
        rcvBuyDelivering = view.findViewById(R.id.rcvBuyDelivering);
        GridLayoutManager gridLayoutManagerBuyDelivering = new GridLayoutManager(requireContext(),1);
        rcvBuyDelivering.setLayoutManager(gridLayoutManagerBuyDelivering);
        buyFragmentPresenter.LoadBuyDeliveringOrder();
        return view;
    }

    @Override
    public void DisplayBuyDeliveringOrder(ArrayList<BuyOrder> orders) {
        if (orders == null){
            progressBar.setVisibility(View.GONE);
            return;
        }
        BuyDeliveringAdapter BuyDeliveringAdapter = new BuyDeliveringAdapter(orders);
        rcvBuyDelivering.setAdapter(BuyDeliveringAdapter);
        progressBar.setVisibility(View.GONE);
    }
}