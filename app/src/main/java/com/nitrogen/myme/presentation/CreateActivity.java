package com.nitrogen.myme.presentation;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nitrogen.myme.BuildConfig;
import com.nitrogen.myme.R;
import com.nitrogen.myme.textEditor.Font;
import com.nitrogen.myme.textEditor.FontProvider;
import com.nitrogen.myme.textEditor.FontsAdapter;
import com.nitrogen.myme.textEditor.MotionEntity;
import com.nitrogen.myme.textEditor.MotionView;
import com.nitrogen.myme.textEditor.TextEditorDialogFragment;
import com.nitrogen.myme.textEditor.TextEntity;
import com.nitrogen.myme.textEditor.TextLayer;

import java.util.List;


public class CreateActivity extends AppCompatActivity implements TextEditorDialogFragment.OnTextLayerCallback {

    private ImageView uploadedImage;
    private Button uploadImagebutton;
    private Button rotateImagebutton;
    private Button addTextButton;
    private Button saveMemeButton;
    private static final int PICK_IMAGE = 100; // can be any value
    private Uri imageURI;

    private TextView orientation;

    protected MotionView motionView;
    protected View textEntityEditPanel;
    private FontProvider fontProvider;

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

        // initializing globals
        this.fontProvider = new FontProvider(getResources());
        motionView = (MotionView) findViewById(R.id.main_motion_view);
        motionView.setVisibility(View.VISIBLE);
        textEntityEditPanel = findViewById(R.id.main_motion_text_entity_edit_panel);
        textEntityEditPanel.setVisibility(View.GONE);
        motionView.setMotionViewCallback(motionViewCallback);
    }

    private void initializeButtons() {
        uploadImagebutton = (Button)findViewById(R.id.gallery_button);

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

        // add text button
        addTextButton = findViewById(R.id.add_text_button);
        addTextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addTextSticker();
            }
        });

        // save meme button
        saveMemeButton = findViewById(R.id.save_meme_button);
        saveMemeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveMeme();
            }
        });

        initTextEntitiesListeners();
    }

    private void saveMeme () {

        // open SaveMeme activity
        startActivity(new Intent(CreateActivity.this, SaveMemeActivity.class));
        finish(); //end this activity

    }

    private void initTextEntitiesListeners() {
        findViewById(R.id.text_entity_font_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeTextEntityFont();
            }
        });
        findViewById(R.id.text_entity_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTextEntityEditing();
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
    // Text Editing (MotionViews)
    //**************************************************

    private final MotionView.MotionViewCallback motionViewCallback = new MotionView.MotionViewCallback() {
        @Override
        public void onEntitySelected(@Nullable MotionEntity entity) {
            if (entity instanceof TextEntity) {
                textEntityEditPanel.setVisibility(View.VISIBLE);
            } else {
                textEntityEditPanel.setVisibility(View.GONE);
            }
        }

        @Override
        public void onEntityDoubleTap(@NonNull MotionEntity entity) {
            startTextEntityEditing();
        }
    };

    private void changeTextEntityFont() {
        final List<String> fonts = fontProvider.getFontNames();
        FontsAdapter fontsAdapter = new FontsAdapter(this, fonts, fontProvider);
        new AlertDialog.Builder(this)
                .setTitle(R.string.select_font)
                .setAdapter(fontsAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        TextEntity textEntity = currentTextEntity();
                        if (textEntity != null) {
                            textEntity.getLayer().getFont().setTypeface(fonts.get(which));
                            textEntity.updateEntity();
                            motionView.invalidate();
                        }
                    }
                })
                .show();
    }

    protected void addTextSticker() {
        TextLayer textLayer = createTextLayer();
        TextEntity textEntity = new TextEntity(textLayer, motionView.getWidth(),
                motionView.getHeight(), fontProvider);
        motionView.addEntityAndPosition(textEntity);

        // move text sticker up so that its not hidden under keyboard
        PointF center = textEntity.absoluteCenter();
        center.y = center.y * 0.5F;
        textEntity.moveCenterTo(center);

        // redraw
        motionView.invalidate();

        startTextEntityEditing();
    }

    @Nullable
    private TextEntity currentTextEntity() {
        if (motionView != null && motionView.getSelectedEntity() instanceof TextEntity) {
            return ((TextEntity) motionView.getSelectedEntity());
        } else {
            return null;
        }
    }

    private void startTextEntityEditing() {
        TextEntity textEntity = currentTextEntity();
        if (textEntity != null) {
            TextEditorDialogFragment fragment = TextEditorDialogFragment.getInstance(textEntity.getLayer().getText());
            fragment.show(getFragmentManager(), TextEditorDialogFragment.class.getName());
        }
    }

    private TextLayer createTextLayer() {
        TextLayer textLayer = new TextLayer();
        Font font = new Font();

        font.setColor(TextLayer.Limits.INITIAL_FONT_COLOR);
        font.setSize(TextLayer.Limits.INITIAL_FONT_SIZE);
        font.setTypeface(fontProvider.getDefaultFontName());//  < runtime error

        textLayer.setFont(font);

        if (BuildConfig.DEBUG) {
            textLayer.setText("Sample Text");
        }

        return textLayer;
    }

    @Override
    public void textChanged(@NonNull String text) {
        TextEntity textEntity = currentTextEntity();
        if (textEntity != null) {
            TextLayer textLayer = textEntity.getLayer();
            if (!text.equals(textLayer.getText())) {
                textLayer.setText(text);
                textEntity.updateEntity();
                motionView.invalidate();
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
