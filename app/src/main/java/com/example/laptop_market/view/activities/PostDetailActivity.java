package com.example.laptop_market.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.laptop_market.R;
import com.example.laptop_market.contracts.IAccountContract;
import com.example.laptop_market.contracts.ILaptopContract;
import com.example.laptop_market.contracts.IPostContract;
import com.example.laptop_market.model.account.Account;
import com.example.laptop_market.model.laptop.Laptop;
import com.example.laptop_market.model.post.Post;
import com.example.laptop_market.presenter.activities.PostDetailActivityPresenter;
import com.example.laptop_market.utils.tables.PostTable;
import com.example.laptop_market.view.adapters.ImageSliderAdapter;
import com.example.laptop_market.view.adapters.PostSearchResult.PostSearchResult;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class PostDetailActivity extends AppCompatActivity implements IPostContract.View.PostDetailActivityView
        , ILaptopContract.View.PostDetailActivityView, IAccountContract.View.PostDetailActivityView {
    private Button btnPostDetailClose, btnSavePost;
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
    private int currentImagePage;
    private LinearLayout layoutButtonCustomer;
    private LinearLayout layoutButtonSeller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        laptopPresenter = new PostDetailActivityPresenter(getApplicationContext(),this,this,this);
        postPresenter = new PostDetailActivityPresenter(getApplicationContext(),this,this,this);
        accountPresenter = new PostDetailActivityPresenter(getApplicationContext(),this,this,this);
        btnPostDetailClose = findViewById(R.id.btnPostDetailClose);
        imgPostDetailShop = findViewById(R.id.imgPostDetailShop);
        postDetailTitleTextView = findViewById(R.id.postDetailTitleTextView);
        NameShopTextView = findViewById(R.id.NameShopTextView);
        postDetailPrice = findViewById(R.id.postDetailPrice);
        totalPictureTextView = findViewById(R.id.totalPictureTextView);
        currentPictureTextView = findViewById(R.id.currentPictureTextView);
        layoutButtonCustomer = findViewById(R.id.layoutButtonForCustomer);
        layoutButtonSeller = findViewById(R.id.layoutButtonForSeller);
        btnSavePost = findViewById(R.id.btnSavePost);
        if(checkSeller()){
            layoutButtonSeller.setVisibility(View.VISIBLE);
            layoutButtonCustomer.setVisibility(View.GONE);
        }
        else{
            layoutButtonCustomer.setVisibility(View.VISIBLE);
            layoutButtonSeller.setVisibility(View.GONE);
        }
        Glide.with(this)
                .load(R.drawable.slide_show1)
                .apply(RequestOptions.circleCropTransform())
                .into(imgPostDetailShop);
        viewPagerImagePostDetail = findViewById(R.id.viewPagerImagePostDetail);
        setListener();
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
            finish();
        });

        btnSavePost.setOnClickListener(v -> {
            postPresenter.OnSavePostClicked(this.postSearchResult.getPostId(), isSaved);
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
        currentImagePage = 1;
        isloadLaptopFinish = false;
        isLoadAccountFinish = false;
        isLoadPostFinish = false;
        Intent intent = getIntent();
        postSearchResult = (PostSearchResult) intent.getSerializableExtra(PostTable.TABLE_NAME);
        postPresenter.OnLoadingPostInPostDetail(postSearchResult.getPostId());
        laptopPresenter.OnLoadingLaptopInPostDetail(postSearchResult.getLaptopId());
        accountPresenter.OnLoadingAccountInPostDetail(postSearchResult.getAccountId());
    }
    private void LoadData()
    {
        postDetailTitleTextView.setText(post.getTitle());
        NameShopTextView.setText(account.getAccountName());
        totalPictureTextView.setText(String.valueOf(laptop.getNumOfImage()));

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
}