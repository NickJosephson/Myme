package com.nitrogen.myme;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.nitrogen.myme.business.AccessTags;
import com.nitrogen.myme.business.MemeValidator;
import com.nitrogen.myme.objects.Tag;
import com.nitrogen.myme.presentation.ExploreActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static com.nitrogen.myme.SystemTestUtils.EspressoTestMatchers.nthChildOf;
import static com.nitrogen.myme.SystemTestUtils.EspressoTestMatchers.withDrawable;
import static com.nitrogen.myme.presentation.SaveMemeActivity.INVALID_NAME_DUPLICATE;
import static com.nitrogen.myme.presentation.SaveMemeActivity.INVALID_NAME_LENGTH;
import static com.nitrogen.myme.presentation.SaveMemeActivity.INVALID_NAME_NULL;
import static com.nitrogen.myme.presentation.SaveMemeActivity.INVALID_TAGS_NULL;
import static junit.framework.Assert.assertNotNull;

/* SavingInvalidMemeTest
 *
 * purpose: Horizontally prototype saving invalid memes
 *
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class SavingInvalidMemeTest {

    private AccessTags accessTags;

    private String MEME_NAME;
    private int TAG_ID;

    /******* Helper Methods *******/

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

    /******* Tests *******/

    @Rule
    public ActivityTestRule<ExploreActivity> mActivityRule =
            new ActivityTestRule<>(ExploreActivity.class);

    @Before
    public void setUp(){
        accessTags = new AccessTags();
        assertNotNull(accessTags);

        MEME_NAME = "hello" + Math.random();
        TAG_ID = (int)(Math.random()*accessTags.getTags().size());

        // navigate to studio (create activity)
        onView(withId(R.id.navigation_studio)).perform(click());

        // make sure initial canvas is there
        onView(withId(R.id.imageView1)).check(matches((withDrawable(R.drawable.initial_canvas))));
    }

    @Test
    public void saveMeme_duplicateTest() {
        // Create and save a meme
        onView(withId(R.id.save_meme_button)).perform(click());
        fillMemeInfo(TAG_ID, MEME_NAME);
        onView(withId(R.id.accept_save_meme_button)).perform(click());

        // navigate back to studio (create activity)
        onView(withId(R.id.navigation_studio)).perform(click());

        // make sure initial canvas is there
        onView(withId(R.id.imageView1)).check(matches((withDrawable(R.drawable.initial_canvas))));

        // try saving a meme with the same name
        onView(withId(R.id.save_meme_button)).perform(click());
        fillMemeInfo(TAG_ID, MEME_NAME);
        onView(withId(R.id.accept_save_meme_button)).perform(click());
        onView(withText(INVALID_NAME_DUPLICATE)).inRoot(isDialog()).check(matches(isDisplayed()));
        Espresso.pressBack();
    }

    @Test
    public void saveMeme_nameInvalidTest(){
        MemeValidator memeValidator = new MemeValidator();

        onView(withId(R.id.save_meme_button)).perform(click());

        // Empty string
        fillMemeInfo(TAG_ID, "");
        onView(withId(R.id.accept_save_meme_button)).perform(click());
        onView(withText(INVALID_NAME_NULL)).inRoot(isDialog()).check(matches(isDisplayed()));
        Espresso.pressBack();

        // Name that is too long
        onView(withId(R.id.edit_text_meme_name))
                .perform(replaceText(MEME_NAME+MEME_NAME), closeSoftKeyboard());
        onView(withId(R.id.accept_save_meme_button)).perform(click());
        onView(withText(INVALID_NAME_LENGTH + memeValidator.MAX_NAME_LEN)).inRoot(isDialog()).check(matches(isDisplayed()));
        Espresso.pressBack();
    }

    @Test
    public void saveMeme_tagsInvalidTest(){
        onView(withId(R.id.save_meme_button)).perform(click());

        fillMemeInfo(TAG_ID, MEME_NAME);

        // deselect the only tag selected
        onView(nthChildOf(withId(R.id.tag_list_save_meme_button), TAG_ID+1)).perform(click());
        onView(withId(R.id.accept_save_meme_button)).perform(click());
        onView(withText(INVALID_TAGS_NULL)).inRoot(isDialog()).check(matches(isDisplayed()));
        Espresso.pressBack();
    }

}


