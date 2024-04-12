package com.example.timeglimpse


import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


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