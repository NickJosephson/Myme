package com.nitrogen.myme.tests.Business;

import com.nitrogen.myme.application.Services;
import com.nitrogen.myme.business.AccessFavourites;
import com.nitrogen.myme.objects.Meme;
import com.nitrogen.myme.tests.utils.TestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

public class AccessFavouritesIT {
    private File tempDB;
    private AccessFavourites accessFavourites;

    @Before
    public void setUp() throws IOException {
        System.out.println("Starting tests for AccessFavourites...");

        // build database
        tempDB = TestUtils.copyDB();

        accessFavourites = new AccessFavourites();
        assertNotNull(accessFavourites);
    }

    @Test
    public void testGetMemes_allFavouritedMemes() {
        // Retrieve all favourited memes in the database
        System.out.println("Testing getMemes(), getting all favourited memes in the database");

        assertTrue(accessFavourites.getMemes().size() >= 0);
    }

    @Test
    public void testInstanceNotNull() {
        AccessFavourites newInstance = new AccessFavourites();
        assertNotNull(newInstance);
    }

    @Test
    public void testGetMemes_validateFavouritedMemes() {
        // Ensure memes are actually favourited
        System.out.println("Testing getMemes(), validating favourites list");
        List<Meme> favouritedMemes = accessFavourites.getMemes();
        for(int i = 0; i < favouritedMemes.size(); i++){
            assertTrue(favouritedMemes.get(i).isFavourite());
        }
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
