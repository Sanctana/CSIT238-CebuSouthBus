package edu.cit.tangpos.bustransport.screens.reviewPay

import edu.cit.tangpos.bustransport.screens.passengerDetails.PassengerData

class ReviewPayPresenter(
    private var view: ReviewPayContract.View?,
    private val model: ReviewPayContract.Model
) : ReviewPayContract.Presenter {

    override fun onConfirmButtonClicked(
        passengers: List<PassengerData>,
        busNumber: String,
        userId: Int
    ) {
        model.savePassengers(passengers, busNumber, userId) { success, error ->
            if (success) {
                view?.showSuccess("Booking Successful!")
                view?.navigateToHome()
            } else {
                view?.showError("Error saving details: $error")
            }
        }
    }

    fun detachView() {
        view = null
    }
}
