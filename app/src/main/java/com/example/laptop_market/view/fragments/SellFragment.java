package com.example.laptop_market.view.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;

import com.example.laptop_market.R;
import com.example.laptop_market.contracts.IOrderContract;
import com.example.laptop_market.presenter.fragments.SellFragmentPresenter;
import com.example.laptop_market.utils.elses.FragmentActivityType;
import com.example.laptop_market.utils.elses.PreferenceManager;
import com.example.laptop_market.view.activities.LoginActivity;
import com.example.laptop_market.view.activities.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;


public class SellFragment extends Fragment implements IOrderContract.View.SellFragmentView {
    private List<Fragment> fragmentList;
    private ViewPager2 viewPagerSell;
    private BottomNavigationView navSell;
    private FragmentStateAdapter fragmentStateAdapter;
    private int currentSelectedItem = 0;
    private Button btnRequireLoginForSell;
    private GridLayout gridRequireLoginForSell;
    private ActivityResultLauncher<Intent> loginLauncher;
    private IOrderContract.Presenter.SellFragmentPresenter sellFragmentPresenter;
    private boolean applySlideTransition = true;
    public SellFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set activity result when Login from BuyFragment
        loginLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        DisplayManageOrderView();
                    }
                }
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sellFragmentPresenter = new SellFragmentPresenter(this, getContext());
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sell, container, false);
        fragmentList = new ArrayList<>();
        fragmentList.add(new SellProcessingFragment());
        fragmentList.add(new SellDeliveringFragment());
        fragmentList.add(new SellFinishFragment());
        fragmentList.add(new SellCancelFragment());
        viewPagerSell = view.findViewById(R.id.viewPagerSell);
        gridRequireLoginForSell = view.findViewById(R.id.gridRequireLoginForSell);
        btnRequireLoginForSell = view.findViewById(R.id.btnRequireLoginForSell);
        fragmentStateAdapter = new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getItemCount() {
                return fragmentList.size();
            }
        };
        viewPagerSell.setAdapter(fragmentStateAdapter);
        navSell = view.findViewById(R.id.navSell);
        navSell.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if(itemId == R.id.sellProcessing && currentSelectedItem != 0){
                    viewPagerSell.setCurrentItem(0);
                    currentSelectedItem = 0;
                } else if (itemId == R.id.sellDelivering && currentSelectedItem != 1) {
                    viewPagerSell.setCurrentItem(1);
                    currentSelectedItem = 1;
                } else if (itemId == R.id.sellFinish && currentSelectedItem != 2) {
                    viewPagerSell.setCurrentItem(2);
                    currentSelectedItem = 2;
                } else if (itemId == R.id.sellCancel && currentSelectedItem != 3) {
                    viewPagerSell.setCurrentItem(3);
                    currentSelectedItem = 3;
                }

                return true;
            }
        });
        viewPagerSell.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                navSell.getMenu().getItem(position).setChecked(true);
                currentSelectedItem = position;
            }
        });
        btnRequireLoginForSell.setOnClickListener(view1 -> {
            Intent intent = new Intent(this.getActivity(), LoginActivity.class);
            PreferenceManager preferenceManager = new PreferenceManager(getContext());
            preferenceManager.putInt(FragmentActivityType.FRAGMENT_ACTIVITY, FragmentActivityType.MANAGE_SELLING_ORDER);
            loginLauncher.launch(intent);
        });
        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            boolean shouldApplySlideTransition = data.getBooleanExtra("applySlideTransition", false);
            if (shouldApplySlideTransition) {
                applySlideTransition = true;
            }
            else{
                applySlideTransition = false;
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if (applySlideTransition) {
            getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
        sellFragmentPresenter.LoadManageSellOrder();
    }

    @Override
    public void DisplayRequireLoginView() {
        gridRequireLoginForSell.setVisibility(View.VISIBLE);
    }

    @Override
    public void DisplayManageOrderView() {
        gridRequireLoginForSell.setVisibility(View.GONE);
    }
}