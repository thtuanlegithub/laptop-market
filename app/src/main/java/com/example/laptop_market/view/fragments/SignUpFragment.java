package com.example.laptop_market.view.fragments;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptop_market.R;
import com.example.laptop_market.contracts.IAccountContract;
import com.example.laptop_market.model.account.Account;
import com.example.laptop_market.presenter.fragments.SignUpFragmentPresenter;
import com.example.laptop_market.utils.elses.ValidateData;
import com.example.laptop_market.view.activities.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
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
    private IAccountContract.Presenter.SignUpFragmentPresenter presenter;

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
        presenter = new SignUpFragmentPresenter(this,getContext());

        // Disable button
        btnSignUp.setEnabled(false);
        btnSignUp.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.gray));

        // Add event for edit text
        editTextName.addTextChangedListener(textWatcher);
        editTextEmail.addTextChangedListener(textWatcher);
        editTextPassword.addTextChangedListener(textWatcher);
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
            boolean isValidEmail = ValidateData.isValidEmail(email);
            if(!isValidEmail){
                ShowError("Vui lòng kiểm tra lại địa chỉ email");
            }
            else {
                Account account = new Account();
                account.setEmail(email);
                account.setPassword(password);
                account.setAccountName(name);
                presenter.createAccountClicked(account);
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
            boolean isEmailFilled = !TextUtils.isEmpty(editTextEmail.getText());
            boolean isPasswordFilled = editTextPassword.length() >= 6;
            boolean isNameFilled = !TextUtils.isEmpty(editTextName.getText());
            // Enable or disable the login button and change its background color
            if (isEmailFilled && isPasswordFilled && isNameFilled) {
                btnSignUp.setEnabled(true);
                btnSignUp.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.yellow));
            } else {
                btnSignUp.setEnabled(false);
                btnSignUp.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.gray));
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    //endregion

    @Override
    public void ShowError(String message) {
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void RegisterSuccess() {
        Toast.makeText(getContext(),"",Toast.LENGTH_SHORT).show();
        if(loginActivity.authenticationFragment==null)
        {
            loginActivity.authenticationFragment = new AuthenticationFragment(loginActivity);
        }
        loginActivity.replaceFragment(loginActivity.authenticationFragment);
    }
}