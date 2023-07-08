package com.example.laptop_market.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.laptop_market.R;
import com.example.laptop_market.view.adapters.Buy.BuyOrder;
import com.example.laptop_market.view.adapters.Buy.BuyDeliveringAdapter;

import java.util.ArrayList;
import java.util.List;


public class BuyDeliveringFragment extends Fragment {
    private RecyclerView rcvBuyDelivering;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buy_delivering, container, false);

        //Hiển thị các đơn bán - đang xử lý
        rcvBuyDelivering = view.findViewById(R.id.rcvBuyDelivering);
        GridLayoutManager gridLayoutManagerBuyDelivering = new GridLayoutManager(requireContext(),1);
        rcvBuyDelivering.setLayoutManager(gridLayoutManagerBuyDelivering);
        BuyDeliveringAdapter BuyDeliveringAdapter = new BuyDeliveringAdapter(getListBuyDelivering());
        rcvBuyDelivering.setAdapter(BuyDeliveringAdapter);

        return view;
    }

    private List<BuyOrder> getListBuyDelivering(){
        return null;
    }

}