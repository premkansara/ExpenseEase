package com.example.expenseease.models


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class LauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("expenseEase", Context.MODE_PRIVATE)
        val storedPin = sharedPreferences.getString("pin", null)

        if (storedPin == null) {
            // Redirect to PIN setup if no PIN is found
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            // Redirect to PIN authentication if a PIN exists
            val intent = Intent(this, PinAuthenticationActivity::class.java)
            startActivity(intent)
        }

        finish() // Close LauncherActivity to prevent navigation back
    }
}
