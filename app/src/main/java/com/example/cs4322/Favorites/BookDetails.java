package com.example.cs4322.Favorites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.cs4322.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;


public class BookDetails extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mRef;
    private FirebaseUser user;
    private String userID;

    private ImageView previewImg;
    private TextView titleTxt;
    private TextView authorTxt;
    private TextView detailsTxt;
    private TextView isbnTxt;
    private TextView noImage;
    private TextView previewTxt;
    private Button favoriteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        previewImg = findViewById(R.id.previewImg);
        titleTxt = findViewById(R.id.titleTxt);
        authorTxt = findViewById(R.id.authorTxt);
        isbnTxt = findViewById(R.id.isbnTxt);
        detailsTxt = findViewById(R.id.detailsTxt);
        noImage = findViewById(R.id.noImageTxt);
        previewTxt = findViewById(R.id.previewTxt);

        favoriteBtn = findViewById(R.id.favoriteButton);

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

        Bundle extras = getIntent().getExtras();

        String title =  extras.getString("title");
        String author = extras.getString("author");
        String isbn = extras.getString("isbn");
        String details = extras.getString("details");
        final String preview = extras.getString("preview");
        final String img = extras.getString("img");

        titleTxt.setText(title);
        authorTxt.setText(author);
        isbnTxt.setText(isbn);
        detailsTxt.setText(details);
        previewTxt.setText(preview);
        String imgURL = img;

        Picasso.get().load(imgURL).into(previewImg);

        favoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mTitle = titleTxt.getText().toString();
                String mAuthor = authorTxt.getText().toString();
                String mIsbn = isbnTxt.getText().toString();
                String mdetails = detailsTxt.getText().toString();
                String mPreview = preview;
                String mImage = img;

                if (mIsbn.equals("No ISBN")) {
                    Toast.makeText(BookDetails.this, "This book has no ISBN and can not be added to favorites", Toast.LENGTH_LONG).show();
                    return;
                }

                mRef.child(userID).child("Books").child(mIsbn).child("Title").setValue(mTitle);
                mRef.child(userID).child("Books").child(mIsbn).child("Author").setValue(mAuthor);
                mRef.child(userID).child("Books").child(mIsbn).child("ISBN").setValue(mIsbn);
                mRef.child(userID).child("Books").child(mIsbn).child("Details").setValue(mdetails);
                mRef.child(userID).child("Books").child(mIsbn).child("Image").setValue(mImage);
                mRef.child(userID).child("Books").child(mIsbn).child("Preview").setValue(mPreview);

                Toast.makeText(BookDetails.this, "New book added to Favorites!", Toast.LENGTH_SHORT).show();

                Intent intoFavorites = new Intent(BookDetails.this, FavoritesMenu.class);
                startActivity(intoFavorites);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

}
