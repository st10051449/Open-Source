package com.example.timeglimpse

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val btnCurrentTask: Button = findViewById(R.id.btnCurrentTask)
        val btnAddEntry: Button = findViewById(R.id.btnAddEntry)
        val btnCategory: Button = findViewById(R.id.btnCategory)
        val btnDailyWorkHoursGoals: Button = findViewById(R.id.btnDailyWorkHoursGoals)

        // Set OnClickListener for the Cancel button
        btnCurrentTask.setOnClickListener {
            startActivity(Intent(this, CurrentTasksActivity::class.java))
        }
        btnAddEntry.setOnClickListener {
            startActivity(Intent(this, TimesheetEntry::class.java))
        }
        btnCategory.setOnClickListener {
            startActivity(Intent(this, CartegoriesActivity::class.java))
        }
        btnDailyWorkHoursGoals.setOnClickListener {
            startActivity(Intent(this, DailyWorkHourGoalsActivity::class.java))
        }
    }
}