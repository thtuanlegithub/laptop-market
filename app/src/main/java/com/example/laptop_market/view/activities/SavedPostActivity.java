package com.example.laptop_market.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import com.example.laptop_market.R;
import com.example.laptop_market.view.adapters.SavedPostAdapter;
import com.example.laptop_market.view.adapters.PostSearchResult.PostSearchResult;
import com.example.laptop_market.view.adapters.SavedPostAdapter;

import java.util.ArrayList;
import java.util.List;

public class SavedPostActivity extends AppCompatActivity {
    private RecyclerView rcvSavedPost;
    private Button btnSavedPostBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_post);

        rcvSavedPost = findViewById(R.id.rcvSavedPost);
        GridLayoutManager gridLayoutManagerSavedPost = new GridLayoutManager(this,1);
        rcvSavedPost.setLayoutManager(gridLayoutManagerSavedPost);
        SavedPostAdapter savedPostAdapter = new SavedPostAdapter(getListSavedPost());
        rcvSavedPost.setAdapter(savedPostAdapter);

        btnSavedPostBack = findViewById(R.id.btnSavedPostBack);
        btnSavedPostBack.setOnClickListener(v -> {
            finish();
        });
    }
    private List<PostSearchResult> getListSavedPost(){
        List<PostSearchResult> listSavedPost = new ArrayList<>();
        listSavedPost.add(new PostSearchResult("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listSavedPost.add(new PostSearchResult("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listSavedPost.add(new PostSearchResult("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listSavedPost.add(new PostSearchResult("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listSavedPost.add(new PostSearchResult("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listSavedPost.add(new PostSearchResult("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listSavedPost.add(new PostSearchResult("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        return  listSavedPost;
    }
}