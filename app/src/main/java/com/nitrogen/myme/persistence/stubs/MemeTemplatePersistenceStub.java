package com.nitrogen.myme.persistence.stubs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class MemeTemplatePersistenceStub implements MemeTemplatePersistence {
    private List<TemplateMeme> templates;
    private Map<String,Integer> templateMap = new HashMap<String,Integer>();

    //**************************************************
    // Constructor
    //**************************************************

    public MemeTemplatePersistenceStub() {
        this.templates = new ArrayList<>();

        createTemplateMap();

        for(String name : templateMap.keySet()) {
            PointF points[] = { new PointF(0.0f , 0.0f),
                                new PointF(0.0f , 0.0f)};

            TemplateMeme newMeme = new TemplateMeme(name, ("android.resource://com.nitrogen.myme/" + templateMap.get(name)), points);
            templates.add(newMeme);
        }
    }

    //**************************************************
    // Methods
    //**************************************************

    /* createTemplateMap
     *
     * purpose: Create stub memes. Each resource has a name associated with it
     */
    private void createTemplateMap () {
        templateMap.put("Forever Alone Template", R.drawable.template_forever_alone);
        templateMap.put("Frick Yea", R.drawable.template_frick_yea);
        templateMap.put("Mother Of God", R.drawable.template_mother_of_god);
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
     * purpose: Delete a meme from the database.
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
     * purpose: Return a meme template by id
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
