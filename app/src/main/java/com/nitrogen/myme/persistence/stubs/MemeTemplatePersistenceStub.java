package com.nitrogen.myme.persistence.stubs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import com.nitrogen.myme.objects.TemplateMeme;
import com.nitrogen.myme.persistence.MemeTemplatePersistence;
import com.nitrogen.myme.persistence.MemesPersistence;
import com.nitrogen.myme.application.Services;
import com.nitrogen.myme.objects.Meme;
import com.nitrogen.myme.objects.ImageMeme;
import com.nitrogen.myme.objects.Tag;
import com.nitrogen.myme.R;

public class MemeTemplatePersistenceStub implements MemeTemplatePersistence {
    private List<Meme> memes;
    private List<Tag> tags;
    private Map<String,Integer> memeMap = new HashMap<String,Integer>();

    //**************************************************
    // Constructor
    //**************************************************

    public MemeTemplatePersistenceStub() {
        this.memes = new ArrayList<>();
        this.tags = Services.getTagsPersistence().getTags();

        createMemeMap();

        BitmapFactory.Options dimensions = new BitmapFactory.Options();
        dimensions.inJustDecodeBounds = true;


        for(String name : memeMap.keySet()) {
            Bitmap mBitmap = BitmapFactory.decodeFile("android.resource://com.nitrogen.myme/" + memeMap.get(name), dimensions);
            int height = dimensions.outHeight;
            int width =  dimensions.outWidth;

            PointF points[] = { new PointF((float)(width*0.5), (float)(height*0.3)),
                                new PointF((float)(width*0.5), (float)(height*0.6))};

            TemplateMeme newMeme = new TemplateMeme(name, ("android.resource://com.nitrogen.myme/" + memeMap.get(name)), points);
            newMeme.setTags(randomTags(memeMap.get(name)));
            memes.add(newMeme);
        }
    }

    //**************************************************
    // Methods
    //**************************************************

    /* createMemeMap
     *
     * purpose: Create stub memes. Each resource has a name associated with it
     */
    private void createMemeMap () {
        memeMap.put("Pff Guy", R.drawable.pff_guy);
        memeMap.put("Frick Yea", R.drawable.frick_yea);
        memeMap.put("Questioning Face", R.drawable.questioning_face);
    }

    /* randomTags
     *
     * purpose: Assign pseudo-random tags to a meme.
     *          This ensures each meme has at least 1 tag.
     *
     */
    private ArrayList<Tag> randomTags(int num) {
        ArrayList<Tag> result = new ArrayList<>();

        if(num%3 == 0) {
            result.add(tags.get(0));
        }
        if(num%2 == 0) {
            result.add(tags.get(1));
        }
        if(num%2 == 1) {
            result.add(tags.get(4));
        }
        if(num%5 == 0) {
            result.add(tags.get(2));
        }
        if(num%7 == 0) {
            result.add(tags.get(3));
        }

        return result;
    }

    @Override
    public List<Meme> getMemes() {
        return Collections.unmodifiableList(memes);
    }

    /* insertMeme
     *
     * purpose: Insert a meme into the database.
     *          Returns True if the meme was added and False otherwise.
     */
    @Override
    public boolean insertMeme(Meme meme) {
        boolean memeInserted = false;

        // don't add duplicates
        if(!memes.contains(meme)) {
            memes.add(meme);
            memeInserted = true;
        }

        return memeInserted;
    }

    /* deleteMeme
     *
     * purpose: Delete a meme from the database.
     */
    @Override
    public Meme deleteMeme(Meme meme) {
        int index;

        index = memes.indexOf(meme);
        if (index >= 0) {
            memes.remove(index);
        }

        return meme;
    }

}
