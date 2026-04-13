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

        /*
        val email = findViewById<EditText>(R.id.etEmail)
        val password = findViewById<EditText>(R.id.etPassword)
*/

//      app ray maka view data, Bus Transport name sa localstorage
        val sharedPreferences = getSharedPreferences(Utility.APP_SHARED_PREFERENCES, MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", null)
//      for naka log in daan, tan awns localstorage
        if (userId != null){
            startActivity(Intent(this, HomeActivity::class.java))
        }


        findViewById<AppCompatButton>(R.id.btnSignIn).setOnClickListener {
            val db = DBHelper(this).readableDatabase

            db.rawQuery("SELECT * FROM ${DBHelper.TABLE_USERS} WHERE ${DBHelper.USERS_EMAIL} = ? AND ${DBHelper.USERS_PASSWORD} = ?", arrayOf(findViewById<EditText>(R.id.etEmail).text.toString(),
                Utility.hashPassword(findViewById<EditText>(R.id.etPassword).text.toString()))).use { cursor ->
                if (cursor.moveToFirst()) {
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