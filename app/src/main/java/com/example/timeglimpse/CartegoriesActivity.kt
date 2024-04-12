package com.example.timeglimpse

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CartegoriesActivity : AppCompatActivity() {
    private val categoryList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cartegories)

        val categoryNameEditText = findViewById<EditText>(R.id.etCategoryName)
        val addButton = findViewById<Button>(R.id.btnAddCategory)

        addButton.setOnClickListener {
            val categoryName = categoryNameEditText.text.toString().trim()
            if (categoryName.isNotEmpty()) {
                categoryList.add(categoryName)
                categoryNameEditText.text.clear()
            }
        }
    }
}