package com.nitrogen.myme.business;

import com.nitrogen.myme.application.Services;
import com.nitrogen.myme.objects.Meme;
import com.nitrogen.myme.objects.Tag;
import com.nitrogen.myme.persistence.MemesPersistence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchMemes {
    private MemesPersistence memesPersistence;

    //**************************************************
    // Constructor
    //**************************************************

    public SearchMemes() {
        this.memesPersistence = Services.getMemesPersistence();
    }

    //**************************************************
    // Methods
    //**************************************************

    /* getMemesRelatedTo
     *
     * purpose: Filters through the meme database to return a list of Memes
     *          related to the given query.
     */
    public List<Meme> getMemesRelatedTo(String query) {
        String[] keys = query.trim().toLowerCase().split(" ");

        return getMemesRelatedTo(keys);
    }

    /* getMemesRelatedTo
     *
     * purpose: Filters through the meme database to return a list of Memes
     *          related to the keys given.
     */
    public List<Meme> getMemesRelatedTo(String[] keys) {
        Set<Meme> combinedMemes = new HashSet<>();

        combinedMemes.addAll(getMemesByTags(keys));
        combinedMemes.addAll(getMemesByNames(keys));

        return new ArrayList<>(combinedMemes);
    }

    /* getMemesByName
     *
     * purpose: Filters through the meme database to return a list of Memes where each
     *          Meme has a similar name to the keys provided.
     */
    public List<Meme> getMemesByNames(String[] keys) {
        List<Meme> result = new ArrayList<>();

        // search through all memes
        for (int i = 0; i < memesPersistence.getMemes().size(); i++) {
            // examine a meme
            Meme currMeme = memesPersistence.getMemes().get(i);
            String[] potentials = currMeme.getName().trim().toLowerCase().split(" ");
            boolean match = false;

            for (String key : keys) {
                for (String potential : potentials) {
                    if (key.equals(potential)) {
                        match = true;
                    }
                }
            }

            if (match) {
                result.add(currMeme);
            }
        }

        return result;
    }


    /* getMemesByTags
     *
     * purpose: Filters through the meme database to return a list of Memes where each
     *          Meme has one or more of the tags in the list of tags provided.
     */
    public List<Meme> getMemesByTags(String[] tags) {
        List<Meme> result = new ArrayList<>();

        // search through all memes
        for(Meme meme : memesPersistence.getMemes()) {
            // check if the current meme has any of the tags we're looking for
            for(Tag tag : meme.getTags()) {
                for(String inputTag : tags) {
                    if (tag.getName().equalsIgnoreCase(inputTag) && !result.contains(meme))
                        result.add(meme);
                }
            }
        }
        return Collections.unmodifiableList(result);
    }


}
