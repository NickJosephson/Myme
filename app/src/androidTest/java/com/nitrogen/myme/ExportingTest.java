package com.nitrogen.myme;

import android.os.SystemClock;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.nitrogen.myme.SystemTestUtils.RVHelper;
import com.nitrogen.myme.presentation.ExploreActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.nitrogen.myme.SystemTestUtils.PermissionGranter.allowPermissionsIfNeeded;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExportingTest {
    private RVHelper rvHelper;

    @Rule
    public ActivityTestRule<ExploreActivity> mActivityRule =
            new ActivityTestRule<>(ExploreActivity.class);

    @Before
    public void setUp(){
        rvHelper = new RVHelper();
    }


    @Test
    public void ExportExploreMeme() {

        // We will select a random meme to export
        int explorePos = (int)(Math.random() * rvHelper.getItemCountExplore());

        // open a meme
        onView(ViewMatchers.withId(R.id.rvMemes));

        onView(ViewMatchers.withId(R.id.rvMemes)).perform(RecyclerViewActions.scrollToPosition(explorePos));

        onView(withId(R.id.rvMemes)).perform(RecyclerViewActions.actionOnItemAtPosition(explorePos, click()));

        // export the meme
        onView(withId(R.id.export_meme)).perform(click());

        if(!allowPermissionsIfNeeded()){
            // check that the snackbar appeared
            onView(withText("Meme exported to Gallery.")).check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        }

        // navigate to explore page
        Espresso.pressBack();
    }
}
