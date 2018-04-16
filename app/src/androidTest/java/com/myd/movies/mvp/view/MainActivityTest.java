package com.myd.movies.mvp.view;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.test.espresso.contrib.PickerActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.widget.DatePicker;
import android.widget.ProgressBar;

import com.myd.movies.R;
import com.myd.movies.util.RecyclerViewMatcher;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


/**
 * Created by MYD on 4/14/18.
 *
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class, false, false);

    @Before
    public void setUp() throws Exception {
        activityTestRule.launchActivity(new Intent());

    }

    @Test
    public void testListFragment() throws Exception {
        onView(withId(R.id.fragment)).check(matches(isDisplayed()));
        onView(withId(R.id.fragment_movie_list_rcv)).check(matches(isDisplayed()));
        onView(withId(R.id.fragment_movie_list_rcv)).perform(RecyclerViewActions.scrollToPosition(20));
        onView(withId(R.id.fragment_movie_list_rcv)).perform(RecyclerViewActions.scrollToPosition(40));
    }

    @Test
    public void testDetailFragment() throws Exception {
        onView(withId(R.id.fragment)).check(matches(isDisplayed()));
        onView(withId(R.id.fragment_movie_list_rcv)).check(matches(isDisplayed()));
        onView(withId(R.id.fragment_movie_list_rcv)).perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));

        onView(withId(R.id.fragment_movie_details_backdrop_iv)).check(matches(isDisplayed()));
        onView(withId(R.id.action_filter)).check(doesNotExist());
        onView(withContentDescription(R.string.abc_action_bar_up_description)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.fragment_movie_list_rcv)).check(matches(isDisplayed()));
    }

    @Test
    public void testDatePicker() throws Exception {
        onView(withId(R.id.action_filter)).check(matches(isDisplayed()));
        onView(withId(R.id.action_filter)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2017, 3, 12));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.fragment_movie_list_rcv)).check(matches(isDisplayed()));
        onView(RecyclerViewMatcher.withRecyclerView(R.id.fragment_movie_list_rcv).atPosition(0)).check(matches(hasDescendant(withText("2017-03-12"))));

    }
}
