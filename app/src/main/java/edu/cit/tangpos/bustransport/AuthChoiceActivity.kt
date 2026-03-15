package edu.cit.tangpos.bustransport

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class AuthChoiceActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authchoice)

        findViewById<Button>(R.id.btnSignIn).setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }
}