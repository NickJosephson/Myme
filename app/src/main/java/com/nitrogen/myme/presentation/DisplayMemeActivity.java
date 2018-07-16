package com.nitrogen.myme.presentation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nitrogen.myme.R;
import com.nitrogen.myme.business.AccessMemes;
import com.nitrogen.myme.persistence.ImageSaver;
import com.nitrogen.myme.objects.Meme;
import com.nitrogen.myme.objects.Tag;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class DisplayMemeActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE_MEME_NAME = "com.nitrogen.myme.MESSAGE_MEME_NAME";
    AccessMemes accessMemes = new AccessMemes();
    private Meme meme;

    //**************************************************
    // Activity Lifecycle
    //**************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_meme);

        // Setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setup back arrow in toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // Get the Intent that started this activity and extract the memeID
        Intent intent = getIntent();
        String memeName = intent.getStringExtra(EXTRA_MESSAGE_MEME_NAME);

        // Set the meme for this view with memeID
        if (memeName != null) {
            meme = accessMemes.getMemeByName(memeName);
        }

        toolbar.setTitle(meme.getName());
        setupView();
        setupFavoriteButton();
    }

    //**************************************************
    // Activity Events
    //**************************************************

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.display_meme_activity_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.export_meme:

                //memeExportHandler(meme)
                String memeImgPath = meme.getImagePath();
                Uri the_uri;

                if(memeImgPath.startsWith("android.resource")){
                    the_uri = Uri.parse(meme.getImagePath());
                }else{
                    the_uri = Uri.fromFile(new File(meme.getImagePath()));
                }

                Drawable drawable;

                try {
                    InputStream inputStream = getContentResolver().openInputStream(the_uri);
                    Log.e("input stream:", "hi"+inputStream.toString());
                    drawable = Drawable.createFromStream(inputStream, the_uri.toString() );
                } catch (FileNotFoundException e) {
                    Log.e("exportDrawable",e.getMessage());
                    Log.e("uri_of_failed_exportimg",the_uri.toString());
                    drawable = ContextCompat.getDrawable(findViewById(R.id.display_meme).getContext(),R.drawable.ic_trash);
                }

                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
                int result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

                if (result == 0){
                    new ImageSaver(findViewById(R.id.display_meme).getContext()).
                            setFileName(meme.getName()+".png").
                            setDirectoryName(getString(R.string.dir_img_export)).
                            setExternal(true).
                            save(ImageSaver.drawableToBitmap(drawable));

                    Snackbar.make(findViewById(R.id.display_meme), "Meme exported to Gallery.", Snackbar.LENGTH_LONG)
                            .setAction("export", null).show();

                }

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }


    //**************************************************
    // Helper Methods
    //**************************************************

    /* setupView
     *
     * purpose: Setup the view to display this view's meme.
     */
    private void setupView() {
        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageURI(Uri.parse(meme.getImagePath()));

        TextView textView = findViewById(R.id.panelName);
        textView.setText(meme.getName());

        GridView grid = (GridView) findViewById(R.id.panelTags);
        grid.setNumColumns(3);
        grid.setHorizontalSpacing(-245);
        grid.setVerticalSpacing(30);
        ArrayAdapter<Tag> namesAdaptor = new ArrayAdapter<Tag>(this, R.layout.sliding_panel_tags, meme.getTags());
        grid.setAdapter(namesAdaptor);
    }

    /* setupFavoriteButton
     *
     * purpose: Setup the favorite button.
     */
    private void setupFavoriteButton() {
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
                accessMemes.updatefav(meme);
                if (fav) {
                    button.setImageResource(R.drawable.heart_on);
                    Snackbar.make(view, "Added to favourites.", Snackbar.LENGTH_LONG)
                            .setAction("favourite", null).show();
                } else {
                    button.setImageResource(R.drawable.heart_off);
                    Snackbar.make(view, "Removed from favourites.", Snackbar.LENGTH_LONG)
                            .setAction("un-favourite", null).show();
                }
            }
        });
    }

}
