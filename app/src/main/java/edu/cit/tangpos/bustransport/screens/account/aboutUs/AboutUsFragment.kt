package edu.cit.tangpos.bustransport.screens.account.aboutUs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import edu.cit.tangpos.bustransport.R
import edu.cit.tangpos.bustransport.utilities.goBack

class AboutUsFragment : Fragment(R.layout.fragment_aboutus){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.btnBack).setOnClickListener {
            goBack()
        }
    }
}