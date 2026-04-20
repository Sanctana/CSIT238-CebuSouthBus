package edu.cit.tangpos.bustransport.fragments.home

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import edu.cit.tangpos.bustransport.R

class Dalaguete : Fragment(R.layout.fragment_pd_dalaguete) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
