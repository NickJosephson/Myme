package com.nitrogen.myme.business;

import com.nitrogen.myme.objects.Meme;
import com.nitrogen.myme.objects.Tag;
import com.nitrogen.myme.persistence.MemesPersistence;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class SortMemes implements Comparator<Meme> {
    private MemesPersistence memesPersistence;
    private HashMap<String, Integer> weights;
    private List<Meme> memes;

    @Override
    public int compare(Meme meme1, Meme meme2) {
        return getMemeValue(meme2) - getMemeValue(meme1);
    }

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

    public SortMemes(List<Meme> memes) {
        this.memes = memes;
    }

    // this is needed to swap out real database for stub
    public SortMemes(List<Meme> memes, MemesPersistence memesPersistenceGiven) {
        this.memes = memes;
        this.memesPersistence = memesPersistenceGiven;
    }

    public List<Meme> sortByRelevance() {
        weights = new HashMap<>();
        calculateTagWeights();
        Collections.sort(memes, this);
        return memes;
    }

    private void calculateTagWeights() {
        AccessFavourites accessFavourites = new AccessFavourites(memesPersistence);
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

}
