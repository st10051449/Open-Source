package com.example.timeglimpse

import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CreateAccountActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var createAccountButton: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        // Initialize UI elements
        emailEditText = findViewById(R.id.txtEnterEmail)
        usernameEditText = findViewById(R.id.txtUsername)
        passwordEditText = findViewById(R.id.txtPassword)
        confirmPasswordEditText = findViewById(R.id.txtConfirmPassword)
        createAccountButton = findViewById(R.id.btnCreateAccount)

        // Initialize SharedPreferences for storing user credentials
        sharedPreferences = getSharedPreferences("USER_CREDENTIALS", MODE_PRIVATE)

        // Set OnClickListener for Create Account button
        createAccountButton.setOnClickListener {
            // Retrieve values from EditText fields
            val email = emailEditText.text.toString()
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            // Check if any field is empty
            if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                //displays an error message
                Toast.makeText(this, "Email, Username, and password fields cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Check if password matches confirm password
            if (password != confirmPassword) {
                //display an error message
                Toast.makeText(this, "Confirm Password does not match your password or it is empty", Toast.LENGTH_SHORT).show()
            } else {
                // Save user credentials if validation passes
                sharedPreferences.edit().apply {
                    putString("USERNAME", username)
                    putString("PASSWORD", password)
                    apply()
                }
                //displays and notifies user that account is created
                Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show()
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }
}
