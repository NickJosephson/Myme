package com.nitrogen.myme.tests.Business;

import com.nitrogen.myme.business.AccessTags;
import com.nitrogen.myme.business.UpdateTags;
import com.nitrogen.myme.objects.Meme;
import com.nitrogen.myme.objects.Tag;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class UpdateTagsTest {
    private UpdateTags updateTags;
    private AccessTags accessTags;
    private int initialSize;

    @Before
    public void setUp() {
        System.out.println("Starting tests for UpdateTags.\n");
        updateTags = new UpdateTags();
        accessTags = new AccessTags();
        assertNotNull(updateTags);
        assertNotNull(accessTags);

        initialSize = accessTags.getTags().size();
    }

    /* Method: deleteTag(Tag tag) */

    @Test
    public void testDeleteTag_oneTag() {
        // Delete one tag from the database
        System.out.println("Testing deleteTag(), deleting one tag from the database");

        final int T_INDEX = (int)(Math.random() * (accessTags.getTags().size() - 1));
        Tag target = accessTags.getTags().get(T_INDEX);
        updateTags.deleteTag(target);
        // check size
        assertEquals(initialSize - 1, accessTags.getTags().size());
        // check we deleted the correct tag
        assertFalse(accessTags.getTags().contains(target));
    }

    @Test
    public void testDeleteTag_invalidTag() {
        // Delete a tag that is not in the database
        System.out.println("Testing deleteTag(), deleting a tag that is not in the database");
        updateTags.deleteTag(new Tag("nonexistent"));
        assertEquals(initialSize, accessTags.getTags().size());

        // Delete all tags in the database
        System.out.println("Testing deleteTag(), deleting all tags in the database");
        initialSize = accessTags.getTags().size();
        for(int i = 0 ; i < initialSize ; i++ ) {
            updateTags.deleteTag(accessTags.getTags().get(0));
        }
        assertEquals(0, accessTags.getTags().size());
    }

    @Test
    public void testDeleteTag_allTags() {
        // Delete all tags in the database
        System.out.println("Testing deleteTag(), deleting all tags in the database");
        Tag deleted;
        for(int i = 0 ; i < initialSize ; i++ ) {
            deleted = updateTags.deleteTag(accessTags.getTags().get(0));
            assertFalse(accessTags.getTags().contains(deleted));
        }
        assertEquals(0, accessTags.getTags().size());
    }


    /* Method: insertTag(Tag tag) */

    @Test
    public void testInsertTag_multipleTags() {
        // Insert multiple tags
        System.out.println("Testing insertTag(), inserting tags into a non-empty database");

        final int NUM_TAGS = 5;

        Tag newTag;
        for(int i = 0 ; i < NUM_TAGS ; i++) {
            newTag = new Tag("test_tag_" + i);
            updateTags.insertTag(newTag);
            // ensure the new tag is in the database
            assertTrue(accessTags.getTags().contains(newTag));
        }
        assertEquals(initialSize + NUM_TAGS, accessTags.getTags().size());
    }

    @Test
    public void testInsertTag_duplicateTags() {
        // Insert multiples of the same tags
        System.out.println("Testing insertTag(), inserting doubles of the same tags into a non-empty database");

        final int NUM_TAGS = 5;

        Tag newTag;
        for(int i = 0 ; i < NUM_TAGS ; i++) {
            newTag = new Tag("test_tag_"+i);
            updateTags.insertTag(newTag);
            updateTags.insertTag(newTag);
        }
        assertEquals(initialSize + NUM_TAGS, accessTags.getTags().size());
    }

    @After
    public void tearDown() {
        System.out.println("\nFinished tests.\n");
    }
}
