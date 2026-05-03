package edu.cit.tangpos.bustransport.screens.account.profileEdit

import edu.cit.tangpos.bustransport.Utility

class ProfileEditPresenter(private var view: ProfileEditContract.View) : ProfileEditContract.Presenter {
    private var model : ProfileEditContract.Model = ProfileEditModel(view.getContext())

    override fun loadUserData() {
        val userId = model.getUserId() ?: return
        val userData = model.getUserData(userId)
        if (userData != null) {
            view.displayUserData(userData)
        }
    }

    override fun checkUserExists(): Boolean {
        if (model.getUserId() == null){
            view.goToLogin();
            return false
        }

        return true
    }

    override fun updateProfile() {
        val userId = model.getUserId()
        if (userId == null) {
            view.goToLogin()
            return
        }

        val email = view.getEmail()
        val currentPw = view.getCurrentPassword()
        val confirmPassword = view.getConfirmPassword()
        val newPassword = view.getNewPassword()

        if (email.isEmpty() && currentPw.isEmpty()) {
            view.showToast("No changes to save")
            return
        }


        var newHashedPw: String? = null

        println("HAHAHAHAHAHAHAH")

        if (currentPw.isNotEmpty()) {
            println(currentPw)
            val storedPw = model.getStoredPassword(userId)
            if (storedPw == null) {
                view.goToLogin()
                return
            }

            if (Utility.hashPassword(currentPw) != storedPw) {
                view.showToast("Incorrect current password")
                return
            }

            if (newPassword.isNotEmpty()) {
                if (newPassword != confirmPassword) {
                    view.showToast("Passwords do not match")
                    return
                }
                newHashedPw = Utility.hashPassword(newPassword)
            }
        }

        val success = model.updateProfile(userId, email.ifEmpty { null }, newHashedPw)
        if (success) {
            view.showToast("Profile updated successfully")
            loadUserData()
        } else {
            view.showToast("Update failed")
        }
    }
}
