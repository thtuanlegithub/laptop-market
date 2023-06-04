package com.example.laptop_market.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.laptop_market.R;
import com.example.laptop_market.fragment.HomeFragment;

import java.util.ArrayList;
import java.util.List;

public class PostDetailActivity extends AppCompatActivity {
    private Button btnPostDetailClose;
    private ImageView imgPostDetailShop;
    private ViewPager viewPagerImagePostDetail;
    public class ImageSliderAdapter extends PagerAdapter {
        private List<Integer> imageList;
        private Context context;
        public ImageSliderAdapter(Context context, List<Integer> imageList){
            this.context = context;
            this.imageList = imageList;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position){
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(imageList.get(position));
            container.addView(imageView);
            return imageView;
        }

        @Override
        public int getCount(){
            return imageList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object){
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((ImageView) object);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        btnPostDetailClose = findViewById(R.id.btnPostDetailClose);
        btnPostDetailClose.setOnClickListener(v -> {
            finish();
        });

        imgPostDetailShop = findViewById(R.id.imgPostDetailShop);

        Glide.with(this)
                .load(R.drawable.slide_show1)
                .apply(RequestOptions.circleCropTransform())
                .into(imgPostDetailShop);

        viewPagerImagePostDetail = findViewById(R.id.viewPagerImagePostDetail);
        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.slide_show1);
        imageList.add(R.drawable.slide_show1);
        ImageSliderAdapter adapter = new ImageSliderAdapter(this,imageList);
        viewPagerImagePostDetail.setAdapter(adapter);
    }
}