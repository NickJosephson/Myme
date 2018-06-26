package com.nitrogen.myme.business;

import java.util.List;
import java.util.Collections;

import com.nitrogen.myme.application.Services;
import com.nitrogen.myme.objects.Meme;
import com.nitrogen.myme.persistence.MemesPersistence;

public class AccessMemes {
    private MemesPersistence memePersistence;
    private List<Meme> memes;

    //**************************************************
    // Constructor
    //**************************************************

    public AccessMemes() {
        memePersistence = Services.getMemesPersistence();
        memes = memePersistence.getMemes();;
    }

    //**************************************************
    // Methods
    //**************************************************

    /* getMemes
     *
     * purpose: Return a list of all memes.
     */
    public List<Meme> getMemes() {
        return Collections.unmodifiableList(memes);
    }

    /* getMemeByID
     *
     * purpose: Return a Meme matching the given meme ID
     *          or null if non is found.
     */
    public Meme getMemeByID(int memeID) {
        for (Meme meme : memes) {
            if (meme.getMemeID() == memeID) {
                return meme;
            }
        }

        return null;
    }

}
