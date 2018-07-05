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

import com.nitrogen.myme.objects.TemplateMeme;
import com.nitrogen.myme.persistence.MemeTemplatePersistence;
import com.nitrogen.myme.objects.Meme;
import com.nitrogen.myme.objects.Tag;
import com.nitrogen.myme.R;

import org.json.JSONObject;

public class MemeTemplatePersistenceStub implements MemeTemplatePersistence {
    private List<TemplateMeme> templates;
    private Map<String,Integer> templateMap = new HashMap<String,Integer>();

    private final String PATH = "android.resource://com.nitrogen.myme/";

    //**************************************************
    // Constructor
    //**************************************************

    public MemeTemplatePersistenceStub() {
        this.templates = new ArrayList<>();

        createTemplateMap();

        for(String name : templateMap.keySet()) {
            TemplateMeme newTemplate = new TemplateMeme(name, (PATH + templateMap.get(name)));
            templates.add(newTemplate);
        }
    }

    //**************************************************
    // Methods
    //**************************************************

    /* createTemplateMap
     *
     * purpose: Create stub templates. Each resource has a name associated with it
     */
    private void createTemplateMap () {
        templateMap.put("Two Buttons Template", R.drawable.template_two_buttons);
        templateMap.put("Gru Template", R.drawable.template_gru);
        templateMap.put("Think About It Template", R.drawable.template_think_about_it);
    }


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

    /* deleteMeme
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
     * purpose: Return a template by id
     */
    public TemplateMeme getTemplate(int id) {

        for(TemplateMeme template: templates){
            if(template.getTemplateID() == id) {
                return template;
            }
        }
        return null; // TODO: Violates Liskov
    }

}
