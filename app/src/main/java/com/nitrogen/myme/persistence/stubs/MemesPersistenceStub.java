package com.nitrogen.myme.persistence.stubs;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import com.nitrogen.myme.persistence.MemesPersistence;
import com.nitrogen.myme.application.Services;
import com.nitrogen.myme.objects.Meme;
import com.nitrogen.myme.objects.Tag;
import com.nitrogen.myme.R;

public class MemesPersistenceStub implements MemesPersistence {
    private List<Meme> memes;
    private List<Tag> tags;
    private Map<String,Integer> memeMap = new HashMap<String,Integer>();

    //**************************************************
    // Constructor
    //**************************************************

    public MemesPersistenceStub() {
        this.memes = new ArrayList<>();
        this.tags = Services.getTagsPersistence().getTags();

        createMemeMap();

        for(String name : memeMap.keySet()) {
            Meme newMeme = new Meme(name, ("android.resource://com.nitrogen.myme/" + memeMap.get(name)));
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
        memeMap.put("Mother of God", R.drawable.mother_of_god);
        memeMap.put("Me Gusta", R.drawable.me_gusta);
        memeMap.put("LOL", R.drawable.lol);
        memeMap.put("LOLOLOL", R.drawable.lololol);
        memeMap.put("You Don't Say", R.drawable.you_dont_say);
        memeMap.put("Are You Flipping Kidding Me", R.drawable.are_you_flipping_kidding_me);
        memeMap.put("Forever Alone", R.drawable.forever_alone);
        memeMap.put("Genius", R.drawable.genius);
        memeMap.put("Happy Guy Rage Face", R.drawable.happy_guy_rage_face);
        memeMap.put("Herp Derp", R.drawable.herp_derp);
        memeMap.put("Okay Guy", R.drawable.okay_guy);
        memeMap.put("Poker Face", R.drawable.poker_face);
        memeMap.put("Rage Face", R.drawable.rage_face);
        memeMap.put("Staring Face", R.drawable.staring_face);
        memeMap.put("Thumbs Up", R.drawable.thumbs_up);
        memeMap.put("Troll Face", R.drawable.troll_face);
        memeMap.put("Y U NO", R.drawable.y_u_no);
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
