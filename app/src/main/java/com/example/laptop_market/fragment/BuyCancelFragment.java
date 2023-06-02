package com.example.laptop_market.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.laptop_market.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BuyCancelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BuyCancelFragment extends Fragment {
    public BuyCancelFragment() {
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
        return inflater.inflate(R.layout.fragment_buy_cancel, container, false);
    }
}