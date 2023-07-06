package com.example.laptop_market.view.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.laptop_market.R;
import com.google.android.material.textfield.TextInputEditText;


public class AccountSettingFragment extends Fragment {
    private TextInputEditText edtAccountSettingPassword;
    private Button btnAccountSettingBack;
    public AccountBaseFragment accountBaseFragment;
    public RelativeLayout rltLayoutAccountSettingPassword;

    public AccountSettingFragment(){
        // Required empty public constructor

    }
    public AccountSettingFragment(AccountBaseFragment accountBaseFragment) {

        this.accountBaseFragment = accountBaseFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account_setting, container, false);
        btnAccountSettingBack = view.findViewById(R.id.btnAccountSettingBack);
        btnAccountSettingBack.setOnClickListener(v -> {
            accountBaseFragment.replaceFragment(accountBaseFragment.accountFragment);
            //Ẩn bàn phím:
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            View currentFocus = getActivity().getCurrentFocus();
            if (inputMethodManager != null && currentFocus != null) {
                inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
            }
        });
        rltLayoutAccountSettingPassword = view.findViewById(R.id.rltLayoutAccountSettingPassword);
        rltLayoutAccountSettingPassword.setOnClickListener(v -> {
            if(accountBaseFragment.accountPasswordFragment == null){
                accountBaseFragment.accountPasswordFragment = new AccountPasswordFragment(accountBaseFragment);
            }
            accountBaseFragment.replaceFragment(accountBaseFragment.accountPasswordFragment);
        });

        edtAccountSettingPassword = view.findViewById(R.id.edtAccountSettingPassword);
        edtAccountSettingPassword.setOnTouchListener((l, e) -> {
            if(accountBaseFragment.accountPasswordFragment == null){
                accountBaseFragment.accountPasswordFragment = new AccountPasswordFragment(accountBaseFragment);
            }
            accountBaseFragment.replaceFragment(accountBaseFragment.accountPasswordFragment);
            return true;
        });
        return view;
    }
}