package com.lahtinen.apps.flextime

import android.view.View
import android.widget.NumberPicker
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun addTime() {
        activityRule.activity.runOnUiThread {
            Runnable {
                activityRule.activity.reset()
                onView(withId(R.id.timePicker)).perform(NumberPickerSetter(1))
                onView(withId(R.id.btAdd)).perform(click())
                onView(withId(R.id.tvTime)).check(matches(withText("0h15m")))

                //picker is reset so nothing should happen
                onView(withId(R.id.btAdd)).perform(click())
                onView(withId(R.id.tvTime)).check(matches(withText("0h30m")))


                onView(withId(R.id.timePicker)).perform(NumberPickerSetter(3))
                onView(withId(R.id.btAdd)).perform(click())
                onView(withId(R.id.tvTime)).check(matches(withText("1h15m")))
            }
        }
    }

    @Test
    fun subtractTime() {
        activityRule.activity.runOnUiThread {
            Runnable {
                activityRule.activity.reset()
                onView(withId(R.id.timePicker)).perform(NumberPickerSetter(2))
                onView(withId(R.id.btSubtract)).perform(click())
                onView(withId(R.id.tvTime)).check(matches(withText("-0h30m")))

                //picker is reset so nothing should happen
                onView(withId(R.id.btSubtract)).perform(click())
                onView(withId(R.id.tvTime)).check(matches(withText("-0h30m")))


                onView(withId(R.id.timePicker)).perform(NumberPickerSetter(3))
                onView(withId(R.id.btSubtract)).perform(click())
                onView(withId(R.id.tvTime)).check(matches(withText("-1h15m")))
            }
        }
    }

    @Test
    fun combineAddAndSubtractTime() {
        activityRule.activity.runOnUiThread {
            Runnable {
                activityRule.activity.reset()
                onView(withId(R.id.timePicker)).perform(NumberPickerSetter(2))
                onView(withId(R.id.btSubtract)).perform(click())
                onView(withId(R.id.tvTime)).check(matches(withText("-0h30m")))


                onView(withId(R.id.timePicker)).perform(NumberPickerSetter(3))
                onView(withId(R.id.btAdd)).perform(click())
                onView(withId(R.id.tvTime)).check(matches(withText("0h15m")))
            }
        }
    }

    class NumberPickerSetter(private val value: Int) : ViewAction {
        override fun getDescription(): String = "Set the value of a NumberPicker"

        override fun getConstraints(): Matcher<View> = ViewMatchers.isAssignableFrom(NumberPicker::class.java)

        override fun perform(uiController: UiController?, view: View?) {
            (view as NumberPicker).value = value
        }
    }
}