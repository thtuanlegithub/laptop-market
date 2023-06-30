package com.example.laptop_market.view.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.laptop_market.R;
import com.example.laptop_market.contracts.IStringFilterSearchContract;
import com.example.laptop_market.presenter.fragments.SearchFragmentPresenter;
import com.example.laptop_market.utils.PreferenceManager;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment implements  IStringFilterSearchContract.View.SearchFragmentView{
    private HomeBaseFragment homeBaseFragment;
    public SearchResultFragment searchResultFragment = null;
    private Button btnSearchBack = null;
    private EditText edtTextSearch = null;
    private HomeFragment homeFragment = null;
    private ListView listTimKiemGanDay = null;
    private Button bttXoaListTimKiem;
    private IStringFilterSearchContract.Presenter.SearchFragmentPresenter presenter;

    public SearchFragment(HomeBaseFragment homeBaseFragment) {
        // Required empty public constructor
        this.homeBaseFragment = homeBaseFragment;

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SearchFragmentPresenter(getContext(),this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        edtTextSearch = view.findViewById(R.id.edtTextSearch);
        edtTextSearch.requestFocus();
        btnSearchBack = view.findViewById(R.id.btnSearchBack);
        listTimKiemGanDay = view.findViewById(R.id.listTimKiemGanDay);
        bttXoaListTimKiem = view.findViewById(R.id.bttXoaListTimKiem);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListener();
    }
    //region set Listener
    private void setListener()
    {
        bttXoaListTimKiem.setOnClickListener(view -> {
            presenter.DeleteListSearchClicked();
        });
        btnSearchBack.setOnClickListener(view1 -> {
            homeBaseFragment.replaceFragment(homeBaseFragment.homeFragment);
            //Ẩn bàn phím:
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            View currentFocus = getActivity().getCurrentFocus();
            if (inputMethodManager != null && currentFocus != null) {
                inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
            }
        });
        edtTextSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String itemSearch = edtTextSearch.getText().toString();
                    presenter.EnterSearch(itemSearch);
                    return true;
                }
                return false;
            }
        });
        listTimKiemGanDay.setOnItemClickListener((adapterView, view, i, l) -> {
            String item = (String) adapterView.getItemAtPosition(i);
            presenter.OnItemSearchListClicked(item);
        });
        showKeyboardAndFocusEditText();

    }
//endregion

    private void showKeyboardAndFocusEditText() {
        edtTextSearch.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.showSoftInput(edtTextSearch, InputMethodManager.SHOW_IMPLICIT);
        }
    }


    @Override
    public void PerformSearch() {
        edtTextSearch.setText("");
        if(searchResultFragment==null){
            searchResultFragment = new SearchResultFragment(homeBaseFragment);
        }
//            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.frame_layout, searchResultFragment);
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commit();
        if(homeBaseFragment!=null && searchResultFragment!=null){
            homeBaseFragment.searchResultFragment = searchResultFragment;
            homeBaseFragment.replaceFragment(searchResultFragment);
        }
    }

    @Override
    public void GenerateListSearch(ArrayList<String> getItemInListSearch) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1 ,getItemInListSearch);
        listTimKiemGanDay.setAdapter(adapter);
    }

    @Override
    public void GenerateNullListSearch() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1 ,new ArrayList<String>());
        listTimKiemGanDay.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.GenerateListSearch();
    }
}