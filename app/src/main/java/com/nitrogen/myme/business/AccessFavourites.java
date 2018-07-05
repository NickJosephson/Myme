package com.nitrogen.myme.business;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import com.nitrogen.myme.application.Services;
import com.nitrogen.myme.objects.Meme;
import com.nitrogen.myme.persistence.MemesPersistence;

public class AccessFavourites {
    private MemesPersistence memePersistence;

    //**************************************************
    // Constructors
    //**************************************************

    public AccessFavourites() {
        memePersistence = Services.getMemesPersistence();
    }

    public AccessFavourites(MemesPersistence memesPersistenceGiven) {
        memePersistence = memesPersistenceGiven;
    }

    //**************************************************
    // Methods
    //**************************************************

    /* getMemes
     *
     * purpose: Return a list of all memes that are favourites.
     */
    public List<Meme> getMemes() {
        List<Meme> memes = new ArrayList<Meme>();

        for (Meme meme : memePersistence.getMemes()) {
            if (meme.isFavourite()) {
                memes.add(meme);

            }
        }

        return Collections.unmodifiableList(memes);
    }

}

