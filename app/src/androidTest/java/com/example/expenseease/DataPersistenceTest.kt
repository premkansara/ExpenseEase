package com.example.expenseease

import Expense
import android.content.Context
import android.net.wifi.WifiManager
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.expenseease.models.MainActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by Damini on 10/08/2024
 */
class DataPersistenceTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        // Turn off Wi-Fi before running the tests
        activityScenarioRule.scenario.onActivity { activity ->
            val wifiManager = activity.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            wifiManager.setWifiEnabled(false)
        }
    }

    @Test
    fun testDataPersistence() {
        // Simulate adding an expense
        onView(withId(R.id.addExpenseButton)).perform(click())
        onView(withId(R.id.amountInput)).perform(typeText("100"), closeSoftKeyboard())
        onView(withId(R.id.categorySpinner)).perform(click())
        onView(withText("Food")).perform(click())
        onView(withId(R.id.submitExpenseButton)).perform(click())

        // Verify that the expense is saved in SharedPreferences
        activityScenarioRule.scenario.onActivity { activity ->
            val sharedPreferences = activity.getSharedPreferences("expenseEase", Context.MODE_PRIVATE)
            val expensesJson = sharedPreferences.getString("expenses", "[]")
            val gson = Gson()
            val expenseListType = object : TypeToken<ArrayList<Expense>>() {}.type
            val expenses: ArrayList<Expense> = gson.fromJson(expensesJson, expenseListType)

            // Assert that the expenses list is not null and contains the added expense
            assertNotNull(expenses)
            assertNotNull(expenses.find { it.amount == 100.0 && it.category == "Food" })
        }
    }
}
