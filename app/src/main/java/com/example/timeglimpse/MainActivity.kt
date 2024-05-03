package com.example.timeglimpse

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var btnLogin: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize SharedPreferences for storing user credentials
        sharedPreferences = getSharedPreferences("USER_CREDENTIALS", Context.MODE_PRIVATE)

        // Initialize UI elements
        usernameEditText = findViewById(R.id.txtEnterUsername)
        passwordEditText = findViewById(R.id.txtPassword)
        btnLogin = findViewById(R.id.btnLogin)

        // Set OnClickListener for Sign in button to navigate to CreateAccountActivity
        findViewById<Button>(R.id.btnSignIn).setOnClickListener {
            startActivity(Intent(this, CreateAccountActivity::class.java))
        }

        // Set OnClickListener for Login button to initiate login process
        btnLogin.setOnClickListener {
            loginUser()
        }
    }

    // Function to handle login logic
    private fun loginUser() {
        // Retrieve entered username and password from EditText fields on the main page
        val enteredUsername = usernameEditText.text.toString()
        val enteredPassword = passwordEditText.text.toString()

        // Check if either username or password is empty
        if (enteredUsername.isEmpty() || enteredPassword.isEmpty()) {
            //displays a message if the are empty
            Toast.makeText(this, "Username and password cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        // Retrieve saved username and password from SharedPreferences
        val savedUsername = sharedPreferences.getString("USERNAME", "")
        val savedPassword = sharedPreferences.getString("PASSWORD", "")

        // Check if entered username and password match saved Username and Password
        if (enteredUsername == savedUsername && enteredPassword == savedPassword) {
            // Login successful, show toast message and proceed to CurrentTasksActivity
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MenuActivity::class.java))
        } else {
            // Invalid credentials, show toast message
            Toast.makeText(this, "Invalid username or password. Please make sure that you have created an account" +
                        " or check if you entered your username and password correctly", Toast.LENGTH_SHORT).show()
        }
    }
}
