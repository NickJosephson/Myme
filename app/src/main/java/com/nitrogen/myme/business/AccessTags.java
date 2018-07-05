package com.nitrogen.myme.business;

import java.util.Collections;
import java.util.List;

import com.nitrogen.myme.application.Services;
import com.nitrogen.myme.objects.Tag;
import com.nitrogen.myme.persistence.TagsPersistence;

public class AccessTags {
    private TagsPersistence tagsPersistence;
    private List<Tag> tags;

    //**************************************************
    // Constructors
    //**************************************************

    public AccessTags(){
        tagsPersistence = Services.getTagsPersistence();
        tags = tagsPersistence.getTags();
    }

    // this is needed to swap out real database for stub
    public AccessTags(TagsPersistence tagsPersistenceGiven){
        tagsPersistence = tagsPersistenceGiven;
        tags = tagsPersistence.getTags();
    }

    //**************************************************
    // Methods
    //**************************************************

    /* getTags
     *
     * purpose: Return a list of all tags.
     */
    public List<Tag> getTags() {
        return Collections.unmodifiableList(tags);
    }

}
