package edu.cit.tangpos.bustransport.screens.registration

import android.content.Context

class RegistrationContract {
    interface View {
        fun getFirstName(): String
        fun getMiddleName(): String
        fun getLastName(): String
        fun getEmail(): String
        fun getPassword(): String
        fun getConfirmPassword(): String
        fun showMessage(message: String)
        fun navigateToLogin()
        fun getContext(): Context
    }

    interface Presenter {
        fun register()
    }

    interface Model {
        fun registerUser(
            firstName: String,
            middleName: String,
            lastName: String,
            email: String,
            password: String
        ): Long
    }
}
