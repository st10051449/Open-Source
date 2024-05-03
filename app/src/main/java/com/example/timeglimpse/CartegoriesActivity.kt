package com.example.timeglimpse

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class CartegoriesActivity : AppCompatActivity() {
    private lateinit var categoriesAdapter: ArrayAdapter<String>
    private val categoriesList = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cartegories)

        categoriesAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, categoriesList)
        findViewById<ListView>(R.id.categoriesListView).adapter = categoriesAdapter

        val btnNavigationOptions: Button = findViewById(R.id.btnNavigationOptions)
        // Set onClickListener for Navigation Options button
        btnNavigationOptions.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }

        findViewById<Button>(R.id.addCategoryButton).setOnClickListener {
            val categoryName = findViewById<EditText>(R.id.categoryNameEditText).text.toString()

            if (categoryName.isNotEmpty()) {
                categoriesList.add(categoryName)
                categoriesAdapter.notifyDataSetChanged()
                findViewById<EditText>(R.id.categoryNameEditText).text.clear()
            }
        }

        val listView = findViewById<ListView>(R.id.categoriesListView)
        listView.setOnItemClickListener { parent, view, position, id ->
            val categoryName = categoriesList[position]

            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setTitle("Choose the actions you want to make with the selected category")
            alertDialogBuilder.setMessage("Enter a new name if you want to edit the category:")

            val inputEditText = EditText(this)
            alertDialogBuilder.setView(inputEditText)

            alertDialogBuilder.setNeutralButton("Edit") { dialog, _ ->
                val newCategoryName = inputEditText.text.toString()
                categoriesList[position] = newCategoryName // Update the category name
                categoriesAdapter.notifyDataSetChanged() // Notify the adapter of the change
                dialog.dismiss()
            }
            alertDialogBuilder.setNegativeButton("Delete") { dialog, _ ->
                categoriesList.removeAt(position)
                categoriesAdapter.notifyDataSetChanged()
                dialog.dismiss()
            }
            alertDialogBuilder.setPositiveButton("Tasks in category") { dialog, _ ->
                startActivity(Intent(this, TimeSpentBetweenDatesActivity::class.java))
                dialog.dismiss()
            }
            
            // alertDialogBuilder.setNeutralButton("Cancel") { dialog, _ ->
            //     dialog.dismiss()
            // }

            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
    }
}