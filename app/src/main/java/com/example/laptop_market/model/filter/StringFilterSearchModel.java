package com.example.laptop_market.model.filter;

import android.content.Context;

import com.example.laptop_market.contracts.IStringFilterSearchContract;
import com.example.laptop_market.utils.tables.Constants;
import com.example.laptop_market.utils.elses.PreferenceManager;
import com.example.laptop_market.view.fragments.SearchResultFragment;

import java.util.ArrayList;
import java.util.Collections;

public class StringFilterSearchModel implements IStringFilterSearchContract.Model {

    private PreferenceManager preferenceManager;

    public StringFilterSearchModel(Context context) {
        this.preferenceManager = new PreferenceManager(context);
    }

    @Override
    public void LoadRecentListSearch(OnLoadingRecentListSearchListener listener) {
        ArrayList<String> listRecentSearch = preferenceManager.getFilters(Constants.KEY_FILTER);
        if(listRecentSearch!=null) {
            Collections.reverse(listRecentSearch);
            listener.OnFinishLoadingRecentListSearch(false, listRecentSearch);
        }
        else
        {
            listener.OnFinishLoadingRecentListSearch(true, null);
        }

    }

    @Override
    public void PerformSearch(String item, OnPerformSearchListener listener) {
        preferenceManager.putString(Constants.KEY_SEARCH_ITEM, item);
        preferenceManager.putInt(Constants.KEY_POST_SEARCH_RESULT_TYPE,SearchResultFragment.SEARCH_CLICK);
        ArrayList<String> listSearchString = preferenceManager.getFilters(Constants.KEY_FILTER);
        if(listSearchString==null)
            listSearchString = new ArrayList<>();
        else if(listSearchString.contains(item))
            listSearchString.remove(item);
        listSearchString.add(item);
        preferenceManager.putFilters(Constants.KEY_FILTER,listSearchString);
        listener.OnFinishPerformSearch(item);
    }

    @Override
    public void DeleteRecentSearchList(OnDeleteRecentSearchListener listener) {
        preferenceManager.putString(Constants.KEY_FILTER,"");
        listener.OnFinishDeleteRecentSearch();
    }

    @Override
    public void LoadSearchString(LoadSearchStringListener listener) {
        String item = preferenceManager.getString(Constants.KEY_SEARCH_ITEM);
        preferenceManager.putString(Constants.KEY_SEARCH_ITEM,"");
        preferenceManager.putInt(Constants.KEY_POST_SEARCH_RESULT_TYPE,SearchResultFragment.SEARCH_CLICK);
        listener.OnFinishSearchString(item);
    }

    @Override
    public void ResetItemSearchString(String ItemString, ResetItemSearchListener listener) {
        preferenceManager.putString(Constants.KEY_SEARCH_ITEM, ItemString);
        preferenceManager.putInt(Constants.KEY_POST_SEARCH_RESULT_TYPE,SearchResultFragment.SEARCH_CLICK);
        //String a = preferenceManager.getString(Constants.KEY_SEARCH_ITEM);
        ArrayList<String> listSearchString = preferenceManager.getFilters(Constants.KEY_FILTER);
        if(listSearchString==null)
            listSearchString = new ArrayList<>();
        else if(listSearchString.contains(ItemString))
            listSearchString.remove(ItemString);
        listSearchString.add(ItemString);
        preferenceManager.putFilters(Constants.KEY_FILTER,listSearchString);

        listener.OnFinishResetItemListener();
    }

}
