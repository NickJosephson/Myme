package com.nitrogen.myme.business;

import com.nitrogen.myme.objects.Meme;
import com.nitrogen.myme.objects.Tag;
import com.nitrogen.myme.persistence.MemesPersistence;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class SortMemes implements Comparator<Meme> {
    private MemesPersistence memesPersistence = null;
    private HashMap<String, Integer> weights; //tag(naems) appearances in already favorite memes
    private List<Meme> memes;

    //**************************************************
    // Constructors
    //**************************************************

    public SortMemes(List<Meme> memes) {
        this.memes = memes;
    }

    // this is needed to swap out real database for stub
    public SortMemes(List<Meme> memes, MemesPersistence memesPersistenceGiven) {
        this.memes = memes;
        this.memesPersistence = memesPersistenceGiven;
    }

    //**************************************************
    // General Methods
    //**************************************************

    /* sortByRelevance
     *
     * purpose: To sort memes by relevance based on already favorite memes.
     *          This also sorts already favorite memes to the bottom.
     */
    public List<Meme> sortByRelevance() {
        weights = new HashMap<>();
        calculateTagWeights();
        Collections.sort(memes, this);
        return memes;
    }

    //**************************************************
    // Helper Methods
    //**************************************************

    /* calculateTagWeights
     *
     * purpose: To populate the weights map with counts of tag appearances in already favorite memes.
     */
    private void calculateTagWeights() {
        AccessFavourites accessFavourites;
        if (memesPersistence != null) {
            accessFavourites = new AccessFavourites(memesPersistence);
        } else {
            accessFavourites = new AccessFavourites();
        }
        
        List<Meme> favorites = accessFavourites.getMemes();
        for (Meme meme : favorites) {
            List<Tag> tags = meme.getTags();
            for (Tag tag : tags) {
                if (weights.containsKey(tag.getName())) {
                    weights.put(tag.getName(), weights.get(tag.getName()) + 1);
                } else {
                    weights.put(tag.getName(), 1);
                }
            }
        }
    }

    /* compare
     *
     * purpose: To compare memes based on a meme's combined tag weights
     *          This also sorts already favorite memes to the bottom.
     */
    @Override
    public int compare(Meme meme1, Meme meme2) {
        return getMemeValue(meme2) - getMemeValue(meme1);
    }

    /* getMemeValue
     *
     * purpose: To get the sum of a meme's combined tag weights.
     *          This also gives already favorite memes a value of -1.
     */
    private int getMemeValue(Meme meme) {
        int value = 0;

        if (meme.isFavourite()) {
            value = -1;
        } else {
            List<Tag> tags = meme.getTags();
            for (Tag tag : tags) {
                if (weights.containsKey(tag.getName())) {
                    value += weights.get(tag.getName());
                }
            }
        }

        return value;
    }

}
