package edu.cit.tangpos.bustransport.screens.account.account

import android.content.Context

class AccountContract {
    data class UserData(val email: String, val fullName: String)

    interface View {
        fun goToLogin()
        fun getContext() : Context
        fun goToProfileView()
        fun goToAboutUs()
        fun goToBusCarriers()
        fun displayUserData(userData: UserData)
    }

    interface Presenter {
        fun checkUserExists() : Boolean
        fun getUserId() : String?
        fun loadUserData()
        fun logout()
    }

    interface Model {
        fun getUserId() : String?
        fun getUserData(userId: String): UserData?
        fun clearUserId()
    }
}
