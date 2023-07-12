package com.example.laptop_market.view.fragments;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptop_market.R;
import com.example.laptop_market.contracts.IAccountContract;
import com.example.laptop_market.presenter.fragments.AuthenticationFragmentPresenter;
import com.example.laptop_market.utils.MyDialog;
import com.example.laptop_market.view.activities.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AuthenticationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AuthenticationFragment extends Fragment implements IAccountContract.View.AuthenticationFragmentView {
    private LoginActivity loginActivity;
    private CountDownTimer timer;
    private Button btnAuthenticationBack;
    private TextView timeResentEmailTextView;
    private TextView backToLoginTextView;
    private AppCompatButton sendEmailBtn;
    private LinearLayout layoutTimeLeft;
    private TextView VerifiedEmailText;
    private int numberOfSec;
    private IAccountContract.Presenter.AuthenticationFragmentPresenter presenter;
    public AuthenticationFragment(LoginActivity loginActivity) {
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
        View view = inflater.inflate(R.layout.fragment_authentication, container, false);
        timeResentEmailTextView = view.findViewById(R.id.timeResentEmailTextView);
        btnAuthenticationBack = view.findViewById(R.id.btnAuthenticationBack);
        backToLoginTextView = view.findViewById(R.id.backToLoginTextView);
        layoutTimeLeft = view.findViewById(R.id.layoutTimeLeft);
        VerifiedEmailText = view.findViewById(R.id.VerifiedEmailText);
        sendEmailBtn = view.findViewById(R.id.sendEmailBtn);
        btnAuthenticationBack.setOnClickListener(v -> {
            loginActivity.replaceFragment(loginActivity.signUpFragment);
        });

        numberOfSec = 60;
        timer = new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long l) {
                timeResentEmailTextView.setText(String.valueOf(--numberOfSec));
                presenter.CheckVerifiedEmail();
            }

            @Override
            public void onFinish() {
                enableSendEmailButton();
            }
        };
        setListener();
        presenter = new AuthenticationFragmentPresenter(this);
        presenter.SendEmail();
        timer.start();
        return view;
    }
    private void setListener()
    {
        backToLoginTextView.setOnClickListener(view -> {
            loginActivity.replaceFragment(loginActivity.loginFragment);
            loginActivity.authenticationFragment = null;
        });
        sendEmailBtn.setOnClickListener(view -> {
            presenter.SendEmail();
            numberOfSec = 60;
            timer.start();
            disableSendEmailButton();
        });
    }
    private void enableSendEmailButton()
    {
        sendEmailBtn.setEnabled(true);
        sendEmailBtn.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.yellow));

    }
    private void disableSendEmailButton(){
        sendEmailBtn.setEnabled(false);
        sendEmailBtn.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.gray));
    }
    @Override
    public void sendEmailSuccess() {
        Toast.makeText(loginActivity, "Vui lòng kiểm tra email đã gửi", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void CheckVerifiedEmailSuccess() {
        timer.cancel();
        disableSendEmailButton();
        layoutTimeLeft.setVisibility(View.GONE);
        MyDialog.showDialog(getContext(), "Xác thực email thành công", MyDialog.DialogType.OK, new MyDialog.DialogClickListener() {
            @Override
            public void onYesClick() {

            }

            @Override
            public void onNoClick() {

            }

            @Override
            public void onOkClick() {
                loginActivity.replaceFragment(loginActivity.loginFragment);
                loginActivity.authenticationFragment = null;
            }
        });

    }
}