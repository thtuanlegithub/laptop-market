package com.example.laptop_market.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.laptop_market.R;
import com.example.laptop_market.view.adapters.SellCancelAdapter;
import com.example.laptop_market.view.adapters.SellOrder;

import java.util.ArrayList;
import java.util.List;


public class SellCancelFragment extends Fragment {
    private RecyclerView rcvSellCancel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sell_cancel, container, false);

        //Hiển thị các đơn bán - đang xử lý
        rcvSellCancel = view.findViewById(R.id.rcvSellCancel);
        GridLayoutManager gridLayoutManagerSellCancel = new GridLayoutManager(requireContext(),1);
        rcvSellCancel.setLayoutManager(gridLayoutManagerSellCancel);
        SellCancelAdapter sellCancelAdapter = new SellCancelAdapter(getListSellCancel());
        rcvSellCancel.setAdapter(sellCancelAdapter);

        return view;
    }

    private List<SellOrder> getListSellCancel(){
        List<SellOrder> listSellCancel = new ArrayList<>();
        listSellCancel.add(new SellOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listSellCancel.add(new SellOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listSellCancel.add(new SellOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listSellCancel.add(new SellOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listSellCancel.add(new SellOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listSellCancel.add(new SellOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listSellCancel.add(new SellOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        return  listSellCancel;
    }
}