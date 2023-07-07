package com.example.laptop_market.view.fragments;

import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.laptop_market.R;
import com.google.android.material.textfield.TextInputEditText;


public class AccountPasswordFragment extends Fragment {
    private Button btnAccountPasswordBack;
    private ImageView eyeOldPassword;
    private ImageView eyeNewPassword;
    private ImageView eyeNewPasswordConfirm;
    private TextInputEditText edtAccountSettingOldPassword;
    private TextInputEditText edtAccountSettingNewPassword;
    private TextInputEditText edtAccountSettingNewPasswordConfirm;
    private boolean eyeOldPasswordVisible = false;
    private boolean eyeNewPasswordVisible = false;
    private boolean eyeNewPasswordConfirmVisible = false;
    public AccountBaseFragment accountBaseFragment;
    public AccountPasswordFragment(AccountBaseFragment accountBaseFragment){
        this.accountBaseFragment = accountBaseFragment;
    }
    public AccountPasswordFragment() {
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
        View view = inflater.inflate(R.layout.fragment_account_password, container, false);

        btnAccountPasswordBack = view.findViewById(R.id.btnAccountPasswordBack);
        btnAccountPasswordBack.setOnClickListener(v -> {
            accountBaseFragment.replaceFragment(accountBaseFragment.accountSettingFragment);
        });

        eyeOldPassword = view.findViewById(R.id.eyeOldPassword);
        eyeNewPassword = view.findViewById(R.id.eyeNewPassword);
        eyeNewPasswordConfirm = view.findViewById(R.id.eyeNewPasswordConfirm);

        edtAccountSettingOldPassword = view.findViewById(R.id.edtAccountSettingOldPassword);
        edtAccountSettingNewPassword = view.findViewById(R.id.edtAccountSettingNewPassword);
        edtAccountSettingNewPasswordConfirm = view.findViewById(R.id.edtAccountSettingNewPasswordConfirm);

        eyeOldPassword.setOnClickListener(v -> {
            if(!eyeOldPasswordVisible){
                eyeOldPasswordVisible = true;
                eyeOldPassword.setImageResource(R.drawable.ic_password_eye_orange); // Đặt hình ảnh mắt mở
                edtAccountSettingOldPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }
            else{
                eyeOldPasswordVisible = false;
                eyeOldPassword.setImageResource(R.drawable.ic_password_eye); // Đặt hình ảnh mắt mở
                edtAccountSettingOldPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            edtAccountSettingOldPassword.setSelection(edtAccountSettingOldPassword.getText().length());
        });

        eyeNewPassword.setOnClickListener(v -> {
            if(!eyeNewPasswordVisible){
                eyeNewPasswordVisible = true;
                eyeNewPassword.setImageResource(R.drawable.ic_password_eye_orange); // Đặt hình ảnh mắt mở
                edtAccountSettingNewPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }
            else{
                eyeNewPasswordVisible = false;
                eyeNewPassword.setImageResource(R.drawable.ic_password_eye); // Đặt hình ảnh mắt mở
                edtAccountSettingNewPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            edtAccountSettingNewPassword.setSelection(edtAccountSettingNewPassword.getText().length());
        });

        eyeNewPasswordConfirm.setOnClickListener(v -> {
            if(!eyeNewPasswordConfirmVisible){
                eyeNewPasswordConfirmVisible = true;
                eyeNewPasswordConfirm.setImageResource(R.drawable.ic_password_eye_orange); // Đặt hình ảnh mắt mở
                edtAccountSettingNewPasswordConfirm.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }
            else{
                eyeNewPasswordConfirmVisible = false;
                eyeNewPasswordConfirm.setImageResource(R.drawable.ic_password_eye); // Đặt hình ảnh mắt mở
                edtAccountSettingNewPasswordConfirm.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            edtAccountSettingNewPasswordConfirm.setSelection(edtAccountSettingNewPasswordConfirm.getText().length());
        });

        return view;
    }
}