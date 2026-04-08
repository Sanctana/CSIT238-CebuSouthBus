package edu.cit.tangpos.bustransport

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import edu.cit.tangpos.bustransport.database.DBHelper

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val firstNameField = findViewById<EditText>(R.id.etFirstName)
        val middleNameField = findViewById<EditText>(R.id.etMiddleName)
        val lastNameField = findViewById<EditText>(R.id.etLastName)
        val emailField = findViewById<EditText>(R.id.etEmail)
        val passwordField = findViewById<EditText>(R.id.etPassword)
        val confirmPasswordField = findViewById<EditText>(R.id.etConfirmPassword)

        findViewById<Button>(R.id.btnCreateAccount).setOnClickListener {
            val firstName = firstNameField.text.toString().trim()
            val middleName = middleNameField.text.toString().trim()
            val lastName = lastNameField.text.toString().trim()
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()
            val confirmPassword = confirmPasswordField.text.toString().trim()

            if (firstName.isEmpty()) {
                Toast.makeText(this, "First name is required", Toast.LENGTH_SHORT).show()
            } else if (lastName.isEmpty()) {
                Toast.makeText(this, "Last name is required", Toast.LENGTH_SHORT).show()
            } else if (email.isEmpty()) {
                Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show()
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show()
            } else if (password.isEmpty()) {
                Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show()
            } else if (password.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
            } else if (confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please confirm your password", Toast.LENGTH_SHORT).show()
            } else if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else {
                val db = DBHelper(this).writableDatabase
                val userValues = ContentValues().apply {
                    put(DBHelper.USERS_FIRST_NAME, firstName)
                    put(DBHelper.USERS_MIDDLE_NAME, middleName)
                    put(DBHelper.USERS_LAST_NAME, lastName)
                    put(DBHelper.USERS_PASSWORD, Utility.hashPassword(password))
                    put(DBHelper.USERS_EMAIL, email)
                }

                db.insert(DBHelper.TABLE_USERS, null, userValues)
                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
}