package com.example.laptop_market.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.laptop_market.R;
import com.example.laptop_market.utils.tables.Constants;
import com.example.laptop_market.utils.tables.LaptopTable;
import com.example.laptop_market.utils.elses.MyImageView;
import com.example.laptop_market.view.activities.PictureDetailActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ImageSliderAdapter extends PagerAdapter {
    private List<Bitmap> imageList;
    private int numOfPicture;
    private Context context;
    private int position;
    private int j;
    public ImageSliderAdapter(int numOfPicture, Context context, List<Bitmap> imageList){
        this.context = context;
        this.imageList = imageList;
        this.numOfPicture = numOfPicture;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position){
        MyImageView imageView = new MyImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageBitmap(imageList.get(position));
        imageView.setAdjustViewBounds(true);
        imageView.setTag(position);
        imageView.setBackgroundResource(R.drawable.background_clicked_image);
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        imageView.setAlpha(0.5f);
                        break;
                    case MotionEvent.ACTION_UP:
                        imageView.performClick();
                        imageView.setAlpha(1f);
                        break;
                    case MotionEvent.ACTION_OUTSIDE:
                    case MotionEvent.ACTION_CANCEL:
                        imageView.setAlpha(1f);
                        break;
                }
                return true;
            }
        });

        container.addView(imageView);
        Context c = context;
        int num = container.getChildCount();
        if(numOfPicture==container.getChildCount()) {
            j=0;
            JSONObject data = new JSONObject();
            for (int i = 0; i < imageList.size(); i++) {
                try {
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    imageList.get(i).compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    byte[] byteArray = outputStream.toByteArray();
                    String encodedString = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    data.put(Constants.KEY_IMAGE + String.valueOf(i), encodedString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            for (j = 0; j < container.getChildCount(); j++) {
                container.getChildAt(j).setOnClickListener(view -> {
                    try {
                        data.put(Constants.KEY_POSITION_IMAGE, (int)view.getTag());
                        data.put(LaptopTable.NUM_OF_IMAGE,numOfPicture);
                        // Lưu dữ liệu vào file JSON
                        File file = new File(context.getFilesDir(), "data.json");
                        try {
                            FileWriter fileWriter = new FileWriter(file);
                            fileWriter.write(data.toString());
                            fileWriter.flush();
                            fileWriter.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(context, PictureDetailActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("data_file", file.getAbsolutePath());
                        context.startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
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
