package com.example.timeglimpse

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(private val taskList: List<Task>, private val onItemClick: (Task) -> Unit) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var selectedTaskPosition: Int = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.one_line_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.bind(task)

        holder.itemView.setOnClickListener {
            onItemClick(task)
        }
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    fun getSelectedPosition(): Task? {
        return if (selectedTaskPosition != RecyclerView.NO_POSITION) {
            taskList[selectedTaskPosition]
        } else {
            null
        }
    }



    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        //private val imageView: TextView = itemView.findViewById(R.id.iv_taskImage)
        private val dateTextView: TextView = itemView.findViewById(R.id.tv_Date)
        private val startTimeTextView: TextView = itemView.findViewById(R.id.tv_startTime)
        private val endTimeTextView: TextView = itemView.findViewById(R.id.tv_endTime)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.tv_Description)
        private val categoryTextView: TextView = itemView.findViewById(R.id.tv_Category)

        fun bind(task: Task) {

            //imageView.text = task.image
            dateTextView.text = task.date
            startTimeTextView.text = task.startTime
            endTimeTextView.text = task.endTime
            descriptionTextView.text = task.description
            categoryTextView.text = task.category
        }
    }
}

