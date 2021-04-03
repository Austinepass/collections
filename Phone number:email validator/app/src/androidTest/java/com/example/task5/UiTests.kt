package com.example.task5

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.core.AllOf.allOf
import org.hamcrest.core.IsInstanceOf.instanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class UiTests {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)
    @Test
    fun test_isActivityInView() {
        onView(withId(R.id.main)).check(matches(isDisplayed()))
    }

    @Test
    fun test_visibility_all_children_view() {
        onView(withId(R.id.name_editText)).check(matches(isDisplayed()))
        onView(withId(R.id.email_editText)).check(matches(isDisplayed()))
        onView(withId(R.id.phone_editText)).check(matches(isDisplayed()))
        onView(withId(R.id.spinner)).check(matches(isDisplayed()))
        onView(withId(R.id.button)).check(matches(isDisplayed()))
    }

    @Test
    fun test_button_opens_SecondActivity() {
        onView(withId(R.id.name_editText)).perform(typeText("Augustine"))
        onView(withId(R.id.phone_editText)).perform(typeText("09063366524"))
        onView(withId(R.id.email_editText)).perform(typeText("augustine@mymail.com"), closeSoftKeyboard())
        onView(withId(R.id.spinner)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("Male")))
            .perform(click())
        onView(withId(R.id.button)).perform(click())
        onView(withId(R.id.summary)).check(matches(isDisplayed()))
    }
}
