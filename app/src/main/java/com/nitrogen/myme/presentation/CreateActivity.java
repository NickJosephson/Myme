package com.nitrogen.myme.presentation;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
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
import android.widget.ImageButton;
import android.widget.ImageView;

import android.widget.Toast;

import com.nitrogen.myme.BuildConfig;
import com.nitrogen.myme.R;

import com.nitrogen.myme.business.SaveHandler;
import com.nitrogen.myme.persistence.ImageSaver;
import com.nitrogen.myme.presentation.textEditor.Font;
import com.nitrogen.myme.presentation.textEditor.FontProvider;
import com.nitrogen.myme.presentation.textEditor.FontsAdapter;
import com.nitrogen.myme.presentation.textEditor.MotionView;
import com.nitrogen.myme.presentation.textEditor.TextEditorDialogFragment;
import com.nitrogen.myme.presentation.textEditor.TextEntity;
import com.nitrogen.myme.presentation.textEditor.TextLayer;

import java.net.URI;
import java.util.Date;
import java.util.List;

public class CreateActivity extends AppCompatActivity implements TextEditorDialogFragment.OnTextLayerCallback {

    private ImageView canvas;
    private Button rotateImageButton;
    private static final int PICK_IMAGE = 100; // can be any value
    private static final int PICK_TEMPLATE = 200;
    private static final int SAVE_MEME = 300;

    protected MotionView motionView;
    protected View textEntityEditPanel;
    private FontProvider fontProvider;
    private TextEditorDialogFragment fragment;

