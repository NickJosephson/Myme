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
     * purpose: Filters through the meme database to return a list of Memes related to the
     */
    public List<Meme> getMemesRelatedTo(String query) {
        Set<Meme> combinedMemes = new HashSet<>();

        combinedMemes.addAll(getMemesByTagName(query));
        combinedMemes.addAll(getMemesByName(query));

        return new ArrayList<>(combinedMemes);
    }

    /* getMemesByName
     *
     * purpose: Filters through the meme database to return a list of Memes where each
     *          Meme has a similar name to the provided.
     */
    public List<Meme> getMemesByName(String name) {
        List<Meme> result = new ArrayList<>();

        String[] keys = name.trim().toLowerCase().split(" ");

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

    /* getMemesByTagName
     *
     * purpose: Filters through the meme database to return a list of Memes where each
     *          Meme has a tag similar to the tag name provided.
     */
    public List<Meme> getMemesByTagName(String input) {
        List<Meme> result = new ArrayList<>();

        String[] strings = input.split("\\s");
        List<Tag> tags = new ArrayList<>();

        for (String str : strings) {
            tags.add(new Tag(str));
        }

        List<Meme> memes = getMemesByTags(tags);

        return memes;
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
        for (int i = 0; i < memesPersistence.getMemes().size(); i++) {
            // examine a meme
            currMeme = memesPersistence.getMemes().get(i);

            // get the tags associated with the current meme
            currMemeTags = currMeme.getTags();

            // add current meme to the list if it has a tag we're looking for
            for (int j = 0; j < tags.size(); j++) {
                if (currMemeTags.contains(tags.get(j)) && !(result.contains(currMeme))) {
                    result.add(currMeme);
                }
            }
        }

        return result;
    }

}
