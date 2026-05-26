package edu.cit.tangpos.bustransport.screens.tripSummary

import android.content.Context
import edu.cit.tangpos.bustransport.database.DBHelper

class TripSummaryContract {
    interface View {
        fun displayTripDetails(trip: DBHelper.Route, passengers: Int)
        fun getContext(): Context
    }

    interface Presenter {
        fun loadTripDetails(busNumber: String, passengers: Int)
    }

    interface Model {
        fun getTripByBusNumber(busNumber: String): DBHelper.Route?
    }
}