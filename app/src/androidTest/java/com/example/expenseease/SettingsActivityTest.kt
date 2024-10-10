package com.example.expenseease

import android.content.Context
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.expenseease.models.SettingsActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Prem on 10/08/2024
 */
@RunWith(AndroidJUnit4::class)
class SettingsActivityTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(SettingsActivity::class.java)

    @Test
    fun testAddCategoryAndTag() {
        // Add a custom category
        onView(withId(R.id.customCategoryInput)).perform(typeText("Bills"), closeSoftKeyboard())
        onView(withId(R.id.addCategoryButton)).perform(click())

        // Add a custom tag
        onView(withId(R.id.customTagInput)).perform(typeText("Business"), closeSoftKeyboard())
        onView(withId(R.id.addTagButton)).perform(click())

        // Verify that categories and tags were saved to SharedPreferences
        activityScenarioRule.scenario.onActivity { activity ->
            val sharedPreferences = activity.getSharedPreferences("expenseEase", Context.MODE_PRIVATE)
            val gson = Gson()

            // Verify category
            val categoriesJson = sharedPreferences.getString("customCategories", "[]")
            val categoryListType = object : TypeToken<ArrayList<String>>() {}.type
            val categoryList: ArrayList<String> = gson.fromJson(categoriesJson, categoryListType)
            assertEquals("Bills", categoryList.last())

            // Verify tag
            val tagsJson = sharedPreferences.getString("customTags", "[]")
            val tagListType = object : TypeToken<ArrayList<String>>() {}.type
            val tagList: ArrayList<String> = gson.fromJson(tagsJson, tagListType)
            assertEquals("Business", tagList.last())
        }
    }

    @Test
    fun testClearAllData() {
        // Clear all data
        onView(withId(R.id.clearAllDataButton)).perform(click())
        onView(withId(android.R.id.button1)).perform(click())  // Click "Yes"

        // Verify that all data was cleared from SharedPreferences
        activityScenarioRule.scenario.onActivity { activity ->
            val sharedPreferences = activity.getSharedPreferences("expenseEase", Context.MODE_PRIVATE)
            val allPreferences = sharedPreferences.all
            assertEquals(0, allPreferences.size)
        }
    }
}
