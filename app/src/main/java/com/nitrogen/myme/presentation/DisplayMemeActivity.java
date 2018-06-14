package com.nitrogen.myme.presentation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nitrogen.myme.R;
import com.nitrogen.myme.business.AccessMemes;
import com.nitrogen.myme.objects.ImageMeme;
import com.nitrogen.myme.objects.Meme;
import com.nitrogen.myme.objects.Tag;

public class DisplayMemeActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE_MEME_ID = "com.nitrogen.myme.MESSAGE_MEME_ID";
    private ImageMeme meme;

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_meme);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        int memeID = intent.getIntExtra(EXTRA_MESSAGE_MEME_ID, 0);

        AccessMemes accessMemes = new AccessMemes();

        meme = (ImageMeme) accessMemes.getMemeByID(memeID);
        ImageView imageView = findViewById(R.id.imageView);

        TextView textView = findViewById(R.id.panelName);
        textView.setText(meme.getName());
        imageView.setImageURI(Uri.parse(meme.getImagePath()));

        GridView grid = (GridView) findViewById(R.id.panelTags);
        grid.setNumColumns(3);
        grid.setHorizontalSpacing(-245);
        grid.setVerticalSpacing(30);
        ArrayAdapter<Tag> namesAdaptor = new ArrayAdapter<Tag>(this, R.layout.sliding_panel_tags, meme.getTags());

        grid.setAdapter(namesAdaptor);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        boolean fav = meme.isFavourite();

        if (fav) {
            fab.setImageResource(R.drawable.heart_on);
        } else {
            fab.setImageResource(R.drawable.heart_off);
        }


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                meme.setFavourite(!meme.isFavourite());
                boolean fav = meme.isFavourite();
                FloatingActionButton button = (FloatingActionButton) view;

                if (fav) {
                    button.setImageResource(R.drawable.heart_on);
                    Snackbar.make(view, "Added to favourites.", Snackbar.LENGTH_LONG)
                            .setAction("favourite", null).show();
                } else {
                    button.setImageResource(R.drawable.heart_off);
                    Snackbar.make(view, "Removed from favourites.", Snackbar.LENGTH_LONG)
                            .setAction("unfavourite", null).show();
                }

            }
        });
    }


}
