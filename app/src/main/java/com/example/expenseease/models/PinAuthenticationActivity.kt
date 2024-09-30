package com.example.expenseease.models

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.expenseease.R

class PinAuthenticationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin_authentication)

        val pinInput: EditText = findViewById(R.id.pinInput)
        val submitPinButton: Button = findViewById(R.id.submitPinButton)

        submitPinButton.setOnClickListener {
            val enteredPin = pinInput.text.toString()

            // Retrieve stored PIN from SharedPreferences
            val sharedPreferences = getSharedPreferences("expenseEase", Context.MODE_PRIVATE)
            val storedPin = sharedPreferences.getString("pin", null)

            if (enteredPin == storedPin) {
                // Correct PIN entered, allow access to MainActivity
                navigateToMainActivity()
            } else {
                // Incorrect PIN
                Toast.makeText(this, "Incorrect PIN", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Close PinAuthenticationActivity
    }
}
