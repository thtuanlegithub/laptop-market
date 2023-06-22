package com.example.laptop_market.presenter.fragments;

import com.example.laptop_market.contracts.IStringFilterSearchContract;
import com.example.laptop_market.model.filter.StringFilterSearchModel;
import com.example.laptop_market.utils.PreferenceManager;

public class SearchFragmentPresenter implements IStringFilterSearchContract.Presenter.SearchFragmentPresenter {
    private PreferenceManager preferenceManager;
    private IStringFilterSearchContract.View.SearchFragmentView view;
    private IStringFilterSearchContract.Model model;

    public SearchFragmentPresenter(PreferenceManager preferenceManager, IStringFilterSearchContract.View.SearchFragmentView view) {
        this.preferenceManager = preferenceManager;
        this.view = view;
        model = new StringFilterSearchModel(preferenceManager);
    }

    @Override
    public void EnterSearch(String itemSearch) {
        model.PerformSearch(itemSearch,item -> {
            view.PerformSearch();
        });
    }

    @Override
    public void DeleteListSearchClicked() {
        model.DeleteRecentSearchList(()->{
            view.GenerateNullListSearch();
        });
    }

    @Override
    public void GenerateListSearch() {
        model.LoadRecentListSearch((isNullList, ListSearchString) -> {
            if(isNullList)
                view.GenerateNullListSearch();
            else
                view.GenerateListSearch(ListSearchString);
        });
    }
    @Override
    public void OnItemSearchListClicked(String item) {
        model.ResetItemSearchString(item, ()->{
            view.PerformSearch();
        });
    }
}
