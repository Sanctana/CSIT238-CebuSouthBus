package edu.cit.tangpos.bustransport.screens.account.account

class AccountPresenter(private var view: AccountContract.View) : AccountContract.Presenter{
    private val model : AccountContract.Model = AccountModel(view.getContext())

    override fun checkUserExists() : Boolean {
        if (model.getUserId() == null){
            view.goToLogin()
            return false
        }

        return true
    }

    override fun getUserId(): String? = model.getUserId()

    override fun loadUserData() {
        val userId = getUserId()
        if (userId != null) {
            model.getUserData(userId)?.let {
                view.displayUserData(it)
            }
        }
    }

    override fun logout() {
        model.clearUserId()
        view.goToLogin()
    }

}