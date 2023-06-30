package com.example.laptop_market.contracts;

import com.example.laptop_market.model.laptop.Laptop;
import com.example.laptop_market.model.post.Post;
import com.example.laptop_market.view.adapters.PostSearchResult.PostSearchResult;

import java.util.ArrayList;

public interface IPostContract {
    interface Model{
        //region Create new post
        void CreateNewPost(Post post, OnCreatePostListener listener);
        interface OnCreatePostListener{
            void OnFinishCreatePostListener(boolean isSuccess, Exception error);
        }
        //endregion
        //region Load Search Post
        void LoadSearchPost(String searchPost, OnLoadingSearchPostListener listener);
        interface OnLoadingSearchPostListener{
            void OnFinishLoadingSearchPostListener(ArrayList<PostSearchResult> posts, Exception error);
        }
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
    }
    interface Presenter{
        interface NewPostActivityPresenter{
            void OnCreateNewPostClicked(Post post);
        }
        interface SearchResultFragmentPresenter{
            void OnSearchPost(String searchPost);
        }
        interface PostDetailActivityPresenter{
            void OnLoadingPostInPostDetail(String postID);
        }
    }
}
