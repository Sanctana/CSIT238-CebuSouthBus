package edu.cit.tangpos.bustransport.screens.trips

interface TripsContract {
    interface View {
        fun showTripsList(trips: List<Trip>)
        fun showTripDetails(trip: Trip)
        fun navigateBack()
    }

    interface Presenter {
        fun onViewCreated()
        fun onDetailClicked(trip: Trip)
        fun onBackClicked()
    }
}
