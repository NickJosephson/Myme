package com.nitrogen.myme.tests.Business;

import com.nitrogen.myme.business.AccessMemes;
import com.nitrogen.myme.business.Exceptions.MemeNotFoundException;
import com.nitrogen.myme.persistence.MemesPersistence;
import com.nitrogen.myme.persistence.TagsPersistence;
import com.nitrogen.myme.persistence.stubs.MemesPersistenceStub;
import com.nitrogen.myme.persistence.stubs.TagsPersistenceStub;

import org.junit.Test;

import org.junit.After;
import org.junit.Before;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

public class AccessMemesTest {
    private AccessMemes accessMemes;

    @Before
    public void setUp() {
        System.out.println("Starting tests for AccessMemes...");
        // stub database
        TagsPersistence tagsPersistenceStub = new TagsPersistenceStub();
        MemesPersistence memesPersistenceStub = new MemesPersistenceStub(tagsPersistenceStub);

        accessMemes = new AccessMemes(memesPersistenceStub);
        assertNotNull(accessMemes);
    }

    /* Method: getMemes() */

    @Test
    public void testGetMemes() {
        // Retrieve all memes in the database
        System.out.println("...Testing getMemes()");
        assertTrue(accessMemes.getMemes().size() >= 0);
    }

    /* Method: getMemeName() */

    @Test
    public void testGetMemeByName_validName() {
        // Retrieve a meme with an name we know exists in the database
        System.out.println("...Testing getMemeByName() with a name we know is in the database");

        boolean success = true;

        try{
            accessMemes.getMemeByName("LOL");
        } catch (MemeNotFoundException e) {
            success = false;
        }
        assertTrue(success);
    }

    @Test
    public void testGetMemeByName_invalidName() {
        // Retrieve a meme with an name we know doesn't exist in the database
        System.out.println("...Testing getMemeByName() with a name we know is in the database");

        boolean success = true;

        try{
            accessMemes.getMemeByName("");
        } catch (MemeNotFoundException e) {
            success = false;
        }
        assertFalse(success);
    }

    @After
    public void tearDown() {
        System.out.println("\nFinished tests.\n");
    }
}
