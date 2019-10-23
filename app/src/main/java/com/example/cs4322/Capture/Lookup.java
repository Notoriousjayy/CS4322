package com.example.cs4322.Capture;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.cs4322.R;
import com.example.cs4322.ui.login.HomeActivity;

public class Lookup extends AppCompatActivity {

    EditText isbnInput;
    Button homeButton, lookupButton, camera;
    ImageView preview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lookup);

        homeButton = findViewById(R.id.homeButton);

        lookupButton = findViewById(R.id.Search);

        camera = findViewById(R.id.captureButton);

        isbnInput = findViewById(R.id.isbnInput);

        preview = (ImageView)findViewById(R.id.previewImg);


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
                Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePic, 0);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            preview.setImageBitmap(bitmap);
    }
}
