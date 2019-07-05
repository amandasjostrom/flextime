package com.lahtinen.apps.flextime

import android.view.View
import android.widget.NumberPicker
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    /**
     * NOTE
     * You need to disable Animations and transitions on the target device.
     * https://stackoverflow.com/questions/44005338/android-espresso-performexception
     */

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun before(){
        reset()
    }

    @Test
    fun addTime() {
        reset()
        onView(withId(R.id.timePicker)).perform(NumberPickerSetter("04:30"))

        onView(withId(R.id.btAdd)).perform(click())
        onView(withId(R.id.tvTime)).check(matches(withText("4h30m")))

        //picker is reset so nothing should happen
        onView(withId(R.id.btAdd)).perform(click())
        onView(withId(R.id.tvTime)).check(matches(withText("4h30m")))

        onView(withId(R.id.timePicker)).perform(NumberPickerSetter("02:15"))
        onView(withId(R.id.btAdd)).perform(click())
        onView(withId(R.id.tvTime)).check(matches(withText("6h45m")))
    }

    @Test
    fun subtractTime() {
        reset()

        onView(withId(R.id.timePicker)).perform(NumberPickerSetter("00:30"))
        onView(withId(R.id.btSubtract)).perform(click())
        onView(withId(R.id.tvTime)).check(matches(withText("-0h30m")))

        //picker is reset so nothing should happen
        onView(withId(R.id.btSubtract)).perform(click())
        onView(withId(R.id.tvTime)).check(matches(withText("-0h30m")))

        onView(withId(R.id.timePicker)).perform(NumberPickerSetter("00:45"))
        onView(withId(R.id.btSubtract)).perform(click())
        onView(withId(R.id.tvTime)).check(matches(withText("-1h15m")))
    }

    @Test
    fun combineAddAndSubtractTime() {
        reset()

        onView(withId(R.id.timePicker)).perform(NumberPickerSetter("00:30"))
        onView(withId(R.id.btSubtract)).perform(click())
        onView(withId(R.id.tvTime)).check(matches(withText("-0h30m")))

        onView(withId(R.id.timePicker)).perform(NumberPickerSetter("00:45"))
        onView(withId(R.id.btAdd)).perform(click())
        onView(withId(R.id.tvTime)).check(matches(withText("0h15m")))
    }

    @Test
    fun onResetClick_resetFlexTime() {
        onView(withId(R.id.timePicker)).perform(NumberPickerSetter("04:15"))
        onView(withId(R.id.btSubtract)).perform(click())

        openActionBarOverflowOrOptionsMenu(getInstrumentation().targetContext)
        Espresso.onView(allOf(withText(R.string.action_reset), isDisplayed())).perform(click())
        onView(withId(R.id.tvTime)).check(matches(withText("0h0m")))
    }

    private fun reset() {
        activityRule.activity.reset()
    }

    class NumberPickerSetter(private val time: String) : ViewAction {
        private val numbersInPicker: List<String> =  MainActivity.NUMBER_PICKER_VALUES.toList()

        private fun getPickerIndexForTime(time: String): Int = numbersInPicker.indexOf(time)

        override fun getDescription(): String = "Set the value of a NumberPicker"

        override fun getConstraints(): Matcher<View> = isAssignableFrom(NumberPicker::class.java)

        override fun perform(uiController: UiController?, view: View?) {
            (view as NumberPicker).value = getPickerIndexForTime(time)
        }
    }
}
