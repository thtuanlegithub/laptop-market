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
import com.example.laptop_market.view.adapters.Buy.BuyProcessingAdapter;

import java.util.ArrayList;
import java.util.List;


public class BuyCancelFragment extends Fragment implements IOrderContract.View.BuyCancelFragmentView {
    private RecyclerView rcvBuyCancel;
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
        View view = inflater.inflate(R.layout.fragment_buy_cancel, container, false);

        //Hiển thị các đơn bán - đang xử lý
        progressBar = view.findViewById(R.id.progressBarBuyCancel);
        rcvBuyCancel = view.findViewById(R.id.rcvBuyCancel);
        GridLayoutManager gridLayoutManagerBuyCancel = new GridLayoutManager(requireContext(),1);
        rcvBuyCancel.setLayoutManager(gridLayoutManagerBuyCancel);
        buyFragmentPresenter.LoadBuyCancelOrder();
        return view;
    }

    @Override
    public void DisplayCancelFinishedOrder(ArrayList<BuyOrder> orders) {
        if (orders == null)
            orders = new ArrayList<>();
        BuyCancelAdapter buyCancelAdapter = new BuyCancelAdapter(orders);
        rcvBuyCancel.setAdapter(buyCancelAdapter);
        progressBar.setVisibility(View.GONE);
    }
}