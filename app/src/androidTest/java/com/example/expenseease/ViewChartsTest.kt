import android.content.Context
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.expenseease.R
import com.example.expenseease.models.MainActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Rule
import org.junit.Test


class ViewChartsTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testViewCharts() {
        // Interact with views in ChartActivity and assert that they are displayed as expected
        onView(withId(R.id.addExpenseButton)).perform(click())
        onView(withId(R.id.amountInput)).perform(typeText("100"), closeSoftKeyboard())
        onView(withId(R.id.categorySpinner)).perform(click())
        onView(withText("Food")).perform(click())

        onView(withId(R.id.tagSpinner)).perform(click())
        onView(withText("Work")).perform(click())

        // Click Submit
        onView(withId(R.id.noteInput)).perform(typeText("Lunch"), closeSoftKeyboard())
        onView(withId(R.id.submitExpenseButton)).perform(click())

        Thread.sleep(500)
        onView(withId(R.id.addExpenseButton)).perform(click())
        onView(withId(R.id.amountInput)).perform(typeText("750"), closeSoftKeyboard())
        onView(withId(R.id.categorySpinner)).perform(click())
        onView(withText("Entertainment")).perform(click())

        onView(withId(R.id.tagSpinner)).perform(click())
        onView(withText("Personal")).perform(click())

        // Click Submit
        onView(withId(R.id.noteInput)).perform(typeText("Gaming Console"), closeSoftKeyboard())
        onView(withId(R.id.submitExpenseButton)).perform(click())

        // Verify that the expense was added to SharedPreferences
        activityScenarioRule.scenario.onActivity { activity ->
            val sharedPreferences = activity.getSharedPreferences("expenseEase", Context.MODE_PRIVATE)
            val gson = Gson()
            val expensesJson = sharedPreferences.getString("expenses", "[]")
            val expenseListType = object : TypeToken<ArrayList<Expense>>() {}.type
            val expenseList: ArrayList<Expense> = gson.fromJson(expensesJson, expenseListType)

            // Verify that the expense with note "Lunch" exists
            val savedExpense = expenseList.find { it.note == "Lunch" }
            assertNotNull(savedExpense)
            assertEquals(100.0, savedExpense?.amount!!, 0.01)
            assertEquals("Food", savedExpense?.category)
            assertEquals("Work", savedExpense?.tag)
        }

        Thread.sleep(500)
        // Step 3: Add income
        onView(withId(R.id.addIncomeButton)).perform(click())
        onView(withId(R.id.incomeAmountInput)).perform(typeText("5000"), closeSoftKeyboard())
        onView(withId(R.id.incomeSourceInput)).perform(typeText("Salary"), closeSoftKeyboard())
        onView(withId(R.id.submitIncomeButton)).perform(click())

        // Verify that the income was added
        activityScenarioRule.scenario.onActivity { activity ->
            val gson = Gson()
            val sharedPreferences = activity.getSharedPreferences("expenseEase", Context.MODE_PRIVATE)
            val incomesJson = sharedPreferences.getString("incomes", "[]")
            val incomeListType = object : TypeToken<ArrayList<Income>>() {}.type
            val incomeList: ArrayList<Income> = gson.fromJson(incomesJson, incomeListType)

            // Verify that the income with source "Salary" exists
            val savedIncome = incomeList.find { it.source == "Salary" }
            assertNotNull(savedIncome)
            assertEquals(5000.0, savedIncome?.amount!!, 0.01)
        }

        Thread.sleep(500)

        // Step 4: View charts
        onView(withId(R.id.viewActivityButton)).perform(click())
        Thread.sleep(2000)
        onView(withId(R.id.pieChart)).check(matches(isDisplayed()))
    }
}