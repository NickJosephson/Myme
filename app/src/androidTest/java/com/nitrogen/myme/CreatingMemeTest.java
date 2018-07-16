package com.nitrogen.myme;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.nitrogen.myme.SystemTestUtils.RVHelper;
import com.nitrogen.myme.business.AccessTags;
import com.nitrogen.myme.objects.Tag;
import com.nitrogen.myme.presentation.ExploreActivity;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static com.nitrogen.myme.SystemTestUtils.EspressoTestMatchers.nthChildOf;
import static com.nitrogen.myme.SystemTestUtils.EspressoTestMatchers.withDrawable;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsAnything.anything;
import static org.hamcrest.core.IsNot.not;

/* CreatingMemeTest
 *
 * purpose: Vertically prototype creating a meme
 *
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class CreatingMemeTest {

    private AccessTags accessTags;
    private RVHelper rvHelper;

    private String MEME_NAME;
    private int TAG_ID;

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

    /* addText
     *
     * purpose: Add text to the canvas for meme creation
     *
     */
    private void addText(){
        onView(withId(R.id.add_text_button)).perform(click());
        onView(withId(R.id.text_editor_root)).check(matches(isDisplayed()));
        onView(withId(R.id.edit_text_view)).perform(replaceText("COMP 3350"));
        onView(withText("Sample Test")).check(doesNotExist());
        onView(withText("COMP 3350")).check(matches(isDisplayed()));

        // go back to creating meme
        Espresso.pressBack();
        Espresso.pressBack();

        // assert that text editing buttons appeared
        assertEditingBtnsVisible();
    }

    /* openFavouritesMeme
     *
     * purpose: Open a meme in the Favourites activity and verify it is the correct meme (by name)
     *
     */
    private void openFavouritesMeme(int rvPos, String memeName) {
        onView(ViewMatchers.withId(R.id.rvFavourites));
        onView(ViewMatchers.withId(R.id.rvFavourites)).perform(RecyclerViewActions.scrollToPosition(rvPos));
        onView(withId(R.id.rvFavourites)).perform(RecyclerViewActions.actionOnItemAtPosition(rvPos, click()));
        // ensure we're at the right one
        onView(withId(R.id.panelName)).check(matches(withText(memeName)));
        // check that the heart is filled in
        onView(withId(R.id.fab)).check(matches(withDrawable(R.drawable.heart_on)));
    }

    /* fillMemeInfo
     *
     * purpose: Fill in the info required when saving a meme
     *
     */
    private void fillMemeInfo(int tagID, String memeName){

        List<Tag> tags = accessTags.getTags();
        String currTagName;

        // input name
        onView(withId(R.id.edit_text_meme_name)).perform(typeText(memeName), closeSoftKeyboard());

        // make sure all tags were loaded
        // ** the first child of the view tag_list_save_meme_button is the the TextView title
        for(int i = 0; i < tags.size(); i++) {
            currTagName = tags.get(i).getName();
            onView(nthChildOf(withId(R.id.tag_list_save_meme_button), i+1)).check(matches(withText(currTagName)));
        }

        // select tag
        onView(nthChildOf(withId(R.id.tag_list_save_meme_button), tagID+1)).perform(click());
    }

    /* checkInFavourites
     *
     * purpose: Check if a meme exists in their favourites
     *
     */
    private void checkInFavourites(int tagID, String memeName){
        int favouritesPos;
        String currTagName;

        // we should be in the Favourites Activity
        onView(withId(R.id.rvFavourites)).check(matches(isDisplayed()));

        favouritesPos = rvHelper.getPositionFavourites(memeName);

        openFavouritesMeme(favouritesPos, memeName);

        // verify it has the right tag
        onView(withId(R.id.drag_indicator)).check(matches(isDisplayed()));
        onView(withId(R.id.drag_indicator)).perform(click());

        onView(withId(R.id.panelTags)).check(matches(isDisplayed()));

        currTagName = accessTags.getTags().get(tagID).getName();
        onData(anything()).inAdapterView(withId(R.id.panelTags)).atPosition(0).check(matches(withText(containsString(currTagName))));

        // check that the heart is filled in
        onView(withId(R.id.fab)).check(matches(withDrawable(R.drawable.heart_on)));
    }

    /******* Tests *******/

    @Rule
    public ActivityTestRule<ExploreActivity> mActivityRule =
            new ActivityTestRule<>(ExploreActivity.class);

    @Before
    public void setUp(){
        accessTags = new AccessTags();
        assertNotNull(accessTags);

        rvHelper = new RVHelper();
        assertNotNull(rvHelper);

        MEME_NAME = "hello" + Math.random();
        TAG_ID = (int)(Math.random()*accessTags.getTags().size());

        // navigate to studio (create activity)
        onView(withId(R.id.navigation_studio)).perform(click());

        // make sure initial canvas is there
        onView(withId(R.id.imageView1)).check(matches((withDrawable(R.drawable.initial_canvas))));

        // make sure editing buttons aren't visible
        assertEditingBtnsNotVisible();
    }

    @Test
    public void noImageNoTextTest() {
        // save meme
        onView(withId(R.id.save_meme_button)).perform(click());
        fillMemeInfo(TAG_ID, MEME_NAME);
        onView(withId(R.id.accept_save_meme_button)).perform(click());

        // Verify it was created
        checkInFavourites(TAG_ID, MEME_NAME);
    }

    @Test
    public void noImageTest() {
        // add text
        addText();

        // save meme
        onView(withId(R.id.save_meme_button)).perform(click());
        fillMemeInfo(TAG_ID, MEME_NAME);
        onView(withId(R.id.accept_save_meme_button)).perform(click());

        // Verify it was created
        checkInFavourites(TAG_ID, MEME_NAME);
    }

    @Test
    public void galleryImageTest() {

        /* https://www.programcreek.com/java-api-examples/index.php?api=android.support.test.espresso.intent.Intents
         *
         * Because we launch an Android built in intent, when the test terminates,
         * the gallery image picker is the last thing you'll see.
         *
        */

        // Build the result to return when the activity is launched
        Intents.init();
        Intent resultData = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        resultData.setData(Uri.parse(("content://media/external/images/media/57")));
        Matcher<Intent> MediaPickIntent = allOf(hasAction(Intent.ACTION_PICK));

        // Set up result stubbing when an intent is seen
        intending(MediaPickIntent).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData));

        // select image from gallery
        onView(withId(R.id.gallery_button)).perform(click());

        // assert that text editing buttons haven't appeared (there's no text on the screen)
        assertEditingBtnsNotVisible();
        onView(withId(R.id.rotateImgButton)).check(matches(isDisplayed()));

        // add text
        addText();

        // save meme
        onView(withId(R.id.save_meme_button)).perform(click());
        fillMemeInfo(TAG_ID, MEME_NAME);
        onView(withId(R.id.accept_save_meme_button)).perform(click());

        // Verify it was created
        checkInFavourites(TAG_ID, MEME_NAME);
        Intents.release();
    }

    @Test
    public void templateImageTest() {

        if(rvHelper.getItemCountTemplates() > 0) {
            // We will select a random template
            int templatePos = (int) (Math.random() * rvHelper.getItemCountTemplates());

            // select a template
            onView(withId(R.id.from_template_button)).perform(click());
            onView(ViewMatchers.withId(R.id.rvTemplates));
            onView(ViewMatchers.withId(R.id.rvTemplates)).perform(RecyclerViewActions.scrollToPosition(templatePos));

            onView(withId(R.id.rvTemplates)).perform(RecyclerViewActions.actionOnItemAtPosition(templatePos, click()));

            // Assert that the canvas was updated
            onView(withId(R.id.imageView1)).check(matches(not((withDrawable(R.drawable.initial_canvas)))));

            // add text
            addText();

            // save meme
            onView(withId(R.id.save_meme_button)).perform(click());
            fillMemeInfo(TAG_ID, MEME_NAME);
            onView(withId(R.id.accept_save_meme_button)).perform(click());

            // Verify it was created
            checkInFavourites(TAG_ID, MEME_NAME);
        }
    }

    @Test
    public void cancelCreationTest() {
        if (rvHelper.getItemCountTemplates() > 0) {
            // We will select a random template
            int templatePos = (int) (Math.random() * rvHelper.getItemCountTemplates());

            // select a template
            onView(withId(R.id.from_template_button)).perform(click());
            onView(ViewMatchers.withId(R.id.rvTemplates));
            onView(ViewMatchers.withId(R.id.rvTemplates)).perform(RecyclerViewActions.scrollToPosition(templatePos));

            onView(withId(R.id.rvTemplates)).perform(RecyclerViewActions.actionOnItemAtPosition(templatePos, click()));

            // Assert that the canvas was updated
            onView(withId(R.id.imageView1)).check(matches(not((withDrawable(R.drawable.initial_canvas)))));

            addText();

            // save meme
            onView(withId(R.id.save_meme_button)).perform(click());
            fillMemeInfo(TAG_ID, MEME_NAME);
            onView(withId(R.id.cancel_save_meme_button)).perform(click());

            // Assert that the canvas did not revert back to it's original state
            onView(withId(R.id.imageView1)).check(matches(not((withDrawable(R.drawable.initial_canvas)))));
        }
    }
}

