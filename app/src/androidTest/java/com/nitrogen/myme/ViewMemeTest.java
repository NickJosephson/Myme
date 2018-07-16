package com.nitrogen.myme;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.internal.runner.hidden.ExposedInstrumentationApi;
import android.support.test.runner.AndroidJUnit4;

import com.nitrogen.myme.R;
import com.nitrogen.myme.objects.Meme;
import com.nitrogen.myme.presentation.ExploreActivity;
import com.nitrogen.myme.presentation.FavouritesActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.instanceOf;

import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ViewMemeTest {
    @Before
    public void setup(){ System.out.println("Starting tests for View Meme Test...");}
    @After
    public void tearDown() {System.out.println("\nFinished tests for View Meme Test...\n"); }
    @Rule
    public ActivityTestRule<ExploreActivity> activityRule = new ActivityTestRule<>(ExploreActivity.class);
    @Test
    public void viewMeme() {
        onView(ViewMatchers.withId(R.id.rvMemes)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

    }
}
