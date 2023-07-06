package com.example.laptop_market.view.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.laptop_market.R;
import com.example.laptop_market.contracts.IAccountContract;
import com.example.laptop_market.model.account.Account;
import com.example.laptop_market.presenter.fragments.AccountFragmentPresenter;
import com.example.laptop_market.utils.elses.FragmentActivityType;
import com.example.laptop_market.utils.MyDialog;
import com.example.laptop_market.utils.elses.PreferenceManager;
import com.example.laptop_market.view.activities.LoginActivity;
import com.google.android.material.snackbar.Snackbar;


public class AccountFragment extends Fragment implements IAccountContract.View.AccountFragmentView {
    private ImageView imgAccount;
    private TextView txtAccountName;
    private IAccountContract.Presenter.AccountFragmentPresenter accountFragmentPresenter;
    private AppCompatButton bttLogout, btnSellOrder, btnBuyOrder, btnSavedPost, btnYourRating, btnAccountSettings, btnFeedback;
    private PreferenceManager preferenceManager;
    public AccountBaseFragment accountBaseFragment;
    private boolean isLogin = false;
    public AccountFragment() {
        // Required empty public constructor
    }
    public AccountFragment(AccountBaseFragment accountBaseFragment){
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
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        preferenceManager = new PreferenceManager(getContext());
        imgAccount = view.findViewById(R.id.imgAccount);
        txtAccountName = view.findViewById(R.id.txtAccountName);
        btnSellOrder = view.findViewById(R.id.btnSellOrder);
        btnBuyOrder = view.findViewById(R.id.btnBuyOrder);
        btnSavedPost = view.findViewById(R.id.btnSavedPostList);
        btnYourRating = view.findViewById(R.id.btnYourRating);
        btnAccountSettings = view.findViewById(R.id.btnAccountSettings);
        btnFeedback = view.findViewById(R.id.btnFeedback);
        bttLogout = view.findViewById(R.id.bttLogout);
        accountFragmentPresenter = new AccountFragmentPresenter(this, getContext());
        setListener();
        return view;
    }

    private void setListener()
    {
        Glide.with(this)
                .load(R.drawable.slide_show1)
                .apply(RequestOptions.circleCropTransform())
                .into(imgAccount);
        txtAccountName.setOnClickListener(v -> {
            if(!isLogin) {
                Intent intent = new Intent(this.getActivity(), LoginActivity.class);
                PreferenceManager preferenceManager = new PreferenceManager(getContext());
                preferenceManager.putInt(FragmentActivityType.FRAGMENT_ACTIVITY, FragmentActivityType.ACCOUNT_FRAGMENT);
                startActivity(intent);
            }
            else {
                accountBaseFragment.profileFragment = new ProfileFragment(accountBaseFragment);
                accountBaseFragment.replaceFragment(accountBaseFragment.profileFragment);

            }
        });
        btnSellOrder.setOnClickListener(view -> {
            accountFragmentPresenter.ClickSellOrderStatistic();
        });
        btnBuyOrder.setOnClickListener(view -> {
            accountFragmentPresenter.ClickBuyOrderStatistic();
        });
        btnSavedPost.setOnClickListener(view -> {
            accountFragmentPresenter.ClickSavedPost();
        });
        btnYourRating.setOnClickListener(view -> {
            accountFragmentPresenter.ClickYourRating();
        });
        btnAccountSettings.setOnClickListener(view -> {
            accountFragmentPresenter.ClickAccountSettings();
        });
        btnFeedback.setOnClickListener(view -> {
            // Create feedback activity
        });
        bttLogout.setOnClickListener(view -> {
            MyDialog.showDialog(getContext(), "Bạn có chắc muốn đăng xuất không?", MyDialog.DialogType.YES_NO, new MyDialog.DialogClickListener() {
                @Override
                public void onYesClick() {
                    accountFragmentPresenter.LogoutAccount();
                }

                @Override
                public void onNoClick() {
                    // Do nothing
                }

                @Override
                public void onOkClick() {
                    // Do nothing
                }
            });
        });
    }
    public void ShowToast(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show();
    }
    @Override
    public void LoadAccount(Account account) {
        txtAccountName.setText(account.getAccountName());
        bttLogout.setVisibility(View.VISIBLE);
        isLogin=true;
    }

    @Override
    public void LogoutAccount() {
        ShowToast("Đăng xuất thành công");
        txtAccountName.setText("Đăng nhập / Đăng ký");
        bttLogout.setVisibility(View.GONE);
        isLogin = false;
    }

    @Override
    public void LoadNotLoginAccount() {
        txtAccountName.setText("Đăng nhập / Đăng ký");
        bttLogout.setVisibility(View.GONE);
        isLogin = false;
    }

    @Override
    public void LoginAccount() {
        Intent intent = new Intent(this.getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void LoadSellOrder() {

    }

    @Override
    public void LoadBuyOrder() {

    }

    @Override
    public void LoadSavedPost() {

    }

    @Override
    public void LoadYourRating() {

    }

    @Override
    public void LoadAccountSettings() {
        if(accountBaseFragment.accountSettingFragment==null){
            accountBaseFragment.accountSettingFragment = new AccountSettingFragment(accountBaseFragment);
        }
        accountBaseFragment.replaceFragment(accountBaseFragment.accountSettingFragment);
    }

    @Override
    public void onResume() {
        super.onResume();
        accountFragmentPresenter.LoadAccountStatus();
    }
}