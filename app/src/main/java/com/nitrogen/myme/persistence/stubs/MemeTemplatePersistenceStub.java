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
    private List<JSONObject> templateObjs;

    private final String PATH = "android.resource://com.nitrogen.myme/";

    //**************************************************
    // Constructor
    //**************************************************

    public MemeTemplatePersistenceStub() {
        this.templates = new ArrayList<>();

        createTemplates();
    }

    //**************************************************
    // Methods
    //**************************************************

    /* createTemplates
     *
     * purpose: Create stub of meme templates. Each resource has a name associated with it
     */
    private void createTemplates () {
        PointF points1[] = new PointF[1];
        PointF points2[] = new PointF[2];
        PointF points3[] = new PointF[3];
        PointF points4[] = new PointF[4];

        TemplateMeme newTemplate;

        points2[0] = new PointF(389.83154f, 443.7795f);
        points2[1] = new PointF(684.2853f, 403.4104f);
        newTemplate = new TemplateMeme("Two Buttons Template",PATH+R.drawable.template_two_buttons, points2);
        templates.add(newTemplate);

        points4[0] = new PointF(448.7607f, 728.03955f);
        points4[1] = new PointF(1059.5837f, 728.03955f);
        points4[2] = new PointF(448.7607f, 1121.40272f);
        points4[3] = new PointF(1059.5837f, 1121.40272f);
        newTemplate = new TemplateMeme("Gru Template",PATH+R.drawable.template_gru, points4);
        templates.add(newTemplate);

        points1[0] = new PointF(600.0f, 650.0f);
        newTemplate = new TemplateMeme("Think About It Template",PATH+R.drawable.template_think_about_it, points1);
        templates.add(newTemplate);
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

}
