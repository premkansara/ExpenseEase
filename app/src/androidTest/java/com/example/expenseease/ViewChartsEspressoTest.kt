import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.example.expenseease.R
import com.example.expenseease.models.ChartActivity
import org.junit.Rule
import org.junit.Test


class ViewChartsEspressoTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(ChartActivity::class.java)

    @Test
    fun testViewCharts() {
        // Interact with views in ChartActivity and assert that they are displayed as expected
        onView(withId(R.id.pieChart)).check(matches(isDisplayed()))

    }
}
