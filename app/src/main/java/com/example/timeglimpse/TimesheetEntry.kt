package com.example.timeglimpse

import TimesheetAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TimesheetEntry : AppCompatActivity() {
    private val timesheetList = mutableListOf<String>() // Store entries as strings
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timesheet_entry)

        val saveButton: Button = findViewById(R.id.SaveButton)
        val RecurringButton: Button = findViewById(R.id.RecurringButton)
        // Set up RecyclerView
        recyclerView = findViewById(R.id.recyclerViewTimesheet)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = TimesheetAdapter(timesheetList)


        // Set OnClickListener for the Save button
        saveButton.setOnClickListener()
        {
            saveTimesheetEntry()
            startActivity(Intent(this, CurrentTasksActivity::class.java))
        }
// Set OnClickListener for the recurring button
    RecurringButton.setOnClickListener()
    {
    startActivity(Intent(this, RecurringEntryActivity::class.java))
    }
}

    // Called when the user clicks the "Save" button
    private fun saveTimesheetEntry() {
        val date = findViewById<EditText>(R.id.txtDate).text.toString()
        val startTime = findViewById<EditText>(R.id.txtStartTime).text.toString()
        val endTime = findViewById<EditText>(R.id.txtEndTime).text.toString()
        val description = findViewById<EditText>(R.id.txtDescription).text.toString()
        val Category = findViewById<EditText>(R.id.txtTimeSheetCategory).text.toString()
        // Get other input fields (start time, end time, description, category)

        // Example: Format the entry and add it to the list
        val entry = "$date | Start Time: $startTime, End: $endTime, Description: $description Category: $Category"
        timesheetList.add(entry)

        // Notify the adapter that data has changed
        recyclerView.adapter?.notifyDataSetChanged()
    }
}
