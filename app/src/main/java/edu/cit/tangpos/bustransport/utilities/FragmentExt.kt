package edu.cit.tangpos.bustransport.utilities

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

fun Fragment.navigate(int: Int) {
    findNavController().navigate(int)
}

fun Fragment.goBack(){
    findNavController().popBackStack()
}