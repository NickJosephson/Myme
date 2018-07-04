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

import com.nitrogen.myme.objects.Placeholder;
import com.nitrogen.myme.presentation.textEditor.Font;
import com.nitrogen.myme.presentation.textEditor.FontProvider;
import com.nitrogen.myme.presentation.textEditor.FontsAdapter;
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

        // Initialize space where user will create their meme
        canvas = (ImageView)findViewById(R.id.imageView1);

        // Initialize buttons
        initializeImageButtons();
        initializeTextButtons();

        // initializing globals
        this.fontProvider = new FontProvider(getResources());
        motionView = (MotionView) findViewById(R.id.main_motion_view);
        motionView.setVisibility(View.VISIBLE);
        textEntityEditPanel = findViewById(R.id.main_motion_text_entity_edit_panel);
        textEntityEditPanel.setVisibility(View.GONE);
        motionView.setMotionViewCallback(motionViewCallback);

    }

    /* initializeImageButtons
     *
     * purpose: A method to assign actions to buttons that will control the following:
     *          - Uploading an image
     *          - Selecting a template
     *          - Saving a meme
     */
    private void initializeImageButtons() {
        Button uploadImageButton;
        Button fromTemplateButton;
        Button saveMemeButton;

        // upload image button
        uploadImageButton = (Button)findViewById(R.id.gallery_button);
        uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        // select template button
        fromTemplateButton = findViewById(R.id.from_template_button);
        fromTemplateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openTemplates();
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

        // save meme button
        saveMemeButton = findViewById(R.id.save_meme_button);
        saveMemeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveMeme();
            }
        });

    }

    /* initializeTextButtons
     *
     * purpose: A method to assign actions to buttons that will control the following:
     *          - Adding text
     *          - Editing text
     *          - Deleting text
     */
    private void initializeTextButtons() {
        Button addTextButton;

        // add text button
        addTextButton = findViewById(R.id.add_text_button);
        addTextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addTextSticker();
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

    /* openGallery
     *
     * reference: https://www.youtube.com/watch?v=OPnusBmMQTw
     *
     * purpose: A method that launches a new activity to prompt the user to select and image
     *          they want to edit.
     *
     */
    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    /* openTemplates
     *
     * purpose: A method that launches a new activity to prompt the user to select the
     *          template they want to edit.
     *
     */
    private void openTemplates() {
        Intent templates = new Intent(CreateActivity.this, SelectTemplateActivity.class);
        startActivityForResult(templates, PICK_TEMPLATE);
    }

    /* loadTemplate
     *
     * purpose: Render the template the user selected.
     *
     */
    private void loadTemplate(Bundle template) {
        String templatePath;

        // get the image path
        templatePath = template.getString("templatePath");

        // render template
        canvas.setImageURI(Uri.parse(templatePath));

        // get the placeholders for this template
        ArrayList<Placeholder> placeholders = template.getParcelableArrayList("Placeholders");

        // render placeholders
        for(Placeholder p : placeholders) {
            renderPlaceholder(p);

        }
    }

    /* renderPlaceholder
     *
     * purpose: Render a placeholder on the screen for the user to edit.
     *
     */
    private void renderPlaceholder(Placeholder p) {
        TextLayer textLayer;
        TextEntity textEntity;

        textLayer = createTextLayer();
        textLayer.setText(p.getText());

        if(fontProvider.getFontNames().contains(p.getFontName())) {
            textLayer.getFont().setTypeface(p.getFontName());
        }

        textEntity = new TextEntity(textLayer, p.getWidth(), p.getHeight(), fontProvider);
        motionView.addEntityAndPosition(textEntity);

        // move it to its correct position
        textEntity.moveCenterTo(p.getPosition());

        // redraw
        motionView.invalidate();
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
                        // remove the rotation button
                        rotateImageButton.setVisibility(View.GONE);
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
        TextEntity textEntity = new TextEntity(textLayer, motionView.getWidth(), motionView.getHeight(), fontProvider);
        motionView.addEntityAndPosition(textEntity);

        // move text sticker up so that its not hidden under keyboard
        // Note: this must happen after calling motionView.addEntityAndPosition(textEntity);
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
