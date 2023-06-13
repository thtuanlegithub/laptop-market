package com.example.laptop_market.view.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
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
import com.example.laptop_market.utils.PreferenceManager;
import com.example.laptop_market.view.activities.LoginActivity;
import com.example.laptop_market.view.activities.MainActivity;


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
    private static final int ENTER_EMAIL = 1;
    private static final int INVALID_EMAIL = 2;
    private static final int ENTER_PASSWORD = 3;
    private static final int VALID_EMAIL = 4;
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
        presenter = new LoginFragmentPresenter(this,preferenceManager);
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
            if(isValidLogin(email,password) == ENTER_EMAIL)
                LoginFailed("Vui lòng nhập email");
            else if(isValidLogin(email,password) == INVALID_EMAIL)
                LoginFailed("Vui lòng nhập lại email");
            else if(isValidLogin(email,password) == ENTER_PASSWORD)
                LoginFailed("Vui lòng nhập password");
            else {
                Account account = new Account();
                account.setEmail(email);
                account.setPassword(password);
                presenter.LoginButtonClicked(account);
            }
        });
    }

    //region Function check valid email and password before Login
    private int isValidLogin(String email, String password)
    {
        if(email.trim().isEmpty())
        {
            return ENTER_EMAIL;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {

            return INVALID_EMAIL;
        }
        else if(password.trim().isEmpty())
        {
            return ENTER_PASSWORD;
        }
        return VALID_EMAIL;
    }
    // endregion
    @Override
    public void LoginSuccess() {
        Toast.makeText(getContext(), "Login success", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void LoginFailed(String message) {
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }
}