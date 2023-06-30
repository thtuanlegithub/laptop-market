package com.example.laptop_market.view.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
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
        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.slide_show1);
        imageList.add(R.drawable.slide_show1);
   /*     ImageSliderAdapter adapter = new ImageSliderAdapter(this,imageList);
        viewPagerImagePostDetail.setAdapter(adapter);*/
        InitData();
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
        PostSearchResult postSearchResult = (PostSearchResult) intent.getSerializableExtra(PostTable.TABLE_NAME);
        postPresenter.OnLoadingPostInPostDetail(postSearchResult.getPostId());
        laptopPresenter.OnLoadingLaptopInPostDetail(postSearchResult.getLaptopId());
        accountPresenter.OnLoadingAccountInPostDetail(postSearchResult.getAccountId());
    }
    private void LoadData()
    {
        postDetailTitleTextView.setText(post.getTitle());
        NameShopTextView.setText(account.getAccountName());
        postDetailPrice.setText(String.valueOf(laptop.getPrice()) + " VNĐ");
        for(Uri uri: laptop.getImgLists())
        {
            // Xử lí up ảnh lên
        }
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