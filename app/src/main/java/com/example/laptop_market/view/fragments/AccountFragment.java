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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.laptop_market.R;
import com.example.laptop_market.contracts.IAccountContract;
import com.example.laptop_market.model.account.Account;
import com.example.laptop_market.presenter.fragments.AccountFragmentPresenter;
import com.example.laptop_market.utils.MyDialog;
import com.example.laptop_market.utils.PreferenceManager;
import com.example.laptop_market.view.activities.LoginActivity;


public class AccountFragment extends Fragment implements IAccountContract.View.AccountFragmentView {
    private ImageView imgAccount;
    private TextView txtAccountName;
    private IAccountContract.Presenter.AccountFragmentPresenter presenter;
    private AppCompatButton bttLogout;
    private PreferenceManager preferenceManager;
    private boolean isLogin = false;
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
        preferenceManager = new PreferenceManager(getContext());
        imgAccount = view.findViewById(R.id.imgAccount);
        txtAccountName = view.findViewById(R.id.txtAccountName);
        bttLogout = view.findViewById(R.id.bttLogout);
        presenter = new AccountFragmentPresenter(this, getContext());
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
                startActivity(intent);
            }
        });
        bttLogout.setOnClickListener(view -> {
            MyDialog.showDialog(getContext(), "Bạn có chắc muốn đăng xuất không?", MyDialog.DialogType.YES_NO, new MyDialog.DialogClickListener() {
                @Override
                public void onYesClick() {
                    presenter.LogoutAccount();
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
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
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
    public void onResume() {
        super.onResume();
        presenter.LoadAccountStatus();
    }
}