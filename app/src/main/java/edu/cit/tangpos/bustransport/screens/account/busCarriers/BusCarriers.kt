package edu.cit.tangpos.bustransport.screens.account.busCarriers

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import edu.cit.tangpos.bustransport.R
import edu.cit.tangpos.bustransport.utilities.goBack
import edu.cit.tangpos.bustransport.utilities.navigate

class BusCarriers : Fragment(R.layout.fragment_bus_carriers), BusCarriersContract.View {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        view.findViewById<TextView>(R.id.btnCeresDetails).setOnClickListener {
            navigateCeres()
        }

        view.findViewById<TextView>(R.id.btnMetroDetails).setOnClickListener {
            navigateMetro()
        }

        view.findViewById<TextView>(R.id.btnSunraysDetails).setOnClickListener {
            navigateSunrays()
        }

        view.findViewById<TextView>(R.id.btnVhireDetails).setOnClickListener {
            navigateVHire()
        }

        view.findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            goBack()
        }
    }

    override fun navigateCeres() {
        navigate(R.id.nav_bc_ceres)
    }

    override fun navigateMetro() {
        navigate(R.id.nav_bc_metro)
    }

    override fun navigateSunrays() {
        navigate(R.id.nav_bc_sunrays)
    }

    override fun navigateVHire() {
        navigate(R.id.nav_bc_vhire)
    }
}