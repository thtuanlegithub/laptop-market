package com.example.laptop_market.view.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
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
import com.example.laptop_market.model.account.Account;
import com.example.laptop_market.presenter.fragments.LoginFragmentPresenter;
import com.example.laptop_market.utils.elses.FragmentActivityType;
import com.example.laptop_market.utils.elses.PreferenceManager;
import com.example.laptop_market.utils.elses.ValidateData;
import com.example.laptop_market.view.activities.LoginActivity;
import com.example.laptop_market.view.activities.MainActivity;
import com.example.laptop_market.view.activities.NewPostActivity;
import com.google.android.material.snackbar.Snackbar;


public class LoginFragment extends Fragment implements IAccountContract.View.LoginFragmentView{
    public LoginActivity loginActivity;
    private Button btnLoginBack;
    private TextView txtSignUp;
    private TextView txtForgotPassword;
    private AppCompatButton bttLogin;
    private TextView txtPassword;
    private TextView txtEmail;
    private PreferenceManager preferenceManager;
    private IAccountContract.Presenter.LoginFragmentPresenter presenter;
    private int Previous_Intent;
    public LoginFragment(LoginActivity loginActivity) {
        // Required empty public constructor
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        btnLoginBack = view.findViewById(R.id.btnLoginBack);
        txtSignUp = view.findViewById(R.id.txtSignUp);
        txtForgotPassword = view.findViewById(R.id.txtForgotPassword);
        bttLogin = view.findViewById(R.id.bttLogin);
        txtPassword = view.findViewById(R.id.txtPasswordLogin);
        txtEmail = view.findViewById(R.id.txtEmailLogin);
        preferenceManager = new PreferenceManager(getContext());
        presenter = new LoginFragmentPresenter(this, getContext());

        // Disable button
        bttLogin.setEnabled(false);
        bttLogin.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.gray));

        // Add event for edit text
        txtEmail.addTextChangedListener(textWatcher);
        txtPassword.addTextChangedListener(textWatcher);
        setListener();
        return view;
    }
    private void setListener()
    {
        btnLoginBack.setOnClickListener(v -> {
            loginActivity.finish();
        });

        txtSignUp.setOnClickListener(v -> {
            if(loginActivity.signUpFragment==null)
            {
                loginActivity.signUpFragment = new SignUpFragment(loginActivity);
            }
            loginActivity.replaceFragment(loginActivity.signUpFragment);
        });

        txtForgotPassword.setOnClickListener(v -> {
            if (loginActivity.forgotPasswordFragment == null){
                loginActivity.forgotPasswordFragment = new ForgotPasswordFragment(loginActivity);
            }
            loginActivity.replaceFragment(loginActivity.forgotPasswordFragment);
        });

        bttLogin.setOnClickListener(view -> {
            String email = txtEmail.getText().toString();
            String password = txtPassword.getText().toString();
            boolean isValidEmail = ValidateData.isValidEmail(email);
            if(!isValidEmail){
                Toast.makeText(getContext(), "Vui lòng kiểm tra lại địa chỉ email", Toast.LENGTH_SHORT).show();
            }
            else {
                Account account = new Account();
                account.setEmail(email);
                account.setPassword(password);
                presenter.LoginButtonClicked(account);
            }
        });
    }

    //region Validate data format
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Check if both email and password fields are filled
            boolean isEmailFilled = !TextUtils.isEmpty(txtEmail.getText());
            boolean isPasswordFilled = !TextUtils.isEmpty(txtPassword.getText());

            // Enable or disable the login button and change its background color
            if (isEmailFilled && isPasswordFilled) {
                bttLogin.setEnabled(true);
                bttLogin.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.yellow));
            } else {
                bttLogin.setEnabled(false);
                bttLogin.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.gray));
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    //endregion

    @Override
    public void LoginSuccess() {
        preferenceManager = new PreferenceManager(getContext());
        Intent intent = null;
        int PreviousFragment = preferenceManager.getInt(FragmentActivityType.FRAGMENT_ACTIVITY,0);
        switch (PreviousFragment)
        {
            case FragmentActivityType.NEW_POST_ACTIVITY:
                intent = new Intent(getContext(), NewPostActivity.class);
                break;
            case FragmentActivityType.NOTIFICATION_ACTIVITY:

                break;
            case FragmentActivityType.CHAT_ACTIVITY:

                break;
            case FragmentActivityType.SELL_ORDER_STATISTIC_ACTIVITY:

                break;
            case FragmentActivityType.BUY_ORDER_STATISTIC_ACTIVITY:

                break;
            case FragmentActivityType.SAVED_POST_ACTIVITY:

                break;
            case FragmentActivityType.YOUR_RATING_ACTIVITY:

                break;
            case FragmentActivityType.ACCOUNT_SETTINGS_ACTIVITY:

                break;
            default:
                intent = new Intent(getContext(), MainActivity.class);
        }
        preferenceManager.putInt(FragmentActivityType.FRAGMENT_ACTIVITY,0);
        startActivity(intent);
        loginActivity.finish();
    }

    @Override
    public void LoginFailed(String message) {
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }
}