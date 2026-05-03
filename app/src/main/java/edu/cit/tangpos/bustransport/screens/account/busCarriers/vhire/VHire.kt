package edu.cit.tangpos.bustransport.screens.account.busCarriers.vhire

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import edu.cit.tangpos.bustransport.R
import edu.cit.tangpos.bustransport.utilities.goBack

class VHire : Fragment(R.layout.fragment_bc_vhire) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            goBack()
        }
    }
}