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
import com.example.laptop_market.view.adapters.SellProcessingAdapter;

import java.util.ArrayList;
import java.util.List;

public class SellProcessingFragment extends Fragment {
    private RecyclerView rcvSellProcessing;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sell_processing, container, false);

        //Hiển thị các đơn bán - đang xử lý
        rcvSellProcessing = view.findViewById(R.id.rcvSellProcessing);
        GridLayoutManager gridLayoutManagerSellProcessing = new GridLayoutManager(requireContext(),1);
        rcvSellProcessing.setLayoutManager(gridLayoutManagerSellProcessing);
        SellProcessingAdapter sellProcessingAdapter = new SellProcessingAdapter(getListSellProcessing());
        rcvSellProcessing.setAdapter(sellProcessingAdapter);

        return view;
    }

    private List<SellOrder> getListSellProcessing(){
        List<SellOrder> listSellProcessing = new ArrayList<>();
        listSellProcessing.add(new SellOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listSellProcessing.add(new SellOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listSellProcessing.add(new SellOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listSellProcessing.add(new SellOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listSellProcessing.add(new SellOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listSellProcessing.add(new SellOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listSellProcessing.add(new SellOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        return  listSellProcessing;
    }

}