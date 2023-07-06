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

public class AccountBaseFragment extends Fragment {

    private FragmentManager fragmentManager;
    public AccountFragment accountFragment;
    public AccountSettingFragment accountSettingFragment;
    public AccountPasswordFragment accountPasswordFragment;
    public ProfileFragment profileFragment;
    public FrameLayout frameAccountBase;

    public AccountBaseFragment() {
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
        View view = inflater.inflate(R.layout.fragment_account_base, container, false);

        frameAccountBase = view.findViewById(R.id.frameAccountBase);
        if(accountFragment==null){
            accountFragment = new AccountFragment(this);
        }
        replaceFragment(accountFragment);
        return view;
    }

    public void replaceFragment(Fragment fragment) {
        fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameAccountBase, fragment);
        fragmentTransaction.commit();
    }
}