package edu.cit.tangpos.bustransport

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.commit
import edu.cit.tangpos.bustransport.fragments.AccountFragment


// a testing file
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        val email = findViewById<EditText>(R.id.etEmail)
        val buttonLogin = findViewById<AppCompatButton>(R.id.btnSignIn)

        buttonLogin.setOnClickListener {
            val emailValue = email.text.toString()
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("email", emailValue)
            startActivity(intent)
        }
    }
}