package com.nitrogen.myme.tests.Business;

import com.nitrogen.myme.business.AccessMemes;
import com.nitrogen.myme.business.AccessTags;
import com.nitrogen.myme.business.SearchTags;
import com.nitrogen.myme.objects.Meme;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SearchTagsTest {
    // Get the memes from the database
    AccessMemes accessMemes = new AccessMemes();
    List<Meme> memes = accessMemes.getMemes();

    @Before
    public void setUp() {
        System.out.println("Starting tests for SearchTags.\n");

        assertNotNull(memes);
        assertTrue(memes.size() > 0);
    }

    @Test
    public void testGetTagsFromMemes() {
        System.out.println("Testing getTagsFromMemes()...");
        SearchTags searchTags = new SearchTags();
        assertNotNull(searchTags);

        AccessTags accessTags = new AccessTags();

        // Meme database where each tag appears at least once
        System.out.println("...Standard case");
        assertEquals(accessTags.getTags().size(), searchTags.getTagsFromMemes(memes).size());

        // Non-empty tag database where meme list is empty
        System.out.println("...Empty meme list");
        assertEquals(0, searchTags.getTagsFromMemes(new ArrayList<Meme>()).size());

        // Non-empty tag database with meme list of size 1 with its only meme having one or more tags that we know are present
        System.out.println("...One meme with existing tag(s)");
        List<Meme> oneMeme = memes.subList(0,1);
        assertTrue(searchTags.getTagsFromMemes(oneMeme).size() > 0);
        assertTrue(searchTags.getTagsFromMemes(oneMeme).size() <= accessTags.getTags().size());
    }

    @After
    public void tearDown() {
        System.out.println("\nFinished tests.\n");
    }
}
