package com.example.cs4322.Capture;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cs4322.R;
import com.example.cs4322.ui.login.HomeActivity;

public class Lookup extends AppCompatActivity {

    EditText isbnInput;
    Button homeButton, lookupButton, camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lookup);

        homeButton = findViewById(R.id.homeButton);

        lookupButton = findViewById(R.id.Search);

        camera = findViewById(R.id.captureButton);

        isbnInput = findViewById(R.id.isbnInput);


        lookupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intoResults = new Intent(Lookup.this, LookupResults.class);
                startActivity(intoResults);
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toHome = new Intent(Lookup.this, HomeActivity.class);
                startActivity(toHome);
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intoCapture = new Intent(Lookup.this, Capture.class);
                startActivity(intoCapture);
            }
        });
    }
}
