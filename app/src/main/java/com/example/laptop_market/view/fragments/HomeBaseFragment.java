package com.example.laptop_market.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.laptop_market.R;

public class HomeBaseFragment extends Fragment {
    private FragmentManager fragmentManager;
    public boolean isSearch = false;
    public HomeFragment homeFragment;
    public SearchFragment searchFragment;
    public SearchResultFragment searchResultFragment;
    public FrameLayout frameHomeBase;

    public HomeBaseFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_base, container, false);
        //
        frameHomeBase = view.findViewById(R.id.frameHomeBase);
        if(homeFragment == null){
            homeFragment = new HomeFragment(this);
        }
        replaceFragment(homeFragment);
        return view;
    }
    public void replaceFragment(Fragment fragment) {
        if(fragment instanceof SearchResultFragment)
        {
            fragment = new SearchResultFragment(this);
        }
        fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameHomeBase, fragment);
        fragmentTransaction.commit();
    }
}