package com.example.laptop_market.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.laptop_market.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeBaseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeBaseFragment extends Fragment {
    private FragmentManager fragmentManager;
    public HomeFragment homeFragment;
    public SearchFragment searchFragment;
    public SearchResultFragment searchResultFragment;
    private FrameLayout frameHomeBase;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
        if(homeFragment==null){
            homeFragment=new HomeFragment(this);
        }
        replaceFragment(homeFragment);
        return view;
    }
    public void replaceFragment(Fragment fragment) {
        fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameHomeBase, fragment);
        fragmentTransaction.commit();
    }
}