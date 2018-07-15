package com.nitrogen.myme.tests.Business;

import com.nitrogen.myme.application.Main;
import com.nitrogen.myme.application.Services;
import com.nitrogen.myme.business.AccessMemes;
import com.nitrogen.myme.business.Exceptions.InvalidMemeException;
import com.nitrogen.myme.business.Exceptions.MemeNotFoundException;
import com.nitrogen.myme.objects.Meme;
import com.nitrogen.myme.tests.utils.TestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

public class AccessMemesIT {
    private File tempDB;
    private AccessMemes accessMemes;

    @Before
    public void setUp() throws IOException {
        System.out.println("Starting tests for AccessMemes...");

        // build database
        tempDB = TestUtils.copyDB();

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

    @Test
    public void testInstanceNotNull() {
        AccessMemes newInstance = new AccessMemes();
        assertNotNull(newInstance);
    }

    /* Method: getMemeName() */

    @Test
    public void testGetMemeByName_validName() {
        // Retrieve a meme with an name we know exists in the database
        System.out.println("...Testing getMemeByName() with a name we know is in the database");

        boolean success = true;

        try{
            accessMemes.getMemeByName("A day without laughter");
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

    /* Method: updatefav() */

    @Test
    public void testUpdateFav_validMeme() {
        System.out.println("...Testing updateFav() with a meme we know is in the database");

        Meme meme;
        List<Meme> memeList = accessMemes.getMemes();

        assertTrue(memeList.size() > 0);

        meme = memeList.get(0);

        accessMemes.updatefav(meme);
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
