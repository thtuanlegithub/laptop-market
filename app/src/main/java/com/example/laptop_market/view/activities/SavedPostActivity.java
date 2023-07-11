package com.example.laptop_market.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.laptop_market.R;
import com.example.laptop_market.contracts.IAccountContract;
import com.example.laptop_market.presenter.activities.SavedPostActivityPresenter;
import com.example.laptop_market.view.adapters.PostActiveAdapter;
import com.example.laptop_market.view.adapters.SavedPostAdapter;
import com.example.laptop_market.view.adapters.PostSearchResult.PostSearchResult;
import com.example.laptop_market.view.adapters.SavedPostAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class SavedPostActivity extends AppCompatActivity implements IAccountContract.View.SavedPostActivityView {
    private RecyclerView rcvSavedPost;
    private Button btnSavedPostBack;
    private IAccountContract.Presenter.SavedPostActivityPresenter savedPostActivityPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_post);
        savedPostActivityPresenter = new SavedPostActivityPresenter(this, this);

        // Find views
        rcvSavedPost = findViewById(R.id.rcvSavedPost);
        GridLayoutManager gridLayoutManagerSavedPost = new GridLayoutManager(this,1);
        rcvSavedPost.setLayoutManager(gridLayoutManagerSavedPost);

        btnSavedPostBack = findViewById(R.id.btnSavedPostBack);
        btnSavedPostBack.setOnClickListener(v -> {
            finish();
        });

        InitData();
    }

    private void InitData(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            String userId = firebaseUser.getUid();
            savedPostActivityPresenter.LoadSavedPosts(userId);
        }
    }
    @Override
    public void DisplaySavedPosts(ArrayList<PostSearchResult> postSearchResults) {
        SavedPostAdapter savedPostAdapter = new SavedPostAdapter(postSearchResults, this);
        //PostActiveAdapter PostActiveAdapter = new PostActiveAdapter(postSearchResults, this);
        rcvSavedPost.setAdapter(savedPostAdapter);
    }
}