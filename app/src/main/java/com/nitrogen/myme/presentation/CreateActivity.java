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
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.Toast;

import com.nitrogen.myme.BuildConfig;
import com.nitrogen.myme.R;
import com.nitrogen.myme.business.AccessMemeTemplates;

import com.nitrogen.myme.presentation.textEditor.Font;
import com.nitrogen.myme.presentation.textEditor.FontProvider;
import com.nitrogen.myme.presentation.textEditor.FontsAdapter;
import com.nitrogen.myme.presentation.textEditor.Layer;
import com.nitrogen.myme.presentation.textEditor.MotionEntity;
import com.nitrogen.myme.presentation.textEditor.MotionView;
import com.nitrogen.myme.presentation.textEditor.TextEditorDialogFragment;
import com.nitrogen.myme.presentation.textEditor.TextEntity;
import com.nitrogen.myme.presentation.textEditor.TextLayer;

import java.util.ArrayList;
import java.util.List;


public class CreateActivity extends AppCompatActivity implements TextEditorDialogFragment.OnTextLayerCallback {

    private ImageView canvas;
    private Button rotateImageButton;
    private static final int PICK_IMAGE = 100; // can be any value
    private static final int PICK_TEMPLATE = 200;

    protected MotionView motionView;
    protected View textEntityEditPanel;
    private FontProvider fontProvider;
    private TextEditorDialogFragment fragment;

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

        canvas = (ImageView)findViewById(R.id.imageView1);
        initializeButtons();

        // initializing globals
        this.fontProvider = new FontProvider(getResources());
        motionView = (MotionView) findViewById(R.id.main_motion_view);
        motionView.setVisibility(View.VISIBLE);
        textEntityEditPanel = findViewById(R.id.main_motion_text_entity_edit_panel);
        textEntityEditPanel.setVisibility(View.GONE);
        motionView.setMotionViewCallback(motionViewCallback);

    }

    private void initializeButtons() {
        Button uploadImageButton;
        Button addTextButton;
        Button saveMemeButton;
        Button fromTemplateButton;

        // upload image button
        uploadImageButton = (Button)findViewById(R.id.gallery_button);
        uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        // rotate image button
        rotateImageButton = (Button)findViewById(R.id.rotateImgButton);
        rotateImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(canvas != null) {
                    canvas.setRotation(canvas.getRotation() + 90);
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

        // text editing buttons
        findViewById(R.id.text_entity_font_change_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeTextEntityFont();
            }
        });
        findViewById(R.id.text_entity_edit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTextEntityEditing();
            }
        });

        // select template button
        fromTemplateButton = findViewById(R.id.from_template_button);
        fromTemplateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openTemplates();
            }
        });


        findViewById(R.id.delete_text_entity_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTextEntity();
            }
        });

    }

    private void saveMeme () {

        // open SaveMeme activity
        startActivity(new Intent(CreateActivity.this, SaveMemeActivity.class));
        finish(); //end this activity

    }

    // reference: https://www.youtube.com/watch?v=OPnusBmMQTw
    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    private void openTemplates() {
        Intent templates = new Intent(CreateActivity.this, SelectTemplateActivity.class);
        startActivityForResult(templates, PICK_TEMPLATE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            switch(requestCode) {
                case PICK_IMAGE:
                    Uri imageURI = data.getData();
                    canvas.setImageURI(imageURI);

                    if(!rotateImageButton.isShown()) {
                        rotateImageButton.setVisibility(View.VISIBLE);
                    }
                    break;
                case PICK_TEMPLATE:
                    Bundle extras = data.getExtras();
                    if(extras != null && extras.getString("templatePath") != null) {
                        // first clear the canvas by removing all text entities
                        deleteAllTextEntities();

                        // then load the template
                        loadTemplate(extras);
                    } else {
                        Toast toast = Toast.makeText(this, "Sorry, it looks like that template isn't available.", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                    break;

            }
        }

    }

    private void loadTemplate(Bundle template) {
        String templatePath;
        Object temp[]; // required to get serializable data
        float coordinates[][];
        float x, y;
        int width, height;

        // get the image path
        templatePath = template.getString("templatePath");

        // display the template
        canvas.setImageURI(Uri.parse(templatePath));

        // get the coordinates of where the text should be
        temp = (Object[])template.getSerializable("templateCoordinates");
        if(temp != null) {
            coordinates = new float[temp.length][2];
            for(int i = 0 ; i < temp.length ; i++) {
                coordinates[i] = (float[]) temp[i];

                x = coordinates[i][0];
                y = coordinates[i][1];
                width = 200;
                height = 800;

                // add text to the template
                addTextSticker(x, y, width, height);
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

    private void deleteTextEntity() {
        // delete TextEntity
        motionView.deletedSelectedEntity();

        // remove text editor buttons
        textEntityEditPanel.setVisibility(View.GONE);
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

    protected void addTextSticker(float x, float y, int width, int height) {
        List<MotionEntity> entities = motionView.getEntities();
        TextLayer textLayer = createTextLayer();

        // if the width is smaller than the length of the default "Sample Text"
        if(width <= 200);
            textLayer.setText("Sample\nText");

        TextEntity textEntity = new TextEntity(textLayer, motionView.getWidth(), motionView.getHeight(), fontProvider);
        motionView.addEntityAndPosition(textEntity);

        // move text sticker up so that its not hidden under keyboard
        PointF center = textEntity.absoluteCenter();
        center.x = x;
        center.y = y;
        textEntity.moveCenterTo(center);

        // redraw
        motionView.invalidate();

//        startTextEntityEditing();
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
            fragment = TextEditorDialogFragment.getInstance(textEntity.getLayer().getText());
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

    public void deleteAllTextEntities(){
        motionView.release();
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
