package com.nitrogen.myme.presentation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nitrogen.myme.R;


public class CreateActivity extends AppCompatActivity {

    private ImageView uploadedImage;
    private Button uploadImagebutton;
    private Button rotateImagebutton;
    private static final int PICK_IMAGE = 100; // can be any value
    private Uri imageURI;

    private TextView orientation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        // Set up bottom navigation
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Menu bottomNavMenu = navigation.getMenu();
        MenuItem item = bottomNavMenu.getItem(1);
        item.setChecked(true);

        uploadedImage = (ImageView)findViewById(R.id.imageView1);
        initializeButtons();
        orientation = (TextView)findViewById(R.id.imageOrientation);
    }

    private void initializeButtons() {
        uploadImagebutton = (Button)findViewById(R.id.galleryButton);

        uploadImagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        rotateImagebutton = (Button)findViewById(R.id.rotateImgButton);

        rotateImagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uploadedImage != null) {
                    uploadedImage.setRotation(uploadedImage.getRotation() + 90);
                }
            }
        });

    }

    // reference: https://www.youtube.com/watch?v=OPnusBmMQTw
    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageURI = data.getData();
            uploadedImage.setImageURI(imageURI);

            if(!rotateImagebutton.isShown()) {
                rotateImagebutton.setVisibility(View.VISIBLE);
            }
        }
    }

    //**************************************************
    // Navigation Events
    //**************************************************

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_explore:
                    startActivity(new Intent(CreateActivity.this, ExploreActivity.class));
                    finish(); //end this activity
                    return true;
                case R.id.navigation_studio:
                    return true;
                case R.id.navigation_favourites:
                    startActivity(new Intent(CreateActivity.this, FavouritesActivity.class));
                    finish(); //end this activity
                    return true;
            }
            return false;
        }
    };
}
