package com.example.laptop_market.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        //Hiển thị các đơn bán - đang xử lý
        rcvSellDelivering = view.findViewById(R.id.rcvSellDelivering);
        GridLayoutManager gridLayoutManagerSellDelivering = new GridLayoutManager(requireContext(),1);
        rcvSellDelivering.setLayoutManager(gridLayoutManagerSellDelivering);
        sellFragmentPresenter.LoadSellDeliveringOrder();
        return view;
    }

    @Override
    public void DisplaySellDeliveringOrder(ArrayList<SellOrder> orders) {
        SellDeliveringAdapter sellDeliveringAdapter = new SellDeliveringAdapter(orders);
        rcvSellDelivering.setAdapter(sellDeliveringAdapter);
    }
}