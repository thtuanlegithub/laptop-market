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
import com.example.laptop_market.presenter.fragments.SellFragmentPresenter;
import com.example.laptop_market.view.adapters.Sell.SellCancelAdapter;
import com.example.laptop_market.view.adapters.Sell.SellOrder;
import com.google.firestore.admin.v1.IndexOrBuilder;

import java.util.ArrayList;
import java.util.List;


public class SellCancelFragment extends Fragment implements IOrderContract.View.SellCancelFragmentView{
    private RecyclerView rcvSellCancel;
    private IOrderContract.Presenter.SellFragmentPresenter sellFragmentPresenter;
    private ProgressBar progressBar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        sellFragmentPresenter = new SellFragmentPresenter(this, getContext());
        View view = inflater.inflate(R.layout.fragment_sell_cancel, container, false);

        //Hiển thị các đơn bán - đang xử lý
        progressBar = view.findViewById(R.id.progressBarSellCancel);
        rcvSellCancel = view.findViewById(R.id.rcvSellCancel);
        GridLayoutManager gridLayoutManagerSellCancel = new GridLayoutManager(requireContext(),1);
        rcvSellCancel.setLayoutManager(gridLayoutManagerSellCancel);
        sellFragmentPresenter.LoadSellCancelOrder();
        return view;
    }

    @Override
    public void DisplaySellFinishedOrder(ArrayList<SellOrder> orders) {
        if (orders == null)
            orders = new ArrayList<>();
        SellCancelAdapter sellCancelAdapter = new SellCancelAdapter(orders);
        rcvSellCancel.setAdapter(sellCancelAdapter);
        progressBar.setVisibility(View.GONE);
    }
}