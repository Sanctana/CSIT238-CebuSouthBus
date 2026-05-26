package edu.cit.tangpos.bustransport.screens.tripSummary

class TripSummaryPresenter(private val view: TripSummaryContract.View) : TripSummaryContract.Presenter {
    private val model: TripSummaryContract.Model = TripSummaryModel(view.getContext())

    override fun loadTripDetails(busNumber: String, passengers: Int) {
        val trip = model.getTripByBusNumber(busNumber)
        trip?.let {
            view.displayTripDetails(it, passengers)
        }
    }
}
