package com.example.expenseease.models

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.expenseease.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Add Expense button click listener
        findViewById<Button>(R.id.addExpenseButton).setOnClickListener {
            startActivity(Intent(this, AddExpenseActivity::class.java))
        }

        // Add Income button click listener
        findViewById<Button>(R.id.addIncomeButton).setOnClickListener {
            startActivity(Intent(this, AddIncomeActivity::class.java))
        }

        // View Activity button click listener
        findViewById<Button>(R.id.viewActivityButton).setOnClickListener {
            startActivity(Intent(this, ChartActivity::class.java))
        }

        val settingsButton = findViewById<Button>(R.id.settingsButton)
        settingsButton.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }
}