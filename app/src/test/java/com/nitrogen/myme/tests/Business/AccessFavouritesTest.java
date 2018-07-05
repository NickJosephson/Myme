package com.nitrogen.myme.tests.Business;

import com.nitrogen.myme.business.AccessFavourites;
import com.nitrogen.myme.business.AccessMemes;
import com.nitrogen.myme.business.AccessTags;
import com.nitrogen.myme.objects.Meme;
import com.nitrogen.myme.persistence.MemesPersistence;
import com.nitrogen.myme.persistence.TagsPersistence;
import com.nitrogen.myme.persistence.stubs.MemesPersistenceStub;
import com.nitrogen.myme.persistence.stubs.TagsPersistenceStub;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

public class AccessFavouritesTest {
    private AccessFavourites accessFavourites;

    @Before
    public void setUp() {
        System.out.println("Starting tests for AccessFavourites...");
        // stub database
        TagsPersistence tagsPersistence = new TagsPersistenceStub();
        MemesPersistence memesPersistence = new MemesPersistenceStub(tagsPersistence);

        accessFavourites = new AccessFavourites(memesPersistence);
        assertNotNull(accessFavourites);
    }

    /* Method: getMemes() */
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
        System.out.println("\nFinished tests.\n");
    }
}
