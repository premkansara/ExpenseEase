package com.example.expenseease.models

import Expense
import Income
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.expenseease.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.random.Random

class ChartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)

        val pieChart = findViewById<PieChart>(R.id.pieChart)

        // Load saved expenses from SharedPreferences
        val expenses = loadExpenses()

        val materialColors = ColorTemplate.MATERIAL_COLORS

        // Prepare category-color mapping using Material Design colors
        val categoryColors = mapOf(
            "Food" to materialColors[0],          // First Material color for Food
            "Transport" to materialColors[1],     // Second Material color for Transport
            "Groceries" to materialColors[2],     // Third Material color for Groceries
            "Entertainment" to materialColors[3], // Fourth Material color for Entertainment
        )

        // Populate PieChart (Expenses by Category)
        val pieEntries = ArrayList<PieEntry>()
        val pieColors = ArrayList<Int>()

        val categoryTotals = expenses.groupBy { it.category }.mapValues { entry ->
            entry.value.sumOf { expense -> expense.amount }
        }

        categoryTotals.forEach { (category, total) ->
            pieEntries.add(PieEntry(total.toFloat(), category))

            // Use color from the map, or default to a random color
            val color = categoryColors[category] ?: ColorTemplate.COLORFUL_COLORS.random()
            pieColors.add(color)
        }

        val pieDataSet = PieDataSet(pieEntries, "Expenses by Category")
        pieDataSet.setColors(pieColors) // Apply colors to PieDataSet
        val pieData = PieData(pieDataSet)
        pieChart.data = pieData
        pieChart.invalidate() // Refresh chart


    }

    fun getRandomColor(): Int {
        val random = Random
        return Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256))
    }

    private fun loadExpenses(): List<Expense> {
        val sharedPreferences = getSharedPreferences("expenseEase", Context.MODE_PRIVATE)
        val gson = Gson()
        val expensesJson = sharedPreferences.getString("expenses", "[]")
        val expenseListType = object : TypeToken<ArrayList<Expense>>() {}.type
        return gson.fromJson(expensesJson, expenseListType)
    }
}