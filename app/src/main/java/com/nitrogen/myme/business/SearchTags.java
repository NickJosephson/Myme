package com.nitrogen.myme.business;

import com.nitrogen.myme.application.Services;
import com.nitrogen.myme.objects.Meme;
import com.nitrogen.myme.objects.Tag;
import com.nitrogen.myme.persistence.TagsPersistence;

import java.util.ArrayList;
import java.util.List;

public class SearchTags {
    private TagsPersistence tagsPersistence;

    //**************************************************
    // Constructor
    //**************************************************

    public SearchTags() {
        this.tagsPersistence = Services.getTagsPersistence();
    }

    //**************************************************
    // Methods
    //**************************************************

    /* getTagsFromMemes
     *
     * purpose: Compile a unique list of tags that appear in the given list of memes.
     */
    public List<Tag> getTagsFromMemes(List<Meme> memes) {
        List<Tag> result = new ArrayList<>();
        boolean foundTag;

        // look for an occurrence of an existing tag
        // if we come across a meme that has this tag, we will add it to the list
        for(Tag tag : tagsPersistence.getTags()) {
            foundTag = false;

            // check if a meme in the list has this tag
            for(int m = 0 ; m < memes.size() && !foundTag ; m++ ) {
                if(memes.get(m).hasTag(tag)) {
                    result.add(tag);
                    foundTag = true;
                }
            }
        }
        return result;
    }

}
