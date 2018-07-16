package com.nitrogen.myme;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.nitrogen.myme.SystemTestUtils.RVHelper;
import com.nitrogen.myme.business.AccessMemes;
import com.nitrogen.myme.business.AccessTags;


import com.nitrogen.myme.application.Main;
import com.nitrogen.myme.application.Services;
import com.nitrogen.myme.business.AccessFavourites;
import com.nitrogen.myme.presentation.ExploreActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class RecommendationTest {

    @Rule
    public ActivityTestRule<ExploreActivity> activityRule = new ActivityTestRule<>(ExploreActivity.class);

    @Before
    public void setup(){

        System.out.println("Starting tests for recommendation...");
        try(Connection c = connect()){
            PreparedStatement in = c.prepareStatement("UPDATE meme SET fav = 1 WHERE name = 'Queen'");
            in.executeUpdate();
            in = c.prepareStatement("UPDATE meme SET fav = 1 WHERE name = 'Only khlav kalash'");
            in.executeUpdate();
            in = c.prepareStatement("UPDATE meme SET fav = 1 WHERE name = 'Homer buying car'");
            in.executeUpdate();
            in.close();
            Services.clean();
            AccessMemes accessmeme  = new AccessMemes(Services.getMemesPersistence());
            accessmeme.getMemes();
            AccessFavourites accessFavourites = new AccessFavourites(Services.getMemesPersistence());
            accessFavourites.getMemes();
            AccessTags accessTags = new AccessTags(Services.getTagsPersistence());
            accessTags.getTags();

            in = c.prepareStatement("UPDATE meme SET fav = 0  WHERE name = 'Queen'");
            in.executeUpdate();
            in = c.prepareStatement("UPDATE meme SET fav = 0 WHERE name = 'Only khlav kalash'");
            in.executeUpdate();
            in = c.prepareStatement("UPDATE meme SET fav = 0 WHERE name = 'Homer buying car'");
            in.executeUpdate();

            in.close();

        }
        catch (final SQLException e){

            Log.e("Connect SQL1",e.getMessage()+ e.getSQLState());
        }
    }
    @After
    public  void tearDown() {
        Services.clean();
        System.out.println("\nFinished tests for recommendation...\n");
    }

    @Test
    public void recommendation() {
        RVHelper rv = new RVHelper();
        onView(ViewMatchers.withId(R.id.navigation_favourites)).perform(click());
        onView(ViewMatchers.withId(R.id.navigation_explore)).perform(click());
        assertTrue(rv.confirmTags("dank"));
        onView(withId(R.id.rvMemes)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
    }

    private static Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + Main.getDBPathName() +";shutdown=true", "SA","");
    }



}
