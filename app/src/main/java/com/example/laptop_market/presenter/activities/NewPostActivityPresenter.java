package com.example.laptop_market.presenter.activities;

import android.content.Context;

import com.example.laptop_market.contracts.IAccountContract;
import com.example.laptop_market.contracts.ILaptopContract;
import com.example.laptop_market.contracts.IPostContract;
import com.example.laptop_market.model.account.AccountModel;
import com.example.laptop_market.model.laptop.Laptop;
import com.example.laptop_market.model.laptop.LaptopModel;
import com.example.laptop_market.model.post.Post;
import com.example.laptop_market.model.post.PostModel;

public class NewPostActivityPresenter implements ILaptopContract.Presenter.NewPostActivityPresenter, IPostContract.Presenter.NewPostActivityPresenter {
    private ILaptopContract.Model laptopModel;
    private IPostContract.Model postModel;
    private IPostContract.View.NewPostActivityView postView;
    private ILaptopContract.View.NewPostActivityView laptopView;
    private IAccountContract.Model accountModel;

    public NewPostActivityPresenter(Context context, ILaptopContract.View.NewPostActivityView laptopView, IPostContract.View.NewPostActivityView postView) {
        this.laptopView = laptopView;
        laptopModel = new LaptopModel(context);
        postModel = new PostModel(context);
        accountModel = new AccountModel();
        this.postView = postView;
    }


    @Override
    public void OnCreateNewLaptopClicked(Laptop laptop) {
        laptopModel.CreateLaptop(laptop,(isSuccess, idLaptop, error) -> {
            if(isSuccess)
                laptopView.CreateLaptopSuccess(idLaptop);
            else
                laptopView.CreateLaptopFailure(error);
        });
    }

    @Override
    public void OnCreateNewPostClicked(Post post,Laptop laptop) {
        postModel.CreateNewPost(post,laptop, (isSuccess, error) -> {
            if(isSuccess)
                postView.CreatePostSuccess();
            else
                postView.CreatePostFailure(error);
        });
    }

    @Override
    public void OnLoadingAccount() {
        accountModel.LoadAccountSetting((account, e) -> {
            if(e!=null)
                e.printStackTrace();
            else
                postView.LoadDataInView(account);
        });
    }
}
