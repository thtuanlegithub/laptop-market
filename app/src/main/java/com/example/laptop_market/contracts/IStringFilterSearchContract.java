package com.example.laptop_market.contracts;

import com.example.laptop_market.model.post.Post;

import java.util.ArrayList;

public interface IStringFilterSearchContract {
    interface Model{
        //region Load recent list
        void LoadRecentListSearch(OnLoadingRecentListSearchListener listener);
        interface OnLoadingRecentListSearchListener{
            void OnFinishLoadingRecentListSearch(boolean isNullList, ArrayList<String> ListSearchString);
        }
        //endregion
        //region Preform Search
        void PerformSearch(String item, OnPerformSearchListener listener);
        interface OnPerformSearchListener{
            void OnFinishPerformSearch(String item);
        }
        //endregion
        //region Delete list Search
        void DeleteRecentSearchList(OnDeleteRecentSearchListener listener);
        interface OnDeleteRecentSearchListener{
            void OnFinishDeleteRecentSearch();
        }
        //endregion
        //region Load Search String in previous view
        void LoadSearchString(LoadSearchStringListener listener);
        interface LoadSearchStringListener{
            void OnFinishSearchString(String item);
        }
        //endregion
        //region Reset Array
        void ResetItemSearchString(String ItemString, ResetItemSearchListener listener);
        interface ResetItemSearchListener{
            void OnFinishResetItemListener();
        }
        //endregion
    }
    interface View{
        interface SearchResultFragmentView{
            void LoadSearchingFragment(String itemString);
        }
        interface SearchFragmentView{

            void PerformSearch();
            void GenerateListSearch(ArrayList<String> getItemInListSearch);
            void GenerateNullListSearch();
        }
    }
    interface  Presenter{
        interface SearchResultFragmentPresenter{
            void OnLoadingPageView();
        }
        interface SearchFragmentPresenter{
            void EnterSearch(String itemSearch);
            void DeleteListSearchClicked();
            void GenerateListSearch();
            void OnItemSearchListClicked(String item);

        }
    }
}
