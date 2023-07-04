package com.example.laptop_market.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.laptop_market.R;
import com.example.laptop_market.view.adapters.SellOrder;
import com.example.laptop_market.view.adapters.SellDeliveringAdapter;

import java.util.ArrayList;
import java.util.List;


public class SellDeliveringFragment extends Fragment {
    private RecyclerView rcvSellDelivering;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sell_delivering, container, false);

        //Hiển thị các đơn bán - đang xử lý
        rcvSellDelivering = view.findViewById(R.id.rcvSellDelivering);
        GridLayoutManager gridLayoutManagerSellDelivering = new GridLayoutManager(requireContext(),1);
        rcvSellDelivering.setLayoutManager(gridLayoutManagerSellDelivering);
        SellDeliveringAdapter sellDeliveringAdapter = new SellDeliveringAdapter(getListSellDelivering());
        rcvSellDelivering.setAdapter(sellDeliveringAdapter);

        return view;
    }

    private List<SellOrder> getListSellDelivering(){
        List<SellOrder> listSellDelivering = new ArrayList<>();
        listSellDelivering.add(new SellOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listSellDelivering.add(new SellOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listSellDelivering.add(new SellOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listSellDelivering.add(new SellOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listSellDelivering.add(new SellOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listSellDelivering.add(new SellOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listSellDelivering.add(new SellOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        return  listSellDelivering;
    }

}