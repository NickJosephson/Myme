package com.nitrogen.myme.tests.Business;

import com.nitrogen.myme.business.AccessMemes;
import com.nitrogen.myme.business.AccessTags;
import com.nitrogen.myme.business.SearchMemes;
import com.nitrogen.myme.objects.Tag;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SearchMemesTest {
    // Get the tags from the db
    AccessTags accessTags = new AccessTags();
    List<Tag> tags = accessTags.getTags();

    @Before
    public void setUp() {
        System.out.println("Starting tests for SearchMemes.\n");

        assertNotNull(tags);
        assertTrue(tags.size() > 0);
    }

    @Test
    public void testGetMemesByTags() {
        System.out.println("Testing getMemesByTags()...");

        AccessMemes accessMemes = new AccessMemes();
        SearchMemes searchMemes = new SearchMemes();
        assertNotNull(searchMemes);

        // Non-empty Meme db where each tag appears at least once
        System.out.println("...Standard case");
        assertEquals(accessMemes.getMemes().size(), searchMemes.getMemesByTags(tags).size()); // should be all memes in the database

        // Non-empty Meme db where tag list is empty
        System.out.println("...Empty tag list");
        assertEquals(0, searchMemes.getMemesByTags(new ArrayList<Tag>()).size());

        // Non-empty Meme db with tag list of size 1 with its only tag being one we know exists in the tag db
        System.out.println("...One tag we know is present");
        List<Tag> oneTag = tags.subList(0,1);
        assertTrue(searchMemes.getMemesByTags(oneTag).size() > 0); // at least one meme has this tag
        assertTrue(searchMemes.getMemesByTags(oneTag).size() <= accessMemes.getMemes().size()); // number of memes returned doesn't exceed the number of memes in the database

        // Non-empty Meme db where we know the tags in the tag list don't exist in the tag db
        System.out.println("...Tags we know aren't present");
        List<Tag> newTags = new ArrayList<>();
        for(int i = 0 ; i < 5 ; i ++) {
            newTags.add(new Tag("tag"+i));
        }
        assertEquals(0, searchMemes.getMemesByTags(newTags).size());
}

    @After
    public void tearDown() {
        System.out.println("\nFinished tests.\n");
    }
}
