/*
    App:   WITSWARZONE
    Names: Emilio Cruz, William Siri
    Date: May 2023
 */

package edu.quinnipiac.ser210.witswarzone

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {
    @get:Rule
    val avtivityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    @Throws(Exception::class)
    fun enterUsername() {
        onView(withId(R.id.usernameInput)).perform(
            click(), replaceText("Billy")
        )
        onView(withId(R.id.button_go)).perform(click())
    }
}