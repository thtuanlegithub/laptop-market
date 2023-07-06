package com.example.laptop_market.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.laptop_market.R;
import com.example.laptop_market.utils.tables.Constants;
import com.example.laptop_market.utils.tables.LaptopTable;
import com.example.laptop_market.view.adapters.ImageDetailSliderAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PictureDetailActivity extends AppCompatActivity {

    private LinearLayout btnPictureDetailClose;
    private ViewPager viewPagerImagePostDetail;
    private TextView totalPictureTextView;
    private TextView currentPictureTextView;
    private int currentImagePage;
    private LinearLayout mutiplePictureLayout;
    private ArrayList<Bitmap> listbitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_detail);
        btnPictureDetailClose = findViewById(R.id.btnPictureDetailClose);
        viewPagerImagePostDetail = findViewById(R.id.viewPagerImagePostDetail);
        totalPictureTextView = findViewById(R.id.totalPictureTextView);
        currentPictureTextView = findViewById(R.id.currentPictureTextView);
        mutiplePictureLayout = findViewById(R.id.mutiplePictureLayout);
        setListener();
        getData();
    }
    //region get date from intent before
    private void getData()
    {
        Intent intent = getIntent();
        boolean isSingleImage = intent.getBooleanExtra("isSingleImage",false);
        listbitmap = new ArrayList<>();
        if(isSingleImage)
        {
            String filePath = intent.getStringExtra("image_file");
            if(filePath!=null) {
                try {
                    File file = new File(filePath);
                    FileInputStream inputStream = new FileInputStream(file);
                    byte[] buffer = new byte[(int) file.length()];
                    inputStream.read(buffer);
                    inputStream.close();
                    String data = new String(buffer, "UTF-8");
                    JSONObject jsonObject = new JSONObject(data);
                    String imageString = jsonObject.getString(Constants.KEY_IMAGE);
                    Bitmap bitmap = getBitMapFromString(imageString);
                    listbitmap.add(bitmap);
                    ImageDetailSliderAdapter imageDetailSliderAdapter = new ImageDetailSliderAdapter(getApplicationContext(), listbitmap);
                    viewPagerImagePostDetail.setAdapter(imageDetailSliderAdapter);
                    mutiplePictureLayout.setVisibility(View.GONE);
                    file.delete();
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            String filePath = intent.getStringExtra("data_file");

            if (filePath != null) {
                try {
                    // Đọc dữ liệu từ file JSON
                    File file = new File(filePath);
                    FileInputStream inputStream = new FileInputStream(file);
                    byte[] buffer = new byte[(int) file.length()];
                    inputStream.read(buffer);
                    inputStream.close();
                    String data = new String(buffer, "UTF-8");

                    // Chuyển đổi dữ liệu từ chuỗi JSON sang đối tượng JSONObject
                    JSONObject jsonObject = new JSONObject(data);
                    int numOfPicture = jsonObject.getInt(LaptopTable.NUM_OF_IMAGE);
                    // Truy xuất các giá trị từ đối tượng JSONObject
                    for (int i = 0; i < numOfPicture; i++) {
                        String imageString = jsonObject.getString(Constants.KEY_IMAGE + i);
                        Bitmap bitmap = getBitMapFromString(imageString);
                        listbitmap.add(bitmap);
                    }
                    ImageDetailSliderAdapter imageDetailSliderAdapter = new ImageDetailSliderAdapter(getApplicationContext(), listbitmap);
                    viewPagerImagePostDetail.setAdapter(imageDetailSliderAdapter);
                    currentImagePage = jsonObject.getInt(Constants.KEY_POSITION_IMAGE);
                    viewPagerImagePostDetail.setCurrentItem(currentImagePage);
                    totalPictureTextView.setText(String.valueOf(numOfPicture));
                    file.delete();
                    // Xử lý các giá trị ở đây
                    // ...
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //endregion
    //region Convert String to Bitmap Function
    private Bitmap getBitMapFromString(String encodedImage)
    {
        if(encodedImage!=null) {
            byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        else
            return  null;
    }
    //endregion
    //region set listener
    private void setListener()
    {
        btnPictureDetailClose.setOnClickListener(view -> {
            finish();
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
    //endregion
}