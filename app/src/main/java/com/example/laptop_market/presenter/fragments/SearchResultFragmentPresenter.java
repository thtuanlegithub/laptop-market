package com.example.laptop_market.presenter.fragments;

import com.example.laptop_market.contracts.IStringFilterSearchContract;
import com.example.laptop_market.model.filter.StringFilterSearchModel;
import com.example.laptop_market.utils.PreferenceManager;

public class SearchResultFragmentPresenter implements IStringFilterSearchContract.Presenter.SearchResultFragmentPresenter {

    private PreferenceManager preferenceManager;
    private IStringFilterSearchContract.Model model;
    private IStringFilterSearchContract.View.SearchResultFragmentView view;

    public SearchResultFragmentPresenter(PreferenceManager preferenceManager, IStringFilterSearchContract.View.SearchResultFragmentView view) {
        this.preferenceManager = preferenceManager;
        this.view = view;
        model = new StringFilterSearchModel(preferenceManager);
    }

    @Override
    public void OnLoadingPageView() {
        model.LoadSearchString(item -> {
            view.LoadSearchingFragment(item);
        });
    }


}
