package com.nitrogen.myme.business;

import java.util.List;
import java.util.Collections;

import com.nitrogen.myme.application.Services;
import com.nitrogen.myme.objects.Meme;
import com.nitrogen.myme.objects.TemplateMeme;
import com.nitrogen.myme.persistence.MemeTemplatePersistence;
import com.nitrogen.myme.persistence.MemesPersistence;

public class AccessMemeTemplates {
    private MemeTemplatePersistence memeTemplatePersistence;
    private List<TemplateMeme> templates;

    //**************************************************
    // Constructor
    //**************************************************

    public AccessMemeTemplates() {
        memeTemplatePersistence = Services.getMemeTemplatePersistence();
        templates = memeTemplatePersistence.getTemplates();;
    }

    //**************************************************
    // Methods
    //**************************************************

    /* getTemplates
     *
     * purpose: Return a list of all templates.
     */
    public List<TemplateMeme> getTemplates() {
        return Collections.unmodifiableList(templates);
    }

    /* getTemplateByID
     *
     * purpose: Return a Meme matching the given meme ID
     *          or null if non is found.
     */
    public TemplateMeme getTemplateByID(int templateID) {
        for (TemplateMeme template : templates) {
            if (template.getTemplateID() == templateID) {
                return template;
            }
        }

        return null;
    }

}
