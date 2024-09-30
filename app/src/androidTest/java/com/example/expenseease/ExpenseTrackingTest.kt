package com.example.expenseease

import Expense
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ExpenseTrackingTest {

    private lateinit var expenses: MutableList<Expense>

    @Before
    fun setUp() {
        expenses = mutableListOf()
    }

    @Test
    fun testAddExpense() {
        val expense = Expense(1, 50.0, "Food", "Lunch", "Work-related")
        expenses.add(expense)

        assertEquals(1, expenses.size)
        assertEquals(50.0, expenses[0].amount, 0.001)
        assertEquals("Food", expenses[0].category)
    }

    @Test
    fun testEditExpense() {
        val expense = Expense(1, 50.0, "Food", "Lunch", "Work-related")
        expenses.add(expense)

        // Edit the expense
        expenses[0].amount = 60.0
        expenses[0].category = "Groceries"

        assertEquals(60.0, expenses[0].amount, 0.001)
        assertEquals("Groceries", expenses[0].category)
    }

    @Test
    fun testDeleteExpense() {
        val expense = Expense(1, 50.0, "Food", "Lunch", "Work-related")
        expenses.add(expense)

        expenses.removeAt(0)

        assertEquals(0, expenses.size)
    }
}
