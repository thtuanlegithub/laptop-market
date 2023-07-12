package com.example.laptop_market.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityOptionsCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.laptop_market.R;
import com.example.laptop_market.contracts.IAccountContract;
import com.example.laptop_market.contracts.IFragmentListener;
import com.example.laptop_market.model.account.Account;
import com.example.laptop_market.presenter.activities.AccountSettingActivityPresenter;
import com.example.laptop_market.utils.MyDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class AccountSettingActivity extends AppCompatActivity implements IAccountContract.View.AccountSettingActivityView {
    private TextInputEditText edtAccountSettingPassword;
    private Account account;
    private IAccountContract.Presenter.AccountSettingActivityPresenter accountSettingActivityPresenter;
    private Button btnAccountSettingBack;
    private EditText edtAccountSettingPhone;
    private EditText edtAccountSettingName;
    private EditText edtAccountSettingAddress;
    private TextInputEditText edtAccountSettingGmail;
    private TextInputEditText edtAccountSettingIntroduction;
    private boolean isUpdate;
    private boolean backFromPassword = false;
    public RelativeLayout rltLayoutAccountSettingPassword;
    private AppCompatButton saveAccountSettingbtn;
    private static final int REQUEST_CODE_FOR_PROFILE = 5;
    private IFragmentListener fragmentListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);
        edtAccountSettingPhone = findViewById(R.id.edtAccountSettingPhone);
        edtAccountSettingName = findViewById(R.id.edtAccountSettingName);
        edtAccountSettingAddress = findViewById(R.id.edtAccountSettingAddress);
        edtAccountSettingGmail = findViewById(R.id.edtAccountSettingGmail);
        btnAccountSettingBack = findViewById(R.id.btnAccountSettingBack);
        edtAccountSettingIntroduction = findViewById(R.id.edtAccountSettingIntroduction);
        rltLayoutAccountSettingPassword = findViewById(R.id.rltLayoutAccountSettingPassword);
        edtAccountSettingPassword = findViewById(R.id.edtAccountSettingPassword);
        saveAccountSettingbtn = findViewById(R.id.saveAccountSettingbtn);
        accountSettingActivityPresenter = new AccountSettingActivityPresenter(this);
        isUpdate = false;
        setListener();
        loadProfileSetting();
    }
    private void setListener()
    {
        btnAccountSettingBack.setOnClickListener(v -> {
            MyDialog.showDialog(this, "Bạn có muốn hủy bỏ thông tin vừa chỉnh sửa?", MyDialog.DialogType.YES_NO, new MyDialog.DialogClickListener() {
                @Override
                public void onYesClick() {
                    Intent intent = new Intent();
                    intent.putExtra("applySlideTransition", true);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                    //Ẩn bàn phím:
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    View currentFocus = getCurrentFocus();
                    if (inputMethodManager != null && currentFocus != null) {
                        inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
                    }
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
        edtAccountSettingPassword.setOnClickListener(v -> {
            Intent intent = new Intent(this,AccountPasswordActivity.class);
            Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(this, R.anim.slide_in_right, R.anim.slide_out_left).toBundle();
            startActivity(intent,bundle);
            backFromPassword = true;
        });
        saveAccountSettingbtn.setOnClickListener(view -> {
            if(edtAccountSettingName.getText().toString().isEmpty())
            {
                Toast.makeText(getApplicationContext(),"Vui lòng nhập tên", Toast.LENGTH_SHORT).show();
            }
            else {
                MyDialog.showDialog(this, "Bạn có chắc các thông tin trên chưa?", MyDialog.DialogType.YES_NO, new MyDialog.DialogClickListener() {
                    @Override
                    public void onYesClick() {
                        account.setAccountName(edtAccountSettingName.getText().toString());
                        account.setDescription(Objects.requireNonNull(edtAccountSettingIntroduction.getText()).toString());
                        account.setPhoneNumber(edtAccountSettingPhone.getText().toString());
                        account.setAddress(edtAccountSettingAddress.getText().toString());
                        accountSettingActivityPresenter.UpdateProfileOnClick(account);
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
            }
        });
    }
    private void loadProfileSetting()
    {
        accountSettingActivityPresenter.LoadAccountSetting();
    }
    public void onResume() {
        super.onResume();
        if(backFromPassword)
        {
            this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            backFromPassword=false;
        }
    }

    @Override
    public void LoadProfileData(Account account) {
        this.account = account;
        edtAccountSettingName.setText(account.getAccountName());
        edtAccountSettingGmail.setText(account.getEmail());
        if(account.getAddress()!=null )
            edtAccountSettingAddress.setText(account.getAddress());
        if(account.getPhoneNumber()!=null)
            edtAccountSettingPhone.setText(account.getPhoneNumber());
        if(account.getDescription()!=null )
            edtAccountSettingIntroduction.setText(account.getDescription());
        setBackCall();
    }

    @Override
    public void Error(Exception e) {
        e.printStackTrace();
    }
    public void ShowToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void UpdateAccountSettingSuccess() {
        ShowToast("Cập nhật thông tin thành công");
        Intent intent = new Intent();
        intent.putExtra("applySlideTransition", true);
        intent.putExtra("AccountInfo", account);
        setResult(Activity.RESULT_OK, intent);
        setResult(REQUEST_CODE_FOR_PROFILE, intent);
        finish();
        //Ẩn bàn phím:
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View currentFocus = getCurrentFocus();
        if (inputMethodManager != null && currentFocus != null) {
            inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
    }
    private void setBackCall()
    {
        edtAccountSettingName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isUpdate = true;
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        edtAccountSettingAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isUpdate = true;
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        edtAccountSettingIntroduction.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isUpdate = true;
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        edtAccountSettingPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isUpdate = true;
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
}