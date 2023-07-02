package com.example.laptop_market.view.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
import com.example.laptop_market.utils.PostTable;
import com.example.laptop_market.view.adapters.ImageSliderAdapter;
import com.example.laptop_market.view.adapters.PostSearchResult.PostSearchResult;

import java.util.ArrayList;
import java.util.List;

public class PostDetailActivity extends AppCompatActivity implements IPostContract.View.PostDetailActivityView
        , ILaptopContract.View.PostDetailActivityView, IAccountContract.View.PostDetailActivityView {
    private Button btnPostDetailClose;
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
    private ImageSliderAdapter Imageadapter;
    private PostSearchResult postSearchResult;
    private int targetWidth;
    private int targetHeight;
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
        Glide.with(this)
                .load(R.drawable.slide_show1)
                .apply(RequestOptions.circleCropTransform())
                .into(imgPostDetailShop);
        viewPagerImagePostDetail = findViewById(R.id.viewPagerImagePostDetail);
        setListener();
        InitData();
    }
    private int dpToPx( float dp) {
        Resources resources = getApplicationContext().getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return (int) (dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }
    private Bitmap resizeBitmap(Bitmap bitmap)
    {
        targetWidth = dpToPx(viewPagerImagePostDetail.getWidth());
        targetHeight = dpToPx(viewPagerImagePostDetail.getHeight());

        // Tính toán tỷ lệ giữa chiều rộng và chiều cao của đối tượng bitmap so với chiều rộng và chiều cao mục tiêu
        float aspectRatio = (float) bitmap.getWidth() / (float) bitmap.getHeight();
        float targetAspectRatio = (float) targetWidth / (float) targetHeight;

// Tính toán kích thước mới của đối tượng bitmap
        int newWidth, newHeight;
        if (targetAspectRatio > aspectRatio) {
            // Kích thước mới dựa trên chiều rộng
            newWidth = targetWidth;
            newHeight = (int) (targetWidth / aspectRatio);
        } else {
            // Kích thước mới dựa trên chiều cao
            newHeight = targetHeight;
            newWidth = (int) (targetHeight * aspectRatio);
        }

// Tạo bitmap mới với kích thước mới
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);

// Tạo bitmap mới với kích thước thu nhỏ
        //Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    private void setListener()
    {
        btnPostDetailClose.setOnClickListener(v -> {
            finish();
        });
    }
    private void InitData()
    {
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
        postDetailPrice.setText(String.valueOf(laptop.getPrice()) + " VNĐ");
        Imageadapter = new ImageSliderAdapter(this,laptop.getListDownloadImages());
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

        laptop.getListDownloadImages().add(resizeBitmap(bitmap));
        Imageadapter.notifyDataSetChanged();
    }


    @Override
    public void LoadingPostInPostDetail(Post post) {
        this.post=post;
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
}