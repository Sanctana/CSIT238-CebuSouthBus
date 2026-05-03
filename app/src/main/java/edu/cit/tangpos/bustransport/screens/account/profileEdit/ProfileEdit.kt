package edu.cit.tangpos.bustransport.screens.account.profileEdit

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import edu.cit.tangpos.bustransport.R
import androidx.navigation.fragment.findNavController
import edu.cit.tangpos.bustransport.utilities.showShortToast

class ProfileEdit : Fragment(R.layout.fragment_profile_edit), ProfileEditContract.View {
    private val presenter : ProfileEditContract.Presenter by lazy { ProfileEditPresenter(this) }

    private lateinit var etFullName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etCurrentPassword: EditText
    private lateinit var etNewPassword: EditText
    private lateinit var etConfirmPassword: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etFullName = view.findViewById(R.id.etFullName)
        etEmail = view.findViewById(R.id.etEmail)
        etCurrentPassword = view.findViewById(R.id.etCurrentPassword)
        etNewPassword = view.findViewById(R.id.etNewPassword)
        etConfirmPassword = view.findViewById(R.id.etConfirmPassword)

        if (!presenter.checkUserExists()){
            return
        }

        presenter.loadUserData()

        view.findViewById<TextView>(R.id.btnSave).setOnClickListener {
            presenter.updateProfile()
        }

        view.findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            findNavController().popBackStack()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun displayUserData(userData: ProfileEditContract.UserData) {
        etFullName.setText("${userData.firstName} ${userData.middleName} ${userData.lastName}")
        etEmail.setText(userData.email)
    }

    override fun showToast(message: String) {
        requireActivity().showShortToast(message)
    }

    override fun getFullName(): String {
        return etFullName.text.toString().trim()
    }

    override fun getEmail(): String {
        return etEmail.text.toString().trim()
    }

    override fun getCurrentPassword(): String {
        return etCurrentPassword.text.toString().trim()
    }

    override fun getNewPassword(): String {
        return etNewPassword.text.toString().trim()
    }

    override fun getConfirmPassword(): String {
        return etConfirmPassword.text.toString().trim()
    }

    override fun getContext(): Context = requireActivity()

    override fun goToLogin() {
        requireActivity().finish()
    }
}
