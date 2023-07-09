package com.example.laptop_market.presenter.fragments;

import android.content.Context;

import com.example.laptop_market.contracts.IAccountContract;
import com.example.laptop_market.contracts.IPostContract;
import com.example.laptop_market.model.account.AccountModel;
import com.example.laptop_market.model.post.PostModel;
import com.example.laptop_market.view.adapters.PostSearchResult.PostSearchResult;

import java.util.ArrayList;

public class PostFragmentPresenter implements IPostContract.Presenter.PostFragmentPresenter {
    private IPostContract.View.PostFragmentView postFragmentView;
    private IPostContract.View.PostActiveFragmentView postActiveFragmentView;
    private IPostContract.View.PostInactiveFragmentView postInactiveFragmentView;
    private IAccountContract.Model accountModel;
    private IPostContract.Model postModel;

    public PostFragmentPresenter(Context context, IPostContract.View.PostFragmentView postFragmentView) {
        this.postFragmentView = postFragmentView;
        accountModel = new AccountModel(context);
    }

    public PostFragmentPresenter(Context context, IPostContract.View.PostActiveFragmentView postActiveFragmentView){
        this.postActiveFragmentView = postActiveFragmentView;
        postModel = new PostModel(context);
    }

    public PostFragmentPresenter(Context context, IPostContract.View.PostInactiveFragmentView postInactiveFragmentView){
        this.postInactiveFragmentView = postInactiveFragmentView;
        postModel = new PostModel(context);
    }

    @Override
    public void CreateNewPost() {
        accountModel.CheckSignedInAccount(isLogin -> {
            if(isLogin)
                postFragmentView.CreateNewPost();
            else
                postFragmentView.LoginAccount();
        });
    }

    @Override
    public void LoadManagePost() {
        accountModel.CheckSignedInAccount(isLogin -> {
            if(!isLogin)
                postFragmentView.DisplayRequireLoginView();
            else
                postFragmentView.DisplayManagePostView();
        });
    }

    @Override
    public void LoadPostActive() {
        postModel.LoadPostActive(new IPostContract.Model.OnLoadPostActiveListener() {
            @Override
            public void OnFinishLoadPostActive(boolean isSuccess, ArrayList<PostSearchResult> postSearchResults, Exception error) {
                if (isSuccess)
                    postActiveFragmentView.DisplayPostActive(postSearchResults);
                else
                    error.printStackTrace();
            }
        });
    }

    @Override
    public void LoadPostInactive() {
        postModel.LoadPostInActive(new IPostContract.Model.OnLoadPostInactiveListener() {
            @Override
            public void OnFinishLoadPostInactive(boolean isSuccess, ArrayList<PostSearchResult> postSearchResults, Exception error) {
                if (isSuccess)
                    postInactiveFragmentView.DisplayPostInactive(postSearchResults);
                else
                    error.printStackTrace();
            }
        });
    }
}
