package com.nitrogen.myme.tests.Business;

import com.nitrogen.myme.business.AccessTags;
import com.nitrogen.myme.business.UpdateTags;
import com.nitrogen.myme.objects.Tag;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UpdateTagsTest {
    @Before
    public void setUp() {
        System.out.println("Starting tests for UpdateTags.\n");
    }

    @Test
    public void testDeleteTag() {
        System.out.println("Testing deleteTag...");

        UpdateTags updateTags = new UpdateTags();
        AccessTags accessTags = new AccessTags();

        // Delete one tag from the database
        System.out.println("...Delete one tag from the database");
        int initialSize = accessTags.getTags().size();
        Tag target = accessTags.getTags().get(0);
        updateTags.deleteTag(accessTags.getTags().get(0));
        assertEquals(initialSize - 1, accessTags.getTags().size()); // check size
        assertEquals(-1, accessTags.getTags().indexOf(target));    // check we deleted the correct tag

        // Delete a tag that is not in the database
        System.out.println("...Deleting a tag that is not in the database");
        initialSize = accessTags.getTags().size();
        updateTags.deleteTag(new Tag("nonexistent"));
        assertEquals(initialSize, accessTags.getTags().size());

        // Delete all tags in the database
        System.out.println("...Deleting all tags in the database");
        initialSize = accessTags.getTags().size();
        for(int i = 0 ; i < initialSize ; i++ ) {
           updateTags.deleteTag(accessTags.getTags().get(0));
        }
        assertEquals(0, accessTags.getTags().size());
    }

    @Test
    public void testInsertTag() {
        System.out.println("Testing insertTag...");

        UpdateTags updateTags = new UpdateTags();
        AccessTags accessTags = new AccessTags();

        final int NUM_TAGS = 5;

        // Insert multiple tags
        System.out.println("...Insert tags into a non-empty database");

        int initialSize = accessTags.getTags().size(); // save initial number of tags in the database
        Tag newTag;

        for(int i = 0 ; i < NUM_TAGS ; i++) {
            newTag = new Tag("test_tag_" + i);
            updateTags.insertTag(newTag);
            assertTrue(accessTags.getTags().indexOf(newTag) >= 0); // ensure the new tag is in the database
        }
        assertEquals(initialSize + NUM_TAGS, accessTags.getTags().size()); // check size

        // Insert the tags with the same names
        System.out.println("...Insert tags with the same names");
        initialSize = accessTags.getTags().size();
        for(int i = 0 ; i < NUM_TAGS ; i++) {
            updateTags.insertTag(new Tag("test_tag_"+i));
        }
        assertEquals(initialSize, accessTags.getTags().size());

        // Insert a tag that's already in the database
        System.out.println("...Insert a tag that's already in the database");
        initialSize = accessTags.getTags().size();
        updateTags.insertTag(accessTags.getTags().get(0));
        assertEquals(initialSize, accessTags.getTags().size());
    }

    @After
    public void tearDown() {
        System.out.println("\nFinished tests.\n");
    }
}
