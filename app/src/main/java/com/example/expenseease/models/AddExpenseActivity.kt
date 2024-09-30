package com.example.expenseease.models

import Expense
import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.expenseease.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AddExpenseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        val amountInput: EditText = findViewById(R.id.amountInput)
        val categorySpinner: Spinner = findViewById(R.id.categorySpinner)
        val tagSpinner: Spinner = findViewById(R.id.tagSpinner)

        // Load default and custom categories
        val categories = loadCustomItems("customCategories", listOf("Food", "Transport", "Groceries", "Entertainment"))
        val tags = loadCustomItems("customTags", listOf("Work", "Personal", "Family"))

        // Set up category spinner
        val categoryAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = categoryAdapter

        // Set up tag spinner
        val tagAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tags)
        tagAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        tagSpinner.adapter = tagAdapter

        // Submit expense logic
        val submitButton: Button = findViewById(R.id.submitExpenseButton)
        submitButton.setOnClickListener {
            val amountText = amountInput.text.toString()
            val selectedCategory = categorySpinner.selectedItem.toString()
            val selectedTag = tagSpinner.selectedItem.toString()

            if (amountText.isNotEmpty()) {
                val amount = amountText.toDouble()
                val newExpense = Expense(0, amount, selectedCategory, selectedTag, null)
                saveExpense(newExpense)
                Toast.makeText(this, "Expense added: $amount", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this, "Please enter a valid amount", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadCustomItems(key: String, defaultItems: List<String>): List<String> {
        val sharedPreferences = getSharedPreferences("expenseEase", Context.MODE_PRIVATE)
        val gson = Gson()
        val itemsJson = sharedPreferences.getString(key, "[]")
        val itemListType = object : TypeToken<ArrayList<String>>() {}.type
        val customItems: ArrayList<String> = gson.fromJson(itemsJson, itemListType)
        return defaultItems + customItems
    }

    private fun saveExpense(expense: Expense) {
        val sharedPreferences = getSharedPreferences("expenseEase", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val gson = Gson()
        val expensesJson = sharedPreferences.getString("expenses", "[]")
        val expenseListType = object : TypeToken<ArrayList<Expense>>() {}.type
        val expenseList: ArrayList<Expense> = gson.fromJson(expensesJson, expenseListType)

        expenseList.add(expense)
        val updatedExpensesJson = gson.toJson(expenseList)
        editor.putString("expenses", updatedExpensesJson)
        editor.apply()
    }
}