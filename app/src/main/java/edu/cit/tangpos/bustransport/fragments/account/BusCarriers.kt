package edu.cit.tangpos.bustransport.fragments.account

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import edu.cit.tangpos.bustransport.R

class BusCarriers : Fragment(R.layout.fragment_bus_carriers) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        view.findViewById<TextView>(R.id.btnCeresDetails).setOnClickListener {
            findNavController().navigate(R.id.nav_bc_ceres)
        }

        view.findViewById<TextView>(R.id.btnMetroDetails).setOnClickListener {
            findNavController().navigate(R.id.nav_bc_metro)
        }

        view.findViewById<TextView>(R.id.btnSunraysDetails).setOnClickListener {
            findNavController().navigate(R.id.nav_bc_sunrays)
        }

        view.findViewById<TextView>(R.id.btnVhireDetails).setOnClickListener {
            findNavController().navigate(R.id.nav_bc_vhire)
        }

        view.findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            findNavController().popBackStack()
        }
    }
}