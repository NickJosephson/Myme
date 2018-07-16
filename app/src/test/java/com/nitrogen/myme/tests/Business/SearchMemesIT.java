package com.nitrogen.myme.tests.Business;

import com.nitrogen.myme.application.Services;
import com.nitrogen.myme.business.AccessMemes;
import com.nitrogen.myme.business.AccessTags;
import com.nitrogen.myme.business.SearchMemes;
import com.nitrogen.myme.business.UpdateMemes;
import com.nitrogen.myme.objects.Meme;
import com.nitrogen.myme.objects.Tag;
import com.nitrogen.myme.persistence.MemesPersistence;
import com.nitrogen.myme.persistence.TagsPersistence;
import com.nitrogen.myme.persistence.stubs.MemesPersistenceStub;
import com.nitrogen.myme.persistence.stubs.TagsPersistenceStub;
import com.nitrogen.myme.tests.utils.TestUtils;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SearchMemesIT {
    private File tempDB;
    private AccessMemes accessMemes;
    private SearchMemes searchMemes;
    private AccessTags accessTags;

    private String[] tagNames;
    private String[] memeNames;

    /* hasTag
     *
     * helper method to determine if a meme has a specific tag
     */
    private boolean hasTag(String tag, Meme meme) {
        boolean hasTag = false;
        for(Tag t : meme.getTags()) {
            if(t.getName().equals(tag)) {
                hasTag = true;
            }
        }
        return hasTag;
    }

    @Before
    public void setUp() throws IOException {
        System.out.println("Starting tests for SearchMemes...");

        // build database
        tempDB = TestUtils.copyDB();
        accessMemes = new AccessMemes();
        searchMemes = new SearchMemes();
        accessTags = new AccessTags();
        assertNotNull(accessMemes);
        assertNotNull(searchMemes);
        assertNotNull(accessTags);

        // get all tag names
        tagNames = new String[accessTags.getTags().size()];
        int t = 0;
        for (Tag tag : accessTags.getTags()) {
            tagNames[t] = tag.getName();
            t++;
        }

        // get all the meme names
        memeNames = new String[accessMemes.getMemes().size()];
        int m = 0;
        for (Meme meme : accessMemes.getMemes()) {
            memeNames[m] = meme.getName();
            m++;
        }
    }

    /* Method: getMemeByName(String name) */

    @Test
    public void testGetMemeByName_validName() {
        // Non-empty Meme db with a meme having the name we're looking for
        System.out.println("Testing getMemesByName() with a name which we know is in the database");
        String name = accessMemes.getMemes().get(0).getName();
        assertNotNull(searchMemes.getMemesRelatedTo(name));
    }

    @Test
    public void getMemeByName_invalidName() {
        // Non-empty Meme db with no memes under the given name
        System.out.print("Testing getMemesByName() with a name we know is not in the database");
        String noName = "testName";
        assertNotNull(searchMemes.getMemesRelatedTo(noName));
    }

    /* getMemesByTags(String[] tags) */
    @Test
    public void testGetMemesByTags_allTags() {
        // Non-empty Meme db searching with all the tags in the db
        System.out.println("Testing getMemesByTags() with all tags in the database");
        assertTrue( searchMemes.getMemesByTags(tagNames).size() >= 0);
        assertTrue( searchMemes.getMemesByTags(tagNames).size() <= accessMemes.getMemes().size());

    }

    @Test
    public void testGetMemesByTags_noTags() {
        // Non-empty Meme db where tag list is empty
        System.out.println("Testing getMemesByTags()with an empty tag list");
        String[] empty = new String[0];
        assertEquals(0, searchMemes.getMemesByTags(empty).size());
    }

    @Test
    public void testGetMemesByTags_validTag() {
        // Non-empty Meme db with tag list of size 1 with its only tag being one we know exists in the tag db
        if(accessTags.getTags().size() > 0) {
            System.out.println("Testing getMemesByTags()with an existing tag");
            String[] oneTag = new String[1];
            oneTag[0] = accessTags.getTags().get(0).getName();
            // at least one meme has this tag
            assertTrue(searchMemes.getMemesByTags(oneTag).size() > 0);
            // number of memes returned doesn't exceed the number of memes in the database
            assertTrue(searchMemes.getMemesByTags(oneTag).size() <= accessMemes.getMemes().size());
        }
    }

    @Test
    public void testGetMemesByTags_invalidTag() {
        // Non-empty Meme db with tag list of size 1 with its only tag being one we know doesn't exists in the tag db
        System.out.println("Testing getMemesByTags() with tags we know don't exist");
        String[] newTags = new String[5];
        for(int i = 0 ; i < 5 ; i++) {
            newTags[i] = "testTag"+i;
        }
        assertEquals(0, searchMemes.getMemesByTags(newTags).size());
    }

    /* Methods: getMemesByKeys(String[] keys) */
    @Test
    public void testGetMemesByKeys_allTagNames() {
        // Non-empty Meme db with tags we know exist
        System.out.println("Testing getMemesByKeys() with all tag names in the database");
        assertTrue( searchMemes.getMemesByTags(tagNames).size() >= 0);
        assertTrue( searchMemes.getMemesByTags(tagNames).size() <= accessMemes.getMemes().size());
    }

    @Test
    public void testGetMemesByKeys_allMemeNames() {
        // Non-empty Meme db with memes we know exist
        System.out.println("Testing getMemesByKeys() with all memes names in the database");
        HashSet<Meme> foundMemes = new HashSet<>();

        for (String name : memeNames) {
            foundMemes.addAll(searchMemes.getMemesRelatedTo(name));
        }

        assertEquals(accessMemes.getMemes().size(), foundMemes.size());
    }


    @Test
    public void testGetMemesByKeys_validKeys() {
        // Non-empty Meme db with keys being a tag name and a meme name
        System.out.println("Testing getMemesByKeys() with meme and tag names that we know exist");

        // generate random indexes for tag and memes
        final int T_INDEX = (int)(Math.random() * (accessTags.getTags().size() - 1));
        final int M_INDEX = (int)(Math.random() * (accessMemes.getMemes().size() - 1));

        // get a meme name and a tag name
        String[] keys = new String[2];
        keys[0] = accessMemes.getMemes().get(M_INDEX).getName();
        keys[1] = accessTags.getTags().get(T_INDEX).getName();

        assertTrue(searchMemes.getMemesRelatedTo(keys).size() > 0); // should return at least 1
        assertTrue(searchMemes.getMemesRelatedTo(keys).size() <= accessMemes.getMemes().size());

        // make sure we have the correct memes
        boolean hasName;
        boolean hasTag;

        for(Meme meme : searchMemes.getMemesRelatedTo(keys)) {
            hasName = keys[0].equals(meme.getName());
            hasTag = hasTag(keys[1], meme);
            assertTrue(hasName || hasTag);
        }
    }

    @Test
    public void testGetMemesByKeys_invalidKeys() {
        // Non-empty Meme db where we know the keys don't exist in neither tag or meme db
        System.out.println("Testing getMemesByKeys() with meme and tag names that we know don't exist");

        String[] newTags = new String[5];
        for(int i = 0 ; i < 5 ; i++) {
            newTags[i] = "testTag"+i;
        }
        assertEquals(0, searchMemes.getMemesRelatedTo(newTags).size());
    }

    @Test
    public void testGetMemesByKeys_noKeys() {
        // Non-empty Meme db where there are no keys to search for
        System.out.println("Testing getMemesByKeys() with no keys");
        String[] empty = new String[0];
        assertEquals(0, searchMemes.getMemesByTags(empty).size());
    }

    @After
    public void tearDown() {
        // delete file
        tempDB.delete();
        // forget DB
        Services.clean();

        System.out.println("\nFinished tests.\n");
    }
}
