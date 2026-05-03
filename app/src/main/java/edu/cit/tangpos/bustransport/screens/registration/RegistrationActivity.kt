package edu.cit.tangpos.bustransport.screens.registration

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import edu.cit.tangpos.bustransport.screens.login.LoginActivity
import edu.cit.tangpos.bustransport.R
import edu.cit.tangpos.bustransport.utilities.getTextValue
import edu.cit.tangpos.bustransport.utilities.showShortToast

class RegistrationActivity : AppCompatActivity(), RegistrationContract.View {
    private lateinit var presenter: RegistrationContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        presenter = RegistrationPresenter(this)

        findViewById<Button>(R.id.btnCreateAccount).setOnClickListener {
            presenter.register()
        }
    }

    override fun getFirstName(): String = getTextValue(R.id.etFirstName)
    override fun getMiddleName(): String = getTextValue(R.id.etMiddleName)
    override fun getLastName(): String = getTextValue(R.id.etLastName)
    override fun getEmail(): String = getTextValue(R.id.etEmail)
    override fun getPassword(): String = getTextValue(R.id.etPassword)
    override fun getConfirmPassword(): String = getTextValue(R.id.etConfirmPassword)

    override fun showMessage(message: String) {
        showShortToast(message)
    }

    override fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun getContext(): Context = this
}
