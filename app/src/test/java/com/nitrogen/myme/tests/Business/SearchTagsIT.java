package com.nitrogen.myme.tests.Business;

import com.nitrogen.myme.application.Services;
import com.nitrogen.myme.business.AccessMemes;
import com.nitrogen.myme.business.AccessTags;
import com.nitrogen.myme.business.SearchTags;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SearchTagsIT {
    private File tempDB;
    private AccessMemes accessMemes;
    private AccessTags accessTags;
    private SearchTags searchTags;

    @Before
    public void setUp() throws IOException {
        System.out.println("Starting tests for SearchTags...");

        // build database
        tempDB = TestUtils.copyDB();
        accessMemes = new AccessMemes();
        accessTags = new AccessTags();
        searchTags = new SearchTags();
        assertNotNull(accessMemes);
        assertNotNull(accessTags);
        assertNotNull(searchTags);
    }

    @Test
    public void testGetTagsFromMemes_emptyMemeList() {
        // Non-empty tag database where meme list is empty
        System.out.println("Testing getTagsFromMemes() with an empty meme list");
        assertEquals(0, searchTags.getTagsFromMemes(new ArrayList<Meme>()).size());
    }

    @Test
    public void testGetTagsFromMemes_oneMeme() {
        // Non-empty tag database with meme list of size 1 where its only meme has one or more tags that we know exist in the tag db
        System.out.println("Testing getTagsFromMemes() one meme with at least 1 existing tag");

        // generate random index for memes
        final int M_INDEX = (int)(Math.random() * (accessMemes.getMemes().size() - 1));

        List<Meme> oneMeme = new ArrayList<>(); // implementation specific
        oneMeme.add(accessMemes.getMemes().get(M_INDEX));
        assertTrue(searchTags.getTagsFromMemes(oneMeme).size() > 0);
        assertTrue(searchTags.getTagsFromMemes(oneMeme).size() <= accessTags.getTags().size());


        // check that the tags returned are the ones in the meme
        List<Tag> result = searchTags.getTagsFromMemes(oneMeme);
        Meme meme = oneMeme.get(0);
        boolean tagFound = false;

        for(Tag tag : result) {
            if(meme.getTags().contains(tag)) {
                tagFound = true;
                break;
            }
        }
        assertTrue(tagFound);
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
