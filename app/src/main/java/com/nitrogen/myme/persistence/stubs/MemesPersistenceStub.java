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
import com.nitrogen.myme.persistence.TagsPersistence;

public class MemesPersistenceStub implements MemesPersistence {
    private List<Meme> memes;
    private List<Tag> tags;
    private Map<String,Integer> memeMap = new HashMap<String,Integer>();
    private List<Meme> currView = new ArrayList<Meme>();

    //**************************************************
    // Constructors
    //**************************************************

    public MemesPersistenceStub(TagsPersistence tagsPersistenceGiven) {
        this.memes = new ArrayList<>();
        this.tags = tagsPersistenceGiven.getTags();

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
        memeMap.put("a_day_without_laughter", R.drawable.meme_a_day_without_laughter);
        memeMap.put("and_who_could_forget_dear_rat_boy", R.drawable.meme_and_who_could_forget_dear_rat_boy);
        memeMap.put("breathes_in_meep", R.drawable.meme_breathes_in_meep);
        memeMap.put("cat_with_gun", R.drawable.meme_cat_with_gun);
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

    public void updateFav(Meme meme){
        for(Meme mi : memes){
            if(meme.getName().equals(mi.getName())){
                mi.setFavourite(meme.isFavourite());
            }
        }
    }

    public void setCurrView(List<Meme> memes){ currView = memes; }

    public List<Meme> getCurrView() { return currView; }

}
