package com.example.timeglimpse

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CurrentTasksActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_tasks)

        val btnEditTask: Button = findViewById(R.id.btnEditTask)
        val btnAddTask: Button = findViewById(R.id.btnAddTask)

//edit task
        btnEditTask.setOnClickListener()
        {
            startActivity(Intent(this, TimesheetEntry::class.java))
        }
//add task
        btnAddTask.setOnClickListener()
    {
        startActivity(Intent(this, TimesheetEntry::class.java))
    }
}

        //need to get this working
        /*enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
    }