    private boolean isBlankCanvas;

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
        isBlankCanvas = true;

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
     *          - Rotate Image
     *          - Change Font
     *          - Edit Text
     *          - Delete Text
     */
    private void initializeImageButtons() {
        ImageButton changeFontButton;
        ImageButton editTextButton;
        ImageButton deleteTextButton;

        // rotate image button
        rotateImageButton = findViewById(R.id.rotateImgButton);
        rotateImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(canvas != null) {
                    canvas.setRotation(canvas.getRotation() + 90);
                }
            }
        });

        // text editing buttons...
        changeFontButton = findViewById(R.id.text_entity_font_change_button);
        changeFontButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeTextEntityFont();
            }
        });

        editTextButton = findViewById(R.id.text_entity_edit_button);
        editTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTextEntityEditing();
            }
        });

        deleteTextButton = findViewById(R.id.delete_text_entity_button);
        deleteTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTextEntity();
            }
        });
    }

    /* initializeTextButtons
     *
     * purpose: A method to assign actions to buttons that will control the following:
     *          - Adding Text
     *          - Uploading Image
     *          - Saving Meme
     *          - Pick Template
     */
    private void initializeTextButtons() {
        Button addTextButton;
        Button uploadImageButton;
        Button fromTemplateButton;
        Button saveMemeButton;

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
    }

    private void saveMeme () {
        Bitmap bitmap = saveScreenBitmap();

        // save uri
        String fileName = writeMemeToFile(bitmap);

        Intent intent = new Intent(CreateActivity.this, SaveMemeActivity.class);
        intent.putExtra(SaveMemeActivity.EXTRA_MESSAGE_FILE_NAME, fileName);

        // open save meme activity
        startActivityForResult(intent, SAVE_MEME);
    }

    private String writeMemeToFile (Bitmap bitmap) {
//        ImageSaver savior = new ImageSaver(CreateActivity.this);
//        String name = "meme" + (new Date()).toString() + ".png";
//        savior.setExternal(false);
//        savior.setDirectoryName("db");
//        savior.setFileName(name);
//        savior.save(bitmap);
//        return name;
        SaveHandler handler = new SaveHandler(CreateActivity.this);
        String name = handler.writeToFile(bitmap);
        return name;
    }

    private Bitmap saveScreenBitmap () {
        for (TextEntity textEntity: motionView.getTextEntities()) {
            if (textEntity != null) {
                textEntity.setIsSelected(false);
            }
        }

        View view = getWindow().getDecorView().getRootView();
        ImageView imageView = getWindow().getDecorView().findViewById(R.id.imageView1);
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        Rect rect = new Rect();

        imageView.getGlobalVisibleRect(rect);

        //  Create our resulting image (150--50),(75--25) = 200x100px
        Bitmap resultBmp = Bitmap.createBitmap(rect.width(), rect.height(), Bitmap.Config.ARGB_8888);
        //  draw source bitmap into resulting image at given position:
        new Canvas(resultBmp).drawBitmap(bitmap, -rect.left, -rect.top, null);

        return resultBmp;
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

    /* loadGalleryImage
     *
     * purpose: Render the image the user chose from their gallery.
     *
     */
    private void loadGalleryImage(Uri imageURI){
        // first clear the canvas by removing all text entities
        deleteAllTextEntities();


        canvas.setImageURI(imageURI);

        canvas.setRotation(0);

        if(!rotateImageButton.isShown()) {
            rotateImageButton.setVisibility(View.VISIBLE);
        }

        isBlankCanvas = false;
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
    private void loadTemplate(String templatePath) {

        // first clear the canvas by removing all text entities
        deleteAllTextEntities();

        // remove the rotation button
        rotateImageButton.setVisibility(View.GONE);

        // render template
        canvas.setImageURI(Uri.parse(templatePath));
        canvas.setRotation(0);

        // we have an image to edit
        isBlankCanvas = false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            switch(requestCode) {
                case PICK_IMAGE:
                    Uri imageURI = data.getData();
                    if(imageURI != null){
                        loadGalleryImage(imageURI);
                    }
                    break;
                case PICK_TEMPLATE:
                    String templatePath = data.getStringExtra("templatePath");
                    if(templatePath != null) {
                        loadTemplate(templatePath);
                    } else {
                        Toast toast = Toast.makeText(this, "Sorry, it looks like that template isn't available.", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                    break;
                case SAVE_MEME:
                    finish(); // finish this activity

            }
        }
    }

    //**************************************************
    // Text Editing (MotionViews)
    //**************************************************

    private final MotionView.MotionViewCallback motionViewCallback = new MotionView.MotionViewCallback() {
        @Override
        public void onTextEntitySelected(@Nullable TextEntity textEntity) {
            if(textEntity != null)
                textEntityEditPanel.setVisibility(View.VISIBLE);
            else
                textEntityEditPanel.setVisibility(View.GONE);
        }

        @Override
        public void onTextEntityDoubleTap(@NonNull TextEntity textEntity) {
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
                            textEntity.getTextLayer().getFont().setTypeface(fonts.get(which));
                            textEntity.updateEntity();
                            motionView.invalidate();
                        }
                    }
                })
                .show();
    }

    private void deleteTextEntity() {
        // delete TextEntity
        motionView.deletedSelectedTextEntity();

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

        // if we haven't selected an image or template to work with, default to a white background
        if(isBlankCanvas){
            canvas.setImageResource(android.R.color.white);
        }

        // redraw
        motionView.invalidate();

        startTextEntityEditing();
    }

    @Nullable
    private TextEntity currentTextEntity() {
        if (motionView != null && motionView.getSelectedTextEntity() != null) {
            return (motionView.getSelectedTextEntity());
        } else {
            return null;
        }
    }

    private void startTextEntityEditing() {
        TextEntity textEntity = currentTextEntity();
        if (textEntity != null) {
            fragment = TextEditorDialogFragment.getInstance(textEntity.getTextLayer().getText());
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
            TextLayer textLayer = textEntity.getTextLayer();
            if (!text.equals(textLayer.getText())) {
                textLayer.setText(text);
                textEntity.updateEntity();
                motionView.invalidate();
            }
        }
    }

    public void deleteAllTextEntities(){
        motionView.release();

        // remove text editor buttons
        textEntityEditPanel.setVisibility(View.GONE);
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
