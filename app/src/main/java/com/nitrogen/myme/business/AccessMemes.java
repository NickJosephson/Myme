package com.nitrogen.myme.business;

import java.util.List;
import java.util.Collections;

import com.nitrogen.myme.application.Services;
import com.nitrogen.myme.objects.Meme;
import com.nitrogen.myme.persistence.MemesPersistence;
import com.nitrogen.myme.persistence.TagsPersistence;

public class AccessMemes {
    private MemesPersistence memePersistence;
    private List<Meme> memes;

    //**************************************************
    // Constructors
    //**************************************************

    public AccessMemes() {
        memePersistence = Services.getMemesPersistence();
        memes = memePersistence.getMemes();
    }
    public AccessMemes(MemesPersistence memesPersistenceGiven) {
        memePersistence = memesPersistenceGiven;
        memes = memePersistence.getMemes();
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

    /* getMemeByName
     *
     * purpose: Return a Meme matching the given meme name
     *          or null if non is found.
     */
    public Meme getMemeByName(String memeName) {
        for (Meme meme : memes) {
            if (meme.getName().equals(memeName)) {
                return meme;
            }
        }

        return null;
    }

    public void updatefav(Meme meme){
        memePersistence.updateFav( meme);
    }

}
