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
import com.example.laptop_market.contracts.IFragmentListener;
import com.example.laptop_market.contracts.IOrderContract;
import com.example.laptop_market.model.order.Order;
import com.example.laptop_market.presenter.fragments.BuyFragmentPresenter;
import com.example.laptop_market.view.adapters.Buy.BuyOrder;
import com.example.laptop_market.view.adapters.Buy.BuyProcessingAdapter;
import com.example.laptop_market.view.adapters.PostSearchResult.PostSearchResultAdapter;

import java.util.ArrayList;
import java.util.List;

public class BuyProcessingFragment extends Fragment implements IOrderContract.View.BuyProcessingFragmentView {
    private RecyclerView rcvBuyProcessing;
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
        View view = inflater.inflate(R.layout.fragment_buy_processing, container, false);
        buyFragmentPresenter = new BuyFragmentPresenter(this, getContext());
        //Hiển thị các đơn bán - đang xử lý
        progressBar = view.findViewById(R.id.progressBarBuyProcessing);
        rcvBuyProcessing = view.findViewById(R.id.rcvBuyProcessing);
        GridLayoutManager gridLayoutManagerBuyProcessing = new GridLayoutManager(requireContext(),1);
        rcvBuyProcessing.setLayoutManager(gridLayoutManagerBuyProcessing);
        buyFragmentPresenter.LoadBuyProcessingOrder();
        return view;
    }

    @Override
    public void DisplayBuyProcessingOrder(ArrayList<BuyOrder> orders) {
        if (orders == null){
            progressBar.setVisibility(View.GONE);
            return;
        }
        BuyProcessingAdapter BuyProcessingAdapter = new BuyProcessingAdapter(orders);
        rcvBuyProcessing.setAdapter(BuyProcessingAdapter);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}