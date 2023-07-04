package com.example.laptop_market.view.activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.laptop_market.R;
import com.example.laptop_market.contracts.ILaptopContract;
import com.example.laptop_market.contracts.IPostContract;
import com.example.laptop_market.model.laptop.Laptop;
import com.example.laptop_market.model.post.Post;
import com.example.laptop_market.presenter.activities.NewPostActivityPresenter;
import com.example.laptop_market.view.adapters.ImageForNewPostAdapter;
import com.google.android.material.button.MaterialButton;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class NewPostActivity extends AppCompatActivity implements IPostContract.View.NewPostActivityView, ILaptopContract.View.NewPostActivityView {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Button btnNewPostClose;
    private RelativeLayout newPostStatus;
    private RelativeLayout newPostBrand;
    private RelativeLayout newPostCPU;
    private RelativeLayout newPostRAM;
    private RelativeLayout newPostHardDiskType;
    private RelativeLayout newPostHardDiskSize;
    private RelativeLayout newPostGraphicType;
    private RelativeLayout newPostGraphicSize;
    private RelativeLayout newPostWarranty;
    private RelativeLayout newPostOriginCountry;
    private EditText edtNewPostPrice;
    private EditText edtTitle;
    private EditText edtDescription;
    private EditText edtPhoneNumber;
    private EditText edtSellerName;
    private EditText edtSellerAddress;
    private AppCompatButton NewPostOpenImageBtt;
    private AppCompatButton NewPostBttDangTin;
    private ArrayList<Uri> ListPictures;
    private RecyclerView rcvImageForNewPost;
    private List<Bitmap> imageList;
    private IPostContract.Presenter.NewPostActivityPresenter postActivityPresenter;
    private ILaptopContract.Presenter.NewPostActivityPresenter laptopActivityPresenter;
    private  ImageForNewPostAdapter imageForNewPostAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        ListPictures = new ArrayList<>();
        laptopActivityPresenter = new NewPostActivityPresenter(getApplicationContext(),this,this);
        postActivityPresenter = new NewPostActivityPresenter(getApplicationContext(),this,this);
        btnNewPostClose = findViewById(R.id.btnNewPostClose);
        btnNewPostClose.setOnClickListener(view -> {
            finish();
        });

        findView();

        GridLayoutManager gridLayoutManagerImageForNewPost = new GridLayoutManager(this,1, RecyclerView.HORIZONTAL,false);
        rcvImageForNewPost.setLayoutManager(gridLayoutManagerImageForNewPost);

        imageList = new ArrayList<>();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.slide_show1);
        imageList.add(bitmap);
        imageList.add(bitmap);
        imageList.add(bitmap);
        imageList.add(bitmap);

        imageForNewPostAdapter =  new ImageForNewPostAdapter(imageList, position -> {
            // Xử lý sự kiện khi người dùng nhấn nút close
            imageList.remove(position);
            imageForNewPostAdapter.notifyDataSetChanged();
        });
        rcvImageForNewPost.setAdapter(imageForNewPostAdapter);
    }

    private void findView(){
        btnNewPostClose = findViewById(R.id.btnNewPostClose);
        NewPostBttDangTin = findViewById(R.id.NewPostBttDangTin);
        newPostStatus = findViewById(R.id.newPostStatus);
        newPostBrand = findViewById(R.id.newPostBrand);
        newPostCPU = findViewById(R.id.newPostCPU);
        newPostRAM = findViewById(R.id.newPostRAM);
        newPostHardDiskType = findViewById(R.id.newPostHardDiskType);
        newPostHardDiskSize = findViewById(R.id.newPostGraphicSize);
        newPostGraphicType = findViewById(R.id.newPostGraphicType);
        newPostGraphicSize = findViewById(R.id.newPostGraphicSize);
        newPostWarranty = findViewById(R.id.newPostWarranty);
        newPostOriginCountry = findViewById(R.id.newPostOriginCountry);
        edtNewPostPrice = findViewById(R.id.edtNewPostPrice);
        edtTitle = findViewById(R.id.edtTitle);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        edtSellerAddress = findViewById(R.id.edtSellerAddress);
        edtSellerName = findViewById(R.id.edtSellerName);
        NewPostOpenImageBtt = findViewById(R.id.NewPostOpenImageBtt);
        edtDescription=findViewById(R.id.edtDescription);
        rcvImageForNewPost = findViewById(R.id.rcvImageForNewPost);
        setListener();
    }
    private void ShowToast(String message)
    {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }
    private void setListener()
    {
        btnNewPostClose.setOnClickListener(view -> {
            finish();
        });
        NewPostOpenImageBtt.setOnClickListener(
                view -> {
                    // Kiểm tra xem quyền đã được cấp hay chưa
/*                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Nếu chưa được cấp, yêu cầu cấp quyền
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                REQUEST_CODE_READ_EXTERNAL_STORAGE);
                    } else {*/
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                    //}

                }
        );
        NewPostBttDangTin.setOnClickListener(view -> {
            Laptop laptop = new Laptop();
            laptop.setLaptopName(edtTitle.getText().toString());
            laptop.setBrandID("Dell");
            laptop.setPrice(100000d);
            laptop.setStatus("Còn thời gian bảo hành");
            laptop.setCpu("Intel i7");
            laptop.setRam("8GB ram");
            laptop.setHardDrive("SSD");
            laptop.setHardDriveSize("256gb");
            laptop.setGraphics("NVIDIA");
            laptop.setScreenSize("15.6 inch");
            laptop.setGuarantee("Còn tg bảo hành");
            laptop.setOrigin("Trung Quốc");
            laptop.setImgLists(ListPictures);
            laptop.setNumOfImage(ListPictures.size());
            laptopActivityPresenter.OnCreateNewLaptopClicked(laptop);
        });
    }
    private String encode_img(Uri uri)
    {
        Bitmap bitmap = null;
        ContentResolver contentResolver = getContentResolver();
        try {
            InputStream inputStream = contentResolver.openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(inputStream);
            int previewWidth = 480;
            int previewHeight=bitmap.getHeight()*previewWidth/bitmap.getWidth();
            Bitmap preivewBitmap= Bitmap.createScaledBitmap(bitmap,previewWidth,previewHeight,false);
            ByteArrayOutputStream byteArrayOutputStream =new ByteArrayOutputStream();
            preivewBitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(bytes,Base64.DEFAULT);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            ListPictures.clear();
            if (data.getData() != null) {
                // Xử lý một ảnh duy nhất
                Uri uri = data.getData();
                ListPictures.add(uri);
            } else {
                // Xử lý nhiều ảnh
                ClipData clipData = data.getClipData();
                if (clipData != null) {
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        Uri uri = clipData.getItemAt(i).getUri();
                        ListPictures.add(uri);
                    }
                }
            }
        }
    }
    //region Create laptop View
    @Override
    public void CreateLaptopSuccess(String id_LapTop) {
        Post post = new Post();
        post.setLaptopID(id_LapTop);
        post.setDescription(edtDescription.getText().toString());
        post.setSellerAddress(edtSellerAddress.getText().toString());
        post.setSellerName(edtSellerName.getText().toString());
        post.setSellerPhoneNumber(edtPhoneNumber.getText().toString());
        post.setTitle(edtTitle.getText().toString());
        post.setPostMainImage(encode_img(ListPictures.get(0)));
        postActivityPresenter.OnCreateNewPostClicked(post);
    }

    @Override
    public void CreateLaptopFailure(Exception error) {
        error.printStackTrace();
    }
    //endregion
    //region Craete Post view

    @Override
    public void CreatePostSuccess() {
        ShowToast("Up post success");
    }

    @Override
    public void CreatePostFailure(Exception error) {
        error.printStackTrace();
    }
    //endregion
}