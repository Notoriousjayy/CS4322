package com.example.cs4322.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cs4322.Search.Lookup;
import com.example.cs4322.Favorites.FavoritesMenu;
import com.example.cs4322.R;
import com.example.cs4322.Search.LookupResults;
import com.google.firebase.auth.FirebaseAuth;


public class HomeActivity extends AppCompatActivity {
    Button btnLogout;
    Button capture;
    Button favorites;
    TextView welcome;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        welcome = findViewById(R.id.welcome);
        btnLogout = findViewById(R.id.button);
        capture = findViewById(R.id.capture);
        favorites = findViewById(R.id.history);

        Intent intent = getIntent();

        String id = intent.getStringExtra("extra");

        welcome.setText("Welcome, " + id + "!");

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intoFavorites = new Intent(HomeActivity.this, FavoritesMenu.class);
                startActivity(intoFavorites);
            }
        });
    }
}
