package edu.cit.tangpos.bustransport.screens.reviewPay

import edu.cit.tangpos.bustransport.screens.passengerDetails.PassengerData

class ReviewPayContract {
    interface View {
        fun showError(message: String)
        fun showSuccess(message: String)
        fun navigateToHome()
    }

    interface Presenter {
        fun onConfirmButtonClicked(
            passengers: List<PassengerData>,
            busNumber: String,
            userId: Int
        )
    }

    interface Model {
        fun savePassengers(
            passengers: List<PassengerData>,
            busNumber: String,
            userId: Int,
            callback: (Boolean, String?) -> Unit
        )
    }
}
