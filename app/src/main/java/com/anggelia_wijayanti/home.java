package com.anggelia_wijayanti;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class home extends AppCompatActivity {
    private ImageView imageView;
    private Button btn;
    private static final int ACTIVITY_REQUEST_CODE = 1088;
    private static final int PERMISSION_REQUEST_CODE = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        TextView userNameTextView = findViewById(R.id.tekshome);
        userNameTextView.setText("Hello " + username + ", kamu sudah login!");

        imageView =findViewById(R.id.imageViewCamera);
        btn = findViewById(R.id.btnCamera);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                checkPermissionAndOpenCamera();
            }
        });
    }
    private void checkPermissionAndOpenCamera() {
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},PERMISSION_REQUEST_CODE);
            return;
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ACTIVITY_REQUEST_CODE:
                Bitmap capturedImage = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(capturedImage);
                break;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @Nullable String[] permissions, @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_REQUEST_CODE:
            if (grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
               checkPermissionAndOpenCamera();
            }
                break;
        }


    }


}