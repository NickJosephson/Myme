package com.nitrogen.myme.persistence.stubs;

import com.nitrogen.myme.objects.Tag;
import com.nitrogen.myme.persistence.TagsPersistence;

import java.util.ArrayList;
import java.util.List;

public class TagsPersistenceStub implements TagsPersistence {

    private List<Tag> tags;
    private String[] availableTags = {"dank", "edgy", "normie", "wholesome"};

    public TagsPersistenceStub() {
        this.tags = new ArrayList<>();

        for(int i = 0 ; i < availableTags.length ; i++) {
            tags.add(new Tag(availableTags[i]));
        }
    }

    // accessor
    public List<Tag> getTags() { return this.tags; }

    public Boolean insertTag(Tag newTag) {
        boolean tagAdded = false;
        if(!tags.contains(newTag)) {
            tags.add(newTag);
            tagAdded = true;
        }

        return tagAdded;
    }

    public Boolean removeTag(Tag targetTag) {
        return tags.remove(targetTag);
    }

}
