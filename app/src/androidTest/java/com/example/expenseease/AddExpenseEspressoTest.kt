package com.example.expenseease

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.expenseease.models.AddExpenseActivity
import org.junit.Rule
import org.junit.Test


class AddExpenseEspressoTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(AddExpenseActivity::class.java)

    @Test
    fun testAddExpense() {
        // Type amount
        onView(withId(R.id.amountInput)).perform(typeText("50"))

        // Select category (assuming you have a dropdown spinner for this)
        onView(withId(R.id.categorySpinner)).perform(click())
        onView(withText("Food")).perform(click())

        onView(withId(R.id.tagSpinner)).perform(click())
        onView(withText("Personal")).perform(click())

        // Click Submit
        onView(withId(R.id.submitExpenseButton)).perform(click())
    }
}
