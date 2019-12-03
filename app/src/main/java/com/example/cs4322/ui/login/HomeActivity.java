package com.example.cs4322.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cs4322.Account.AccountActivity;
import com.example.cs4322.Favorites.FavoritesMenu;
import com.example.cs4322.R;
import com.example.cs4322.Search.LookupResults;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;


public class HomeActivity extends AppCompatActivity {
    Button btnLogout;
    Button capture;
    Button favorites;
    Button account;
    TextView welcome;

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mRef;
    private FirebaseUser user;
    private String userID;
    private String id;
    private ImageView img1, img2, img3, img4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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

        id = user.getDisplayName();


        welcome = findViewById(R.id.welcome);
        btnLogout = findViewById(R.id.button);
        capture = findViewById(R.id.capture);
        favorites = findViewById(R.id.history);
        account = findViewById(R.id.accountBtn);
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        img4 = findViewById(R.id.img4);

        Picasso.get().load("https://strandmag.com/wp-content/uploads/2015/12/sherlock-magnfier-strand.jpg").fit().centerCrop().into(img1);
        Picasso.get().load("https://images.unsplash.com/photo-1524995997946-a1c2e315a42f?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80").fit().centerCrop().into(img2);
        Picasso.get().load("https://www.harpgallery.com/photos/cd/dsk10-1-19gr2.jpg").fit().centerCrop().into(img3);
        Picasso.get().load("https://i.redd.it/maqdqda94v901.jpg").fit().centerCrop().into(img4);


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent inToMain = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(inToMain);
            }
        });

        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent inToMain = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(inToMain);
            }
        });

        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intoLookup = new Intent(HomeActivity.this, LookupResults.class);
                startActivity(intoLookup);
            }
        });

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intoLookup = new Intent(HomeActivity.this, LookupResults.class);
                startActivity(intoLookup);
            }
        });

        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intoFavorites = new Intent(HomeActivity.this, FavoritesMenu.class);
                startActivity(intoFavorites);
            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intoFavorites = new Intent(HomeActivity.this, FavoritesMenu.class);
                startActivity(intoFavorites);
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intoAccount = new Intent(HomeActivity.this, AccountActivity.class);
                startActivity(intoAccount);
            }
        });

        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intoAccount = new Intent(HomeActivity.this, AccountActivity.class);
                startActivity(intoAccount);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        id = user.getDisplayName();

        if (id != null && !id.equals("")) {
            welcome.setText("Welcome, " + id + "!");
        } else
            welcome.setText("Welcome, New User!");

    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }
}
