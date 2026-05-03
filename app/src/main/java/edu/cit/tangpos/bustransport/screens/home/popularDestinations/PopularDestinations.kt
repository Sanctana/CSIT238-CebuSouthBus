package edu.cit.tangpos.bustransport.screens.home.popularDestinations

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import edu.cit.tangpos.bustransport.R
import edu.cit.tangpos.bustransport.utilities.goBack
import edu.cit.tangpos.bustransport.utilities.navigate

class PopularDestinations : Fragment(R.layout.fragment_popular_destinations), PopularDestinationsContract.View {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<CardView>(R.id.cardMoalboal).setOnClickListener {
            navigateMoalboal()
        }

        view.findViewById<CardView>(R.id.cardBadian).setOnClickListener {
            navigateBadian()
        }

        view.findViewById<CardView>(R.id.cardOslob).setOnClickListener {
            navigateOslob()
        }

        view.findViewById<CardView>(R.id.cardCarcar).setOnClickListener {
            navigateCarcar()
        }

        view.findViewById<CardView>(R.id.cardDalaguete).setOnClickListener {
            navigateDalaguete()
        }

        view.findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            goBack()
        }
    }

    override fun navigateMoalboal() {
        navigate(R.id.nav_pd_moalboal)
    }

    override fun navigateBadian() {
        navigate(R.id.nav_pd_badian)
    }

    override fun navigateOslob() {
        navigate(R.id.nav_pd_oslob)
    }

    override fun navigateCarcar() {
        navigate(R.id.nav_pd_carcar)
    }

    override fun navigateDalaguete() {
        navigate(R.id.nav_pd_dalaguete)
    }
}