package com.example.expenseease

import Income
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Created by Prem on 10/08/2024
 */
class IncomeLoggingTest {

    private lateinit var incomes: MutableList<Income>

    @Before
    fun setUp() {
        incomes = mutableListOf()
    }

    @Test
    fun testAddIncome() {
        val income = Income(1, 5000.0, "Salary", "Monthly salary")
        incomes.add(income)

        assertEquals(1, incomes.size)
        assertEquals(5000.0, incomes[0].amount, 0.001)
        assertEquals("Salary", incomes[0].source)
    }
}
