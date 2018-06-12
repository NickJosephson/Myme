package com.nitrogen.myme.business;

import java.util.Collections;
import java.util.List;

import com.nitrogen.myme.application.Services;
import com.nitrogen.myme.objects.Meme;
import com.nitrogen.myme.objects.Tag;
import com.nitrogen.myme.persistence.MemesPersistence;

public class AccessMemes
{ //TODO: Refactor braces
    private MemesPersistence memePersistence;
    private List<Meme> memes;
    private Meme meme;
    private int currentMeme;

    public AccessMemes()
    {
        memePersistence = Services.getMemesPersistence();
        memes = null;
        meme = null;
        currentMeme = 0;
    }

    public List<Meme> getMemes()
    {
        memes = memePersistence.getMemeSequential();
        return Collections.unmodifiableList(memes);
    }

    public Meme getSequential()
    {
        String result = null;
        if (memes == null)
        {
            memes = memePersistence.getMemeSequential();
            currentMeme = 0;
        }
        if (currentMeme < memes.size())
        {
            meme = (Meme) memes.get(currentMeme);
            currentMeme++;
        }
        else
        {
            memes = null;
            meme = null;
            currentMeme = 0;
        }
        return meme;
    }

    /*
    public Meme getMeme(String memeID)
    {
        memes = memePersistence.getMemeRandom(new Meme(memeID));
        currentMeme = 0;
        if (currentMeme < memes.size())
        {
            meme = memes.get(currentMeme);
            currentMeme++;
        }
        else
        {
            memes = null;
            meme = null;
            currentMeme = 0;
        }
        return meme;
    }
    */
}
