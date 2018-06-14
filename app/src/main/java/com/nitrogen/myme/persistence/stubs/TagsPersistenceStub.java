package com.nitrogen.myme.persistence.stubs;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import com.nitrogen.myme.objects.Tag;
import com.nitrogen.myme.persistence.TagsPersistence;

public class TagsPersistenceStub implements TagsPersistence {
    private List<Tag> tags;
    private String[] availableTags = {
            "dank",
            "edgy",
            "normie",
            "wholesome"
    }; //, "dead meme", "aww", "funny", "meIRL"};

    //**************************************************
    // Constructor
    //**************************************************

    public TagsPersistenceStub() {
        this.tags = new ArrayList<>();

        for(int i = 0 ; i < availableTags.length ; i++) {
            tags.add(new Tag(availableTags[i]));
        }
    }

    //**************************************************
    // Methods
    //**************************************************

    @Override
    public List<Tag> getTags() {
        return Collections.unmodifiableList(tags);
    }

    /* insertTag
     *
     * purpose: Insert a tag into the database.
     *          Returns True if the tag was added and False otherwise.
     */
    @Override
    public boolean insertTag(Tag tag) {
        boolean tagAdded = false;

        if(!tags.contains(tag)) {
            tags.add(tag);
            tagAdded = true;
        }

        return tagAdded;
    }

    /* deleteTag
     *
     * purpose: Delete a tag from the database.
     */
    @Override
    public Tag deleteTag(Tag tag) {
        int index;

        index = tags.indexOf(tag);
        if (index >= 0) {
            tags.remove(index);
        }

        return tag;
    }

}
