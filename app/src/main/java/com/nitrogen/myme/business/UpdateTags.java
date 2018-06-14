package com.nitrogen.myme.business;

import com.nitrogen.myme.application.Services;
import com.nitrogen.myme.objects.Tag;
import com.nitrogen.myme.persistence.TagsPersistence;

public class UpdateTags {
    private TagsPersistence tagsPersistence;

    //**************************************************
    // Constructor
    //**************************************************

    public UpdateTags() {
        this.tagsPersistence = Services.getTagsPersistence();
    }

    //**************************************************
    // Methods
    //**************************************************

    public boolean insertTag(Tag tag) {
        return tagsPersistence.insertTag(tag);
    }

    public Tag deleteTag(Tag tag) {
        return tagsPersistence.deleteTag(tag);
    }
}
