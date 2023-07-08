package com.example.laptop_market.view.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.laptop_market.R;
import com.example.laptop_market.contracts.IAccountContract;
import com.example.laptop_market.contracts.ILaptopContract;
import com.example.laptop_market.contracts.IPostContract;
import com.example.laptop_market.model.account.Account;
import com.example.laptop_market.model.account.CurrentBuyer;
import com.example.laptop_market.model.laptop.Laptop;
import com.example.laptop_market.model.post.Post;
import com.example.laptop_market.presenter.activities.PostDetailActivityPresenter;
import com.example.laptop_market.utils.elses.FragmentActivityType;
import com.example.laptop_market.utils.elses.PreferenceManager;
import com.example.laptop_market.utils.tables.AccountTable;
import com.example.laptop_market.utils.tables.PostTable;
import com.example.laptop_market.view.adapters.ImageSliderAdapter;
import com.example.laptop_market.view.adapters.PostSearchResult.PostSearchResult;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class PostDetailActivity extends AppCompatActivity implements IPostContract.View.PostDetailActivityView
        , ILaptopContract.View.PostDetailActivityView, IAccountContract.View.PostDetailActivityView {
    private Button btnPostDetailClose, btnSavePost, btnCallNow, btnBuyNow;
    private ImageView imgPostDetailShop;
    private ViewPager viewPagerImagePostDetail;
    private ILaptopContract.Presenter.PostDetailActivityPresenter laptopPresenter;
    private IPostContract.Presenter.PostDetailActivityPresenter postPresenter;
    private IAccountContract.Presenter.PostDetailActivityPresenter accountPresenter;
    private TextView postDetailTitleTextView;
    private TextView NameShopTextView;
    private TextView postDetailPrice;
    private Post post;
    private Laptop laptop;
    private Account account;
    private boolean isloadLaptopFinish;
    private boolean isLoadPostFinish;
    private boolean isLoadAccountFinish;
    private boolean isSaved = false;
    private ImageSliderAdapter Imageadapter;
    private PostSearchResult postSearchResult;
    private TextView totalPictureTextView;
    private TextView currentPictureTextView;
    private TextView tvPostDetailSellerAddress;
    private int currentImagePage;
    private LinearLayout layoutButtonCustomer;
    private LinearLayout layoutButtonSeller;
    private PreferenceManager preferenceManager;
    private ActivityResultLauncher<Intent> orderDetailsLauncher;
    private TextView tvPostDetailDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        // Set activity result when Confirm buying from OrderDetails
        orderDetailsLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );

        // get view
        preferenceManager = new PreferenceManager(getApplicationContext());
        laptopPresenter = new PostDetailActivityPresenter(getApplicationContext(),this,this,this);
        postPresenter = new PostDetailActivityPresenter(getApplicationContext(),this,this,this);
        accountPresenter = new PostDetailActivityPresenter(getApplicationContext(),this,this,this);
        btnPostDetailClose = findViewById(R.id.btnPostDetailClose);
        imgPostDetailShop = findViewById(R.id.imgPostDetailShop);
        postDetailTitleTextView = findViewById(R.id.postDetailTitleTextView);
        tvPostDetailDescription = findViewById(R.id.tvPostDetailDescription);
        NameShopTextView = findViewById(R.id.NameShopTextView);
        tvPostDetailSellerAddress = findViewById(R.id.tvPostDetailSellerAddress);
        postDetailPrice = findViewById(R.id.postDetailPrice);
        totalPictureTextView = findViewById(R.id.totalPictureTextView);
        currentPictureTextView = findViewById(R.id.currentPictureTextView);
        layoutButtonCustomer = findViewById(R.id.layoutButtonForCustomer);
        layoutButtonSeller = findViewById(R.id.layoutButtonForSeller);
        btnSavePost = findViewById(R.id.btnSavePost);
        btnBuyNow = findViewById(R.id.btnBuyNow);
        btnCallNow = findViewById(R.id.btnCallNow);
        // check seller or customer
        if(checkSeller()){
            layoutButtonSeller.setVisibility(View.VISIBLE);
            layoutButtonCustomer.setVisibility(View.GONE);
        }
        else{
            layoutButtonCustomer.setVisibility(View.VISIBLE);
            layoutButtonSeller.setVisibility(View.GONE);
        }

        // circle avatar of seller
        Glide.with(this)
                .load(R.drawable.slide_show1)
                .apply(RequestOptions.circleCropTransform())
                .into(imgPostDetailShop);
        viewPagerImagePostDetail = findViewById(R.id.viewPagerImagePostDetail);

        // set listener
        setListener();

        // init data
        InitData();
    }
    private boolean checkSeller(){
        // Customer - false

        // Seller - true

        return false;
    }
    private void setListener()
    {
        btnPostDetailClose.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("applySlideTransition", false);
            setResult(Activity.RESULT_OK, intent);
            finish();
        });

        btnSavePost.setOnClickListener(v -> {
            postPresenter.OnSavePostClicked(this.postSearchResult.getPostId(), isSaved);
        });

        btnCallNow.setOnClickListener(v -> {
            postPresenter.OnPhoneDialClicked(this.postSearchResult.getPostId());
        });

        btnBuyNow.setOnClickListener(v -> {
            postPresenter.OnBuyNowClicked();
        });
        viewPagerImagePostDetail.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentImagePage=position+1;
                currentPictureTextView.setText(String.valueOf(currentImagePage));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private void InitData()
    {
        boolean isFromLogin = preferenceManager.getBoolean("IsFromLogin");
        Intent intent = getIntent();
        if (isFromLogin){
            postSearchResult = (PostSearchResult) intent.getSerializableExtra("PostDetailActivity");
            preferenceManager.putBoolean("IsFromLogin", false);
        }
        else{
            postSearchResult = (PostSearchResult) intent.getSerializableExtra(PostTable.TABLE_NAME);
        }
        currentImagePage = 1;
        isloadLaptopFinish = false;
        isLoadAccountFinish = false;
        isLoadPostFinish = false;
        postPresenter.OnLoadingPostInPostDetail(postSearchResult.getPostId());
        laptopPresenter.OnLoadingLaptopInPostDetail(postSearchResult.getLaptopId());
        accountPresenter.OnLoadingAccountInPostDetail(postSearchResult.getAccountId());
    }
    private void LoadData()
    {
        postDetailTitleTextView.setText(post.getTitle());
        NameShopTextView.setText(account.getAccountName());
        totalPictureTextView.setText(String.valueOf(laptop.getNumOfImage()));
        tvPostDetailSellerAddress.setText(post.getSellerAddress());
        tvPostDetailDescription.setText(post.getDescription());
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setGroupingUsed(true); // Bật chế độ hiển thị hàng nghìn
        numberFormat.setMaximumFractionDigits(0); // Số lượng chữ số phần thập phân
        String formattedPrice = numberFormat.format(laptop.getPrice());
        postDetailPrice.setText(String.valueOf(formattedPrice) + " VNĐ");
        Imageadapter = new ImageSliderAdapter(laptop.getNumOfImage(),getApplicationContext(),laptop.getListDownloadImages());
        viewPagerImagePostDetail.setAdapter(Imageadapter);
        laptopPresenter.OnLoadingImageLaptopInPostDetail(postSearchResult.getLaptopId());
    }
    @Override
    public void LoadingLapTopInPostDetail(Laptop laptop) {
        this.laptop=laptop;
        isloadLaptopFinish=true;
        if(isLoadPostFinish & isLoadAccountFinish)
        {
            LoadData();
        }
    }

    @Override
    public void LoadingImageLaptopInPostDetail(Bitmap bitmap) {

        laptop.getListDownloadImages().add(bitmap);
        Imageadapter.notifyDataSetChanged();

    }

    @Override
    public void LoadingPostInPostDetail(Post post) {
        this.post = post;
        isLoadPostFinish =true;
        if(isloadLaptopFinish && isLoadAccountFinish)
            LoadData();
    }

    @Override
    public void LoadingAccountInPostDetail(Account account) {
        this.account=account;
        isLoadAccountFinish=true;
        if(isLoadPostFinish && isloadLaptopFinish)
            LoadData();
    }

    @Override
    public void FailedLoadingPostDetail(Exception error) {
        error.printStackTrace();
    }

    @Override
    public void LoadRemoveSavePostButton() {
        isSaved = true;
        btnSavePost.setText("Bỏ lưu");
    }

    @Override
    public void LoadSavePostButton() {
        isSaved = false;
        btnSavePost.setText("Lưu tin");
    }

    @Override
    protected void onResume() {
        super.onResume();
        postPresenter.LoadSavePostButton(this.postSearchResult.getPostId());
    }

    @Override
    public void ShowPhoneDialIntent(String phoneNumber) {
        // Create an intent with the ACTION_DIAL action and the phone number
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }

    @Override
    public void LoginAccount() {
        Intent intent = new Intent(this, LoginActivity.class);
        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());
        preferenceManager.putSerializable("PostDetailActivity", postSearchResult);
        preferenceManager.putInt(FragmentActivityType.FRAGMENT_ACTIVITY, FragmentActivityType.POST_DETAILS_ACTIVITY);
        startActivity(intent);
        finish();
    }

    @Override
        public void LoadOrderDetails(CurrentBuyer currentBuyer) {
        Intent intent = new Intent(this, BuyOrderDetailActivity.class);
        intent.putExtra("BuyOrderStatus",4);
        intent.putExtra("CurrentBuyer", currentBuyer);

        // EncapsulateDataToPassBuyOrderActivity
        Bundle bundle = new Bundle();
        bundle.putString("PostID", postSearchResult.getPostId());
        bundle.putString("OrderName", postSearchResult.getTitle());
        bundle.putString("TotalAmount", postDetailPrice.getText().toString());
        bundle.putString("SellerID", postSearchResult.getAccountId());
        bundle.putString("SellerName", post.getSellerName());
        bundle.putString("SellerPhoneNumber", post.getSellerPhoneNumber());
        bundle.putString("SellerAddress", post.getSellerAddress());
        intent.putExtras(bundle);

        orderDetailsLauncher.launch(intent);
    }
}