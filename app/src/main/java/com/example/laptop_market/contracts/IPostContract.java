package com.example.laptop_market.contracts;

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
    }
    interface View{
        interface NewPostActivityView{
            void CreatePostSuccess();
            void CreatePostFailure(Exception error);
        }
        interface SearchResultFragmentView{
            void FinishLoadingSearchPost(ArrayList<PostSearchResult> posts);
        }
        interface PostDetailActivityView{
            void LoadingPostInPostDetail(Post post);
            void FailedLoadingPostDetail(Exception error);
        }

        interface PostFragmentView{
            void CreateNewPost();
            void LoginAccount();
        }
    }
    interface Presenter{
        interface NewPostActivityPresenter{
            void OnCreateNewPostClicked(Post post, Laptop laptop);
        }
        interface SearchResultFragmentPresenter{
            void OnSearchPost(String searchPost);
            void OnSearchPostByFilter(SearchFilterPost searchFilterPost);
        }
        interface PostDetailActivityPresenter{
            void OnLoadingPostInPostDetail(String postID);
        }
        interface PostFragmentPresenter{
            void CreateNewPost();
        }
    }
}
