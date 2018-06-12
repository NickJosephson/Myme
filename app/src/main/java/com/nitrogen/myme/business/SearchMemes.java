package com.nitrogen.myme.business;

import com.nitrogen.myme.application.Services;
import com.nitrogen.myme.objects.Meme;
import com.nitrogen.myme.objects.Tag;
import com.nitrogen.myme.persistence.MemesPersistence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchMemes {
    private MemesPersistence memesPersistence;

    public SearchMemes() {
        this.memesPersistence = Services.getMemesPersistence();
    }

    /* getMemesByTags
     *
     * purpose: Filters through the meme database to return a list of Memes where each
     *          Meme has one or more of the tags in the list of tags provided.
     */
    public List<Meme> getMemesByTags(List<Tag> tags) {
        List<Meme> result = new ArrayList<>();

        Meme currMeme;
        List<Tag> currMemeTags;

        // search through all memes
        for(int i = 0 ; i< memesPersistence.getMemeSequential().size() ; i++) {
            // examine a meme
            currMeme = memesPersistence.getMemeSequential().get(i);

            // get the tags associated with the current meme
            currMemeTags = currMeme.getTags();

            // add current meme to the list if it has a tag we're looking for
            for(int j = 0 ; j < tags.size() ; j++) {
                if (currMemeTags.contains(tags.get(j))) {
                    result.add(currMeme);
                }
            }
        }
        return Collections.unmodifiableList(result);
    }
}