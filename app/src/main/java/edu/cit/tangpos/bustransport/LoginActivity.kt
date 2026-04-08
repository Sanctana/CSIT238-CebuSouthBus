package edu.cit.tangpos.bustransport

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.edit
import edu.cit.tangpos.bustransport.database.DBHelper

// a testing file
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        val email = findViewById<EditText>(R.id.etEmail)
        val password = findViewById<EditText>(R.id.etPassword)

        findViewById<AppCompatButton>(R.id.btnSignIn).setOnClickListener {
            val db = DBHelper(this).readableDatabase

            db.rawQuery("SELECT * FROM ${DBHelper.TABLE_USERS} WHERE ${DBHelper.USERS_EMAIL} = ? AND ${DBHelper.USERS_PASSWORD} = ?", arrayOf(email.text.toString(),
                Utility.hashPassword(password.text.toString()))).use { cursor ->
                if (cursor.moveToFirst()) {
                    val sharedPreferences = getSharedPreferences("Bus Transport", MODE_PRIVATE)
                    sharedPreferences.edit {
                        putString("userId", cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.USERS_ID)))
                    }

                    startActivity(Intent(this, HomeActivity::class.java))
                } else {
                    Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}