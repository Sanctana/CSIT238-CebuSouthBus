package edu.cit.tangpos.bustransport.screens.selectTrip

import android.content.Context
import edu.cit.tangpos.bustransport.database.DBHelper

class SelectTripContract {
    interface View {
        fun displayTrips(trips: List<DBHelper.Route>)
        fun showNoTripsFound()
        fun getContext(): Context
    }

    interface Presenter {
        fun loadTrips(destination: String, time: String, isAirCondition: Boolean)
    }

    interface Model {
        fun getTrips(
            destination: String,
            time: String,
            isAirCondition: Boolean
        ): List<DBHelper.Route>
    }
}
