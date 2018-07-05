package com.nitrogen.myme.business;

import java.util.List;
import java.util.Collections;

import com.nitrogen.myme.application.Services;
import com.nitrogen.myme.business.Exceptions.TemplateNotFoundException;
import com.nitrogen.myme.objects.TemplateMeme;
import com.nitrogen.myme.persistence.MemeTemplatesPersistence;

public class AccessMemeTemplates {
    private MemeTemplatesPersistence memeTemplatePersistence;
    private List<TemplateMeme> templates;

    //**************************************************
    // Constructors
    //**************************************************

    public AccessMemeTemplates() {
        memeTemplatePersistence = Services.getMemeTemplatePersistence();
        templates = memeTemplatePersistence.getTemplates();
    }
    public AccessMemeTemplates(MemeTemplatesPersistence MemeTemplatePersistenceGiven) {
        memeTemplatePersistence = MemeTemplatePersistenceGiven;
        templates = memeTemplatePersistence.getTemplates();
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
    public TemplateMeme getTemplateByName(String templateName) throws TemplateNotFoundException {
        for (TemplateMeme template : templates) {
            if (template.getName().equalsIgnoreCase(templateName)) {
                return template;
            }
        }
        throw new TemplateNotFoundException("Sorry, this template isn't available right now");

    }

}
