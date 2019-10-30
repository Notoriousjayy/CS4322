package com.example.cs4322.Capture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cs4322.Favorites.FavoritesMenu;
import com.example.cs4322.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.EventListener;

public class LookupResults extends AppCompatActivity {
    private RecyclerView resultsView;
    private ResultsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mRef;
    private FirebaseUser user;

    private ArrayList<BookItem> bookList;

    Button back;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lookup_results);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference();
        user = mAuth.getCurrentUser();
        userID = user.getUid();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = mAuth.getCurrentUser();

            }
        };

        bookList = new ArrayList<>();
        bookList.add(new BookItem("Harry Potter and the Sorcerer's Stone", "9788700631625", "J.K. Rowling"));
        bookList.add(new BookItem("Harry Potter and the Goblet of Fire", "9780605039070", "J.K. Rowling"));

        resultsView = findViewById(R.id.results);
        resultsView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ResultsAdapter(bookList);
        mAdapter.setOnItemClickListener(new ResultsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String title = bookList.get(position).getTitle();
                String author = bookList.get(position).getAuthor();
                String isbn = bookList.get(position).getISBN();

                mRef.child(userID).child("Books").child(isbn).child("Title").setValue(title);
                mRef.child(userID).child("Books").child(isbn).child("Author").setValue(author);
                mRef.child(userID).child("Books").child(isbn).child("ISBN").setValue(isbn);

                Intent intent = new Intent(LookupResults.this, FavoritesMenu.class);
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

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
}
