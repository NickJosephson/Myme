package com.nitrogen.myme.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.nitrogen.myme.application.Services;
import com.nitrogen.myme.objects.Tag;

import com.nitrogen.myme.R;

import java.util.List;

public class SaveMemeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_meme);

        createTagCheckboxes();

        initializeButtons();
    }

    private void createTagCheckboxes () {
        // LinearLayout, which holds dynamic checkboxes
        final LinearLayout attractedTo = findViewById(R.id.tag_list_save_meme_button);
        List<Tag> allTags = Services.getTagsPersistence().getTags();

        // loop through all tags, creating checkboxes
        for(Tag curr : allTags) {
            final CheckBox tag = new CheckBox(this);
            tag.setText(curr.getName());
            tag.setTextSize(30);
            attractedTo.addView(tag);
        }
    }

    private void initializeButtons() {
        Button cancelButton = findViewById(R.id.cancel_save_meme_button);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SaveMemeActivity.this, ExploreActivity.class));
                finish(); //end this activity
            }
        });
    }
}
