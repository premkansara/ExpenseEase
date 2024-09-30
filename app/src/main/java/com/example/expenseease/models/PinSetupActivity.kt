package com.example.expenseease.models

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.expenseease.R

class PinSetupActivity  : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin_setup)

        val pinInput: EditText = findViewById(R.id.pinInput)
        val createPinButton: Button = findViewById(R.id.createPinButton)

        createPinButton.setOnClickListener {
            val enteredPin = pinInput.text.toString()

            if (enteredPin.length == 4) {
                // Save the entered PIN in SharedPreferences
                val sharedPreferences = getSharedPreferences("expenseEase", Context.MODE_PRIVATE)
                sharedPreferences.edit().putString("pin", enteredPin).apply()

                // Notify user and navigate to the main activity
                Toast.makeText(this, "PIN created successfully!", Toast.LENGTH_SHORT).show()
                navigateToMainActivity()
            } else {
                Toast.makeText(this, "Please enter a 4-digit PIN", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Close PinSetupActivity
    }
}
