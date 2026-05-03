package edu.cit.tangpos.bustransport.screens.authchoice

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import edu.cit.tangpos.bustransport.screens.login.LoginActivity
import edu.cit.tangpos.bustransport.R
import edu.cit.tangpos.bustransport.screens.registration.RegistrationActivity

class AuthChoiceActivity: AppCompatActivity(), AuthChoiceContract.View {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authchoice)

        findViewById<Button>(R.id.btnSignIn).setOnClickListener {
            navigateToLogin();
        }
        findViewById<Button>(R.id.btnSignUp).setOnClickListener {
            navigateToRegister()
        }
    }

    override fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun navigateToRegister() {
        startActivity(Intent(this, RegistrationActivity::class.java))
    }
}