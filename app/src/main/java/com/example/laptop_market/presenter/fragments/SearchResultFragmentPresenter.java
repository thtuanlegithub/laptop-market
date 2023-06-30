package com.example.laptop_market.presenter.fragments;

import android.content.Context;

import com.example.laptop_market.contracts.IPostContract;
import com.example.laptop_market.contracts.IStringFilterSearchContract;
import com.example.laptop_market.model.filter.StringFilterSearchModel;
import com.example.laptop_market.model.post.PostModel;
import com.example.laptop_market.utils.PreferenceManager;

public class SearchResultFragmentPresenter implements IStringFilterSearchContract.Presenter.SearchResultFragmentPresenter
        , IPostContract.Presenter.SearchResultFragmentPresenter {


    private IStringFilterSearchContract.Model model;
    private IStringFilterSearchContract.View.SearchResultFragmentView view;
    private IPostContract.View.SearchResultFragmentView viewPost;
    private IPostContract.Model postModel;

    public SearchResultFragmentPresenter(Context context, IStringFilterSearchContract.View.SearchResultFragmentView view,IPostContract.View.SearchResultFragmentView viewPost) {
        this.view = view;
        this.viewPost=viewPost;
        model = new StringFilterSearchModel(context);
        postModel = new PostModel(context);
    }

    @Override
    public void OnLoadingPageView() {
        model.LoadSearchString(item -> {
            view.LoadSearchingFragment(item);
        });
    }


    @Override
    public void OnSearchPost(String searchPost) {
        postModel.LoadSearchPost(searchPost,(posts,exception)->{
            viewPost.FinishLoadingSearchPost(posts);
        });
    }

}
