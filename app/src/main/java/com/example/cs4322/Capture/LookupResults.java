package com.example.cs4322.Capture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cs4322.Favorites.FavoritesAdapter;
import com.example.cs4322.Favorites.FavoritesMenu;
import com.example.cs4322.R;

import java.util.ArrayList;

public class LookupResults extends AppCompatActivity {
    private RecyclerView resultsView;
    private ResultsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<BookItem> bookList;

    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lookup_results);

        bookList = new ArrayList<>();
        bookList.add(new BookItem("Harry Potter and the Sorcerer's Stone", "ISBN: 9788700631625", "Author: J.K. Rowling"));
        bookList.add(new BookItem("Harry Potter and the Goblet of Fire", "ISBN: 9780605039070", "Author: J.K. Rowling"));

        resultsView = findViewById(R.id.results);
        resultsView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ResultsAdapter(bookList);
        mAdapter.setOnItemClickListener(new ResultsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(LookupResults.this, FavoritesMenu.class);
                intent.putExtra("title", bookList.get(position).getText1());
                intent.putExtra("author", bookList.get(position).getText3());
                intent.putExtra("isbn", bookList.get(position).getText2());

                startActivity(intent);
            }
        });

        resultsView.setLayoutManager(mLayoutManager);
        resultsView.setAdapter(mAdapter);

        back = findViewById(R.id.backButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toLookup = new Intent(LookupResults.this, Lookup.class);
                startActivity(toLookup);
            }
        });
    }
}
