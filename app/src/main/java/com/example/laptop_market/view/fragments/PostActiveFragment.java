package com.example.laptop_market.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout layoutNotPostActive;
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
        rcvPostActive = view.findViewById(R.id.rcvPostActive);
        layoutNotPostActive = view.findViewById(R.id.layoutNotPostActive);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayoutPostActive);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                postFragmentPresenter.LoadPostActive();
                layoutNotPostActive.setVisibility(View.GONE);
                rcvPostActive.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        //Hiển thị các đơn bán - đang xử lý
        progressBar = view.findViewById(R.id.progressBarPostActive);
        GridLayoutManager gridLayoutManagerPostActive = new GridLayoutManager(requireContext(),1);
        rcvPostActive.setLayoutManager(gridLayoutManagerPostActive);
        postFragmentPresenter.LoadPostActive();
        return view;
    }
    @Override
    public void DisplayPostActive(ArrayList<PostSearchResult> postSearchResults) {
        if (postSearchResults == null || postSearchResults.size() == 0){
            progressBar.setVisibility(View.GONE);
            layoutNotPostActive.setVisibility(View.VISIBLE);
            return;
        }
        PostActiveAdapter PostActiveAdapter = new PostActiveAdapter(postSearchResults, this);
        rcvPostActive.setAdapter(PostActiveAdapter);
        layoutNotPostActive.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        rcvPostActive.setVisibility(View.VISIBLE);
    }
}