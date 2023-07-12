package com.example.laptop_market.view.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.laptop_market.R;
import com.example.laptop_market.contracts.IAccountContract;
import com.example.laptop_market.model.account.Account;
import com.example.laptop_market.presenter.activities.ProfileActivityPresenter;
import com.example.laptop_market.view.fragments.PostActiveFragment;
import com.example.laptop_market.view.fragments.PostInactiveFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity implements IAccountContract.View.ProfileActivityView {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Button btnProfileBack;
    private AppCompatButton btnProfileEdit;
    private ImageView imgProfileAvatar;
    private ImageButton imgButtonEditAvatar;
    private List<Fragment> fragmentList;
    private Account account;
    private FragmentStateAdapter fragmentStateAdapter;
    private BottomNavigationView navProfilePost;
    private ViewPager2 viewPagerPost;
    private TextView PhonenumberTextView;
    private TextView AddressTextView;
    private TextView descriptionTextView;
    private TextView accountNameTextView;
    private IAccountContract.Presenter.ProfileActivityPresenter presenter;
    private ActivityResultLauncher<Intent> accountSettingLauncher;
    private static final int REQUEST_CODE_FOR_PROFILE = 5;
    private int currentSelectedItem = 0;
    private String ownerOfPostID;
    public ProfileActivity () {

    }
    public ProfileActivity (String ownerOfPostID) {
        this.ownerOfPostID = ownerOfPostID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ownerOfPostID = getIntent().getStringExtra("AccountID");

        accountSettingLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == REQUEST_CODE_FOR_PROFILE){
                Account account1 = (Account) result.getData().getSerializableExtra("AccountInfo");
                if(account1.getAddress()!=null)
                    AddressTextView.setText(account1.getAddress());
                if(account1.getPhoneNumber()!=null)
                    PhonenumberTextView.setText(account1.getPhoneNumber());
                if(account1.getDescription()!=null) {
                    String description = account1.getDescription();
                    if (description.length() > 35) {
                        description = description.substring(0, 35) + "...";
                    }
                    descriptionTextView.setText(description);
                }
                accountNameTextView.setText(account1.getAccountName());
            }
        });
        fragmentList = new ArrayList<>();
        fragmentList.add(new PostActiveFragment(ownerOfPostID));
        fragmentList.add(new PostInactiveFragment(ownerOfPostID));
        //find view
        imgProfileAvatar = findViewById(R.id.imgProfileAvatar);
        btnProfileEdit = findViewById(R.id.btnProfileEdit);
        viewPagerPost = findViewById(R.id.viewPagerProfilePost);
        PhonenumberTextView = findViewById(R.id.PhonenumberTextView);
        AddressTextView = findViewById(R.id.AddressTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        accountNameTextView = findViewById(R.id.accountNameTextView);
        imgButtonEditAvatar = findViewById(R.id.imgButtonEditAvatar);
        fragmentStateAdapter = new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getItemCount() {
                return fragmentList.size();
            }
        };
        viewPagerPost.setAdapter(fragmentStateAdapter);
        navProfilePost = findViewById(R.id.navProfilePost);
        btnProfileBack = findViewById(R.id.btnProfileBack);
        presenter = new ProfileActivityPresenter(this);
        // circle avatar of seller

        // Check UI if is your own account
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            String currentUserID = firebaseUser.getUid();
            if (currentUserID.equals(ownerOfPostID)) {
                btnProfileEdit.setVisibility(View.VISIBLE);
                imgButtonEditAvatar.setVisibility(View.VISIBLE);
                imgProfileAvatar.setEnabled(true);
            } else {
                btnProfileEdit.setVisibility(View.GONE);
                imgButtonEditAvatar.setVisibility(View.GONE);
                imgProfileAvatar.setEnabled(false);
            }
        } else {
            btnProfileEdit.setVisibility(View.GONE);
            imgButtonEditAvatar.setVisibility(View.GONE);
            imgProfileAvatar.setEnabled(false);
        }
        setListener();
        loadData();

    }
    private void setListener()
    {
        imgButtonEditAvatar.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        });
        navProfilePost.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.postActive && currentSelectedItem != 0) {
                viewPagerPost.setCurrentItem(0);
                currentSelectedItem = 0;
            } else if (itemId == R.id.postInactive && currentSelectedItem != 1) {
                viewPagerPost.setCurrentItem(1);
                currentSelectedItem = 1;
            }

            return true;
        });
        viewPagerPost.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                navProfilePost.getMenu().getItem(position).setChecked(true);
                currentSelectedItem = position;
            }
        });
        btnProfileBack.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("applySlideTransition", true);
            setResult(Activity.RESULT_OK, intent);
            finish();
        });
        imgProfileAvatar.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        });
        btnProfileEdit.setOnClickListener(view -> {
            Intent intent = new Intent(this, AccountSettingActivity.class);
            accountSettingLauncher.launch(intent);
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            presenter.UploadImageClicked(account,uri);
            // Do something with the image path, such as displaying the image or uploading it to a server
        }
    }
    private void loadData()
    {
        presenter.LoadProfile(ownerOfPostID);
    }

    @Override
    public void LoadProfile(Account account) {
        this.account = account;
        if(account.getAvatar()==null) {
            Glide.with(this)
                    .load(R.drawable.avatar_basic)
                    .apply(RequestOptions.circleCropTransform())
                    .into(imgProfileAvatar);
        }
        else
        {
            Glide.with(this)
                    .load(account.getAvatar())
                    .apply(RequestOptions.circleCropTransform())
                    .into(imgProfileAvatar);
        }
        if(account.getAddress()!=null)
            AddressTextView.setText(account.getAddress());
        if(account.getPhoneNumber()!=null)
            PhonenumberTextView.setText(account.getPhoneNumber());
        if(account.getDescription()!=null) {
            String description = account.getDescription();
            if (description.length() > 35) {
                description = description.substring(0, 35) + "...";
            }
            descriptionTextView.setText(description);
        }
        accountNameTextView.setText(account.getAccountName());

    }

    @Override
    public void FinishUploadImage() {
        Toast.makeText(this, "Avatar sẽ cập nhật lại sau", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ExceptionCatch(Exception e) {
        e.printStackTrace();
    }
}