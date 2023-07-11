package com.example.laptop_market.presenter.fragments;

import android.content.Context;

import com.example.laptop_market.contracts.IConversationContract;
import com.example.laptop_market.contracts.IPostContract;
import com.example.laptop_market.contracts.IStringFilterSearchContract;
import com.example.laptop_market.model.conversation.ConversationModel;
import com.example.laptop_market.model.filter.StringFilterSearchModel;
import com.example.laptop_market.model.post.PostModel;
import com.example.laptop_market.utils.tables.SearchFilterPost;

import org.json.JSONException;

public class SearchResultFragmentPresenter implements IStringFilterSearchContract.Presenter.SearchResultFragmentPresenter
        , IPostContract.Presenter.SearchResultFragmentPresenter {


    private IStringFilterSearchContract.Model model;
    private IStringFilterSearchContract.View.SearchResultFragmentView view;
    private IPostContract.View.SearchResultFragmentView viewPost;
    private IPostContract.Model postModel;
    private IConversationContract.Model conversationModel;

    public SearchResultFragmentPresenter(Context context, IStringFilterSearchContract.View.SearchResultFragmentView view,IPostContract.View.SearchResultFragmentView viewPost) {
        this.view = view;
        this.viewPost=viewPost;
        model = new StringFilterSearchModel(context);
        postModel = new PostModel(context);
        conversationModel = new ConversationModel();
    }

    @Override
    public void OnLoadingPageView() {
        model.LoadSearchString(item -> {
            view.LoadSearchingFragment(item);
        });
    }


    @Override
    public void OnSearchPost(String searchPost) {
        try {
            postModel.LoadSearchPost(searchPost, (posts, exception) -> {
                if (exception == null)
                    viewPost.FinishLoadingSearchPost(posts);
                else
                    exception.printStackTrace();
            });
        }catch (JSONException ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void OnSearchPostByFilter(SearchFilterPost searchFilterPost) {
        postModel.LoadSearchPostByFilter(searchFilterPost,(posts, error) -> {
            if(error==null)
                viewPost.FinishLoadingSearchPost(posts);
            else
                error.printStackTrace();
        });
    }
    @Override
    public void LoadingNotSeenMessage() {
        conversationModel.getNumberOfNotSeenMessage(numberOfMessage -> {
            viewPost.LoadNotSeenMessage(numberOfMessage);
        });
    }
}
