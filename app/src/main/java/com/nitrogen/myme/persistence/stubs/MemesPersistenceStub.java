package com.nitrogen.myme.persistence.stubs;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import com.nitrogen.myme.persistence.MemesPersistence;
import com.nitrogen.myme.application.Services;
import com.nitrogen.myme.objects.Meme;
import com.nitrogen.myme.objects.ImageMeme;
import com.nitrogen.myme.objects.Tag;
import com.nitrogen.myme.R;

public class MemesPersistenceStub implements MemesPersistence {
    private List<Meme> memes;
    private List<Tag> tags;
    private int[] memeResourceIDs = {
            R.drawable.pff_guy,
            R.drawable.fuck_yea,
            R.drawable.questioning_face,
            R.drawable.mother_of_god,
            R.drawable.me_gusta,
            R.drawable.lol,
            R.drawable.lololol,
            R.drawable.you_dont_say,
            R.drawable.are_you_fucking_kidding_me,
            R.drawable.forever_alone,
            R.drawable.genius,
            R.drawable.happy_guy_rage_face,
            R.drawable.herp_derp,
            R.drawable.okay_guy,
            R.drawable.poker_face,
            R.drawable.rage_face,
            R.drawable.staring_face,
            R.drawable.thumbs_up,
            R.drawable.troll_face,
            R.drawable.y_u_no
    };
    private String[] memeNames = {"Pff Guy",
            "Fuck Yea",
            "Questioning Face",
            "Mother of God",
            "Me Gusta",
            "LOL",
            "LOLOLOL",
            "You Don't Say",
            "Are You Fucking Kidding Me",
            "Forever Alone",
            "Genius",
            "Happy Guy Rage Face",
            "Herp Derp",
            "Okay Guy",
            "Poker Face",
            "Rage Face",
            "Staring Face",
            "Thumbs Up",
            "Troll Face",
            "Y U NO"
    };

    //**************************************************
    // Constructor
    //**************************************************

    public MemesPersistenceStub() {
        this.memes = new ArrayList<>();
        this.tags = Services.getTagsPersistence().getTags();

        for(int i = 0 ; i < memeResourceIDs.length ; i++) {
            Meme newMeme = new ImageMeme(memeNames[i], ("android.resource://com.nitrogen.myme/" + memeResourceIDs[i]));
            newMeme.setTags(randomTags(i));
            memes.add(newMeme);
        }
    }

    //**************************************************
    // Methods
    //**************************************************

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
            result.add(tags.get(0));
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
