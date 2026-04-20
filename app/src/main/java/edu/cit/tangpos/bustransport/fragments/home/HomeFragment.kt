package edu.cit.tangpos.bustransport.fragments.home

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import edu.cit.tangpos.bustransport.R

class HomeFragment : Fragment(R.layout.fragment_home) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.btnSeeAllPopular).setOnClickListener {
            findNavController().navigate(R.id.nav_popular_destinations)
        }
    }
}