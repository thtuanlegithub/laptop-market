package com.example.laptop_market.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.laptop_market.R;
import com.example.laptop_market.contracts.IOrderContract;
import com.example.laptop_market.model.order.Order;
import com.example.laptop_market.model.order.OrderStatus;
import com.example.laptop_market.model.post.Post;
import com.example.laptop_market.model.post.PostStatus;
import com.example.laptop_market.presenter.activities.OrderDetailActivityPresenter;
import com.example.laptop_market.utils.MyDialog;
import com.example.laptop_market.view.adapters.Sell.SellOrder;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SellOrderDetailActivity extends AppCompatActivity implements IOrderContract.View.OrderDetailsActivityView {
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
    private LinearLayout linearLayoutButtonUpdatePostStatus;
    private LinearLayout linearLayoutSellOrderStatus;
    private LinearLayout linearLayoutFinishTime;
    private TextView txtViewSellOrderStatus;
    private TextView tvSellOrderDetailOrderName;
    private TextView tvSellOrderDetailTotalAmount;
    private TextView tvSellOrderDetailOrderedTime;
    private TextView tvSellOrderDetailFinishedTime;
    private TextView tvSellOrderDetailFinishStatus;
    private AppCompatButton btnShipNowSellOrderDetail;
    private AppCompatButton btnFinishOrderSellOrderDetail;
    private AppCompatButton btnCancelOrderSellOrder;
    private AppCompatButton btnUpdatePostStatusSellOrder;
    private Order fullOrderDetails;
    private Post postOfThisOrder;
    private SellOrder clickedOrder;
    private int currentOrderStatus;
    private IOrderContract.Presenter.OrderDetailActivityPresenter orderDetailActivityPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_order_detail);
        orderDetailActivityPresenter = new OrderDetailActivityPresenter(this, this);

        findViews();

        onCloseClicked();

        onShipNowClicked();

        onFinishOrderClicked();

        onCancelOrderClicked();

        onUpdatePostStatusClicked();

        // Get intent from previous activity/fragment
        Intent intent = getIntent();
        if(intent.hasExtra("SellOrderStatus")){
            currentOrderStatus = intent.getIntExtra("SellOrderStatus",0);
            clickedOrder = (SellOrder) intent.getSerializableExtra("ClickedOrder");
            displayLinearLayoutButton(currentOrderStatus);
            // Load data here
            orderDetailActivityPresenter.LoadOrderInfo(clickedOrder.getOrderId(), clickedOrder.getPostID());
        }
    }

    // region Display view
    private void findViews(){
        edtSellOrderDetailPostServiceName = findViewById(R.id.edtSellOrderDetailPostServiceName);
        edtSellOrderDetailPostServiceCode = findViewById(R.id.edtSellOrderDetailPostServiceCode);
        edtSellOrderDetailPostServiceName.addTextChangedListener(textWatcher);
        edtSellOrderDetailPostServiceCode.addTextChangedListener(textWatcher);

        edtSellOrderDetailBuyerName = findViewById(R.id.edtSellOrderDetailBuyerName);
        edtSellOrderDetailBuyerPhone = findViewById(R.id.edtSellOrderDetailBuyerPhone);
        edtSellOrderDetailBuyerAddress = findViewById(R.id.edtSellOrderDetailBuyerAddress);

        edtSellOrderDetailSellerName = findViewById(R.id.edtSellOrderDetailSellerName);
        edtSellOrderDetailSellerPhone = findViewById(R.id.edtSellOrderDetailSellerPhone);
        edtSellOrderDetailSellerAddress = findViewById(R.id.edtSellOrderDetailSellerAddress);
        edtSellOrderDetailSellerName.addTextChangedListener(textWatcher);
        edtSellOrderDetailSellerPhone.addTextChangedListener(textWatcher);
        edtSellOrderDetailSellerAddress.addTextChangedListener(textWatcher);

        linearLayoutButtonSellProcessing = findViewById(R.id.linearLayoutButtonSellProcessing);
        linearLayoutButtonSellDelivering = findViewById(R.id.linearLayoutButtonSellDelivering);
        linearLayoutButtonUpdatePostStatus = findViewById(R.id.linearLayoutButtonUpdatePostStatus);
        linearLayoutFinishTime = findViewById(R.id.linearLayoutFinishTime);
        linearLayoutSellOrderStatus = findViewById(R.id.linearLayoutSellOrderStatus);

        txtViewSellOrderStatus = findViewById(R.id.txtViewSellOrderStatus);
        tvSellOrderDetailFinishStatus = findViewById(R.id.tvSellOrderDetailFinishStatus);
        tvSellOrderDetailOrderName = findViewById(R.id.tvSellOrderDetailOrderName);
        tvSellOrderDetailTotalAmount = findViewById(R.id.tvSellOrderDetailTotalAmount);
        tvSellOrderDetailOrderedTime = findViewById(R.id.tvSellOrderDetailOrderedTime);
        tvSellOrderDetailFinishedTime = findViewById(R.id.tvSellOrderDetailFinishedTime);
    }
    private void SetGeneralData() {
        // Order info
        tvSellOrderDetailOrderName.setText(clickedOrder.getLaptopName());
        tvSellOrderDetailTotalAmount.setText(fullOrderDetails.getTotalAmount());
        tvSellOrderDetailOrderedTime.setText(fullOrderDetails.getOrderedDate());
        if (fullOrderDetails.getFinishedDate() != null)
            tvSellOrderDetailFinishedTime.setText(fullOrderDetails.getFinishedDate());

        // Delivering info is not initialized yet (Because of processing)
        if (fullOrderDetails.getPostServiceName() != null)
            edtSellOrderDetailPostServiceName.setText(fullOrderDetails.getPostServiceName());
        else
            edtSellOrderDetailPostServiceName.setText("");

        if (fullOrderDetails.getPostServiceCode() != null)
            edtSellOrderDetailPostServiceCode.setText(fullOrderDetails.getPostServiceCode());
        else
            edtSellOrderDetailPostServiceCode.setText("");

        // Buyer info
        edtSellOrderDetailBuyerPhone.setText(fullOrderDetails.getBuyerPhone());
        edtSellOrderDetailBuyerName.setText(fullOrderDetails.getBuyerName());
        edtSellOrderDetailBuyerAddress.setText(fullOrderDetails.getShipAddress());

        // Seller info
        edtSellOrderDetailSellerPhone.setText(postOfThisOrder.getSellerPhoneNumber());
        edtSellOrderDetailSellerName.setText(postOfThisOrder.getSellerName());
        edtSellOrderDetailSellerAddress.setText(postOfThisOrder.getSellerAddress());

        // Button
        String postStatus = postOfThisOrder.getPostStatus();
        if (postStatus.equals(PostStatus.AVAILABLE)){
            btnUpdatePostStatusSellOrder.setText("Hủy tin");
            btnUpdatePostStatusSellOrder.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.cancel_post));
        }
        else {
            btnUpdatePostStatusSellOrder.setText("Đăng lại tin");
            btnUpdatePostStatusSellOrder.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.repost_post));
        }
    }
    private void displayLinearLayoutButton(int SellOrderStatus){
        switch (SellOrderStatus){
            case 0: // Processing
                SetDataForSellProcessing();
                break;
            case 1: // Delivering
                SetDataForSellDelivering();
                break;
            case 2: // Finished
                SetDataForSellFinish();
                break;
            case 3: // Canceled
                SetDataForSellCanceled();
                break;
            default:
                break;
        }
    }
    private void SetDataForSellProcessing () {
        linearLayoutButtonSellProcessing.setVisibility(View.VISIBLE);
        linearLayoutButtonSellDelivering.setVisibility(View.GONE);
        linearLayoutFinishTime.setVisibility(View.GONE);
        linearLayoutButtonUpdatePostStatus.setVisibility(View.GONE);
        txtViewSellOrderStatus.setText("Đang xử lý");
        linearLayoutSellOrderStatus.setBackgroundColor(getResources().getColor(R.color.order_processing,null));
    }
    private void SetDataForSellDelivering () {
        linearLayoutButtonSellProcessing.setVisibility(View.GONE);
        linearLayoutButtonSellDelivering.setVisibility(View.VISIBLE);
        linearLayoutFinishTime.setVisibility(View.GONE);
        linearLayoutButtonUpdatePostStatus.setVisibility(View.GONE);
        edtSellOrderDetailPostServiceName.setFocusable(false);
        edtSellOrderDetailPostServiceCode.setFocusable(false);
        edtSellOrderDetailSellerName.setFocusable(false);
        edtSellOrderDetailSellerPhone.setFocusable(false);
        edtSellOrderDetailSellerAddress.setFocusable(false);
        txtViewSellOrderStatus.setText("Đang giao");
        linearLayoutSellOrderStatus.setBackgroundColor(getResources().getColor(R.color.order_delivering,null));
    }
    private void SetDataForSellFinish() {
        linearLayoutButtonSellProcessing.setVisibility(View.GONE);
        linearLayoutButtonSellDelivering.setVisibility(View.GONE);
        linearLayoutFinishTime.setVisibility(View.VISIBLE);
        linearLayoutButtonUpdatePostStatus.setVisibility(View.VISIBLE);
        edtSellOrderDetailPostServiceName.setFocusable(false);
        edtSellOrderDetailPostServiceCode.setFocusable(false);
        edtSellOrderDetailSellerName.setFocusable(false);
        edtSellOrderDetailSellerPhone.setFocusable(false);
        edtSellOrderDetailSellerAddress.setFocusable(false);
        tvSellOrderDetailFinishStatus.setText("Thời gian hoàn thành: ");
        txtViewSellOrderStatus.setText("Đã hoàn thành");
        linearLayoutSellOrderStatus.setBackgroundColor(getResources().getColor(R.color.order_finish,null));
    }

    private void SetDataForSellCanceled() {
        linearLayoutButtonSellProcessing.setVisibility(View.GONE);
        linearLayoutButtonSellDelivering.setVisibility(View.GONE);
        linearLayoutFinishTime.setVisibility(View.VISIBLE);
        linearLayoutButtonUpdatePostStatus.setVisibility(View.VISIBLE);
        edtSellOrderDetailPostServiceName.setFocusable(false);
        edtSellOrderDetailPostServiceCode.setFocusable(false);
        edtSellOrderDetailSellerName.setFocusable(false);
        edtSellOrderDetailSellerPhone.setFocusable(false);
        edtSellOrderDetailSellerAddress.setFocusable(false);
        tvSellOrderDetailFinishStatus.setText("Thời gian hủy: ");
        txtViewSellOrderStatus.setText("Đã hủy");
        linearLayoutSellOrderStatus.setBackgroundColor(getResources().getColor(R.color.order_cancel,null));
    }
    // endregion

    // region Close order details
    private void onCloseClicked(){
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
    }
    // endregion

    // region Ship order
    private void onShipNowClicked(){
        btnShipNowSellOrderDetail = findViewById(R.id.btnShipNowSellOrderDetail);
        btnShipNowSellOrderDetail.setEnabled(false);
        btnShipNowSellOrderDetail.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));
        btnShipNowSellOrderDetail.setOnClickListener(view -> {
            MyDialog.showDialog(this, "Bạn chắc chắn với các thông tin chưa?", MyDialog.DialogType.YES_NO, new MyDialog.DialogClickListener() {
                @Override
                public void onYesClick() {
                    // Update Status and PostService Data
                    DisplayShipOrderSuccess();
                    String postServiceName = edtSellOrderDetailPostServiceName.getText().toString();
                    String postServiceCode = edtSellOrderDetailPostServiceCode.getText().toString();
                    orderDetailActivityPresenter.UpdateOrderInfo(clickedOrder.getOrderId(), OrderStatus.SHIPPING, postServiceName, postServiceCode, null);
                }

                @Override
                public void onNoClick() {

                }

                @Override
                public void onOkClick() {

                }
            });
        });
    }
    private void DisplayShipOrderSuccess(){
        MyDialog.showDialog(this, "Đơn hàng đã được cập nhật trạng thái thành công.", MyDialog.DialogType.OK, new MyDialog.DialogClickListener() {
            @Override
            public void onYesClick() {
            }

            @Override
            public void onNoClick() {

            }

            @Override
            public void onOkClick() {
                finish();
            }
        });
    }
    // endregion

    // region Finish order
    private void onFinishOrderClicked(){
        btnFinishOrderSellOrderDetail = findViewById(R.id.btnFinishOrderSellOrderDetail);
        btnFinishOrderSellOrderDetail.setOnClickListener(view ->  {
            MyDialog.showDialog(this, "Bạn chắc chắn đã hoàn thành đơn hàng chưa?", MyDialog.DialogType.YES_NO, new MyDialog.DialogClickListener() {
                @Override
                public void onYesClick() {
                    // Update Status and PostService Data
                    DisplayFinishOrderSuccess();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
                    Date currentDateTime = new Date();
                    // Order info
                    String finishedTime = sdf.format(currentDateTime);
                    orderDetailActivityPresenter.UpdateOrderInfo(clickedOrder.getOrderId(), OrderStatus.FINISHED, null, null, finishedTime);
                }

                @Override
                public void onNoClick() {

                }

                @Override
                public void onOkClick() {

                }
            });
        });
    }

    private void DisplayFinishOrderSuccess(){
        MyDialog.showDialog(this, "Đơn hàng đã được giao thành công.", MyDialog.DialogType.OK, new MyDialog.DialogClickListener() {
            @Override
            public void onYesClick() {
            }

            @Override
            public void onNoClick() {

            }

            @Override
            public void onOkClick() {
                finish();
            }
        });
    }
    //endregion

    // region Cancel order
    private void onCancelOrderClicked() {
        btnCancelOrderSellOrder = findViewById(R.id.btnCancelOrderSellOrder);
        btnCancelOrderSellOrder.setOnClickListener(view -> {
            MyDialog.showDialog(this, "Bạn có chắc là muốn hủy đơn hàng không?", MyDialog.DialogType.YES_NO, new MyDialog.DialogClickListener() {
                @Override
                public void onYesClick() {
                    // Update Status and PostService Data
                    DisplayCancelOrderSuccess();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
                    Date currentDateTime = new Date();
                    // Order info
                    String finishedTime = sdf.format(currentDateTime);
                    orderDetailActivityPresenter.UpdateOrderInfo(clickedOrder.getOrderId(), OrderStatus.CANCELED, null, null, finishedTime);
                }

                @Override
                public void onNoClick() {

                }

                @Override
                public void onOkClick() {
                }
            });
        });
    }

    private void DisplayCancelOrderSuccess() {
        MyDialog.showDialog(this, "Đơn hàng đã được hủy thành công.", MyDialog.DialogType.OK, new MyDialog.DialogClickListener() {
            @Override
            public void onYesClick() {
            }

            @Override
            public void onNoClick() {

            }

            @Override
            public void onOkClick() {
                finish();
            }
        });
    }
    // endregion

    // region Update post status
    private void onUpdatePostStatusClicked() {
        btnUpdatePostStatusSellOrder = findViewById(R.id.btnUpdatePostStatusSellOrder);
        btnUpdatePostStatusSellOrder.setOnClickListener(view -> {
            String currentStatus = this.postOfThisOrder.getPostStatus();
            if (currentStatus.equals(PostStatus.AVAILABLE))
                orderDetailActivityPresenter.UpdatePostStatus(clickedOrder.getPostID(), PostStatus.NOT_AVAILABLE);
            else if (currentStatus.equals(PostStatus.NOT_AVAILABLE))
                orderDetailActivityPresenter.UpdatePostStatus(clickedOrder.getPostID(), PostStatus.AVAILABLE);
        });
    }
    // endregion

    // region Override interface
    @Override
    public void DisplayOrderDetailInformation(Order orderInfo, Post post) {
        fullOrderDetails = orderInfo;
        postOfThisOrder = post;
        // Then display UI
        SetGeneralData();
    }

    @Override
    public void DisplayUpdatePostStatus(String currentStatus) {
        if (currentStatus.equals(PostStatus.AVAILABLE)) {
            postOfThisOrder.setPostStatus(PostStatus.AVAILABLE);
            btnUpdatePostStatusSellOrder.setText("Hủy tin");
            btnUpdatePostStatusSellOrder.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.cancel_post));
        } else if (currentStatus.equals(PostStatus.NOT_AVAILABLE)){
            postOfThisOrder.setPostStatus(PostStatus.NOT_AVAILABLE);
            btnUpdatePostStatusSellOrder.setText("Đăng lại tin");
            btnUpdatePostStatusSellOrder.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.repost_post));
        }
    }
    // endregion

    // region Text change event
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Check if three information is filled
            boolean isPostServiceNameFilled = !TextUtils.isEmpty(edtSellOrderDetailPostServiceName.getText());
            boolean isPostServiceCodeFilled = !TextUtils.isEmpty(edtSellOrderDetailPostServiceCode.getText());
            boolean isSellerNameFilled = !TextUtils.isEmpty(edtSellOrderDetailSellerName.getText());
            boolean isSellerPhoneFilled = !TextUtils.isEmpty(edtSellOrderDetailBuyerPhone.getText());
            boolean isSellerAddressFilled = !TextUtils.isEmpty(edtSellOrderDetailSellerAddress.getText());
            // Enable or disable the login button and change its background color
            if (isPostServiceNameFilled && isPostServiceCodeFilled && isSellerNameFilled && isSellerPhoneFilled && isSellerAddressFilled) {
                btnShipNowSellOrderDetail.setEnabled(true);
                btnShipNowSellOrderDetail.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.yellow));
            } else {
                btnShipNowSellOrderDetail.setEnabled(false);
                btnShipNowSellOrderDetail.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    // endregion
}