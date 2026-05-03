package edu.cit.tangpos.bustransport.screens.account.profileEdit

import android.content.Context

class ProfileEditContract {
    data class UserData(val firstName: String, val middleName: String, val lastName: String, val email: String)

    interface View {
        fun getContext() : Context
        fun goToLogin()
        fun displayUserData(userData: UserData)
        fun showToast(message: String)
        fun getFullName() : String
        fun getEmail(): String
        fun getCurrentPassword() : String
        fun getNewPassword() : String
        fun getConfirmPassword() : String
    }

    interface Presenter {
        fun loadUserData()
        fun checkUserExists(): Boolean
        fun updateProfile()
    }

    interface Model {
        fun getUserId() : String?
        fun getUserData(userId: String): UserData?
        fun getStoredPassword(userId: String): String?
        fun updateProfile(userId: String, email: String?, newHashedPw: String?): Boolean
    }
}
