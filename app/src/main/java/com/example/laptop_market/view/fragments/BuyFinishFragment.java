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
import com.example.laptop_market.view.adapters.Buy.BuyFinishAdapter;
import com.example.laptop_market.view.adapters.Buy.BuyOrder;
import com.google.firestore.admin.v1.IndexOrBuilder;

import java.util.ArrayList;
import java.util.List;


public class BuyFinishFragment extends Fragment implements IOrderContract.View.BuyFinishFragmentView {
    private RecyclerView rcvBuyFinish;
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
        View view = inflater.inflate(R.layout.fragment_buy_finish, container, false);

        //Hiển thị các đơn bán - đang xử lý
        progressBar = view.findViewById(R.id.progressBarBuyFinish);
        rcvBuyFinish = view.findViewById(R.id.rcvBuyFinish);
        GridLayoutManager gridLayoutManagerBuyFinish = new GridLayoutManager(requireContext(),1);
        rcvBuyFinish.setLayoutManager(gridLayoutManagerBuyFinish);
        buyFragmentPresenter.LoadBuyFinishedOrder();
        return view;
    }
    @Override
    public void DisplayBuyFinishedOrder(ArrayList<BuyOrder> orders) {
        if (orders == null)
            orders = new ArrayList<>();
        BuyFinishAdapter BuyFinishAdapter = new BuyFinishAdapter(orders);
        rcvBuyFinish.setAdapter(BuyFinishAdapter);
        progressBar.setVisibility(View.GONE);
    }
}