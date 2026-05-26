package edu.cit.tangpos.bustransport.screens.account.account

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import edu.cit.tangpos.bustransport.R
import edu.cit.tangpos.bustransport.utilities.navigate

class AccountFragment : Fragment(R.layout.fragment_account), AccountContract.View {
    private val presenter: AccountContract.Presenter by lazy { AccountPresenter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!presenter.checkUserExists()) {
            return
        }

        presenter.loadUserData()

        view.findViewById<TextView>(R.id.profile_details).setOnClickListener {
            goToProfileView()
        }
        view.findViewById<LinearLayout>(R.id.btnAboutUs).setOnClickListener {
            goToAboutUs()
        }
        view.findViewById<LinearLayout>(R.id.btnBusCarriers).setOnClickListener {
            goToBusCarriers()
        }
        view.findViewById<LinearLayout>(R.id.btnLogOut).setOnClickListener {
            presenter.logout()
        }
    }

    override fun goToLogin() {
        requireActivity().finish()
    }

    override fun getContext(): Context = requireActivity()

    override fun goToProfileView() {
        navigate(R.id.nav_profile_view)
    }

    override fun goToAboutUs() {
        navigate(R.id.nav_about_us)
    }

    override fun goToBusCarriers() {
        navigate(R.id.nav_bus_carriers)
    }

    override fun displayUserData(userData: AccountContract.UserData) {
        view?.findViewById<TextView>(R.id.account_email)?.text = userData.email
        view?.findViewById<TextView>(R.id.username)?.text = userData.fullName
    }
}