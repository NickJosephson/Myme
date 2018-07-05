package com.nitrogen.myme.tests.Business;

import com.nitrogen.myme.business.AccessMemeTemplates;
import com.nitrogen.myme.business.AccessTags;
import com.nitrogen.myme.persistence.TagsPersistence;
import com.nitrogen.myme.persistence.stubs.TagsPersistenceStub;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.*;

public class AccessTagsTest {
    private AccessTags accessTags;

    @Before
    public void setUp() {
        System.out.println("Starting tests for AccessTags...");
        // stub database
        TagsPersistence tagsPersistenceStub = new TagsPersistenceStub();

        accessTags = new AccessTags(tagsPersistenceStub);
        assertNotNull(accessTags);
    }

    /* Method: getTags() */

    @Test
    public void testGetTags() {
        // Retrieve all tags in the database
        System.out.println("...Testing getTags()");
        assertTrue(accessTags.getTags().size() >= 0);
    }

    @Test
    public void testInstanceNotNull() {
        AccessTags newInstance = new AccessTags();
        assertNotNull(newInstance);
    }

    @After
    public void tearDown() {
        System.out.println("\nFinished tests.\n");
    }
}
