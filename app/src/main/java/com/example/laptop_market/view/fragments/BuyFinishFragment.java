package com.example.laptop_market.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.laptop_market.R;
import com.example.laptop_market.view.adapters.Buy.BuyFinishAdapter;
import com.example.laptop_market.view.adapters.Buy.BuyOrder;

import java.util.ArrayList;
import java.util.List;


public class BuyFinishFragment extends Fragment {
    private RecyclerView rcvBuyFinish;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buy_finish, container, false);

        //Hiển thị các đơn bán - đang xử lý
        rcvBuyFinish = view.findViewById(R.id.rcvBuyFinish);
        GridLayoutManager gridLayoutManagerBuyFinish = new GridLayoutManager(requireContext(),1);
        rcvBuyFinish.setLayoutManager(gridLayoutManagerBuyFinish);
        BuyFinishAdapter BuyFinishAdapter = new BuyFinishAdapter(getListBuyFinish());
        rcvBuyFinish.setAdapter(BuyFinishAdapter);

        return view;
    }

    private List<BuyOrder> getListBuyFinish(){
        List<BuyOrder> listBuyFinish = new ArrayList<>();
        listBuyFinish.add(new BuyOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listBuyFinish.add(new BuyOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listBuyFinish.add(new BuyOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listBuyFinish.add(new BuyOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listBuyFinish.add(new BuyOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listBuyFinish.add(new BuyOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listBuyFinish.add(new BuyOrder("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        return  listBuyFinish;
    }
}