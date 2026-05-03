package edu.cit.tangpos.bustransport.screens.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import edu.cit.tangpos.bustransport.screens.authchoice.AuthChoiceActivity
import edu.cit.tangpos.bustransport.R

class SplashActivity : AppCompatActivity(), SplashContract.View {
    private var splashPresenter: SplashPresenter = SplashPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        splashPresenter.delayNavigate(2000)
    }

    override fun navigateToAuthChoice() {
        startActivity(Intent(this, AuthChoiceActivity::class.java))
        finish()
    }
}
