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

    private val taskList = mutableListOf<Task>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TaskAdapter

    companion object {
        const val ADD_TASK_REQUEST_CODE = 1
        const val EDIT_TASK_REQUEST_CODE = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_current_tasks)

        val btnAddTask: Button = findViewById(id.btnAddTask)
        val btnEditTask: Button = findViewById(id.btnEditTask)

        recyclerView = findViewById(id.lv_taskList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = TaskAdapter(taskList) { task ->
            // Handle item click, open activity or dialog to edit task details
            Toast.makeText(this, "Clicked: ${task.date}", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = adapter

        // Set onClickListener for EditTask button
        btnEditTask.setOnClickListener {
            // Get the selected task from the adapter
            val selectedTask = adapter.getSelectedTask()

            if (selectedTask != null) {
                // Ensure that selectedTask is an instance of Task
                if (selectedTask is Task) {
                    // Create an intent to launch EditTaskActivity and pass the selected task
                    val editIntent = Intent(this, TimesheetEntry::class.java)
                    editIntent.putExtra("task", selectedTask)
                    startActivityForResult(editIntent, 2)
                } else {
                    // Handle the case where selectedTask is not an instance of Task
                    Toast.makeText(this, "Selected task is not valid", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Handle the case where no task is selected
                Toast.makeText(this, "No task selected", Toast.LENGTH_SHORT).show()
            }
        }

        // Set onClickListener for AddTask button
        btnAddTask.setOnClickListener {
            startActivityForResult(Intent(this, TimesheetEntry::class.java), 1)
        }
    }

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

    //handles what is returned to the recycler view when the add button is pressed
    private fun handleAddTaskResult(data: Intent?) {
        if (data != null && data.hasExtra("date") && data.hasExtra("startTime")
            && data.hasExtra("endTime") && data.hasExtra("description") && data.hasExtra("category")
        ) {
            val date = data.getStringExtra("date")
            val startTime = data.getStringExtra("startTime")
            val endTime = data.getStringExtra("endTime")
            val description = data.getStringExtra("description")
            val category = data.getStringExtra("category")

            // Create a new Task object with the retrieved data
            val newTask = Task(date!!, startTime!!, endTime!!, description!!, category!!)

            // Add the new task to the taskList
            taskList.add(newTask)

            // Notify the adapter that a new item has been inserted
            adapter.notifyItemInserted(taskList.size - 1)

            // Optionally, you can save the new task to your data storage mechanism (e.g., database)
        } else {
            Log.e(TAG, "One or more fields are missing in the Intent")
        }
    }
    private fun handleEditTaskResult(data: Intent?) {
        data?.let { editedIntent ->
            val editedTask = editedIntent.getParcelableExtra<Task>("editedTask")
            val editedPosition = editedIntent.getIntExtra("editedPosition", RecyclerView.NO_POSITION)

            if (editedTask != null && editedPosition != RecyclerView.NO_POSITION) {
                // Update the task in the list
                taskList[editedPosition] = editedTask

                // Notify the adapter that the item has been changed
                adapter.notifyItemChanged(editedPosition)
            } else {
                Log.e(TAG, "Edited task data or position is missing in the Intent")
            }
        } ?: run {
            Log.e(TAG, "No data received from the editing activity")
        }
    }
}


