package com.myd.movies.mvp.view;

import android.content.Intent;
import android.support.test.espresso.contrib.PickerActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.DatePicker;

import com.myd.movies.R;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


/**
 * Created by MYD on 4/14/18.
 *
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class, false, false);


    @Test
    public void TestFragmentVisibility() throws Exception {
        activityTestRule.launchActivity(new Intent());
        onView(withId(R.id.fragment)).check(matches(isDisplayed()));
    }

    @Test
    public void TestDatePicker() throws Exception {
        activityTestRule.launchActivity(new Intent());
        onView(withId(R.id.action_filter)).check(matches(isDisplayed()));
        onView(withId(R.id.action_filter)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2017, 3, 12));
        onView(withId(android.R.id.button1)).perform(click());

    }
}
