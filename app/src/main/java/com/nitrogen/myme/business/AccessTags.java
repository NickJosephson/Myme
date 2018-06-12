package com.nitrogen.myme.business;

import com.nitrogen.myme.application.Services;
import com.nitrogen.myme.objects.Tag;
import com.nitrogen.myme.persistence.TagsPersistence;

import java.util.List;

public class AccessTags {
    private TagsPersistence tagsPersistence;

    public AccessTags(){
        tagsPersistence = Services.getTagsPersistence();
    }

    public List<Tag> getTags() {
        return tagsPersistence.getTags();
    }
}
