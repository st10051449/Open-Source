package com.example.timeglimpse

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class TimeSpentBetweenDatesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_spent_between_dates)

        val btnNavigationOptions: Button = findViewById(R.id.btnNavigationOptions3)
        // Set onClickListener for Navigation Options button
        btnNavigationOptions.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }
    }
}