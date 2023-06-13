package com.example.laptop_market.view.fragments;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptop_market.R;
import com.example.laptop_market.contracts.IAccountContract;
import com.example.laptop_market.model.Account.Account;
import com.example.laptop_market.presenter.fragment.LoginFragmentPresenter;
import com.example.laptop_market.presenter.fragment.SignUpFragmentPresenter;
import com.example.laptop_market.ultilities.PreferenceManager;
import com.example.laptop_market.view.activities.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment implements IAccountContract.View.SignUpFragmentView{
    private Button btnSignUpBack;
    private AppCompatButton btnSignUp;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextName;
    private TextView txtLogin;
    public LoginActivity loginActivity;
    private PreferenceManager preferenceManager;
    private IAccountContract.Presenter.SignUpFragmentPresenter presenter;
    private static final int ENTER_EMAIL = 1;
    private static final int INVALID_EMAIL = 2;
    private static final int ENTER_PASSWORD = 3;
    private static final int INVALID_PASSWORD = 4;
    private static final int ENTER_NAME = 5;
    private static final int VALID_EMAIL = 6;

    public SignUpFragment(LoginActivity loginActivity) {
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
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        btnSignUpBack = view.findViewById(R.id.btnSignUpBack);
        btnSignUp = view.findViewById(R.id.btnSignUp);
        txtLogin = view.findViewById(R.id.txtLogin);
        editTextName = view.findViewById(R.id.editTextName);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        btnSignUp = view.findViewById(R.id.btnSignUp);
        preferenceManager = new PreferenceManager(getContext());
        presenter = new SignUpFragmentPresenter(this,preferenceManager);
        setListener();
        return view;
    }
    private void setListener()
    {
        btnSignUpBack.setOnClickListener(v -> {
            loginActivity.replaceFragment(loginActivity.loginFragment);
        });

        btnSignUp.setOnClickListener(v -> {
            if(loginActivity.authenticationFragment==null)
            {
                loginActivity.authenticationFragment = new AuthenticationFragment(loginActivity);
            }
            loginActivity.replaceFragment(loginActivity.authenticationFragment);
        });

        txtLogin.setOnClickListener(v -> {
            loginActivity.replaceFragment(loginActivity.loginFragment);
        });
        btnSignUp.setOnClickListener(v->{
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();
            String name = editTextName.getText().toString();
            int caseHappen = isValidLogin(email, password, name);
            if(caseHappen == ENTER_NAME)
                ShowError("Vui lòng nhập tên người dùng");
            else if (caseHappen == ENTER_EMAIL)
                ShowError("Vui lòng nhập email");
            else if (caseHappen == INVALID_EMAIL)
                ShowError("Vui lòng nhập lại email");
            else if (caseHappen == ENTER_PASSWORD)
                ShowError("Vui lòng nhập password");
            else if (caseHappen == INVALID_PASSWORD)
                ShowError("Mật khẩu phải lớn hơn 6 ký tự");
            else {
                Account account = new Account();
                account.setEmail(email);
                account.setPassword(password);
                account.setAccountName(name);
                presenter.createAccountClicked(account);
            }
        });
    }
    private int isValidLogin(String email, String password,String name)
    {
        if(name.trim().isEmpty())
        {
            return ENTER_NAME;
        }
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
        else if(password.length()<6)
        {
            return INVALID_PASSWORD;
        }
        else
            return VALID_EMAIL;
    }

    @Override
    public void ShowError(String message) {
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void RegisterSuccess() {
        Toast.makeText(getContext(),"Success",Toast.LENGTH_SHORT).show();
        if(loginActivity.authenticationFragment==null)
        {
            loginActivity.authenticationFragment = new AuthenticationFragment(loginActivity);
        }
        loginActivity.replaceFragment(loginActivity.authenticationFragment);
    }
}