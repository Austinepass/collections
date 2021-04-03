package com.example.task6

import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.After
import org.junit.Before

import org.junit.Rule
import org.junit.Test

class UITest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        launch(MainActivity::class.java)
        Intents.init()
    }
    @After
    fun tearDown() {
        Intents.release()
    }
    @Test
    fun test_isActivityInView() {
        Espresso.onView(withId(R.id.main)).check(matches(isDisplayed()))
    }

    @Test
    fun test_visibility_of_views() {
        Espresso.onView(withId(R.id.main))
            .check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.bottomNavigationView))
            .check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.contact_list_recycler))
            .check(matches(isDisplayed()))

    }

    @Test
    fun test_BottomNav_opens_PhoneContactsFragment() {
        Espresso.onView(withId(R.id.phone)).perform(click())
        Espresso.onView(withId(R.id.phone_list)).check(matches(isDisplayed()))
    }

}