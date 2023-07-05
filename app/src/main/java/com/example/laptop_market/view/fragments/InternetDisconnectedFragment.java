package com.example.laptop_market.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.laptop_market.R;
import com.example.laptop_market.utils.elses.MyRelativeLayout;
import com.example.laptop_market.view.activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InternetDisconnectedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InternetDisconnectedFragment extends Fragment {
    private static final int DEFAULT_FRAGMENT = 0;
    private static final int HOME_BASE_FRAGMENT = 1;
    private static final int SELL_FRAGMENT = 2;
    private static final int POST_FRAGMENT = 3;
    private static final int BUY_FRAGMENT = 4;
    private static final int ACCOUNT_FRAGMENT = 5;

    private boolean isCreated;
    private Fragment previousFragment;
    private MyRelativeLayout internetConnectionLayout;
    private MainActivity mainActivity;
    private LinearLayout toolBarHomeFragment, toolBarSellFragment, toolBarBuyFragment, toolBarPostFragment, toolBarAccountFragment, toolBarDefault;
    private int fragmentType;

    public void setPreviousFragment(Fragment previousFragment) {
        this.previousFragment = previousFragment;
        if(isCreated)
            loadToolBar();
    }

    public InternetDisconnectedFragment() {
    }
    public InternetDisconnectedFragment(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        isCreated = false;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_internet_disconnected, container, false);
        internetConnectionLayout = view.findViewById(R.id.internetConnectionLayout);
        toolBarHomeFragment = view.findViewById(R.id.toolBarHomeFragment);
        toolBarBuyFragment = view.findViewById(R.id.toolBarBuyFragment);
        toolBarSellFragment = view.findViewById(R.id.toolBarSellFragment);
        toolBarPostFragment = view.findViewById(R.id.toolBarPostFragment);
        toolBarAccountFragment = view.findViewById(R.id.toolBarAccountFragment);
        toolBarDefault = view.findViewById(R.id.toolBarDefault);
        setLisener();
        loadToolBar();
        isCreated = true;
        return view;
    }
    private void setLisener()
    {
        internetConnectionLayout.setOnTouchListener((view1, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    internetConnectionLayout.setAlpha(0.5f);
                    break;
                case MotionEvent.ACTION_UP:
                    internetConnectionLayout.performClick();
                    internetConnectionLayout.setAlpha(1f);
                    break;
                case MotionEvent.ACTION_OUTSIDE:
                case MotionEvent.ACTION_CANCEL:
                    internetConnectionLayout.setAlpha(1f);
                    break;
            }
            return true;
        });
        internetConnectionLayout.setOnClickListener(view1 -> {
            mainActivity.showFragment(previousFragment);
        });
    }
    private void getFragmentType()
    {
        if(previousFragment instanceof HomeBaseFragment )
            fragmentType = HOME_BASE_FRAGMENT;
        else if(previousFragment instanceof SellFragment)
            fragmentType = SELL_FRAGMENT;
        else if(previousFragment instanceof BuyFragment)
            fragmentType = BUY_FRAGMENT;
        else if(previousFragment instanceof  PostFragment)
            fragmentType = POST_FRAGMENT;
        else if(previousFragment instanceof AccountFragment)
            fragmentType = ACCOUNT_FRAGMENT;
        else
            fragmentType = DEFAULT_FRAGMENT;
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    private void loadToolBar()
    {
        getFragmentType();
        switch (fragmentType)
        {
            case HOME_BASE_FRAGMENT:
                setToolBar(toolBarHomeFragment);
                break;
            case SELL_FRAGMENT:
                setToolBar(toolBarSellFragment);
                break;
            case POST_FRAGMENT:
                setToolBar(toolBarPostFragment);
                break;
            case BUY_FRAGMENT:
                setToolBar(toolBarBuyFragment);
                break;
            case ACCOUNT_FRAGMENT:
                setToolBar(toolBarAccountFragment);
                break;
            default:
                setToolBar(toolBarDefault);
        }
    }
    private void setToolBar(LinearLayout toolBar)
    {
        toolBarHomeFragment.setVisibility(View.GONE);
        toolBarBuyFragment.setVisibility(View.GONE);
        toolBarSellFragment.setVisibility(View.GONE);
        toolBarPostFragment.setVisibility(View.GONE);
        toolBarAccountFragment.setVisibility(View.GONE);
        toolBarDefault.setVisibility(View.GONE);
        toolBar.setVisibility(View.VISIBLE);
    }
}