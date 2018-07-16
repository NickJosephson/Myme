package com.nitrogen.myme;

import android.os.SystemClock;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.nitrogen.myme.SystemTestUtils.RVHelper;
import com.nitrogen.myme.presentation.ExploreActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.nitrogen.myme.SystemTestUtils.EspressoTestMatchers.withDrawable;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.core.IsAnything.anything;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class FavouritingTest {

    private RVHelper rvHelper;

    /******* Helper Methods *******/

    /* openExploreMeme
     *
     * purpose: Open a meme in the Explore activity
     *
     */
    private void openExploreMeme(int rvPos){
        onView(ViewMatchers.withId(R.id.rvMemes));
        onView(ViewMatchers.withId(R.id.rvMemes)).perform(RecyclerViewActions.scrollToPosition(rvPos));
        onView(withId(R.id.rvMemes)).perform(RecyclerViewActions.actionOnItemAtPosition(rvPos, click()));
    }

    /* openFavouritesMeme
     *
     * purpose: Open a meme in the Favourites activity
     *
     */
    private void openFavouritesMeme(int rvPos){
        onView(ViewMatchers.withId(R.id.rvFavourites));
        onView(ViewMatchers.withId(R.id.rvFavourites)).perform(RecyclerViewActions.scrollToPosition(rvPos));
        onView(withId(R.id.rvFavourites)).perform(RecyclerViewActions.actionOnItemAtPosition(rvPos, click()));
    }

    /******* Tests *******/

    @Rule
    public ActivityTestRule<ExploreActivity> mActivityRule =
            new ActivityTestRule<>(ExploreActivity.class);

    @Before
    public void setUp(){
        rvHelper = new RVHelper();
    }

    @Test
    public void favouriteMeme() {

        // We will select a random meme to favourite
        int explorePos = (int)(Math.random() * rvHelper.getItemCountExplore());

        // open a meme
        openExploreMeme(explorePos);

        // favourite the meme
        onView(withId(R.id.fab)).perform(click());

        // check that the snackbar appeared
        onView(withText("Added to favourites.")).check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        // check that the heart is filled in
        onView(withId(R.id.fab)).check(matches(withDrawable(R.drawable.heart_on)));

        // navigate to favourites page
        Espresso.pressBack();
        onView(withId(R.id.navigation_favourites)).perform(click());

        // check that the meme is found in their favourites
        int favouritesPos = rvHelper.getItemCountFavourites()-1;

        // open the meme in the favourites activity
        openFavouritesMeme(favouritesPos);

        // check that the heart is filled in
        onView(withId(R.id.fab)).check(matches(withDrawable(R.drawable.heart_on)));

        Espresso.pressBack();
    }

    @Test
    public void unfavouriteMeme() {

        // We will select a random meme to favourite then unfavourite it
        int favouritePos = (int)(Math.random() * rvHelper.getItemCountFavourites());

        String memeName = rvHelper.getNameAtPosFavourites(favouritePos);

        // navigate to favourites activity
        onView(withId(R.id.navigation_favourites)).perform(click());

        // open a meme
        openFavouritesMeme(favouritePos);
        onView(withId(R.id.panelName)).check(matches(withText(memeName))); // ensure we're at the right one

        // unfavourite the meme
        onView(withId(R.id.fab)).perform(click());

        // check that the snackbar appeared
        onView(withText("Removed from favourites.")).check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        // check that the heart is not filled in
        onView(withId(R.id.fab)).check(matches(withDrawable(R.drawable.heart_off)));

        // check that it's no longer displayed in the favourites activity
        assertTrue(rvHelper.getPositionFavourites(memeName) == -1);

        Espresso.pressBack();
    }

    @Test
    public void favoriteUnfavouriteMeme() {

        // We will select a random meme to favourite and then unfavourite it
        int explorePos = (int)(Math.random() * rvHelper.getItemCountExplore());

        // open a meme
        openExploreMeme(explorePos);

        // favourite the meme
        onView(withId(R.id.fab)).perform(click());

        // check that the snackbar appeared
        onView(withText("Added to favourites.")).check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        // check that the heart is filled in
        onView(withId(R.id.fab)).check(matches(withDrawable(R.drawable.heart_on)));

        // unfavourite the meme
        onView(withId(R.id.fab)).perform(click());

        // check that the snackbar appeared
        onView(withText("Removed from favourites.")).check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        // check that the heart is not filled in
        onView(withId(R.id.fab)).check(matches(withDrawable(R.drawable.heart_off)));

        Espresso.pressBack();
    }
}

