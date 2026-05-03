package edu.cit.tangpos.bustransport.screens.account.profileView

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import edu.cit.tangpos.bustransport.R
import edu.cit.tangpos.bustransport.utilities.goBack
import edu.cit.tangpos.bustransport.utilities.navigate
import edu.cit.tangpos.bustransport.utilities.setTextValue

class ProfileView : Fragment(R.layout.fragment_profile_view), ProfileViewContract.View {
    private val presenter : ProfileViewContract.Presenter by lazy { ProfileViewPresenter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!presenter.checkUserExists()){
            return
        }

        presenter.displayUserdata()

        view.findViewById<View>(R.id.btnBack).setOnClickListener {
            goBack()
        }
        view.findViewById<TextView>(R.id.btnEdit).setOnClickListener {
            navigateProfileEdit()
        }
    }

    override fun getContext(): Context = requireActivity()

    override fun goToLogin() {
        requireActivity().finish()
    }

    override fun setEmail(email: String) {
        requireActivity().setTextValue(R.id.tvEmail, email)
    }

    override fun setFullName(fullName: String) {
        requireActivity().setTextValue(R.id.tvFullName, fullName)
    }

    override fun setUsername(username: String) {
        requireActivity().setTextValue(R.id.tvUserName, username)
    }

    override fun navigateProfileEdit() {
        navigate(R.id.nav_profile_edit)
    }
}