package com.nitrogen.myme.persistence.stubs;

import android.net.Uri;

import com.nitrogen.myme.R;
import com.nitrogen.myme.objects.ImageMeme;
import com.nitrogen.myme.persistence.MemesPersistence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.nitrogen.myme.objects.Meme;

public class MemesPersistenceStub implements MemesPersistence {
    private List<Meme> memes;

    public MemesPersistenceStub() {
        this.memes = new ArrayList<>();

        memes.add(new ImageMeme("memeCool", Uri.parse("android.resource://com.nitrogen.myme/" + R.mipmap.meme1)));
        memes.add(new ImageMeme("meme", Uri.parse("android.resource://com.nitrogen.myme/" + R.mipmap.meme2)));
        memes.add(new ImageMeme("meme", Uri.parse("android.resource://com.nitrogen.myme/" + R.mipmap.meme3)));
        memes.add(new ImageMeme("meme", Uri.parse("android.resource://com.nitrogen.myme/" + R.mipmap.meme4)));
        memes.add(new ImageMeme("meme", Uri.parse("android.resource://com.nitrogen.myme/" + R.mipmap.meme5)));
        memes.add(new ImageMeme("meme", Uri.parse("android.resource://com.nitrogen.myme/" + R.mipmap.meme6)));
        memes.add(new ImageMeme("meme", Uri.parse("android.resource://com.nitrogen.myme/" + R.mipmap.meme7)));
        memes.add(new ImageMeme("meme", Uri.parse("android.resource://com.nitrogen.myme/" + R.mipmap.meme8)));
        memes.add(new ImageMeme("meme", Uri.parse("android.resource://com.nitrogen.myme/" + R.mipmap.meme1)));
        memes.add(new ImageMeme("meme", Uri.parse("android.resource://com.nitrogen.myme/" + R.mipmap.meme2)));
        memes.add(new ImageMeme("meme", Uri.parse("android.resource://com.nitrogen.myme/" + R.mipmap.meme3)));
        memes.add(new ImageMeme("meme", Uri.parse("android.resource://com.nitrogen.myme/" + R.mipmap.meme4)));
        memes.add(new ImageMeme("meme", Uri.parse("android.resource://com.nitrogen.myme/" + R.mipmap.meme5)));
        memes.add(new ImageMeme("meme", Uri.parse("android.resource://com.nitrogen.myme/" + R.mipmap.meme6)));
        memes.add(new ImageMeme("meme", Uri.parse("android.resource://com.nitrogen.myme/" + R.mipmap.meme7)));
        memes.add(new ImageMeme("meme", Uri.parse("android.resource://com.nitrogen.myme/" + R.mipmap.meme8)));
        memes.add(new ImageMeme("meme", Uri.parse("android.resource://com.nitrogen.myme/" + R.mipmap.meme1)));
        memes.add(new ImageMeme("meme", Uri.parse("android.resource://com.nitrogen.myme/" + R.mipmap.meme2)));
        memes.add(new ImageMeme("meme", Uri.parse("android.resource://com.nitrogen.myme/" + R.mipmap.meme3)));
        memes.add(new ImageMeme("meme", Uri.parse("android.resource://com.nitrogen.myme/" + R.mipmap.meme4)));
        memes.add(new ImageMeme("meme", Uri.parse("android.resource://com.nitrogen.myme/" + R.mipmap.meme5)));
        memes.add(new ImageMeme("meme", Uri.parse("android.resource://com.nitrogen.myme/" + R.mipmap.meme6)));
        memes.add(new ImageMeme("meme", Uri.parse("android.resource://com.nitrogen.myme/" + R.mipmap.meme7)));
        memes.add(new ImageMeme("meme", Uri.parse("android.resource://com.nitrogen.myme/" + R.mipmap.meme8)));
        memes.add(new ImageMeme("meme", Uri.parse("android.resource://com.nitrogen.myme/" + R.mipmap.meme1)));
        memes.add(new ImageMeme("meme", Uri.parse("android.resource://com.nitrogen.myme/" + R.mipmap.meme2)));
        memes.add(new ImageMeme("meme", Uri.parse("android.resource://com.nitrogen.myme/" + R.mipmap.meme3)));
        memes.add(new ImageMeme("meme", Uri.parse("android.resource://com.nitrogen.myme/" + R.mipmap.meme4)));
        memes.add(new ImageMeme("meme", Uri.parse("android.resource://com.nitrogen.myme/" + R.mipmap.meme5)));
        memes.add(new ImageMeme("meme", Uri.parse("android.resource://com.nitrogen.myme/" + R.mipmap.meme6)));
        memes.add(new ImageMeme("meme", Uri.parse("android.resource://com.nitrogen.myme/" + R.mipmap.meme7)));
        memes.add(new ImageMeme("meme", Uri.parse("android.resource://com.nitrogen.myme/" + R.mipmap.meme8)));

    }

    @Override
    public List<Meme> getMemeSequential() {
        return Collections.unmodifiableList(memes);
    }

    @Override
    public List<Meme> getMemeRandom(Meme currentMeme) {
        List<Meme> newMemes = new ArrayList<>();
        int index;

        index = memes.indexOf(currentMeme);
        if (index >= 0)
        {
            newMemes.add(memes.get(index));
        }
        return newMemes;
    }

    @Override
    public Meme insertMeme(Meme currentMeme) {
        // don't bother checking for duplicates
        memes.add(currentMeme);
        return currentMeme;
    }

    @Override
    public Meme updateMeme(Meme currentMeme) {
        int index;

        index = memes.indexOf(currentMeme);
        if (index >= 0)
        {
            memes.set(index, currentMeme);
        }
        return currentMeme;
    }

    @Override
    public void deleteMeme(Meme currentMeme) {
        int index;

        index = memes.indexOf(currentMeme);
        if (index >= 0)
        {
            memes.remove(index);
        }
    }
}
