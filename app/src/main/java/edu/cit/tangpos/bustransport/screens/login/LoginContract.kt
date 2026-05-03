package edu.cit.tangpos.bustransport.screens.login

import android.content.Context

class LoginContract {
    interface View {
        fun navigateToHome()
        fun getContext() : Context;

        fun getEmail(): String;
        fun getPassword(): String;

        fun showInvalidCredentials();
    }

    interface Presenter {
        fun checkIfUserExists();
        fun login()
    }

    interface Model {
        fun userExists() : Boolean;
        fun login(email: String, password: String) : Boolean
        fun saveId(userId: String);
    }
}