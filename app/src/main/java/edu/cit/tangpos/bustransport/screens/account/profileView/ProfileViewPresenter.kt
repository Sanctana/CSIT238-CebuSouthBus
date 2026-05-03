package edu.cit.tangpos.bustransport.screens.account.profileView

class ProfileViewPresenter(private var view : ProfileViewContract.View) : ProfileViewContract.Presenter {
    private val model : ProfileViewContract.Model = ProfileViewModel(view.getContext())

    override fun checkUserExists(): Boolean {
        if (model.getUserId() == null){
            view.goToLogin()
            return false
        }

        return true
    }

    override fun displayUserdata() {
        val userId = model.getUserId()
        if (userId == null){
            view.goToLogin()
            return
        }

        val userData = model.getUserData(userId) ?: return

        view.setEmail(userData.email)
        view.setUsername(userData.fullName)
        view.setFullName(userData.fullName)
    }
}