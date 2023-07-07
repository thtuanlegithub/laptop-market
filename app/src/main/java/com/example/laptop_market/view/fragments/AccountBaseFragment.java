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
import com.example.laptop_market.contracts.IFragmentListener;
import com.example.laptop_market.view.activities.MainActivity;

public class AccountBaseFragment extends Fragment {

    private FragmentManager fragmentManager;
    public AccountFragment accountFragment;
    public AccountSettingFragment accountSettingFragment;
    public AccountPasswordFragment accountPasswordFragment;
    public ProfileFragment profileFragment;
    public FrameLayout frameAccountBase;
    private IFragmentListener listener;

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

    public void setFragmentListener(IFragmentListener mainActivity){
        if (accountFragment == null)
            accountFragment = new AccountFragment(this);
        accountFragment.setFragmentListener(mainActivity);
    }

    public void replaceFragment(Fragment fragment) {
        fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameAccountBase, fragment);
        fragmentTransaction.commit();
    }
}