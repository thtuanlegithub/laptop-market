package com.example.laptop_market.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import com.example.laptop_market.R;
import com.example.laptop_market.view.adapters.RatedPostAdapter;
import com.example.laptop_market.view.adapters.PostSearchResult.PostSearchResult;
import com.example.laptop_market.view.adapters.RatedPostAdapter;

import java.util.ArrayList;
import java.util.List;

public class RatedPostActivity extends AppCompatActivity {
    private RecyclerView rcvRatedPost;
    private Button btnRatedPostBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rated_post);

        rcvRatedPost = findViewById(R.id.rcvRatedPost);
        GridLayoutManager gridLayoutManagerRatedPost = new GridLayoutManager(this,1);
        rcvRatedPost.setLayoutManager(gridLayoutManagerRatedPost);
        RatedPostAdapter RatedPostAdapter = new RatedPostAdapter(getListRatedPost());
        rcvRatedPost.setAdapter(RatedPostAdapter);

        btnRatedPostBack = findViewById(R.id.btnRatedPostBack);
        btnRatedPostBack.setOnClickListener(v -> {
            finish();
        });
    }
    private List<PostSearchResult> getListRatedPost(){
        List<PostSearchResult> listRatedPost = new ArrayList<>();
        listRatedPost.add(new PostSearchResult("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listRatedPost.add(new PostSearchResult("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listRatedPost.add(new PostSearchResult("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listRatedPost.add(new PostSearchResult("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listRatedPost.add(new PostSearchResult("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listRatedPost.add(new PostSearchResult("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        listRatedPost.add(new PostSearchResult("Asus Gaming TUF A15 - Ryzen 7 - 16GB RAM",26000000,"Thành phố Hồ Chí Minh"));
        return  listRatedPost;
    }
}