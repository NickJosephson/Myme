package com.nitrogen.myme.tests.Business;

import android.net.Uri;

import com.nitrogen.myme.R;
import com.nitrogen.myme.business.AccessMemes;
import com.nitrogen.myme.business.UpdateMemes;
import com.nitrogen.myme.objects.ImageMeme;
import com.nitrogen.myme.objects.Meme;
import com.nitrogen.myme.objects.Tag;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
public class UpdateMemesTest {

    /* createMeme
     *
     * purpose: Private helper method to facilitate creating a meme with a given name.
     *          Each meme created has the same image.
     */
    private Meme createMeme(String name) {
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("test_tag"));
        Meme newMeme = new ImageMeme( name, ("android.resource://com.nitrogen.myme/"+ R.drawable.lololol));
        newMeme.setTags(tags);
        return newMeme;
    }

    @Before
    public void setUp() {
        System.out.println("Starting tests for UpdateMemes.\n");
    }

    @Test
    public void testDeleteMeme() {
        System.out.println("Testing deleteMeme...");

        UpdateMemes updateMemes = new UpdateMemes();
        AccessMemes accessMemes = new AccessMemes();

        // Delete one meme from the database
        System.out.println("...Delete one meme from the database");
        int initialSize = accessMemes.getMemes().size();
        Meme target = accessMemes.getMemes().get(0);
        updateMemes.deleteMeme(accessMemes.getMemes().get(0));
        assertEquals(initialSize - 1, accessMemes.getMemes().size()); // check size
        assertEquals(-1, accessMemes.getMemes().indexOf(target));    // check we deleted the correct meme

        // Delete a meme that is not in the database
        System.out.println("...Deleting a meme that is not in the database");
        initialSize = accessMemes.getMemes().size();
        updateMemes.deleteMeme(createMeme("nonexistent"));
        assertEquals(initialSize, accessMemes.getMemes().size());

        // Delete all memes in the database
        System.out.println("...Deleting all memes in the database");
        initialSize = accessMemes.getMemes().size();
        for(int i = 0 ; i < initialSize ; i++ ) {
            updateMemes.deleteMeme(accessMemes.getMemes().get(0));
        }
        assertEquals(0, accessMemes.getMemes().size());
    }

    @Test
    public void testInsertMeme() {
        System.out.println("Testing insertMeme...");

        UpdateMemes updateMemes = new UpdateMemes();
        AccessMemes accessMemes = new AccessMemes();

        final int NUM_MEMES = 5;

        // Insert multiple memes
        System.out.println("...Insert memes into a non-empty database");
        int initialSize = accessMemes.getMemes().size();
        ArrayList<Meme> memes = new ArrayList<>();
        for(int i = 0 ; i < NUM_MEMES ; i++) {
            Meme newMeme = createMeme("test_meme_"+i);
            memes.add(newMeme);
            updateMemes.insertMeme(newMeme);
        }
        assertEquals(initialSize + NUM_MEMES, accessMemes.getMemes().size());

        // Insert the same memes again
        System.out.println("...Insert memes with the same names");
        initialSize = accessMemes.getMemes().size();
        for(int i = 0 ; i < NUM_MEMES ; i++) {
            updateMemes.insertMeme(memes.get(i));
        }
        assertEquals(initialSize, accessMemes.getMemes().size());

        // Insert a meme that's already in the database
        System.out.println("...Insert a meme that's already in the database");
        initialSize = accessMemes.getMemes().size();
        updateMemes.insertMeme(accessMemes.getMemes().get(0));
        assertEquals(initialSize, accessMemes.getMemes().size());
    }

    @After
    public void tearDown() {
        System.out.println("\nFinished tests.\n");
    }
}
