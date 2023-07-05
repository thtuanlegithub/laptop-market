package com.example.laptop_market.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.laptop_market.R;
import com.example.laptop_market.model.post.Post;
import com.example.laptop_market.view.adapters.PostInactiveAdapter;
import com.example.laptop_market.view.adapters.PostSearchResult.PostSearchResult;

import java.util.ArrayList;
import java.util.List;

public class PostInactiveFragment extends Fragment {
    private RecyclerView rcvPostInactive;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_inactive, container, false);

        //Hiển thị các đơn bán - đang xử lý
        rcvPostInactive = view.findViewById(R.id.rcvPostInactive);
        GridLayoutManager gridLayoutManagerPostInactive = new GridLayoutManager(requireContext(),1);
        rcvPostInactive.setLayoutManager(gridLayoutManagerPostInactive);
        PostInactiveAdapter PostInactiveAdapter = new PostInactiveAdapter(getListPostInactive());
        rcvPostInactive.setAdapter(PostInactiveAdapter);

        return view;
    }

    private List<PostSearchResult> getListPostInactive(){
        List<PostSearchResult> listPostInactive = new ArrayList<>();
        listPostInactive.add(new PostSearchResult("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listPostInactive.add(new PostSearchResult("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listPostInactive.add(new PostSearchResult("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listPostInactive.add(new PostSearchResult("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listPostInactive.add(new PostSearchResult("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listPostInactive.add(new PostSearchResult("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listPostInactive.add(new PostSearchResult("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        return  listPostInactive;
    }

}