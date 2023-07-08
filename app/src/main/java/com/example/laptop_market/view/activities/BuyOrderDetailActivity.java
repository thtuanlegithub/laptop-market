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
    private LinearLayout linearLayoutButtonConfirmBuying;
    private LinearLayout linearLayoutButtonBuyProcessing;
    private LinearLayout linearLayoutButtonBuyDelivering;
    private LinearLayout linearLayoutButtonBuyFinish;
    private LinearLayout linearLayoutButtonBuyCancel;
    private LinearLayout linearLayoutBuyOrderStatus;
    private LinearLayout linearLayoutShippingInformation1;
    private LinearLayout linearLayoutShippingInformation2;
    private LinearLayout linearLayoutOrderTime;
    private LinearLayout linearLayoutFinishTime;
    private TextView txtViewBuyOrderStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_order_detail);

        btnBuyOrderDetailClose = findViewById(R.id.btnBuyOrderDetailClose);
        btnBuyOrderDetailClose.setOnClickListener(v -> {
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

        edtBuyOrderDetailPostServiceName = findViewById(R.id.edtBuyOrderDetailPostServiceName);
        edtBuyOrderDetailPostServiceCode = findViewById(R.id.edtBuyOrderDetailPostServiceCode);

        edtBuyOrderDetailCustomerName = findViewById(R.id.edtBuyOrderDetailCustomerName);
        edtBuyOrderDetailCustomerPhone = findViewById(R.id.edtBuyOrderDetailCustomerPhone);
        edtBuyOrderDetailCustomerAddress = findViewById(R.id.edtBuyOrderDetailCustomerAddress);

        edtBuyOrderDetailBuyerName = findViewById(R.id.edtBuyOrderDetailBuyerName);
        edtBuyOrderDetailBuyerPhone = findViewById(R.id.edtBuyOrderDetailBuyerPhone);
        edtBuyOrderDetailBuyerAddress = findViewById(R.id.edtBuyOrderDetailBuyerAddress);

        linearLayoutButtonConfirmBuying = findViewById(R.id.linearLayoutButtonConfirmBuying);
        linearLayoutButtonBuyProcessing = findViewById(R.id.linearLayoutButtonBuyProcessing);
        linearLayoutButtonBuyDelivering = findViewById(R.id.linearLayoutButtonBuyDelivering);
        linearLayoutButtonBuyFinish = findViewById(R.id.linearLayoutButtonBuyFinish);
        linearLayoutButtonBuyCancel = findViewById(R.id.linearLayoutButtonBuyCancel);
        linearLayoutShippingInformation1 = findViewById(R.id.linearLayoutShippingInformation1);
        linearLayoutShippingInformation2 = findViewById(R.id.linearLayoutShippingInformation2);
        linearLayoutBuyOrderStatus = findViewById(R.id.linearLayoutBuyOrderStatus);
        linearLayoutOrderTime = findViewById(R.id.linearLayoutOrderTime);
        linearLayoutFinishTime = findViewById(R.id.linearLayoutFinishTime);

        txtViewBuyOrderStatus = findViewById(R.id.txtViewBuyOrderStatus);

        Intent intent = getIntent();
        if(intent.hasExtra("BuyOrderStatus")){
            int BuyOrderStatus = intent.getIntExtra("BuyOrderStatus",0);
            displayLinearLayoutButton(BuyOrderStatus);
        }

    }
    private void displayLinearLayoutButton(int BuyOrderStatus){
        switch (BuyOrderStatus){
            case 0: // Processing Order
                linearLayoutButtonConfirmBuying.setVisibility(View.GONE);
                linearLayoutButtonBuyProcessing.setVisibility(View.VISIBLE);
                linearLayoutButtonBuyDelivering.setVisibility(View.GONE);
                linearLayoutButtonBuyFinish.setVisibility(View.GONE);
                linearLayoutButtonBuyCancel.setVisibility(View.GONE);
                linearLayoutShippingInformation1.setVisibility(View.VISIBLE);
                linearLayoutShippingInformation2.setVisibility(View.VISIBLE);
                linearLayoutOrderTime.setVisibility(View.VISIBLE);
                linearLayoutFinishTime.setVisibility(View.GONE);
                txtViewBuyOrderStatus.setText("Đang xử lý");
                linearLayoutBuyOrderStatus.setBackgroundColor(getResources().getColor(R.color.order_processing,null));
                break;
            case 1: // Shipping Order
                linearLayoutButtonConfirmBuying.setVisibility(View.GONE);
                linearLayoutButtonBuyProcessing.setVisibility(View.GONE);
                linearLayoutButtonBuyDelivering.setVisibility(View.VISIBLE);
                linearLayoutButtonBuyFinish.setVisibility(View.GONE);
                linearLayoutButtonBuyCancel.setVisibility(View.GONE);
                linearLayoutShippingInformation1.setVisibility(View.VISIBLE);
                linearLayoutShippingInformation2.setVisibility(View.VISIBLE);
                linearLayoutOrderTime.setVisibility(View.VISIBLE);
                linearLayoutFinishTime.setVisibility(View.GONE);
                txtViewBuyOrderStatus.setText("Đang giao");
                linearLayoutBuyOrderStatus.setBackgroundColor(getResources().getColor(R.color.order_delivering,null));
                break;
            case 2: // Finished Order
                linearLayoutButtonConfirmBuying.setVisibility(View.GONE);
                linearLayoutButtonBuyProcessing.setVisibility(View.GONE);
                linearLayoutButtonBuyDelivering.setVisibility(View.GONE);
                linearLayoutButtonBuyFinish.setVisibility(View.VISIBLE);
                linearLayoutButtonBuyCancel.setVisibility(View.GONE);
                linearLayoutShippingInformation1.setVisibility(View.VISIBLE);
                linearLayoutShippingInformation2.setVisibility(View.VISIBLE);
                linearLayoutOrderTime.setVisibility(View.VISIBLE);
                linearLayoutFinishTime.setVisibility(View.VISIBLE);
                txtViewBuyOrderStatus.setText("Đã hoàn thành");
                linearLayoutBuyOrderStatus.setBackgroundColor(getResources().getColor(R.color.order_finish,null));
                break;
            case 3: // Cancel Order
                linearLayoutButtonConfirmBuying.setVisibility(View.GONE);
                linearLayoutButtonBuyProcessing.setVisibility(View.GONE);
                linearLayoutButtonBuyDelivering.setVisibility(View.GONE);
                linearLayoutButtonBuyFinish.setVisibility(View.GONE);
                linearLayoutButtonBuyCancel.setVisibility(View.VISIBLE);
                linearLayoutShippingInformation1.setVisibility(View.VISIBLE);
                linearLayoutShippingInformation2.setVisibility(View.VISIBLE);
                linearLayoutOrderTime.setVisibility(View.VISIBLE);
                linearLayoutFinishTime.setVisibility(View.VISIBLE);
                linearLayoutBuyOrderStatus.setBackgroundColor(getResources().getColor(R.color.order_cancel,null));
                txtViewBuyOrderStatus.setText("Đã hủy");
                break;
            case 4: // Confirm buying
                linearLayoutButtonConfirmBuying.setVisibility(View.VISIBLE);
                linearLayoutButtonBuyProcessing.setVisibility(View.GONE);
                linearLayoutButtonBuyDelivering.setVisibility(View.GONE);
                linearLayoutButtonBuyFinish.setVisibility(View.GONE);
                linearLayoutButtonBuyCancel.setVisibility(View.GONE);
                linearLayoutShippingInformation1.setVisibility(View.GONE);
                linearLayoutShippingInformation2.setVisibility(View.GONE);
                linearLayoutOrderTime.setVisibility(View.GONE);
                linearLayoutFinishTime.setVisibility(View.GONE);
                txtViewBuyOrderStatus.setText("Đang chờ xác nhận");
                linearLayoutBuyOrderStatus.setBackgroundColor(getResources().getColor(R.color.order_processing,null));
            default:
                break;
        }
    }
}