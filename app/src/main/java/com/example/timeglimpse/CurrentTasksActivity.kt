package com.example.timeglimpse

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.timeglimpse.R.*

class CurrentTasksActivity : AppCompatActivity() {

    // List to store tasks
    private val taskList = mutableListOf<Task>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TaskAdapter
    private var selectedItem = "0"

    companion object {
        const val ADD_TASK_REQUEST_CODE = 1
        const val EDIT_TASK_REQUEST_CODE = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_current_tasks)

        // Initialize RecyclerView and adapter
        recyclerView = findViewById(id.lv_taskList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = TaskAdapter(taskList) { task ->
            // Handle item click, show a toast with task date
            Toast.makeText(this, "Selected task", Toast.LENGTH_SHORT).show()
            selectedItem = task.description.toString()
        }
        recyclerView.adapter = adapter


        // Find buttons
        val btnAddTask: Button = findViewById(id.btnAddTask)
        val btnEditTask: Button = findViewById(id.btnEditTask)
        val btnNavigationOptions: Button = findViewById(id.btnNavigationOptions)

        // Set onClickListener for EditTask button
        btnEditTask.setOnClickListener {
            // Find the task with the selected description
            val selectedTask = taskList.find { it.description == selectedItem }

            // Check if a task with the selected description was found
            if (selectedTask != null) {
                // Create an intent to launch EditTaskActivity and pass the selected task
                val editIntent = Intent(this, EditTaskActivity::class.java)
                editIntent.putExtra("taskToEdit", selectedTask)
                startActivityForResult(editIntent, EDIT_TASK_REQUEST_CODE)
            } else {
                Toast.makeText(this, "Selected task not found", Toast.LENGTH_SHORT).show()
            }
        }


// Set onClickListener for AddTask button
        btnAddTask.setOnClickListener {
            startActivityForResult(Intent(this, TimesheetEntry::class.java), ADD_TASK_REQUEST_CODE)
        }
        // Set onClickListener for Navigation Options button
        btnNavigationOptions.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }
    }

    // Handle activity result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                ADD_TASK_REQUEST_CODE -> {
                    // Handle result from adding a new task
                    data?.let { handleAddTaskResult(it) }
                }

                EDIT_TASK_REQUEST_CODE -> {
                    // Handle result from editing an existing task
                    data?.let { handleEditTaskResult(it) }
                }
            }
        }
    }

    // Handle result from adding a new task
    private fun handleAddTaskResult(data: Intent?) {
        if (data != null && data.hasExtra("date") && data.hasExtra("startTime")
            && data.hasExtra("endTime") && data.hasExtra("description") && data.hasExtra("category")
        ) {
            // Extract data from Intent
            val date = data.getStringExtra("date")
            val startTime = data.getStringExtra("startTime")
            val endTime = data.getStringExtra("endTime")
            val description = data.getStringExtra("description")
            val category = data.getStringExtra("category")

            // Create a new Task object with the retrieved data
            val newTask = Task( date!!, startTime!!, endTime!!, description!!, category!!)

            // Add the new task to the taskList
            taskList.add(newTask)

            // Sort the taskList by date in ascending order
            taskList.sortBy { it.date }

            // Notify the adapter that a new item has been inserted
            adapter.notifyDataSetChanged()
        } else {
            Log.e(TAG, "One or more fields are missing in the Intent")
        }
    }

    // Handle result from editing an existing task
    private fun handleEditTaskResult(data: Intent?) {
        data?.let { receivedIntent ->
            val editedTask = receivedIntent.getParcelableExtra<Task>("editedTask")
            val editedPosition = receivedIntent.getIntExtra("editedPosition", RecyclerView.NO_POSITION)

            if (editedTask != null && editedPosition != RecyclerView.NO_POSITION) {
                // Check if the edited position is valid
                if (editedPosition >= 0 && editedPosition < taskList.size) {
                    // Update the task in the list
                    taskList[editedPosition] = editedTask

                    // Notify the adapter of the change
                    adapter.notifyItemChanged(editedPosition)
                } else {
                    Log.e(TAG, "Invalid edited position: $editedPosition")
                }
            } else {
                Log.e(TAG, "Edited task data or position is missing in the Intent")
            }
        } ?: run {
            Log.e(TAG, "No data received from the editing activity")
        }

    }
}



