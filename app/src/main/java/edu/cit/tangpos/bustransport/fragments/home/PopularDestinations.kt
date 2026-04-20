package edu.cit.tangpos.bustransport.fragments.home

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import edu.cit.tangpos.bustransport.R

class PopularDestinations : Fragment(R.layout.fragment_popular_destinations) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<CardView>(R.id.cardMoalboal).setOnClickListener {
            findNavController().navigate(R.id.nav_pd_moalboal)
        }

        view.findViewById<CardView>(R.id.cardBadian).setOnClickListener {
            findNavController().navigate(R.id.nav_pd_badian)
        }

        view.findViewById<CardView>(R.id.cardOslob).setOnClickListener {
            findNavController().navigate(R.id.nav_pd_oslob)
        }

        view.findViewById<CardView>(R.id.cardCarcar).setOnClickListener {
            findNavController().navigate(R.id.nav_pd_carcar)
        }

        view.findViewById<CardView>(R.id.cardDalaguete).setOnClickListener {
            findNavController().navigate(R.id.nav_pd_dalaguete)
        }

        view.findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            findNavController().popBackStack()
        }
    }
}