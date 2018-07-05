package com.nitrogen.myme.tests.Business;

import com.nitrogen.myme.business.AccessMemes;
import com.nitrogen.myme.business.AccessTags;
import com.nitrogen.myme.business.SearchTags;
import com.nitrogen.myme.objects.Meme;
import com.nitrogen.myme.objects.Tag;
import com.nitrogen.myme.persistence.MemesPersistence;
import com.nitrogen.myme.persistence.TagsPersistence;
import com.nitrogen.myme.persistence.stubs.MemesPersistenceStub;
import com.nitrogen.myme.persistence.stubs.TagsPersistenceStub;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SearchTagsTest {
    private AccessMemes accessMemes;
    private AccessTags accessTags;
    private SearchTags searchTags;

    @Before
    public void setUp() {
        System.out.println("Starting tests for SearchTags.\n");
        // stub database
        TagsPersistence tagsPersistenceStub = new TagsPersistenceStub();
        MemesPersistence memesPersistenceStub = new MemesPersistenceStub(tagsPersistenceStub);

        accessMemes = new AccessMemes(memesPersistenceStub);
        accessTags = new AccessTags(tagsPersistenceStub);
        searchTags = new SearchTags(tagsPersistenceStub);
        assertNotNull(accessMemes);
        assertNotNull(accessTags);
        assertNotNull(searchTags);
    }

    /* getTagsFromMemes(List<Meme> memes) */

    @Test
    public void testGetTagsFromMemes_allMemes() {
        // Non-empty tag database where there are no orphan tags (tags that aren't assigned to any memes)
        System.out.println("Testing getTagsFromMemes() with all memes in the database");
        assertEquals(accessTags.getTags().size(), searchTags.getTagsFromMemes(accessMemes.getMemes()).size());
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
        System.out.println("\nFinished tests.\n");
    }
}
