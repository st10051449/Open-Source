package com.example.timeglimpse

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class EditTaskActivity : AppCompatActivity() {

    private lateinit var btnDate: Button
    private lateinit var btnStartTime: Button
    private lateinit var btnEndTime: Button
    private lateinit var txtDescription: EditText
    private lateinit var txtCategory: EditText
    private lateinit var btnSaveTask: Button
    private lateinit var calendar: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)


        // Initialize views
        btnDate = findViewById(R.id.btnEDate)
        btnStartTime = findViewById(R.id.btnEStartTime)
        btnEndTime = findViewById(R.id.btnEEndTime)
        txtDescription = findViewById(R.id.txtEDescription)
        txtCategory = findViewById(R.id.txtECategory)
        btnSaveTask = findViewById(R.id.btnESaveTask)
        calendar = Calendar.getInstance()

        // Retrieve Task data from Intent
        val receivedTask = intent.getParcelableExtra<Task>("taskToEdit")
        if (receivedTask != null) {
            // Populate fields with received Task details
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
                // Pass updated data back to CurrentTasksActivity

                val receivedIntent = Intent()
                receivedIntent.putExtra("editedTask", Task( date, startTime, endTime, description, category))
                receivedIntent.putExtra("editedPosition", intent.getIntExtra("position", RecyclerView.NO_POSITION))
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }

    private fun showDatePicker() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                // Update the button text with the selected date
                btnDate.text = String.format(Locale.getDefault(), "%d-%02d-%02d", year, monthOfYear + 1, dayOfMonth)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    private fun showTimePicker(button: Button) {
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                // Update the button text with the selected time
                button.text = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute)
            },
            hour,
            minute,
            true
        )

        timePickerDialog.show()
    }

    private fun endTimeGreaterThanStart(startTime: String, endTime: String): Boolean {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        val startDateTime = sdf.parse(startTime)
        val endDateTime = sdf.parse(endTime)
        return startDateTime.after(endDateTime)
    }
}





