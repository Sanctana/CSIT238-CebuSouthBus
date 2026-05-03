package edu.cit.tangpos.bustransport.screens.login

class LoginPresenter(private var view: LoginContract.View) : LoginContract.Presenter {
    private val model : LoginContract.Model = LoginModel(view.getContext())

    override fun checkIfUserExists() {
        if (model.userExists()){
            view.navigateToHome()
        }
    }

    override fun login() {
        if (model.login(view.getEmail(), view.getPassword())){
            view.navigateToHome()
        } else {
            view.showInvalidCredentials()
        }
    }
}
