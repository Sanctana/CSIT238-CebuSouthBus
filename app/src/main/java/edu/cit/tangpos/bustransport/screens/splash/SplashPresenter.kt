package edu.cit.tangpos.bustransport.screens.splash

import android.os.Handler
import android.os.Looper

class SplashPresenter(private var view: SplashContract.View) : SplashContract.Presenter {
    override fun delayNavigate(delayMillis: Long) {
        // Wait for 2 seconds then transition to LoginActivity
        Handler(Looper.getMainLooper()).postDelayed({
            view.navigateToAuthChoice()
        }, delayMillis)
    }
}