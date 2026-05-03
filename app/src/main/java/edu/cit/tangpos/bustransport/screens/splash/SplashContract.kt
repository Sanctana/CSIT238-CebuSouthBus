package edu.cit.tangpos.bustransport.screens.splash

class SplashContract {
    interface View {
        fun navigateToAuthChoice();
    }


    interface Presenter {
        fun delayNavigate(delayMillis: Long);
    }
}