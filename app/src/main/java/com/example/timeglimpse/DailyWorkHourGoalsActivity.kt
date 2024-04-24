package com.example.timeglimpse

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener

private lateinit var editMinHours: EditText
private lateinit var editMaxHours: EditText
class DailyWorkHourGoalsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_work_hour_goals)


        editMinHours = findViewById(R.id.editMinHours)
        editMaxHours = findViewById(R.id.editMaxHours)

        val btnCancel: Button = findViewById(R.id.btnCancel)
        val btnSave: Button = findViewById(R.id.btnSave)

        // Set OnClickListener for the Cancel button
        btnCancel.setOnClickListener {
           startActivity(Intent(this, CurrentTasksActivity::class.java))
        }

        // Set OnClickListener for the Save button
        btnSave.setOnClickListener {
            if (validateInput()) {
                saveData()
               startActivity(Intent(this, CurrentTasksActivity::class.java))
            }
        }

        // Optional: Add text change listeners for real-time validation
        editMinHours.addTextChangedListener {
            validateInput()
        }

        editMaxHours.addTextChangedListener {
            validateInput()
        }
    }

    // Function to validate user input
    private fun validateInput(): Boolean {
        val minHours = editMinHours.text.toString().toIntOrNull()
        val maxHours = editMaxHours.text.toString().toIntOrNull()

        //clear error message when valid input
        editMinHours.error = null
        editMaxHours.error = null

        // Check if either field is empty
        if(minHours == null){
            editMinHours.error = "Min Hours cannot be empty"
            return false
        }
        if(maxHours==null){
            editMaxHours.error = "Max Hours cannot be empty"
            return false
        }

        // Check if min value is less than max value
        if (minHours >= maxHours) {
            editMinHours.error = "Min Hours must be less than Max Hours"
            editMaxHours.error = "Max Hours must be greater than Min Hours"
            return false
        }

        // Check if either value is 24 or greater
        if (minHours >= 24) {
            editMinHours.error = "Min Hours cannot be 24 or greater"
            return false
        }

        if (maxHours >= 24) {
            editMaxHours.error = "Max Hours cannot be 24 or greater"
            return false
        }
        return true
    }

    // Function to save data to SharedPreferences
    private fun saveData() {
        val sharedPreferences: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(this)
        val editor = sharedPreferences.edit()
        editor.putString("MinHours", editMinHours.text.toString())
        editor.putString("MaxHours", editMaxHours.text.toString())
        editor.apply()
    }
}
