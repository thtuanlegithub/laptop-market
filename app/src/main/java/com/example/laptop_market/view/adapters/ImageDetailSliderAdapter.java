package com.example.laptop_market.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.laptop_market.view.activities.PictureDetailActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ImageDetailSliderAdapter extends PagerAdapter {
    private List<Bitmap> imageList;
    private Context context;
    public ImageDetailSliderAdapter(Context context, List<Bitmap> imageList){

        this.context = context;
        this.imageList = imageList;
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position){
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageBitmap(imageList.get(position));
        imageView.setAdjustViewBounds(true);
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
