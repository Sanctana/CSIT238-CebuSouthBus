package edu.cit.tangpos.bustransport.screens.book

import androidx.core.os.bundleOf

class BookPresenter(private var view: BookContract.View) : BookContract.Presenter {
    private val model: BookContract.Model = BookModel(view.getContext())

    override fun getAllLocations(): List<String> {
        return model.getAllLocations()
    }

    override fun findTrips() {
        val destination = view.getDestination()
        val travelersText = view.getTravelers()
        val selectedTime = view.getSelectedTime()

        // Basic validation
        if (destination == "To" || destination.isEmpty()) {
            view.invalidDestination()
            return
        }

        if (selectedTime.isEmpty()) {
            view.invalidDateAndTime()
            return
        }

        // Extract number of travelers (assuming "X traveler(s)" format)
        val passengers = travelersText.filter { it.isDigit() }.toIntOrNull() ?: 1

        view.navigateSelectTrip(
            bundleOf(
                "destination" to destination,
                "time" to selectedTime,
                "passengers" to passengers
            )
        )
    }
}