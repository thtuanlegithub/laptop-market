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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.laptop_market.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    private HomeBaseFragment homeBaseFragment;
    public SearchResultFragment searchResultFragment = null;
    private Button btnSearchBack = null;
    private EditText edtTextSearch = null;
    private HomeFragment homeFragment = null;

    public SearchFragment(HomeBaseFragment homeBaseFragment) {
        // Required empty public constructor
        this.homeBaseFragment = homeBaseFragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        edtTextSearch = view.findViewById(R.id.edtTextSearch);
        edtTextSearch.requestFocus();
        btnSearchBack = view.findViewById(R.id.btnSearchBack);

        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
                    performSearch();
                    return true;
                }
                return false;
            }
        });

        showKeyboardAndFocusEditText();
    }

    private void performSearch() {
        // Lấy nội dung từ EditText
        //String searchQuery = edtTextSearch.getText().toString().trim();

        //if (!searchQuery.isEmpty()) {
            // Chuyển sang Fragment Search Result và truyền dữ liệu tìm kiếm
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

    private void showKeyboardAndFocusEditText() {
        edtTextSearch.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.showSoftInput(edtTextSearch, InputMethodManager.SHOW_IMPLICIT);
        }
    }



}