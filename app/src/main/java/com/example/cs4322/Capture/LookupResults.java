package com.example.cs4322.Capture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cs4322.R;

import java.util.ArrayList;

public class LookupResults extends AppCompatActivity {
    private RecyclerView resultsView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lookup_results);

        ArrayList<BookItem> bookList = new ArrayList<>();
        bookList.add(new BookItem("Harry Potter and the Sorcerer's Stone", "ISBN: 9788700631625", "Author: J.K. Rowling"));
        bookList.add(new BookItem("Harry Potter and the Goblet of Fire", "ISBN: 9780605039070", "Author: J.K. Rowling"));

        resultsView = findViewById(R.id.results);
        resultsView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ResultsAdapter(bookList);

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
