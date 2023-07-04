package com.example.laptop_market.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.laptop_market.R;
import com.google.android.material.textfield.TextInputEditText;

public class BuyOrderDetailActivity extends AppCompatActivity {
    private Button btnBuyOrderDetailClose;
    private TextInputEditText edtBuyOrderDetailPostServiceName;
    private TextInputEditText edtBuyOrderDetailPostServiceCode;
    private TextInputEditText edtBuyOrderDetailCustomerName;
    private TextInputEditText edtBuyOrderDetailCustomerPhone;
    private TextInputEditText edtBuyOrderDetailCustomerAddress;
    private TextInputEditText edtBuyOrderDetailBuyerName;
    private TextInputEditText edtBuyOrderDetailBuyerPhone;
    private TextInputEditText edtBuyOrderDetailBuyerAddress;
    private LinearLayout linearLayoutButtonBuyProcessing;
    private LinearLayout linearLayoutButtonBuyDelivering;
    private LinearLayout linearLayoutButtonBuyFinish;
    private LinearLayout linearLayoutButtonBuyCancel;
    private LinearLayout linearLayoutBuyOrderStatus;
    private TextView txtViewBuyOrderStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_order_detail);

        btnBuyOrderDetailClose = findViewById(R.id.btnBuyOrderDetailClose);
        btnBuyOrderDetailClose.setOnClickListener(v -> {
            finish();
            //Ẩn bàn phím:
            InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            View currentFocus = this.getCurrentFocus();
            if (inputMethodManager != null && currentFocus != null) {
                inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
            }
        });

        edtBuyOrderDetailPostServiceName = findViewById(R.id.edtBuyOrderDetailPostServiceName);
        edtBuyOrderDetailPostServiceCode = findViewById(R.id.edtBuyOrderDetailPostServiceCode);

        edtBuyOrderDetailCustomerName = findViewById(R.id.edtBuyOrderDetailCustomerName);
        edtBuyOrderDetailCustomerPhone = findViewById(R.id.edtBuyOrderDetailCustomerPhone);
        edtBuyOrderDetailCustomerAddress = findViewById(R.id.edtBuyOrderDetailCustomerAddress);

        edtBuyOrderDetailBuyerName = findViewById(R.id.edtBuyOrderDetailBuyerName);
        edtBuyOrderDetailBuyerPhone = findViewById(R.id.edtBuyOrderDetailBuyerPhone);
        edtBuyOrderDetailBuyerAddress = findViewById(R.id.edtBuyOrderDetailBuyerAddress);

        linearLayoutButtonBuyProcessing = findViewById(R.id.linearLayoutButtonBuyProcessing);
        linearLayoutButtonBuyDelivering = findViewById(R.id.linearLayoutButtonBuyDelivering);
        linearLayoutButtonBuyFinish = findViewById(R.id.linearLayoutButtonBuyFinish);
        linearLayoutButtonBuyCancel = findViewById(R.id.linearLayoutButtonBuyCancel);

        linearLayoutBuyOrderStatus = findViewById(R.id.linearLayoutBuyOrderStatus);
        txtViewBuyOrderStatus = findViewById(R.id.txtViewBuyOrderStatus);

        Intent intent = getIntent();
        if(intent.hasExtra("BuyOrderStatus")){
            int BuyOrderStatus = intent.getIntExtra("BuyOrderStatus",0);
            displayLinearLayoutButton(BuyOrderStatus);
        }


    }
    private void displayLinearLayoutButton(int BuyOrderStatus){
        switch (BuyOrderStatus){
            case 0:
                linearLayoutButtonBuyProcessing.setVisibility(View.VISIBLE);
                linearLayoutButtonBuyDelivering.setVisibility(View.GONE);
                linearLayoutButtonBuyFinish.setVisibility(View.GONE);
                linearLayoutButtonBuyCancel.setVisibility(View.GONE);
                txtViewBuyOrderStatus.setText("Đang xử lý");
                linearLayoutBuyOrderStatus.setBackgroundColor(getResources().getColor(R.color.order_processing,null));
                break;
            case 1:
                linearLayoutButtonBuyProcessing.setVisibility(View.GONE);
                linearLayoutButtonBuyDelivering.setVisibility(View.VISIBLE);
                linearLayoutButtonBuyFinish.setVisibility(View.GONE);
                linearLayoutButtonBuyCancel.setVisibility(View.GONE);
                txtViewBuyOrderStatus.setText("Đang giao");
                linearLayoutBuyOrderStatus.setBackgroundColor(getResources().getColor(R.color.order_delivering,null));
                break;
            case 2:
                linearLayoutButtonBuyProcessing.setVisibility(View.GONE);
                linearLayoutButtonBuyDelivering.setVisibility(View.GONE);
                linearLayoutButtonBuyFinish.setVisibility(View.VISIBLE);
                linearLayoutButtonBuyCancel.setVisibility(View.GONE);
                txtViewBuyOrderStatus.setText("Đã hoàn thành");
                linearLayoutBuyOrderStatus.setBackgroundColor(getResources().getColor(R.color.order_finish,null));
                break;
            case 3:
                linearLayoutButtonBuyProcessing.setVisibility(View.GONE);
                linearLayoutButtonBuyDelivering.setVisibility(View.GONE);
                linearLayoutButtonBuyFinish.setVisibility(View.GONE);
                linearLayoutButtonBuyCancel.setVisibility(View.VISIBLE);
                linearLayoutBuyOrderStatus.setBackgroundColor(getResources().getColor(R.color.order_cancel,null));
                txtViewBuyOrderStatus.setText("Đã hủy");
                break;
            default:
                break;
        }
    }
}