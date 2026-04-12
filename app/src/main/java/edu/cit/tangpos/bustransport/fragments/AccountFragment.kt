package edu.cit.tangpos.bustransport.fragments

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import edu.cit.tangpos.bustransport.R

class AccountFragment : Fragment(R.layout.fragment_account) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val emailField = view.findViewById<TextView>(R.id.account_email)
        val email = arguments?.getString("email")
        emailField.text = email
    }
}