package com.nitrogen.myme.tests.Business;

import com.nitrogen.myme.business.AccessFavourites;
import com.nitrogen.myme.business.AccessMemes;
import com.nitrogen.myme.objects.Meme;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

public class AccessFavouritesTest {
    @Before
    public void setUp() {
        System.out.println("Starting tests for AccessFavourites...");
    }

    @Test
    public void testGetMemes() {
        // Retrieve all favourited memes in the database
        System.out.println("...Testing getMemes()");
        AccessFavourites accessFavourites = new AccessFavourites();
        assertNotNull(accessFavourites);
        assertTrue(accessFavourites.getMemes().size() >= 0);

        // Ensure memes are actually favourited
        System.out.println("...Validating favourites list");
        List<Meme> favouritedMemes = accessFavourites.getMemes();
        for(int i = 0; i < favouritedMemes.size(); i++){
            assertTrue(favouritedMemes.get(i).getIsFavourite());
        }
    }

    @After
    public void tearDown() {
        System.out.println("\nFinished tests.\n");
    }
}
