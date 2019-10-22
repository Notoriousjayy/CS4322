package com.example.cs4322.Capture;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.cs4322.R;

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


    }
}
