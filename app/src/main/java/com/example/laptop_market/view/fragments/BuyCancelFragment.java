package com.example.laptop_market.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.laptop_market.R;
import com.example.laptop_market.view.adapters.Buy.BuyCancelAdapter;
import com.example.laptop_market.view.adapters.Buy.BuyOrder;

import java.util.ArrayList;
import java.util.List;


public class BuyCancelFragment extends Fragment {
    private RecyclerView rcvBuyCancel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buy_cancel, container, false);

        //Hiển thị các đơn bán - đang xử lý
        rcvBuyCancel = view.findViewById(R.id.rcvBuyCancel);
        GridLayoutManager gridLayoutManagerBuyCancel = new GridLayoutManager(requireContext(),1);
        rcvBuyCancel.setLayoutManager(gridLayoutManagerBuyCancel);
        BuyCancelAdapter BuyCancelAdapter = new BuyCancelAdapter(getListBuyCancel());
        rcvBuyCancel.setAdapter(BuyCancelAdapter);

        return view;
    }

    private List<BuyOrder> getListBuyCancel(){
        List<BuyOrder> listBuyCancel = new ArrayList<>();
        listBuyCancel.add(new BuyOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listBuyCancel.add(new BuyOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listBuyCancel.add(new BuyOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listBuyCancel.add(new BuyOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listBuyCancel.add(new BuyOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listBuyCancel.add(new BuyOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listBuyCancel.add(new BuyOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        return  listBuyCancel;
    }
}