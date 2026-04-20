package edu.cit.tangpos.bustransport.fragments.account

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import edu.cit.tangpos.bustransport.R

class AboutUsFragment : Fragment(R.layout.fragment_aboutus){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Handle the back button inside the About Us layout
        view.findViewById<View>(R.id.btnBack).setOnClickListener {
            findNavController().popBackStack()
        }
    }
}