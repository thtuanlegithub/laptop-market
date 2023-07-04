package com.example.laptop_market.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.laptop_market.R;
import com.example.laptop_market.view.adapters.SellFinishAdapter;
import com.example.laptop_market.view.adapters.SellOrder;

import java.util.ArrayList;
import java.util.List;


public class SellFinishFragment extends Fragment {
    private RecyclerView rcvSellFinish;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sell_finish, container, false);

        //Hiển thị các đơn bán - đang xử lý
        rcvSellFinish = view.findViewById(R.id.rcvSellFinish);
        GridLayoutManager gridLayoutManagerSellFinish = new GridLayoutManager(requireContext(),1);
        rcvSellFinish.setLayoutManager(gridLayoutManagerSellFinish);
        SellFinishAdapter sellFinishAdapter = new SellFinishAdapter(getListSellFinish());
        rcvSellFinish.setAdapter(sellFinishAdapter);

        return view;
    }

    private List<SellOrder> getListSellFinish(){
        List<SellOrder> listSellFinish = new ArrayList<>();
        listSellFinish.add(new SellOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listSellFinish.add(new SellOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listSellFinish.add(new SellOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listSellFinish.add(new SellOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listSellFinish.add(new SellOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listSellFinish.add(new SellOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listSellFinish.add(new SellOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        return  listSellFinish;
    }
}