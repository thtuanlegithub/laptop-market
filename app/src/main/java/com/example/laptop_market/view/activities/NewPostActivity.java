package com.example.laptop_market.view.activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Slide;
import androidx.transition.Transition;

import android.app.Activity;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.laptop_market.R;
import com.example.laptop_market.contracts.ILaptopContract;
import com.example.laptop_market.contracts.IPostContract;
import com.example.laptop_market.model.account.Account;
import com.example.laptop_market.model.laptop.Laptop;
import com.example.laptop_market.model.post.Post;
import com.example.laptop_market.presenter.activities.NewPostActivityPresenter;
import com.example.laptop_market.utils.elses.PreferenceManager;
import com.example.laptop_market.utils.tables.Constants;
import com.example.laptop_market.utils.tables.SearchFilterPost;
import com.example.laptop_market.view.adapters.FilterRadioButtonAdapter;
import com.example.laptop_market.view.adapters.ImageForNewPostAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
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
    private RelativeLayout newPostOriginCountry;
    private TextInputEditText edtNewPostStatus;
    private TextInputEditText edtNewPostBrand;
    private TextInputEditText edtNewPostCPU;
    private TextInputEditText edtNewPostRAM;
    private TextInputEditText edtNewPostHardDiskType;
    private TextInputEditText edtNewPostHardDiskSize;
    private TextInputEditText edtNewPostGraphicType;
    private TextInputEditText edtNewPostGraphicSize;
    private TextInputEditText edtNewPostOriginCountry;
    private EditText edtNewPostPrice;
    private EditText edtTitle;
    private EditText edtDescription;
    private EditText edtSellerPhoneNumber;
    private EditText edtSellerName;
    private EditText edtSellerAddress;
    private AppCompatButton NewPostOpenImageBtt;
    private AppCompatButton NewPostBttDangTin;
    private ArrayList<Uri> ListPictures;
    private RecyclerView rcvImageForNewPost;
    private List<Bitmap> imageList;
    private IPostContract.Presenter.NewPostActivityPresenter postActivityPresenter;
    private ILaptopContract.Presenter.NewPostActivityPresenter laptopActivityPresenter;
    private LinearLayout listImageLayout;
    private AppCompatButton addNewImageBtt;
    private Laptop laptop;
    private Post post;


    private  ImageForNewPostAdapter imageForNewPostAdapter;
    private PreferenceManager preferenceManager;
    private SearchFilterPost searchFilterPost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        preferenceManager = new PreferenceManager(getApplicationContext());
        ListPictures = new ArrayList<>();
        laptopActivityPresenter = new NewPostActivityPresenter(getApplicationContext(),this,this);
        postActivityPresenter = new NewPostActivityPresenter(getApplicationContext(),this,this);
        post = new Post();
        laptop = new Laptop();
        findView();
        GridLayoutManager gridLayoutManagerImageForNewPost = new GridLayoutManager(this,1, RecyclerView.HORIZONTAL,false);
        rcvImageForNewPost.setLayoutManager(gridLayoutManagerImageForNewPost);

        imageList = new ArrayList<>();
        imageForNewPostAdapter =  new ImageForNewPostAdapter(imageList, getApplicationContext(), position -> {
            // Xử lý sự kiện khi người dùng nhấn nút close
            imageList.remove(position);
            ListPictures.remove(position);
            if(ListPictures.size()==0) {
                listImageLayout.setVisibility(View.GONE);
                NewPostOpenImageBtt.setVisibility(View.VISIBLE);
            }
            imageForNewPostAdapter.notifyDataSetChanged();
        });
        rcvImageForNewPost.setAdapter(imageForNewPostAdapter);
        LoadData();

    }

    private void findView(){
        btnNewPostClose = findViewById(R.id.btnNewPostClose);
        NewPostBttDangTin = findViewById(R.id.NewPostBttDangTin);
        newPostStatus = findViewById(R.id.newPostStatus);
        newPostBrand = findViewById(R.id.newPostBrand);
        newPostCPU = findViewById(R.id.newPostCPU);
        newPostRAM = findViewById(R.id.newPostRAM);
        newPostHardDiskType = findViewById(R.id.newPostHardDiskType);
        newPostHardDiskSize = findViewById(R.id.newPostHardDiskSize);
        newPostGraphicType = findViewById(R.id.newPostGraphicType);
        newPostGraphicSize = findViewById(R.id.newPostGraphicSize);
        edtNewPostStatus = findViewById(R.id.edtNewPostStatus);
        edtNewPostBrand = findViewById(R.id.edtNewPostBrand);
        edtNewPostCPU = findViewById(R.id.edtNewPostCPU);
        edtNewPostRAM = findViewById(R.id.edtNewPostRAM);
        edtNewPostHardDiskType = findViewById(R.id.edtNewPostHardDiskType);
        edtNewPostHardDiskSize = findViewById(R.id.edtNewPostHardDiskSize);
        edtNewPostGraphicType = findViewById(R.id.edtNewPostGraphicType);
        edtNewPostGraphicSize = findViewById(R.id.edtNewPostGraphicSize);
        edtNewPostOriginCountry = findViewById(R.id.edtNewPostOriginCountry);
        edtNewPostOriginCountry = findViewById(R.id.edtNewPostOriginCountry);
        edtNewPostPrice = findViewById(R.id.edtNewPostPrice);
        edtTitle = findViewById(R.id.edtTitle);
        edtSellerPhoneNumber = findViewById(R.id.edtSellerPhoneNumber);
        edtSellerAddress = findViewById(R.id.edtSellerAddress);
        edtSellerName = findViewById(R.id.edtSellerName);
        NewPostOpenImageBtt = findViewById(R.id.NewPostOpenImageBtt);
        edtDescription=findViewById(R.id.edtDescription);
        rcvImageForNewPost = findViewById(R.id.rcvImageForNewPost);
        listImageLayout = findViewById(R.id.listImageLayout);
        addNewImageBtt = findViewById(R.id.addNewImageBtt);
        setListener();
    }
    private void ShowToast(String message)
    {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }
    private void LoadData()
    {
        postActivityPresenter.OnLoadingAccount();
    }
    private void setListener()
    {
        edtNewPostPrice.addTextChangedListener(new TextWatcher() {
            private DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance();
            private String current = "";
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(current)) {
                    edtNewPostPrice.removeTextChangedListener(this);

                    String cleanString = s.toString().replaceAll("[,.]", "");
                    double parsed = 0;
                    try {
                        parsed = decimalFormat.parse(cleanString).doubleValue();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String formatted = decimalFormat.format(parsed);

                    current = formatted;
                    edtNewPostPrice.setText(formatted);
                    edtNewPostPrice.setSelection(formatted.length());

                    edtNewPostPrice.addTextChangedListener(this);
                }
            }
        });


        btnNewPostClose.setOnClickListener(view -> {
            finish();
        });

        edtNewPostStatus.setOnClickListener(v -> {
            Context context = this;
            Intent intent = new Intent(context,FilterNewPostActivity.class);
            intent.putExtra("filter","Tình trạng +");
            context.startActivity(intent);
        });

        edtNewPostBrand.setOnClickListener(v -> {
            Context context = this;
            Intent intent = new Intent(context,FilterNewPostActivity.class);
            intent.putExtra("filter","Hãng +");
            context.startActivity(intent);
        });

        edtNewPostCPU.setOnClickListener(v -> {
            Context context = this;
            Intent intent = new Intent(context,FilterNewPostActivity.class);
            intent.putExtra("filter","Bộ xử lý +");
            context.startActivity(intent);
        });

        edtNewPostRAM.setOnClickListener(v -> {
            Context context = this;
            Intent intent = new Intent(context,FilterNewPostActivity.class);
            intent.putExtra("filter","RAM +");
            intent.putExtra(SearchFilterPost.SEARCH_NAME, searchFilterPost);
            context.startActivity(intent);
        });

        edtNewPostHardDiskType.setOnClickListener(v -> {
            Context context = this;
            Intent intent = new Intent(context,FilterNewPostActivity.class);
            intent.putExtra("filter","Loại ổ cứng +");
            intent.putExtra(SearchFilterPost.SEARCH_NAME, searchFilterPost);
            context.startActivity(intent);
        });

        edtNewPostHardDiskSize.setOnClickListener(v -> {
            Context context = this;
            Intent intent = new Intent(context,FilterNewPostActivity.class);
            intent.putExtra("filter","Kích thước ổ cứng +");
            intent.putExtra(SearchFilterPost.SEARCH_NAME, searchFilterPost);
            context.startActivity(intent);
        });

        edtNewPostGraphicType.setOnClickListener(v -> {
            Context context = this;
            Intent intent = new Intent(context,FilterNewPostActivity.class);
            intent.putExtra("filter","Card màn hình +");
            intent.putExtra(SearchFilterPost.SEARCH_NAME, searchFilterPost);
            context.startActivity(intent);
        });

        edtNewPostGraphicSize.setOnClickListener(v -> {
            Context context = this;
            Intent intent = new Intent(context,FilterNewPostActivity.class);
            intent.putExtra("filter","Kích cỡ màn hình +");
            intent.putExtra(SearchFilterPost.SEARCH_NAME, searchFilterPost);
            context.startActivity(intent);
        });

        btnNewPostClose.setOnClickListener(view -> {
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


        addNewImageBtt.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        });
        NewPostOpenImageBtt.setOnClickListener(
                view -> {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                }
        );
        NewPostBttDangTin.setOnClickListener(view -> {
            if(ListPictures.size()==0)
            {
                Toast.makeText(getApplicationContext(),"Vui lòng chọn ít nhất 1 ảnh", Toast.LENGTH_SHORT).show();
                return;
            }
            laptop.setLaptopName(edtTitle.getText().toString());
            if( laptop.checkDataNull() || edtNewPostPrice.getText().toString().isEmpty() || edtDescription.getText().toString().isEmpty()
                    || edtTitle.getText().toString().isEmpty() || edtSellerAddress.getText().toString().isEmpty()
                    || edtSellerPhoneNumber.getText().toString().isEmpty())
            {
                Toast.makeText(this,"Vui lòng điền đủ thông tin!!", Toast.LENGTH_SHORT).show();
                return;
            }
            if(edtNewPostOriginCountry.getText().toString().isEmpty())
                laptop.setOrigin("Chưa rõ xuất xứ");
            else
                laptop.setOrigin(edtNewPostOriginCountry.getText().toString());
            laptop.setImgLists(ListPictures);
            String price = edtNewPostPrice.getText().toString().replaceAll("[,.]", "").trim();;
            laptop.setPrice(Integer.parseInt(price));
            laptop.setNumOfImage(ListPictures.size());
            post.setDescription(edtDescription.getText().toString());
            post.setSellerAddress(edtSellerAddress.getText().toString());
            post.setSellerName(edtSellerName.getText().toString());
            post.setSellerPhoneNumber(edtSellerPhoneNumber.getText().toString());
            post.setTitle(edtTitle.getText().toString());
            post.setPostMainImage(encode_img(ListPictures.get(0)));
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
        if (requestCode == PICK_IMAGE_REQUEST && (resultCode == RESULT_OK || resultCode == RESULT_FIRST_USER)) {
            if (data.getData() != null) {
                // Xử lý một ảnh duy nhất
                listImageLayout.setVisibility(View.VISIBLE);
                NewPostOpenImageBtt.setVisibility(View.GONE);
                Uri uri = data.getData();
                ListPictures.add(uri);
                imageList.add(uriToBitmap(uri));
                imageForNewPostAdapter.notifyDataSetChanged();
            } else {
                // Xử lý nhiều ảnh
                ClipData clipData = data.getClipData();
                if (clipData != null) {
                    listImageLayout.setVisibility(View.VISIBLE);
                    NewPostOpenImageBtt.setVisibility(View.GONE);
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        if(ListPictures.size()==5)
                        {
                            Toast.makeText(getApplicationContext(),"Chỉ nhận tối đa 5 ảnh", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        Uri uri = clipData.getItemAt(i).getUri();
                        ListPictures.add(uri);
                        imageList.add(uriToBitmap(uri));
                        imageForNewPostAdapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }
    // Hàm để chuyển từ Uri sang Bitmap
    private Bitmap uriToBitmap(Uri uri)  {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            return bitmap;
        }catch (IOException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
    //region Create laptop View
    @Override
    public void CreateLaptopSuccess(String id_LapTop) {
        post.setLaptopID(id_LapTop);
        postActivityPresenter.OnCreateNewPostClicked(post,laptop);
    }
    @Override
    public void CreateLaptopFailure(Exception error) {
        error.printStackTrace();
    }
    //endregion
    //region Craete Post view

    @Override
    public void CreatePostSuccess() {
        ShowToast("Đăng bài lên thành công");
        finish();
    }

    @Override
    public void CreatePostFailure(Exception error) {
        error.printStackTrace();
    }

    @Override
    public void LoadDataInView(Account account) {
        if(account.getPhoneNumber() != null)
            edtSellerPhoneNumber.setText(account.getPhoneNumber());
        if(account.getAddress() != null)
            edtSellerAddress.setText(account.getAddress());
        edtSellerName.setText(account.getAccountName());
    }
    //endregion

    @Override
    protected void onResume() {
        super.onResume();
        String name =preferenceManager.getString(Constants.KEY_FILTER_NAME_NEW_POST_ADAPTER);
        String tag = preferenceManager.getString(Constants.KEY_FILTER_TAG_NEW_POST_ADAPTER);
        if(tag!=null && name!=null)
        {
            setItem(tag,name);
            preferenceManager.removeKey(Constants.KEY_FILTER_NAME_NEW_POST_ADAPTER);
            preferenceManager.removeKey(Constants.KEY_FILTER_TAG_NEW_POST_ADAPTER);
        }
    }
    private void setItem(String type, String item)
    {
        switch (type)
        {
            case "Hãng +":
                laptop.setBrandID(item);
                edtNewPostBrand.setText(item);
                break;
            case "Tình trạng +":
                laptop.setGuarantee(item);
                edtNewPostStatus.setText(item);
                break;
            case "Bộ xử lý +":
                laptop.setCpu(item);
                edtNewPostCPU.setText(item);
                break;
            case "RAM +":
                laptop.setRam(item);
                edtNewPostRAM.setText(item);
                break;
            case "Loại ổ cứng +":
                laptop.setHardDrive(item);
                edtNewPostHardDiskType.setText(item);
                break;
            case "Kích thước ổ cứng +":
                laptop.setHardDriveSize(item);
                edtNewPostHardDiskSize.setText(item);
                break;
            case "Card màn hình +":
                laptop.setGraphics(item);
                edtNewPostGraphicType.setText(item);
                break;
            case "Kích cỡ màn hình +":
                laptop.setScreenSize(item);
                edtNewPostGraphicSize.setText(item);
                break;
        }
    }
}