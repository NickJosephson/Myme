package com.nitrogen.myme.tests.Business;

import com.nitrogen.myme.business.AccessMemes;

import org.junit.Test;

import org.junit.After;
import org.junit.Before;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

public class AccessMemesTest {
    private AccessMemes accessMemes;

    @Before
    public void setUp() {
        System.out.println("Starting tests for AccessMemes...");
        accessMemes = new AccessMemes();
        assertNotNull(accessMemes);
    }

    /* Method: getMemes() */

    @Test
    public void testGetMemes() {
        // Retrieve all memes in the database
        System.out.println("...Testing getMemes()");
        assertTrue(accessMemes.getMemes().size() >= 0);
    }

    @After
    public void tearDown() {
        System.out.println("\nFinished tests.\n");
    }
}
