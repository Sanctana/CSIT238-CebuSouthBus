package edu.cit.tangpos.bustransport.screens.book

import android.content.Context
import android.os.Bundle

class BookContract {
    interface View {
        fun getContext(): Context
        fun displayLocations()
        fun showDateTimePicker()
        fun getDestination(): String
        fun getTravelers(): String
        fun invalidDestination()
        fun invalidDateAndTime()
        fun getSelectedTime(): String
        fun navigateSelectTrip(bundle: Bundle)
    }

    interface Presenter {
        fun getAllLocations(): List<String>
        fun findTrips()
    }

    interface Model {
        fun getAllLocations(): List<String>
    }
}