package com.nitrogen.myme.business;

import com.nitrogen.myme.application.Services;
import com.nitrogen.myme.objects.Meme;
import com.nitrogen.myme.objects.Tag;
import com.nitrogen.myme.persistence.MemesPersistence;
import com.nitrogen.myme.persistence.TagsPersistence;

import java.util.ArrayList;
import java.util.List;

public class SearchTags {
    private TagsPersistence tagsPersistence;

    public SearchTags() {
        this.tagsPersistence = Services.getTagsPersistence();
    }

    /* getTagsFromMemes
     *
     * purpose: Compile a unique list of tags that appear in the given list of memes.
     */
    public List<Tag> getTagsFromMemes(List<Meme> memes) {
        List<Tag> result = new ArrayList<>();

        Meme currMeme;
        Tag currTag;
        List<Tag> existingTags = tagsPersistence.getTags();
        boolean foundTag;

        // look for an occurrence of an existing tag
        // if we come across a meme that has this tag, we will add it to the list
        for(int t = 0 ; t < existingTags.size() ; t++) {
            currTag = existingTags.get(t);
            foundTag = false;

            // check if a meme in the list has this tag
            for(int m = 0 ; m < memes.size() && !foundTag ; m++ ) {
                if(memes.get(m).hasTag(currTag)) {
                    result.add(currTag);
                    foundTag = true;
                }
            }
        }
        return result;
    }

}
