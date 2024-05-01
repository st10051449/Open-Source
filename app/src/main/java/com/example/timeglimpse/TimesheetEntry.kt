package com.example.timeglimpse

import android.app.Activity
import android.app.TimePickerDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class TimesheetEntry : AppCompatActivity() {

    private lateinit var btnDate: Button
    private lateinit var btnStartTime: Button
    private lateinit var btnEndTime: Button
    private lateinit var txtDescription: EditText
    private lateinit var txtCategory: EditText
    private lateinit var btnSaveTask: Button
    private lateinit var calendar: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timesheet_entry)

        // Initialize views
        btnDate = findViewById(R.id.btnDate)
        btnStartTime = findViewById(R.id.btnStartTime)
        btnEndTime = findViewById(R.id.btnEndTime)
        txtDescription = findViewById(R.id.txtDescription)
        txtCategory = findViewById(R.id.txtCategory)
        btnSaveTask = findViewById(R.id.btnSaveTask)
        // Initialize calendar instance
        calendar = Calendar.getInstance()

        val receivedTask = intent.getParcelableExtra<Task>("task")
        if (receivedTask != null) {
            // Populate fields with receivedTask details
            btnDate.text = receivedTask.date
            btnStartTime.text = receivedTask.startTime
            btnEndTime.text = receivedTask.endTime
            txtDescription.setText(receivedTask.description)
            txtCategory.setText(receivedTask.category)
        }

        // Set onClickListener for date button
        btnDate.setOnClickListener {
            showDatePicker()
        }

        // Set onClickListener for start time button
        btnStartTime.setOnClickListener {
            showTimePicker(btnStartTime)
        }

        // Set onClickListener for end time button
        btnEndTime.setOnClickListener {
            showTimePicker(btnEndTime)
        }

        // Set onClickListener for save button
        btnSaveTask.setOnClickListener {
            // Get data from views
            val date = btnDate.text.toString()
            val startTime = btnStartTime.text.toString()
            val endTime = btnEndTime.text.toString()
            val description = txtDescription.text.toString()
            val category = txtCategory.text.toString()


            // Check if start time is greater than end time
            if (endTimeGreaterThanStart(startTime, endTime)) {
                // Display error message
                Toast.makeText(
                    this,
                    "Start time cannot be greater than end time",
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                // Pass data back to CurrentTasksActivity
                val intent = Intent()
                intent.putExtra("date", date)
                intent.putExtra("startTime", startTime)
                intent.putExtra("endTime", endTime)
                intent.putExtra("description", description)
                intent.putExtra("category", category)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }



//formats the date into a calender
    private fun showDatePicker() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Update the button text with the selected date
                btnDate.text = "$year-${monthOfYear + 1}-$dayOfMonth"
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }
        //formats the time into a time picker
        private fun showTimePicker(button: Button) {
            // Get current time
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            // Create a TimePickerDialog
            val timePickerDialog = TimePickerDialog(
                this,
                { view, hourOfDay, minute ->
                    // Update the button text with the selected time
                    val selectedTime =
                        String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute)
                    button.text = selectedTime
                },
                hour,
                minute,
                true
            )
            // Show the TimePickerDialog
            timePickerDialog.show()
        }

        //checks that the start time is not greater than the end time
        private fun endTimeGreaterThanStart(startTime: String, endTime: String): Boolean {
            val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
            val startDateTime = sdf.parse(startTime)
            val endDateTime = sdf.parse(endTime)
            return startDateTime.after(endDateTime)
        }
    }





