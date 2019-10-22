package com.example.cs4322.Capture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageAnalysisConfig;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureConfig;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.lifecycle.LifecycleOwner;

import android.os.Bundle;
import android.util.Size;
import android.view.TextureView;

import com.example.cs4322.R;

public class Capture extends AppCompatActivity {

    TextureView textureView;
    PreviewConfig previewConfig;
    Preview preview;
    ImageCaptureConfig imageCaptureConfig;
    ImageCapture imageCapture;
    ImageAnalysisConfig analysisConfig;
    ImageAnalysis imageAnalysis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);

        textureView = findViewById(R.id.textureView);

        previewConfig = new PreviewConfig.Builder().setTargetRotation(getWindowManager()
                .getDefaultDisplay().getRotation()).build();
        //  Take picture
        imageCaptureConfig = new ImageCaptureConfig.Builder() .setTargetRotation(getWindowManager()
                .getDefaultDisplay().getRotation()).build();
        //  Analyze Images
        analysisConfig = new ImageAnalysisConfig.Builder().setTargetResolution(new Size(1280, 720))
                .setImageReaderMode(ImageAnalysis.ImageReaderMode.ACQUIRE_LATEST_IMAGE).build();

        imageCapture = new ImageCapture(imageCaptureConfig);
        preview = new Preview(previewConfig);
        imageAnalysis = new ImageAnalysis(analysisConfig);


        imageAnalysis.setAnalyzer(new ImageAnalysis.Analyzer() {
            @Override
            public void analyze(ImageProxy image, int rotationDegrees) {
                // insert custom code here
            }
        });

        //   Implement Control focus
        //   https://developer.android.com/training/camerax/configuration#java
        preview.setOnPreviewOutputUpdateListener(new Preview.OnPreviewOutputUpdateListener() {
            @Override
            public void onUpdated(Preview.PreviewOutput previewOutput) {
                // The output data-handling is configured in a listener.
                textureView.setSurfaceTexture(previewOutput.getSurfaceTexture());

                // custom code here
            }
        });


        CameraX.bindToLifecycle((LifecycleOwner) this, preview, imageCapture, imageAnalysis);


    }
}
