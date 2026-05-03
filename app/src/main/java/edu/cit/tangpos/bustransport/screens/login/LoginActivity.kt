package edu.cit.tangpos.bustransport.screens.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import edu.cit.tangpos.bustransport.R
import edu.cit.tangpos.bustransport.screens.home.HomeActivity
import edu.cit.tangpos.bustransport.utilities.getTextValue
import edu.cit.tangpos.bustransport.utilities.showShortToast

class LoginActivity : AppCompatActivity(), LoginContract.View {
    private var presenter : LoginContract.Presenter = LoginPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        presenter.checkIfUserExists()

        findViewById<AppCompatButton>(R.id.btnSignIn).setOnClickListener {
            presenter.login()
        }
    }

    override fun navigateToHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    override fun getContext(): Context = this

    override fun getEmail(): String = getTextValue(R.id.etEmail)

    override fun getPassword(): String = getTextValue(R.id.etPassword)

    override fun showInvalidCredentials() {
        showShortToast("Invalid email or password")
    }
}
