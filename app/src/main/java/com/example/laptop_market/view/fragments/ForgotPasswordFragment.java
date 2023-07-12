package com.example.laptop_market.view.fragments;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptop_market.R;
import com.example.laptop_market.contracts.IAccountContract;
import com.example.laptop_market.presenter.fragments.ForgotPasswordFragmentPresenter;
import com.example.laptop_market.utils.MyDialog;
import com.example.laptop_market.utils.elses.ValidateData;
import com.example.laptop_market.view.activities.LoginActivity;

public class ForgotPasswordFragment extends Fragment implements IAccountContract.View.ForgotPasswordFragmentView {
    private LoginActivity loginActivity;
    private Button btnForgotPasswordBack;
    private Button btnConfirmEmail;
    private TextView edtEmailForgotPassword;
    private IAccountContract.Presenter.ForgotPasswordFragmentPresenter presenter;
    public ForgotPasswordFragment(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);

        btnForgotPasswordBack = view.findViewById(R.id.btnForgotPasswordBack);
        btnConfirmEmail = view.findViewById(R.id.btnConfirmEmail);
        edtEmailForgotPassword = view.findViewById(R.id.edtEmailForgotPassword);
        presenter = new ForgotPasswordFragmentPresenter(this);
        setListener();
        return view;
    }
    private void setListener()
    {
        btnForgotPasswordBack.setOnClickListener(v -> {
            loginActivity.replaceFragment(loginActivity.loginFragment);
        });

        btnConfirmEmail.setOnClickListener(v -> {
            boolean isValidEmail = ValidateData.isValidEmail(edtEmailForgotPassword.getText().toString());
            if(!isValidEmail)
            {
                ShowError("Vui lòng kiểm tra lại địa chỉ email");
            }
            else
                presenter.SendEmailForgot(edtEmailForgotPassword.getText().toString());
        });
        edtEmailForgotPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                boolean isEmailFilled = !TextUtils.isEmpty(edtEmailForgotPassword.getText());
                if (isEmailFilled) {
                    btnConfirmEmail.setEnabled(true);
                    btnConfirmEmail.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.yellow));
                } else {
                    btnConfirmEmail.setEnabled(false);
                    btnConfirmEmail.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.gray));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    @Override
    public void SendEmailForgotPasswordSuccess() {
        MyDialog.showDialog(getContext(), "Vui lòng kiểm tra thư đặt lại mật khẩu được gửi đến email của bạn", MyDialog.DialogType.OK, new MyDialog.DialogClickListener() {
            @Override
            public void onYesClick() {

            }

            @Override
            public void onNoClick() {

            }

            @Override
            public void onOkClick() {
                loginActivity.replaceFragment(loginActivity.loginFragment);
                loginActivity.forgotPasswordFragment = null;
            }
        });
    }
    public void ShowError(String message) {
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void SendEmailForgotPasswordFailed() {
        Toast.makeText(loginActivity, "Gửi email thất bại", Toast.LENGTH_SHORT).show();
    }
}