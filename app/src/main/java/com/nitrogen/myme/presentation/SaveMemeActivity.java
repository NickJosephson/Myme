package com.nitrogen.myme.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.nitrogen.myme.application.Main;
import com.nitrogen.myme.application.Services;
import com.nitrogen.myme.business.Exceptions.InvalidMemeException;
import com.nitrogen.myme.business.MemeValidator;
import com.nitrogen.myme.business.UpdateMemes;
import com.nitrogen.myme.objects.Meme;
import com.nitrogen.myme.objects.Tag;

import com.nitrogen.myme.R;

import java.util.ArrayList;
import java.util.List;

public class SaveMemeActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE_FILE_NAME = "com.nitrogen.myme.MESSAGE_MEME_NAME";

    List<CheckBox> tagCheckBoxes;
    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_meme);

        // Get the Intent that started this activity and extract the memeID
        Intent intent = getIntent();
        fileName = intent.getStringExtra(EXTRA_MESSAGE_FILE_NAME);

        createTagCheckboxes();

        initializeButtons();
    }

    /* createTagCheckboxes
     *
     * purpose: Create checkboxes to display the tags available to be assigned to their new meme
     *
     */
    private void createTagCheckboxes () {
        // LinearLayout, which holds dynamic checkboxes
        final LinearLayout attractedTo = findViewById(R.id.tag_list_save_meme_button);
        List<Tag> allTags = Services.getTagsPersistence().getTags();
        tagCheckBoxes = new ArrayList<>();

        // loop through all tags, creating checkboxes
        for(Tag curr : allTags) {
            final CheckBox tag = new CheckBox(this);
            tag.setText(curr.getName());
            tag.setTextSize(30);
            attractedTo.addView(tag);
            tagCheckBoxes.add(tag);
        }
    }

    /* initializeButtons
     *
     * purpose: A method to assign actions to buttons that will control the following:
     *          - saving a meme
     *          - cancelling the save
     *
     */
    private void initializeButtons() {
        Button cancelButton = findViewById(R.id.cancel_save_meme_button);
        Button acceptButton = findViewById(R.id.accept_save_meme_button);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go back to create activity
                setResult(RESULT_CANCELED);
                finish();
            }

        });

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptMeme();
            }
        });
    }

    /* acceptMeme
     *
     * purpose: Validates the data the user has provided to save the meme.
     *          If a field is invalid, provide the user with a warning message.
     *          If all fields are valid, save the meme.
     *
     */
    private void acceptMeme () {
        Meme newMeme;
        EditText mEdit = findViewById(R.id.edit_text_meme_name);
        MemeValidator memeValidator = new MemeValidator();
        String name = mEdit.getText().toString();
        String picturePath;
        boolean isValid = true;

        // picture is defaults to trash
        // this will change when we can save memes
        //picturePath = Main.getDBPathName() + "/1234567.png";//"android.resource://com.nitrogen.myme/" + R.drawable.ic_trash;
        picturePath = "/data/user/0/com.nitrogen.myme/app_db/"+fileName;

        // create new Meme object

        newMeme = new Meme(name, picturePath);

        // add tags based on checkboxes
        for(CheckBox tag : tagCheckBoxes) {
            if(tag.isChecked()) {
                newMeme.addTag(new Tag(tag.getText().toString()));
            }
        }

        // validate Name
        try {
            memeValidator.validateName(newMeme);
        } catch(InvalidMemeException e) {

            // open a error message dialog box
            new AlertDialog.Builder(SaveMemeActivity.this)
                    .setTitle("Invalid Name")
                    .setMessage(e.getMessage())
                    .setPositiveButton("OK", null).create().show();

            isValid = false;
        }

        // validate tags
        try {
            memeValidator.validateTags(newMeme);
        } catch(InvalidMemeException e) {
            // open a error message dialog box
            new AlertDialog.Builder(SaveMemeActivity.this)
                    .setTitle("Invalid Tags")
                    .setMessage(e.getMessage())
                    .setPositiveButton("OK", null).create().show();

            isValid = false;
        }

        if(isValid) {
            // insert meme into database
            UpdateMemes memeUpdater = new UpdateMemes();
            memeUpdater.insertMeme(newMeme);

            // go to explore activity
            Intent intent = new Intent(this, ExploreActivity.class);
            startActivity(intent);
            setResult(RESULT_OK);
            finish();
        }
    }
}
