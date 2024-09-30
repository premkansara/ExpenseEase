package com.example.expenseease.models

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.expenseease.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val customCategoryInput: EditText = findViewById(R.id.customCategoryInput)
        val addCategoryButton: Button = findViewById(R.id.addCategoryButton)
        val customTagInput: EditText = findViewById(R.id.customTagInput)
        val addTagButton: Button = findViewById(R.id.addTagButton)

        val changePinButton: Button = findViewById(R.id.changePinButton)
        val removePinButton: Button = findViewById(R.id.removePinButton)

        // Load PIN from SharedPreferences
        val sharedPreferences = getSharedPreferences("expenseEase", Context.MODE_PRIVATE)
        val storedPin = sharedPreferences.getString("pin", null)

        // Disable "Change PIN" button if there's no PIN
        if (storedPin == null) {
            changePinButton.isEnabled = false  // Disable the button
        } else {
            changePinButton.isEnabled = true  // Enable the button
        }

        // Existing logic for adding categories and tags
        addCategoryButton.setOnClickListener {
            val newCategory = customCategoryInput.text.toString()
            if (newCategory.isNotEmpty()) {
                saveCustomItem("customCategories", newCategory)
                Toast.makeText(this, "Category added: $newCategory", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please enter a category", Toast.LENGTH_SHORT).show()
            }
        }

        addTagButton.setOnClickListener {
            val newTag = customTagInput.text.toString()
            if (newTag.isNotEmpty()) {
                saveCustomItem("customTags", newTag)
                Toast.makeText(this, "Tag added: $newTag", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please enter a tag", Toast.LENGTH_SHORT).show()
            }
        }

        // Button to change PIN
        changePinButton.setOnClickListener {
            showChangePinDialog()
        }

        // Button to remove PIN
        removePinButton.setOnClickListener {
            showRemovePinDialog()
            // After removing the PIN, disable the "Change PIN" button
            changePinButton.isEnabled = false
        }
    }

    private fun saveCustomItem(key: String, item: String) {
        val sharedPreferences = getSharedPreferences("expenseEase", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val gson = Gson()
        val itemsJson = sharedPreferences.getString(key, "[]")
        val itemListType = object : TypeToken<ArrayList<String>>() {}.type
        val itemList: ArrayList<String> = gson.fromJson(itemsJson, itemListType)

        itemList.add(item)
        val updatedItemsJson = gson.toJson(itemList)
        editor.putString(key, updatedItemsJson)
        editor.apply()
    }

    // Function to handle PIN change
    private fun showChangePinDialog() {
        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.dialog_change_pin, null)
        builder.setView(dialogView)

        val oldPinInput = dialogView.findViewById<EditText>(R.id.oldPinInput)
        val newPinInput = dialogView.findViewById<EditText>(R.id.newPinInput)
        val sharedPreferences = getSharedPreferences("expenseEase", Context.MODE_PRIVATE)

        builder.setPositiveButton("Change") { _, _ ->
            val oldPin = oldPinInput.text.toString()
            val newPin = newPinInput.text.toString()
            val storedPin = sharedPreferences.getString("pin", null)

            if (storedPin == oldPin && newPin.length == 4) {
                sharedPreferences.edit().putString("pin", newPin).apply()
                Toast.makeText(this, "PIN changed successfully!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Old PIN incorrect or new PIN invalid", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("Cancel", null)
        builder.show()
    }

    // Function to handle PIN removal
    private fun showRemovePinDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to remove the PIN?")
        builder.setPositiveButton("Yes") { _, _ ->
            val sharedPreferences = getSharedPreferences("expenseEase", Context.MODE_PRIVATE)
            sharedPreferences.edit().remove("pin").apply()
            Toast.makeText(this, "PIN removed", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No", null)
        builder.show()
    }
}
