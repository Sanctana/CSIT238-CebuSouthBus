package edu.cit.tangpos.bustransport.screens.home.home

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import edu.cit.tangpos.bustransport.R
import edu.cit.tangpos.bustransport.utilities.navigate

class HomeFragment : Fragment(R.layout.fragment_home), HomeFragmentContract.View {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.btnSeeAllPopular).setOnClickListener {
            navigateToPopularDestinations()
        }
    }

    override fun navigateToPopularDestinations() {
        navigate(R.id.nav_popular_destinations)
    }
}