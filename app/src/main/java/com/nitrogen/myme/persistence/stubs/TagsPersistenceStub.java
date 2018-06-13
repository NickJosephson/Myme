package com.nitrogen.myme.persistence.stubs;

import com.nitrogen.myme.objects.Tag;
import com.nitrogen.myme.persistence.TagsPersistence;

import java.util.ArrayList;
import java.util.List;

public class TagsPersistenceStub implements TagsPersistence {

    private List<Tag> tags;
    private String[] availableTags = {"dank", "edgy", "normie", "wholesome"}; //, "dead meme", "aww", "funny", "meIRL"};

    public TagsPersistenceStub() {
        this.tags = new ArrayList<>();

        for(int i = 0 ; i < availableTags.length ; i++) {
            tags.add(new Tag(availableTags[i]));
        }
    }

    // accessor
    public List<Tag> getTags() { return this.tags; }

    @Override
    public boolean insertTag(Tag tag) {
        boolean tagAdded = false;
        if(!tags.contains(tag)) {
            tags.add(tag);
            tagAdded = true;
        }

        return tagAdded;
    }


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
