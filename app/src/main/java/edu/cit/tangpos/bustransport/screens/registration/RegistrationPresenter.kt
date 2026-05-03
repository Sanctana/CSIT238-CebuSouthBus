package edu.cit.tangpos.bustransport.screens.registration

import android.util.Patterns

class RegistrationPresenter(private var view: RegistrationContract.View) : RegistrationContract.Presenter {

    private val model: RegistrationContract.Model = RegistrationModel(view.getContext())

    override fun register() {
        val firstName = view.getFirstName().trim()
        val middleName = view.getMiddleName().trim()
        val lastName = view.getLastName().trim()
        val email = view.getEmail().trim()
        val password = view.getPassword().trim()
        val confirmPassword = view.getConfirmPassword().trim()

        if (firstName.isEmpty()) {
            view.showMessage("First name is required")
        } else if (lastName.isEmpty()) {
            view.showMessage("Last name is required")
        } else if (email.isEmpty()) {
            view.showMessage("Email is required")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            view.showMessage("Invalid email address")
        } else if (password.isEmpty()) {
            view.showMessage("Password is required")
        } else if (password.length < 6) {
            view.showMessage("Password must be at least 6 characters")
        } else if (confirmPassword.isEmpty()) {
            view.showMessage("Please confirm your password")
        } else if (password != confirmPassword) {
            view.showMessage("Passwords do not match")
        } else {
            if (model.registerUser(firstName, middleName, lastName, email, password) != -1L) {
                view.showMessage("Registration successful")
                view.navigateToLogin()
            } else {
                view.showMessage("Registration failed. Email might already exist.")
            }
        }
    }
}
