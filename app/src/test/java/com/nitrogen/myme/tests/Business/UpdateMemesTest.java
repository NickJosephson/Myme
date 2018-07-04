package com.nitrogen.myme.tests.Business;

import com.nitrogen.myme.R;
import com.nitrogen.myme.business.AccessMemes;
import com.nitrogen.myme.business.UpdateMemes;
import com.nitrogen.myme.objects.Meme;
import com.nitrogen.myme.objects.Tag;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class UpdateMemesTest {
    private UpdateMemes updateMemes;
    private AccessMemes accessMemes;
    private int initialSize;


    /* createMeme
     *
     * purpose: Private helper method to facilitate creating a meme with a given name.
     *          Each meme created has the same image.
     */
    private Meme createMeme(String name) {
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("test_tag"));
        Meme newMeme = new Meme( name, ("android.resource://com.nitrogen.myme/"+ R.drawable.lololol));
        newMeme.setTags(tags);
        return newMeme;
    }

    @Before
    public void setUp() {
        System.out.println("Starting tests for UpdateMemes.\n");
        updateMemes = new UpdateMemes();
        accessMemes = new AccessMemes();
        assertNotNull(updateMemes);
        assertNotNull(accessMemes);

        initialSize = accessMemes.getMemes().size();
    }

    /* Method: deleteMeme(Meme meme) */

    @Test
    public void testDeleteMeme_oneMeme() {
        // Delete one meme from the database
        System.out.println("Testing deleteMeme(), deleting one meme from the database");

        final int M_INDEX = (int)(Math.random() * (accessMemes.getMemes().size() - 1));
        Meme target = accessMemes.getMemes().get(M_INDEX);
        updateMemes.deleteMeme(target);
        // check size
        assertEquals(initialSize - 1, accessMemes.getMemes().size());
        // ensure we deleted the correct meme
        assertEquals(-1, accessMemes.getMemes().indexOf(target));
    }

    @Test
    public void testDeleteMeme_invalidMeme() {
        // Delete a meme that is not in the database
        System.out.println("Testing deleteMeme(), deleting a meme that is not in the database");
        updateMemes.deleteMeme(createMeme("nonexistent"));
        assertEquals(initialSize, accessMemes.getMemes().size());
    }

    @Test
    public void testDeleteMeme_allMemes() {
        // Delete all memes in the database
        System.out.println("Testing deleteMeme(), deleting all memes in the database");
        Meme deleted;
        for(int i = 0 ; i < initialSize ; i++ ) {
            deleted = updateMemes.deleteMeme(accessMemes.getMemes().get(0));
            assertFalse(accessMemes.getMemes().contains(deleted));
        }
        assertEquals(0, accessMemes.getMemes().size());
    }

    /* Method: insertMeme(Meme meme) */

    @Test
    public void testInsertMeme_multipleMemes() {

        final int NUM_MEMES = 5;

        // Insert multiple memes
        System.out.println("Testing insertMeme(), inserting multiple memes into a non-empty database");
        Meme created;
        for(int i = 0 ; i < NUM_MEMES ; i++) {
            created = createMeme("test_meme_"+i);
            updateMemes.insertMeme(created);
            assertTrue(accessMemes.getMemes().contains(created));
        }
        assertEquals(initialSize + NUM_MEMES, accessMemes.getMemes().size());
    }

    @Test
    public void testInsertMeme_duplicateMemes() {
        // Insert multiples of the same memes
        System.out.println("Testing insertMeme(), inserting doubles of the same memes into a non-empty database");

        final int NUM_MEMES = 5;

        Meme meme;
        for(int i = 0 ; i < NUM_MEMES ; i++) {
            meme = createMeme("test_meme_"+i);
            updateMemes.insertMeme(meme);
            updateMemes.insertMeme(meme);
        }
        assertEquals(initialSize + NUM_MEMES, accessMemes.getMemes().size());
    }

    @After
    public void tearDown() {
        System.out.println("\nFinished tests.\n");
    }
}
