package com.example.laptop_market.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.example.laptop_market.model.account.CurrentBuyer;
import com.example.laptop_market.model.order.Order;
import com.example.laptop_market.model.order.OrderStatus;
import com.example.laptop_market.model.post.Post;
import com.example.laptop_market.presenter.activities.BuyOrderDetailActivityPresenter;
import com.example.laptop_market.utils.MyDialog;
import com.example.laptop_market.utils.tables.PostTable;
import com.example.laptop_market.view.adapters.PostSearchResult.PostSearchResult;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BuyOrderDetailActivity extends AppCompatActivity implements IOrderContract.View.BuyOrderDetailActivityView {
    private Button btnBuyOrderDetailClose;
    private TextInputEditText edtBuyOrderDetailPostServiceName;
    private TextInputEditText edtBuyOrderDetailPostServiceCode;
    private TextInputEditText edtBuyOrderDetailSellerName;
    private TextInputEditText edtBuyOrderDetailSellerPhone;
    private TextInputEditText edtBuyOrderDetailSellerAddress;
    private TextInputEditText edtBuyOrderDetailBuyerName;
    private TextInputEditText edtBuyOrderDetailBuyerPhone;
    private TextInputEditText edtBuyOrderDetailBuyerAddress;
    private TextView tvBuyOrderDetailOrderName;
    private TextView tvBuyOrderDetailTotalAmount;
    private TextView tvBuyOrderDetailOrderedTime;
    private TextView tvBuyOrderDetailFinishStatus;
    private TextView tvBuyOrderDetailFinishedTime;
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
    private Button btnConfirmBuyOrder;
    private Post post;
    private String sellerID, postID;
    private CurrentBuyer currentBuyer;
    private IOrderContract.Presenter.BuyOrderDetailActivityPresenter buyOrderDetailActivityPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_order_detail);
        // Create objects
        buyOrderDetailActivityPresenter = new BuyOrderDetailActivityPresenter(this, getApplicationContext());

        btnConfirmBuyOrder = findViewById(R.id.btnConfirmBuyOrder);
        btnConfirmBuyOrder.setOnClickListener(view -> {
            // Đóng gói đối tượng Order tù UI
            // Sau đó gọi presener + truyền đối tượng để lưu data
            // Đối tượng cần truyền gồm: Order, SellerID, BuyerID
            Order newOrder = new Order();
            // Tạo định dạng ngày giờ
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
            Date currentDateTime = new Date();
            // Order info
            newOrder.setOrderedDate(sdf.format(currentDateTime));
            newOrder.setOrderStatus(OrderStatus.PROCESSING);
            newOrder.setTotalAmount(tvBuyOrderDetailTotalAmount.getText().toString());

            // Buyer info
            newOrder.setBuyerID(currentBuyer.getAccountID());
            newOrder.setBuyerName(edtBuyOrderDetailBuyerName.getText().toString().trim());
            newOrder.setBuyerPhone(edtBuyOrderDetailBuyerPhone.getText().toString().trim());
            newOrder.setShipAddress(edtBuyOrderDetailBuyerAddress.getText().toString().trim());

            // Seller info
            newOrder.setSellerID(sellerID);
            newOrder.setPostID(postID);
            buyOrderDetailActivityPresenter.OnConfirmBuyingClicked(newOrder, sellerID, currentBuyer.getAccountID());
        });

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

        txtViewBuyOrderStatus = findViewById(R.id.txtViewBuyOrderStatus);
        tvBuyOrderDetailOrderName = findViewById(R.id.tvBuyOrderDetailOrderName);
        tvBuyOrderDetailTotalAmount = findViewById(R.id.tvBuyOrderDetailTotalAmount);
        tvBuyOrderDetailOrderedTime = findViewById(R.id.tvBuyOrderDetailOrderedTime);
        tvBuyOrderDetailFinishedTime = findViewById(R.id.tvBuyOrderDetailFinishedTime);
        tvBuyOrderDetailFinishStatus = findViewById(R.id.tvBuyOrderDetailFinishStatus);

        edtBuyOrderDetailPostServiceName = findViewById(R.id.edtBuyOrderDetailPostServiceName);
        edtBuyOrderDetailPostServiceCode = findViewById(R.id.edtBuyOrderDetailPostServiceCode);

        edtBuyOrderDetailBuyerName = findViewById(R.id.edtBuyOrderDetailBuyerName);
        edtBuyOrderDetailBuyerPhone = findViewById(R.id.edtBuyOrderDetailBuyerPhone);
        edtBuyOrderDetailBuyerAddress = findViewById(R.id.edtBuyOrderDetailBuyerAddress);

        edtBuyOrderDetailSellerName = findViewById(R.id.edtBuyOrderDetailSellerName);
        edtBuyOrderDetailSellerPhone = findViewById(R.id.edtBuyOrderDetailSellerPhone);
        edtBuyOrderDetailSellerAddress = findViewById(R.id.edtBuyOrderDetailSellerAddress);

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

        LoadDataFromBuyNow();

        // Check if Buyer data has not been initialized
        String phoneNumber = edtBuyOrderDetailBuyerPhone.getText().toString().trim();
        String address = edtBuyOrderDetailBuyerAddress.getText().toString().trim();
        if (phoneNumber.isEmpty() || address.isEmpty()) {
            // Disable button
            btnConfirmBuyOrder.setEnabled(false);
            btnConfirmBuyOrder.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));
            MyDialog.showDialog(this, "Bạn có thể cập nhật thông tin trong mục Cài đặt tài khoản để hệ thống tự động điền thông tin", MyDialog.DialogType.OK, new MyDialog.DialogClickListener() {
                @Override
                public void onYesClick() {

                }

                @Override
                public void onNoClick() {

                }

                @Override
                public void onOkClick() {

                }
            });
        }

        // Add event OnTextChange
        edtBuyOrderDetailBuyerName.addTextChangedListener(textWatcher);
        edtBuyOrderDetailBuyerPhone.addTextChangedListener(textWatcher);
        edtBuyOrderDetailBuyerAddress.addTextChangedListener(textWatcher);
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
                edtBuyOrderDetailBuyerName.setFocusable(false);
                edtBuyOrderDetailBuyerName.setFocusableInTouchMode(false);
                edtBuyOrderDetailBuyerPhone.setFocusable(false);
                edtBuyOrderDetailBuyerPhone.setFocusableInTouchMode(false);
                edtBuyOrderDetailBuyerAddress.setFocusable(false);
                edtBuyOrderDetailBuyerAddress.setFocusableInTouchMode(false);
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
                edtBuyOrderDetailBuyerName.setFocusable(false);
                edtBuyOrderDetailBuyerName.setFocusableInTouchMode(false);
                edtBuyOrderDetailBuyerPhone.setFocusable(false);
                edtBuyOrderDetailBuyerPhone.setFocusableInTouchMode(false);
                edtBuyOrderDetailBuyerAddress.setFocusable(false);
                edtBuyOrderDetailBuyerAddress.setFocusableInTouchMode(false);
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
                tvBuyOrderDetailFinishStatus.setText("Thời gian hoàn thành: ");
                edtBuyOrderDetailBuyerName.setFocusable(false);
                edtBuyOrderDetailBuyerName.setFocusableInTouchMode(false);
                edtBuyOrderDetailBuyerPhone.setFocusable(false);
                edtBuyOrderDetailBuyerPhone.setFocusableInTouchMode(false);
                edtBuyOrderDetailBuyerAddress.setFocusable(false);
                edtBuyOrderDetailBuyerAddress.setFocusableInTouchMode(false);
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
                txtViewBuyOrderStatus.setText("Đã hủy");
                tvBuyOrderDetailFinishStatus.setText("Thời gian hủy: ");
                edtBuyOrderDetailBuyerName.setFocusable(false);
                edtBuyOrderDetailBuyerName.setFocusableInTouchMode(false);
                edtBuyOrderDetailBuyerPhone.setFocusable(false);
                edtBuyOrderDetailBuyerPhone.setFocusableInTouchMode(false);
                edtBuyOrderDetailBuyerAddress.setFocusable(false);
                edtBuyOrderDetailBuyerAddress.setFocusableInTouchMode(false);
                linearLayoutBuyOrderStatus.setBackgroundColor(getResources().getColor(R.color.order_cancel,null));
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
                edtBuyOrderDetailBuyerName.setFocusable(true);
                edtBuyOrderDetailBuyerName.setFocusableInTouchMode(true);
                edtBuyOrderDetailBuyerPhone.setFocusable(true);
                edtBuyOrderDetailBuyerPhone.setFocusableInTouchMode(true);
                edtBuyOrderDetailBuyerAddress.setFocusable(true);
                edtBuyOrderDetailBuyerAddress.setFocusableInTouchMode(true);
                linearLayoutBuyOrderStatus.setBackgroundColor(getResources().getColor(R.color.order_processing,null));
            default:
                break;
        }
    }
    private void LoadDataFromBuyNow()
    {
        Intent intent = getIntent();
        if (intent.hasExtra("BuyOrderStatus")){
            int BuyOrderStatus = intent.getIntExtra("BuyOrderStatus",0);
            displayLinearLayoutButton(BuyOrderStatus);
        }

        if (intent.hasExtra("CurrentBuyer")) {
            currentBuyer = (CurrentBuyer) intent.getSerializableExtra("CurrentBuyer");
            // Buyer
            edtBuyOrderDetailBuyerName.setText(currentBuyer.getAccountName());
            edtBuyOrderDetailBuyerPhone.setText(currentBuyer.getPhoneNumber());
            edtBuyOrderDetailBuyerAddress.setText(currentBuyer.getAddress());
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            // Order
            tvBuyOrderDetailOrderName.setText(bundle.getString("OrderName"));
            tvBuyOrderDetailTotalAmount.setText(bundle.getString("TotalAmount"));
            // Seller
            sellerID = bundle.getString("SellerID");
            edtBuyOrderDetailSellerName.setText(bundle.getString("SellerName"));
            edtBuyOrderDetailSellerPhone.setText(bundle.getString("SellerPhoneNumber"));
            edtBuyOrderDetailSellerAddress.setText(bundle.getString("SellerAddress"));
            // Post
            postID = bundle.getString("PostID");
        }
    }

    @Override
    public void DisplayBuySucessful() {
        Intent resultIntent = new Intent();
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Check if three information is filled
            boolean isNameFilled = !TextUtils.isEmpty(edtBuyOrderDetailBuyerName.getText());
            boolean isPhoneFilled = !TextUtils.isEmpty(edtBuyOrderDetailBuyerPhone.getText());
            boolean isAddressFilled = !TextUtils.isEmpty(edtBuyOrderDetailBuyerAddress.getText());

            // Enable or disable the login button and change its background color
            if (isNameFilled && isPhoneFilled && isAddressFilled) {
                btnConfirmBuyOrder.setEnabled(true);
                btnConfirmBuyOrder.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.yellow));
            } else {
                btnConfirmBuyOrder.setEnabled(false);
                btnConfirmBuyOrder.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}