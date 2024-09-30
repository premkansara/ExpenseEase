package com.example.expenseease.models

import Income
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.expenseease.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AddIncomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_income)

        // Get references to the UI elements
        val incomeAmountInput: EditText = findViewById(R.id.incomeAmountInput)
        val incomeSourceInput: EditText = findViewById(R.id.incomeSourceInput)

        // Submit button logic
        val submitIncomeButton: Button = findViewById(R.id.submitIncomeButton)
        submitIncomeButton.setOnClickListener {
            val incomeAmountText = incomeAmountInput.text.toString()
            val incomeSource = incomeSourceInput.text.toString()

            // Validate the income input
            if (incomeAmountText.isEmpty()) {
                Toast.makeText(this, "Please enter a valid income amount", Toast.LENGTH_SHORT).show()
            } else {
                val amount = incomeAmountText.toDouble()
                val newIncome = Income(0, amount, incomeSource, null)  // Create Income object

                // Save the income to SharedPreferences
                saveIncome(newIncome)

                // Notify the user
                Toast.makeText(this, "Income Added: Amount - $amount, Source - $incomeSource", Toast.LENGTH_LONG).show()

                // After submission, you can clear the form or navigate back
                finish()
            }
        }
    }

    private fun saveIncome(income: Income) {
        val sharedPreferences = getSharedPreferences("expenseEase", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Get the current list of incomes
        val gson = Gson()
        val incomesJson = sharedPreferences.getString("incomes", "[]")
        val incomeListType = object : TypeToken<ArrayList<Income>>() {}.type
        val incomeList: ArrayList<Income> = gson.fromJson(incomesJson, incomeListType)

        // Add the new income
        incomeList.add(income)

        // Save the updated income list back to SharedPreferences
        val updatedIncomesJson = gson.toJson(incomeList)
        editor.putString("incomes", updatedIncomesJson)
        editor.apply()
    }
}