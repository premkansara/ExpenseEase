package com.example.expenseease

import android.content.Context
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.expenseease.models.SettingsActivity  // Ensure this is the correct path
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test

/**
 * Created by Ashwini on 10/08/2024
 */
class ChangePinTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(SettingsActivity::class.java)



    @Test
    fun testChangePin() {
        // Open the change PIN dialog (after setting a PIN, the button should be enabled)
        onView(withId(R.id.setPinButton)).perform(click())
        onView(withId(R.id.newPinInput)).perform(typeText("1234"))
        onView(withId(android.R.id.button1)).perform(click())
        onView(withId(R.id.changePinButton)).perform(click())

        // Type old PIN
        onView(withId(R.id.oldPinInput)).perform(typeText("1234"), closeSoftKeyboard())

        // Type new PIN
        onView(withId(R.id.newPinInput)).perform(typeText("5678"), closeSoftKeyboard())

        // Submit the change
        onView(withId(android.R.id.button1)).perform(click())  // Assuming default dialog button
        // Verify the toast is displayed for successful PIN change
        activityScenarioRule.scenario.onActivity { activity ->
            val sharedPreferences =
                activity.getSharedPreferences("expenseEase", Context.MODE_PRIVATE)
            val savedPin = sharedPreferences.getString("pin", null)

            // Assert that the new PIN is stored
            assertEquals("5678", savedPin)
        }
    }
}
