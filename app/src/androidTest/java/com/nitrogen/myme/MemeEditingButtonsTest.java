package com.nitrogen.myme;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.nitrogen.myme.presentation.CreateActivity;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasCategories;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static com.nitrogen.myme.SystemTestUtils.EspressoTestMatchers.withDrawable;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;

/* MemeEditingButtonsTest
 *
 * purpose: Horizontally prototype creating a meme testing only the editing buttons
 *
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MemeEditingButtonsTest{

    /******* Helper Methods *******/

    /* assertEditingBtnsVisible
     *
     * purpose: Check if the editing buttons are visible
     *
     */
    private void assertEditingBtnsVisible(){

        // font change button
        onView(withId(R.id.text_entity_font_change_button)).check(matches(isDisplayed()));

        // edit button
        onView(withId(R.id.text_entity_edit_button)).check(matches(isDisplayed()));

        // delete text button
        onView(withId(R.id.delete_text_entity_button)).check(matches(isDisplayed()));
    }

    /* assertEditingBtnsNotVisible
     *
     * purpose: Check if the editing buttons are NOT visible
     *
     */
    private void assertEditingBtnsNotVisible(){

        // font change button
        onView(withId(R.id.text_entity_font_change_button)).check(matches(not(isDisplayed())));

        // edit button
        onView(withId(R.id.text_entity_edit_button)).check(matches(not(isDisplayed())));

        // delete text button
        onView(withId(R.id.delete_text_entity_button)).check(matches(not(isDisplayed())));
    }

    /******* Tests *******/

    @Rule
    public ActivityTestRule<CreateActivity> mActivityRule =
            new ActivityTestRule<>(CreateActivity.class);

    @Before
    public void setUp(){
        // make sure initial canvas is there
        onView(withId(R.id.imageView1)).check(matches((withDrawable(R.drawable.initial_canvas))));

        // make sure editing buttons aren't visible
        assertEditingBtnsNotVisible();

        // add a text entity
        onView(withId(R.id.add_text_button)).perform(click());
        onView(withId(R.id.edit_text_view)).perform(replaceText("COMP 3350"));
        onView(withText("Sample Test")).check(doesNotExist());

        Espresso.pressBack();
        Espresso.pressBack();

        // make sure editing buttons are now visible
        assertEditingBtnsVisible();

    }

    @Test
    public void fontButtonTest(){
        String fontName = "Impact";

        onView(withId(R.id.text_entity_font_change_button)).perform(click());
        onView(withId(R.id.select_dialog_listview)).check(matches(isDisplayed()));
        onView(withText(fontName)).check(matches(isDisplayed())).perform(click());
    }

    @Test
    public void editButtonTest() {
        onView(withId(R.id.text_entity_edit_button)).perform(click());
    }

    @Test
    public void deleteButtonTest(){
        onView(withId(R.id.delete_text_entity_button)).perform(click());
        onView(withId(R.id.edit_text_view)).check(doesNotExist());
    }

    @Test
    public void rotateButtonTest(){
        // Build the result to return when the activity is launched
        Intents.init();
        Intent resultData = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        resultData.setData(Uri.parse(("content://media/external/images/media/2"))); // assuming we have images on the device
        Matcher<Intent> MediaPickIntent = allOf(hasAction(Intent.ACTION_PICK));

        // Set up result stubbing when an intent is seen
        intending(MediaPickIntent).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData));

        // select image from gallery
        onView(withId(R.id.gallery_button)).perform(click());

        // assert that text editing buttons haven't appeared (there's no text on the screen)
        assertEditingBtnsNotVisible();

        // test rotating the image
        onView(withId(R.id.rotateImgButton)).perform(click());

    }
}

