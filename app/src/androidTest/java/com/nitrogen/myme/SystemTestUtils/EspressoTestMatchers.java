package com.nitrogen.myme.SystemTestUtils;

import android.support.test.espresso.matcher.BoundedMatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class EspressoTestMatchers {

    /* Matcher<View> withDrawable
     *
     * purpose: determine if a view has a specific drawable
     *
    */
    public static Matcher<View> withDrawable(final int resourceId) {
        return new DrawableMatcher(resourceId);
    }

    /* Matcher<View> nthChildOf
     *
     * purpose: Check the value of the nth child of an array adapter
     *
    */
    public static Matcher<View> nthChildOf(final Matcher<View> parentMatcher, final int childPosition) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("with "+childPosition+" child view of type parentMatcher");
            }

            @Override
            public boolean matchesSafely(View view) {
                if (!(view.getParent() instanceof ViewGroup)) {
                    return parentMatcher.matches(view.getParent());
                }

                ViewGroup group = (ViewGroup) view.getParent();
                return parentMatcher.matches(view.getParent()) && group.getChildAt(childPosition).equals(view);
            }
        };
    }
}
