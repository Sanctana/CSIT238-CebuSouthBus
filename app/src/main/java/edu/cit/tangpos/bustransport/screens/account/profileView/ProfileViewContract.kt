package edu.cit.tangpos.bustransport.screens.account.profileView

import android.content.Context

class ProfileViewContract {
    data class UserData(val email: String, val fullName: String)

    interface View {
        fun getContext() : Context
        fun goToLogin()
        fun setEmail(email: String)
        fun setFullName(fullName: String)
        fun setUsername(username: String)
        fun navigateProfileEdit()
    }

    interface Presenter {
        fun checkUserExists() : Boolean
        fun displayUserdata()
    }

    interface Model {
        fun getUserId() : String?
        fun getUserData(userId: String) : UserData?
    }
}