package com.example.laptop_market.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.laptop_market.R;
import com.example.laptop_market.activity.LoginActivity;


public class AccountFragment extends Fragment {
    private ImageView imgAccount;
    private TextView txtAccountName;
    public AccountFragment() {
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
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        imgAccount = view.findViewById(R.id.imgAccount);
        Glide.with(this)
                .load(R.drawable.slide_show1)
                .apply(RequestOptions.circleCropTransform())
                .into(imgAccount);

        txtAccountName = view.findViewById(R.id.txtAccountName);
        txtAccountName.setOnClickListener(v -> {
            Intent intent = new Intent(this.getActivity(), LoginActivity.class);
            startActivity(intent);
        });

        return view;
    }
}