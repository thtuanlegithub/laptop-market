package com.example.laptop_market.presenter.activities;

import android.content.Context;

import com.example.laptop_market.contracts.IAccountContract;
import com.example.laptop_market.contracts.ILaptopContract;
import com.example.laptop_market.contracts.IPostContract;
import com.example.laptop_market.model.account.Account;
import com.example.laptop_market.model.account.AccountModel;
import com.example.laptop_market.model.account.CurrentBuyer;
import com.example.laptop_market.model.laptop.LaptopModel;
import com.example.laptop_market.model.post.PostModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PostDetailActivityPresenter implements IPostContract.Presenter.PostDetailActivityPresenter
        , ILaptopContract.Presenter.PostDetailActivityPresenter
        , IAccountContract.Presenter.PostDetailActivityPresenter {

    private ILaptopContract.View.PostDetailActivityView laptopView;
    private IPostContract.View.PostDetailActivityView postView;
    private IAccountContract.View.PostDetailActivityView accountView;
    private IPostContract.Model postModel;
    private ILaptopContract.Model laptopModel;
    private IAccountContract.Model accountModel;
    //region Constructor
    public PostDetailActivityPresenter(Context context, ILaptopContract.View.PostDetailActivityView laptopView, IPostContract.View.PostDetailActivityView postView
            , IAccountContract.View.PostDetailActivityView accountView) {
        this.accountView = accountView;
        this.laptopView = laptopView;
        this.postView = postView;
        laptopModel = new LaptopModel(context);
        postModel = new PostModel(context);
        accountModel = new AccountModel(context);
    }
    //endregion
    //region Laptop Presenter
    @Override
    public void OnLoadingLaptopInPostDetail(String laptopId) {
        laptopModel.LoadingLaptopInPostDetail(laptopId,((laptop, error) -> {
            if(error!=null)
                laptopView.FailedLoadingPostDetail(error);
            else
                laptopView.LoadingLapTopInPostDetail(laptop);
        }));
    }

    @Override
    public void OnLoadingImageLaptopInPostDetail(String laptopId) {
        laptopModel.LoadingImageToPostDetail(laptopId,(image, error) -> {
            if(error!=null)
                laptopView.FailedLoadingPostDetail(error);
            else
                laptopView.LoadingImageLaptopInPostDetail(image);
        });
    }
    //endregion
    //region Post presenter
    @Override
    public void OnLoadingPostInPostDetail(String postID) {
        postModel.LoadingPostInPostDetail(postID,(post, error) -> {
            if(error!=null)
                postView.FailedLoadingPostDetail(error);
            else
                postView.LoadingPostInPostDetail(post);
        });
    }

    @Override
    public void LoadSavePostButton(String postID) {
        accountModel.CheckSignedInAccount(isLogin -> {
            if(isLogin){
                accountModel.LoadSavePostButton(postID, new IAccountContract.Model.OnFinishSavePostListener() {
                    @Override
                    public void OnFinishSavePost(boolean isSuccess, boolean isSaved, Exception error) {
                        if (isSuccess){
                            if (isSaved)
                                postView.LoadRemoveSavePostButton();
                            else
                                postView.LoadSavePostButton();
                        }
                        else
                            error.printStackTrace();
                    }
                });
            }
            /*
            else {
                Load default UI (button "Lưu tin")
            }
            */
        });

    }

    @Override
    public void OnSavePostClicked(String postID, boolean isSaved) {
        accountModel.CheckSignedInAccount(isLogin -> {
            if (isLogin){
                if (!isSaved){
                    accountModel.ClickSavePost(postID, new IAccountContract.Model.OnFinishSavePostListener() {
                        @Override
                        public void OnFinishSavePost(boolean isSuccess, boolean isSaved, Exception error) {
                            if (isSuccess)
                                postView.LoadRemoveSavePostButton();
                            else
                                error.printStackTrace();
                        }
                    });
                }
                else {
                    accountModel.ClickRemoveSavePost(postID, new IAccountContract.Model.OnFinishSavePostListener() {
                        @Override
                        public void OnFinishSavePost(boolean isSuccess, boolean isSaved, Exception error) {
                            if (isSuccess)
                                postView.LoadSavePostButton();
                            else
                                error.printStackTrace();
                        }
                    });
                }
            }
            else {
                postView.LoginAccount();
            }
        });

    }

    @Override
    public void OnPhoneDialClicked(String postID) {
        postModel.GetSellerPhoneNumber(postID, new IPostContract.Model.OnLoadSellerPhoneNumber() {
            @Override
            public void OnFinishLoadSellerPhoneNumber(boolean isSuccess, String phoneNumber, Exception error) {
                if (isSuccess)
                    postView.ShowPhoneDialIntent(phoneNumber);
                else
                    error.printStackTrace();
            }
        });
    }

    @Override
    public void OnBuyNowClicked() {
        accountModel.CheckSignedInAccount(isLogin -> {
            if (isLogin){
                accountModel.GetCurrentAccountInformation((isSuccess, currentBuyer, error) -> {
                    if (isSuccess) {
                        postView.LoadOrderDetails(currentBuyer);
                    } else {
                        error.printStackTrace();
                    }
                });
            } else {
                postView.LoginAccount();
           }
        });
    }

    //endregion

    //region Account presenter
    @Override
    public void OnLoadingAccountInPostDetail(String accountId) {
        accountModel.LoadAccountWithId(accountId,(account, error) -> {
            if(error!=null)
                accountView.FailedLoadingPostDetail(error);
            else
                accountView.LoadingAccountInPostDetail(account);
        });
    }
    //endregion

    @Override
    public void OnChangeStatusClicked(String postID, String changePostStatusTo) {
        postModel.UpdatePostStatus(postID, changePostStatusTo, new IPostContract.Model.OnUpdatePostStatusListener() {
            @Override
            public void OnFinishUpdatePostStatus(boolean isSuccess, String currentStatus, Exception error) {
                if (isSuccess) {
                    postView.DisplayNotifyButtonStatus(currentStatus);
                } else {
                    error.printStackTrace();
                }
            }
        });
    }
}
