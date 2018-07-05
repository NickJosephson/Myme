package com.nitrogen.myme.business;

import com.nitrogen.myme.application.Services;
import com.nitrogen.myme.objects.Meme;
import com.nitrogen.myme.persistence.MemesPersistence;

public class UpdateMemes {
    private MemesPersistence memesPersistence;

    //**************************************************
    // Constructors
    //**************************************************

    public UpdateMemes() {
        this.memesPersistence = Services.getMemesPersistence();
    }

    // this is needed to swap out real database for stub
    public UpdateMemes(MemesPersistence memesPersistenceGiven) {
        this.memesPersistence = memesPersistenceGiven;
    }
    //**************************************************
    // Methods
    //**************************************************

    public boolean insertMeme(Meme meme) {
        return memesPersistence.insertMeme(meme);
    }

    public Meme deleteMeme(Meme meme) {
        return memesPersistence.deleteMeme(meme);
    }
}
