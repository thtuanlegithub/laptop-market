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
import com.example.laptop_market.view.adapters.PostActiveAdapter;
import com.example.laptop_market.view.adapters.PostSearchResult.PostSearchResult;

import java.util.ArrayList;
import java.util.List;

public class PostActiveFragment extends Fragment {
    private RecyclerView rcvPostActive;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_active, container, false);

        //Hiển thị các đơn bán - đang xử lý
        rcvPostActive = view.findViewById(R.id.rcvPostActive);
        GridLayoutManager gridLayoutManagerPostActive = new GridLayoutManager(requireContext(),1);
        rcvPostActive.setLayoutManager(gridLayoutManagerPostActive);
        PostActiveAdapter PostActiveAdapter = new PostActiveAdapter(getListPostActive());
        rcvPostActive.setAdapter(PostActiveAdapter);

        return view;
    }

    private List<PostSearchResult> getListPostActive(){
        List<PostSearchResult> listPostActive = new ArrayList<>();
        listPostActive.add(new PostSearchResult("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listPostActive.add(new PostSearchResult("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listPostActive.add(new PostSearchResult("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listPostActive.add(new PostSearchResult("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listPostActive.add(new PostSearchResult("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listPostActive.add(new PostSearchResult("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listPostActive.add(new PostSearchResult("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        return  listPostActive;
    }

}