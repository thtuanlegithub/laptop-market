package com.example.laptop_market.contracts;

import com.example.laptop_market.model.account.Account;
import com.example.laptop_market.model.account.CurrentBuyer;
import com.example.laptop_market.model.laptop.Laptop;
import com.example.laptop_market.model.post.Post;
import com.example.laptop_market.utils.tables.SearchFilterPost;
import com.example.laptop_market.view.adapters.PostSearchResult.PostSearchResult;

import org.json.JSONException;

import java.util.ArrayList;

public interface IPostContract {
    interface Model{
        //region Create new post
        void CreateNewPost(Post post, Laptop laptop, OnCreatePostListener listener);
        interface OnCreatePostListener{
            void OnFinishCreatePostListener(boolean isSuccess, Exception error);
        }
        //endregion
        //region Load Search Post
        void LoadSearchPost(String searchPost, OnLoadingSearchPostListener listener) throws JSONException;
        interface OnLoadingSearchPostListener{
            void OnFinishLoadingSearchPostListener(ArrayList<PostSearchResult> posts, Exception error);
        }
        //endregion
        //region Load Search post with filter
        void LoadSearchPostByFilter(SearchFilterPost searchFilterPost, OnLoadingSearchPostListener listener);
        //endregion
        //region Load post detail
        void LoadingPostInPostDetail(String postID, OnLoadingPostInPostDetailListener listener);
        interface OnLoadingPostInPostDetailListener{
            void OnFinishLoadingPostInPostDetail(Post post, Exception error);
        }
        //endregion
        // region Load your own post
        void LoadPostActive(OnLoadPostActiveListener listener);
        interface OnLoadPostActiveListener {
            void OnFinishLoadPostActive(boolean isSuccess, ArrayList<PostSearchResult> postSearchResults, Exception error);
        }
        void LoadPostInActive(OnLoadPostInactiveListener listener);
        interface OnLoadPostInactiveListener {
            void OnFinishLoadPostInactive(boolean isSuccess, ArrayList<PostSearchResult> postSearchResults, Exception error);
        }
        // endregion
        //region Load seller's phone number
        void GetSellerPhoneNumber(String postID, OnLoadSellerPhoneNumber listener);
        interface  OnLoadSellerPhoneNumber{
            void OnFinishLoadSellerPhoneNumber(boolean isSuccess, String phoneNumber, Exception error);
        }
        //endregion
        void UpdatePostStatus(String postID, OnUpdatePostStatusListener listener);
        interface OnUpdatePostStatusListener {
            void OnFinishUpdatePostStatus(boolean isSuccess, boolean isAvailable, Exception error);
        }
    }
    interface View{
        interface NewPostActivityView{
            void CreatePostSuccess();
            void CreatePostFailure(Exception error);
            void LoadDataInView(Account account);
        }
        interface SearchResultFragmentView{
            void FinishLoadingSearchPost(ArrayList<PostSearchResult> posts);
        }
        interface PostDetailActivityView{
            void LoadingPostInPostDetail(Post post);
            void FailedLoadingPostDetail(Exception error);
            void LoadSavePostButton();
            void LoadRemoveSavePostButton();
            void ShowPhoneDialIntent(String phoneNumber);
            void LoginAccount();
            void LoadOrderDetails(CurrentBuyer currentBuyer);
            void DisplayNotifyButtonStatus(boolean isAvailable);
        }

        interface PostFragmentView{
            void CreateNewPost();
            void LoginAccount();
            void DisplayRequireLoginView();
            void DisplayManagePostView();
        }
        interface PostActiveFragmentView {
            void DisplayPostActive(ArrayList<PostSearchResult> postSearchResults);
        }
        interface PostInactiveFragmentView {
            void DisplayPostInactive(ArrayList<PostSearchResult> postSearchResults);
        }
    }
    interface Presenter{
        interface NewPostActivityPresenter{
            void OnCreateNewPostClicked(Post post, Laptop laptop);
            void OnLoadingAccount();
        }
        interface SearchResultFragmentPresenter{
            void OnSearchPost(String searchPost);
            void OnSearchPostByFilter(SearchFilterPost searchFilterPost);
        }
        interface PostDetailActivityPresenter{
            void OnLoadingPostInPostDetail(String postID);
            void LoadSavePostButton(String postID);
            void OnSavePostClicked(String postID, boolean isSaved);
            void OnPhoneDialClicked(String postID);
            void OnBuyNowClicked();
            void OnChangeStatusClicked(String postID);
        }
        interface PostFragmentPresenter{
            void CreateNewPost();
            void LoadManagePost();
            void LoadPostActive();
            void LoadPostInactive();
        }
    }
}
