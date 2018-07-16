package com.nitrogen.myme.tests.Business;

import com.nitrogen.myme.application.Services;
import com.nitrogen.myme.business.AccessTags;
import com.nitrogen.myme.tests.utils.TestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.*;

public class AccessTagsIT {
    private File tempDB;
    private AccessTags accessTags;

    @Before
    public void setUp() throws IOException {
        System.out.println("Starting tests for AccessTags...");

        // build database
        tempDB = TestUtils.copyDB();

        accessTags = new AccessTags();
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
        // delete file
        tempDB.delete();
        // forget DB
        Services.clean();

        System.out.println("\nFinished tests.\n");
    }
}
