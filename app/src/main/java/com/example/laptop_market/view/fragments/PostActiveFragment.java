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
import com.example.laptop_market.contracts.IPostContract;
import com.example.laptop_market.model.post.Post;
import com.example.laptop_market.presenter.fragments.PostFragmentPresenter;
import com.example.laptop_market.view.adapters.PostActiveAdapter;
import com.example.laptop_market.view.adapters.PostSearchResult.PostSearchResult;

import java.util.ArrayList;
import java.util.List;

public class PostActiveFragment extends Fragment implements IPostContract.View.PostActiveFragmentView {
    private RecyclerView rcvPostActive;
    private IPostContract.Presenter.PostFragmentPresenter postFragmentPresenter;
    private ProgressBar progressBar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        postFragmentPresenter = new PostFragmentPresenter(getContext(), this);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_active, container, false);

        //Hiển thị các đơn bán - đang xử lý
        progressBar = view.findViewById(R.id.progressBarPostActive);
        rcvPostActive = view.findViewById(R.id.rcvPostActive);
        GridLayoutManager gridLayoutManagerPostActive = new GridLayoutManager(requireContext(),1);
        rcvPostActive.setLayoutManager(gridLayoutManagerPostActive);
        postFragmentPresenter.LoadPostActive();
        return view;
    }
    @Override
    public void DisplayPostActive(ArrayList<PostSearchResult> postSearchResults) {
        if (postSearchResults == null){
            progressBar.setVisibility(View.GONE);
            return;
        }
        PostActiveAdapter PostActiveAdapter = new PostActiveAdapter(postSearchResults);
        rcvPostActive.setAdapter(PostActiveAdapter);
        progressBar.setVisibility(View.GONE);
    }
}