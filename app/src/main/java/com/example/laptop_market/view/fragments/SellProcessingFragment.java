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
import com.example.laptop_market.view.adapters.Sell.SellOrder;
import com.example.laptop_market.view.adapters.Sell.SellProcessingAdapter;

import java.util.ArrayList;
import java.util.List;

public class SellProcessingFragment extends Fragment implements IOrderContract.View.SellProcessingFragmentView {
    private RecyclerView rcvSellProcessing;
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
        View view = inflater.inflate(R.layout.fragment_sell_processing, container, false);

        //Hiển thị các đơn bán - đang xử lý
        rcvSellProcessing = view.findViewById(R.id.rcvSellProcessing);
        GridLayoutManager gridLayoutManagerSellProcessing = new GridLayoutManager(requireContext(),1);
        rcvSellProcessing.setLayoutManager(gridLayoutManagerSellProcessing);
        sellFragmentPresenter.LoadSellProcessingOrder();
        return view;
    }

    @Override
    public void DisplaySellProcessingOrder(ArrayList<SellOrder> orders) {
        SellProcessingAdapter sellProcessingAdapter = new SellProcessingAdapter(orders);
        rcvSellProcessing.setAdapter(sellProcessingAdapter);
    }
}