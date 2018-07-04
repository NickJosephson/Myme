package com.nitrogen.myme.persistence.stubs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.PointF;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import com.nitrogen.myme.objects.Placeholder;
import com.nitrogen.myme.objects.TemplateMeme;
import com.nitrogen.myme.persistence.MemeTemplatePersistence;
import com.nitrogen.myme.objects.Meme;
import com.nitrogen.myme.objects.Tag;
import com.nitrogen.myme.R;

import org.json.JSONObject;

public class MemeTemplatePersistenceStub implements MemeTemplatePersistence {
    private List<TemplateMeme> templates;
    private final String PATH = "android.resource://com.nitrogen.myme/";

    //**************************************************
    // Constructor
    //**************************************************

    public MemeTemplatePersistenceStub() {
        this.templates = new ArrayList<>();

        createTemplateTwoButtons();
        createTemplateGru();
        createTemplateThinkAboutIt();
    }

    //**************************************************
    // Methods
    //**************************************************

    @Override
    public List<TemplateMeme> getTemplates() {
        return Collections.unmodifiableList(templates);
    }

    /* insertTemplate
     *
     * purpose: Insert a template into the database.
     *          Returns True if the template was added and False otherwise.
     */
    @Override
    public boolean insertTemplate(TemplateMeme template) {
        boolean templateInserted = false;

        // don't add duplicates
        if(!templates.contains(template)) {
            templates.add(template);
            templateInserted = true;
        }

        return templateInserted;
    }

    /* deleteTemplate
     *
     * purpose: Delete a template from the database.
     */
    @Override
    public TemplateMeme deleteTemplate(TemplateMeme template) {
        int index;

        index = templates.indexOf(template);
        if (index >= 0) {
            templates.remove(index);
        }

        return template;
    }

    /* getTemplate
     *
     * purpose: Return a template template by id
     */
    public TemplateMeme getTemplate(int id) {

        for(TemplateMeme template: templates){
            if(template.getTemplateID() == id) {
                return template;
            }
        }
        return null; // TODO: Violates Liskov
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     * Here is where we create the templates
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    private void createTemplateTwoButtons(){

        ArrayList<Placeholder> placeholders = new ArrayList<>();

        PointF positions[] = {
                new PointF(394.9008f, 464.0278f),
                new PointF(668.89f, 398.39233f)};


        // both placeholders are identical, so we need to only define one width and height
        int width = 800;
        int height = 200;

        for(int i = 0 ; i < positions.length ; i++){
            placeholders.add(new Placeholder(width, height, true, positions[i]));
        }

        templates.add(new TemplateMeme("Two Buttons Template",PATH+R.drawable.template_two_buttons, placeholders));

    }

    private void createTemplateGru(){

        ArrayList<Placeholder> placeholders = new ArrayList<>();

        PointF positions[] = {
                new PointF(448.7607f, 728.03955f),
                new PointF(1059.5837f, 728.03955f),
                new PointF(448.7607f, 1121.40272f),
                new PointF(1059.5837f, 1121.40272f)};

        // all placeholders are identical, so we need to only define one width and height
        int width = 800;
        int height = 200;

        for(int i = 0 ; i < positions.length ; i++){
            placeholders.add(new Placeholder(width, height, true, positions[i]));
        }

        templates.add(new TemplateMeme("Gru Template",PATH+R.drawable.template_gru, placeholders));
    }

    private void createTemplateThinkAboutIt(){

        ArrayList<Placeholder> placeholders = new ArrayList<>();

        PointF positions[] = {new PointF(600.0f, 474.33685f)};

        int width = 1500;
        int height = 800;

        for(int i = 0 ; i < positions.length ; i++){
            placeholders.add(new Placeholder(width, height, false, positions[i]));
        }

        templates.add(new TemplateMeme("Think About It Template",PATH+R.drawable.template_think_about_it, placeholders));
    }

}
