package com.nitrogen.myme.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.nitrogen.myme.R;
import com.nitrogen.myme.business.AccessMemes;
import com.nitrogen.myme.objects.ImageMeme;
import com.nitrogen.myme.objects.Meme;

public class DisplayMemeActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE_MEME_ID = "com.nitrogen.myme.MESSAGE_MEME_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_meme);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        int message = intent.getIntExtra(EXTRA_MESSAGE_MEME_ID, 0);

        AccessMemes accessMemes = new AccessMemes();

        ImageMeme meme = (ImageMeme) accessMemes.getMemes().get(message);
        ImageView imageView = findViewById(R.id.imageView);


        imageView.setImageURI(meme.getImagePath());

    }


}
