package com.nitrogen.myme;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.KeyEvent;

import com.nitrogen.myme.presentation.ExploreActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class SearchForMemeTest {

    @Rule
    public ActivityTestRule<ExploreActivity> activityRule = new ActivityTestRule<>(ExploreActivity.class);

    @Before
    public void setup(){ System.out.println("Starting tests for Search For Meme...");}
    @After
    public void tearDown() {System.out.println("\nFinished tests for Search For Meme...\n"); }

    @Test
    public void searchMeme() {
        onView(ViewMatchers.withId(R.id.search)).perform(click());
        onView(withId(R.id.search)).perform(typeText("Korean")).perform(pressKey(KeyEvent.KEYCODE_ENTER));
        onView(withId(R.id.rvMemes)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withId(R.id.panelName)).check(matches(withText("Korean")));
    }


}
