package com.example.laptop_market.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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

public class SellOrderDetailActivity extends AppCompatActivity {
    private Button btnSellOrderDetailClose;
    private TextInputEditText edtSellOrderDetailPostServiceName;
    private TextInputEditText edtSellOrderDetailPostServiceCode;
    private TextInputEditText edtSellOrderDetailBuyerName;
    private TextInputEditText edtSellOrderDetailBuyerPhone;
    private TextInputEditText edtSellOrderDetailBuyerAddress;
    private TextInputEditText edtSellOrderDetailSellerName;
    private TextInputEditText edtSellOrderDetailSellerPhone;
    private TextInputEditText edtSellOrderDetailSellerAddress;
    private LinearLayout linearLayoutButtonSellProcessing;
    private LinearLayout linearLayoutButtonSellDelivering;
    private LinearLayout linearLayoutButtonSellFinish;
    private LinearLayout linearLayoutButtonSellCancel;
    private LinearLayout linearLayoutSellOrderStatus;
    private LinearLayout linearLayoutFinishTime;
    private TextView txtViewSellOrderStatus;
    private TextView tvSellOrderDetailFinishStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_order_detail);

        btnSellOrderDetailClose = findViewById(R.id.btnSellOrderDetailClose);
        btnSellOrderDetailClose.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("applySlideTransition", false);
            setResult(Activity.RESULT_OK, intent);
            finish();
            //Ẩn bàn phím:
            InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            View currentFocus = this.getCurrentFocus();
            if (inputMethodManager != null && currentFocus != null) {
                inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
            }
        });

        edtSellOrderDetailPostServiceName = findViewById(R.id.edtSellOrderDetailPostServiceName);
        edtSellOrderDetailPostServiceCode = findViewById(R.id.edtSellOrderDetailPostServiceCode);

        edtSellOrderDetailBuyerName = findViewById(R.id.edtSellOrderDetailBuyerName);
        edtSellOrderDetailBuyerPhone = findViewById(R.id.edtSellOrderDetailBuyerPhone);
        edtSellOrderDetailBuyerAddress = findViewById(R.id.edtSellOrderDetailBuyerAddress);

        edtSellOrderDetailSellerName = findViewById(R.id.edtSellOrderDetailSellerName);
        edtSellOrderDetailSellerPhone = findViewById(R.id.edtSellOrderDetailSellerPhone);
        edtSellOrderDetailSellerAddress = findViewById(R.id.edtSellOrderDetailSellerAddress);

        linearLayoutButtonSellProcessing = findViewById(R.id.linearLayoutButtonSellProcessing);
        linearLayoutButtonSellDelivering = findViewById(R.id.linearLayoutButtonSellDelivering);
        linearLayoutButtonSellFinish = findViewById(R.id.linearLayoutButtonSellFinish);
        linearLayoutButtonSellCancel = findViewById(R.id.linearLayoutButtonSellCancel);
        linearLayoutFinishTime = findViewById(R.id.linearLayoutFinishTime);

        linearLayoutSellOrderStatus = findViewById(R.id.linearLayoutSellOrderStatus);
        txtViewSellOrderStatus = findViewById(R.id.txtViewSellOrderStatus);
        tvSellOrderDetailFinishStatus = findViewById(R.id.tvSellOrderDetailFinishStatus);

        Intent intent = getIntent();
        if(intent.hasExtra("SellOrderStatus")){
            int SellOrderStatus = intent.getIntExtra("SellOrderStatus",0);
            displayLinearLayoutButton(SellOrderStatus);
        }


    }
    private void displayLinearLayoutButton(int SellOrderStatus){
        switch (SellOrderStatus){
            case 0: // Processing
                linearLayoutButtonSellProcessing.setVisibility(View.VISIBLE);
                linearLayoutButtonSellDelivering.setVisibility(View.GONE);
                linearLayoutButtonSellFinish.setVisibility(View.GONE);
                linearLayoutButtonSellCancel.setVisibility(View.GONE);
                linearLayoutFinishTime.setVisibility(View.GONE);
                txtViewSellOrderStatus.setText("Đang xử lý");
                linearLayoutSellOrderStatus.setBackgroundColor(getResources().getColor(R.color.order_processing,null));
                break;
            case 1: // Delivering
                linearLayoutButtonSellProcessing.setVisibility(View.GONE);
                linearLayoutButtonSellDelivering.setVisibility(View.VISIBLE);
                linearLayoutButtonSellFinish.setVisibility(View.GONE);
                linearLayoutButtonSellCancel.setVisibility(View.GONE);
                linearLayoutFinishTime.setVisibility(View.GONE);
                edtSellOrderDetailPostServiceName.setFocusable(false);
                edtSellOrderDetailPostServiceCode.setFocusable(false);
                edtSellOrderDetailSellerName.setFocusable(false);
                edtSellOrderDetailSellerPhone.setFocusable(false);
                edtSellOrderDetailSellerAddress.setFocusable(false);
                txtViewSellOrderStatus.setText("Đang giao");
                linearLayoutSellOrderStatus.setBackgroundColor(getResources().getColor(R.color.order_delivering,null));
                break;
            case 2: // Finished
                linearLayoutButtonSellProcessing.setVisibility(View.GONE);
                linearLayoutButtonSellDelivering.setVisibility(View.GONE);
                linearLayoutButtonSellFinish.setVisibility(View.VISIBLE);
                linearLayoutButtonSellCancel.setVisibility(View.GONE);
                linearLayoutFinishTime.setVisibility(View.VISIBLE);
                edtSellOrderDetailPostServiceName.setFocusable(false);
                edtSellOrderDetailPostServiceCode.setFocusable(false);
                edtSellOrderDetailSellerName.setFocusable(false);
                edtSellOrderDetailSellerPhone.setFocusable(false);
                edtSellOrderDetailSellerAddress.setFocusable(false);
                tvSellOrderDetailFinishStatus.setText("Thời gian hoàn thành: ");
                txtViewSellOrderStatus.setText("Đã hoàn thành");
                linearLayoutSellOrderStatus.setBackgroundColor(getResources().getColor(R.color.order_finish,null));
                break;
            case 3: // Canceled
                linearLayoutButtonSellProcessing.setVisibility(View.GONE);
                linearLayoutButtonSellDelivering.setVisibility(View.GONE);
                linearLayoutButtonSellFinish.setVisibility(View.GONE);
                linearLayoutButtonSellCancel.setVisibility(View.VISIBLE);
                linearLayoutFinishTime.setVisibility(View.VISIBLE);
                edtSellOrderDetailPostServiceName.setFocusable(false);
                edtSellOrderDetailPostServiceCode.setFocusable(false);
                edtSellOrderDetailSellerName.setFocusable(false);
                edtSellOrderDetailSellerPhone.setFocusable(false);
                edtSellOrderDetailSellerAddress.setFocusable(false);
                tvSellOrderDetailFinishStatus.setText("Thời gian hủy: ");
                txtViewSellOrderStatus.setText("Đã hủy");
                linearLayoutSellOrderStatus.setBackgroundColor(getResources().getColor(R.color.order_cancel,null));
                break;
            default:
                break;
        }
    }
}