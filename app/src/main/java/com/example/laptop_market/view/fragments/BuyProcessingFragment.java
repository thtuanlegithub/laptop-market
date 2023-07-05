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
import com.example.laptop_market.view.adapters.Buy.BuyProcessingAdapter;

import java.util.ArrayList;
import java.util.List;

public class BuyProcessingFragment extends Fragment {
    private RecyclerView rcvBuyProcessing;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buy_processing, container, false);

        //Hiển thị các đơn bán - đang xử lý
        rcvBuyProcessing = view.findViewById(R.id.rcvBuyProcessing);
        GridLayoutManager gridLayoutManagerBuyProcessing = new GridLayoutManager(requireContext(),1);
        rcvBuyProcessing.setLayoutManager(gridLayoutManagerBuyProcessing);
        BuyProcessingAdapter BuyProcessingAdapter = new BuyProcessingAdapter(getListBuyProcessing());
        rcvBuyProcessing.setAdapter(BuyProcessingAdapter);

        return view;
    }

    private List<BuyOrder> getListBuyProcessing(){
        List<BuyOrder> listBuyProcessing = new ArrayList<>();
        listBuyProcessing.add(new BuyOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listBuyProcessing.add(new BuyOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listBuyProcessing.add(new BuyOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listBuyProcessing.add(new BuyOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listBuyProcessing.add(new BuyOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listBuyProcessing.add(new BuyOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listBuyProcessing.add(new BuyOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        return  listBuyProcessing;
    }

}