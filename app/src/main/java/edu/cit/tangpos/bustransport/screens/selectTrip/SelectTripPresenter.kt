package edu.cit.tangpos.bustransport.screens.selectTrip

class SelectTripPresenter(private val view: SelectTripContract.View) : SelectTripContract.Presenter {
    val model: SelectTripContract.Model = SelectTripModel(view.getContext())

    override fun loadTrips(destination: String, time: String, isAirCondition: Boolean) {
        val trips = model.getTrips(destination, time, isAirCondition)
        if (trips.isEmpty()) {
            view.showNoTripsFound()
        } else {
            view.displayTrips(trips)
        }
    }
}
