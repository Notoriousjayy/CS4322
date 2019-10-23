package com.example.cs4322.Favorites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.cs4322.Capture.ResultsAdapter;
import com.example.cs4322.R;
import com.example.cs4322.ui.login.HomeActivity;

import java.util.ArrayList;

public class FavoritesMenu extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private FavoritesAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    Button back;

    ArrayList<FavoriteItem> favoriteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_menu);

        back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goBack = new Intent(FavoritesMenu.this, HomeActivity.class);
                startActivity(goBack);
            }
        });

        favoriteList = new ArrayList<>();
        favoriteList.add(new FavoriteItem("The Worst President in History: The Legacy of Barrack Obama", "Author: Matt Margolis & Mark Noonan", "ISBN: 1234567890123"));

        mRecyclerView = findViewById(R.id.favorites);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new FavoritesAdapter(favoriteList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new FavoritesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent goBack = new Intent(FavoritesMenu.this, HomeActivity.class);
                startActivity(goBack);
            }

            @Override
            public void onDelete(int position) {
                removeItem(position);
                mAdapter.notifyItemRemoved(position);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        String t1 = intent.getStringExtra("title");
        String t2 = intent.getStringExtra("author");
        String t3 = intent.getStringExtra("isbn");
        if(t1 != null && t2 != null && t3 != null) {
            insertItem(t1, t2, t3);
            Toast.makeText(FavoritesMenu.this, "New Book Added!", Toast.LENGTH_LONG).show();
        }
    }

    public void insertItem(String title, String author, String isbn){
        favoriteList.add(new FavoriteItem(title, author, isbn));
    }


    public void removeItem(int position){
        favoriteList.remove(position);
    }
}
