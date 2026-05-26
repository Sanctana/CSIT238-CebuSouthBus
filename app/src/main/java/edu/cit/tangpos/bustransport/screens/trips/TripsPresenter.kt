package edu.cit.tangpos.bustransport.screens.trips

class TripsPresenter(
    private var view: TripsContract.View?,
    private val model: TripsModel
) : TripsContract.Presenter {

    override fun onViewCreated() {
        model.getTrips { trips ->
            view?.showTripsList(trips)
        }
    }

    override fun onDetailClicked(trip: Trip) {
        view?.showTripDetails(trip)
    }

    override fun onBackClicked() {
        view?.navigateBack()
    }

    fun detachView() {
        view = null
    }
}
