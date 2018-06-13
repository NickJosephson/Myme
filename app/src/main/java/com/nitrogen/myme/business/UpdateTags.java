package com.nitrogen.myme.business;

import com.nitrogen.myme.application.Services;
import com.nitrogen.myme.objects.Tag;
import com.nitrogen.myme.persistence.TagsPersistence;

public class UpdateTags {
    private TagsPersistence tagsPersistence;

    public UpdateTags() {
        this.tagsPersistence = Services.getTagsPersistence();
    }

    public boolean insertTag(Tag tag) {
        return tagsPersistence.insertTag(tag);
    }

    public Tag deleteTag(Tag tag) {

        return tagsPersistence.deleteTag(tag);
    }
}
