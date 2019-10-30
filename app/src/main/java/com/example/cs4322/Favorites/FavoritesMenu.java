package com.example.cs4322.Favorites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cs4322.R;
import com.example.cs4322.ui.login.HomeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class FavoritesMenu extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private FavoritesAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private FirebaseUser user;

    String userID;

    Button back;

    ArrayList<FavoriteItem> favoriteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_menu);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        user = mAuth.getCurrentUser();
        userID = user.getUid();
        myRef = mFirebaseDatabase.getReference();


        back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goBack = new Intent(FavoritesMenu.this, HomeActivity.class);
                startActivity(goBack);
            }
        });


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = mAuth.getCurrentUser();

            }
        };


        favoriteList = new ArrayList<>();

        mRecyclerView = findViewById(R.id.favorites);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new FavoritesAdapter(favoriteList);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> items = dataSnapshot.child(userID).child("Books").getChildren().iterator();
                favoriteList.clear();
                while(items.hasNext()) {
                    DataSnapshot item = items.next();
                    favoriteList.add(new FavoriteItem(
                            item.child("Title").getValue(String.class),
                            item.child("Author").getValue(String.class),
                            item.child("ISBN").getValue(String.class)
                    ));

                    mRecyclerView.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });


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
                String number = favoriteList.get(position).getISBN();
                favoriteList.remove(position);
                mAdapter.notifyItemRemoved(position);

                myRef.child(userID).child("Books").child(number).child("Title").removeValue();
                myRef.child(userID).child("Books").child(number).child("Author").removeValue();
                myRef.child(userID).child("Books").child(number).child("ISBN").removeValue();
            }
        });

    }
}
