package edu.cit.tangpos.bustransport.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import edu.cit.tangpos.bustransport.R

class AccountFragment : Fragment(R.layout.fragment_account) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val emailField = view.findViewById<TextView>(R.id.account_email)
        val email = arguments?.getString("email")
        emailField.text = email

        val btnLogOut = view.findViewById<LinearLayout>(R.id.btnLogOut)
        val username = view.findViewById<TextView>(R.id.username)

        // Activity requirements (Not good)
        val sharedPreferences = requireContext().getSharedPreferences("Bus Transport", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getString("isLoggedIn", "false") == "true"

        if (isLoggedIn) {
            username.text = "Logged In"
        }

        btnLogOut.setOnClickListener {

        }
    }
}