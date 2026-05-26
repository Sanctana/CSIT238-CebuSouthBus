package edu.cit.tangpos.bustransport.screens.passengerDetails

import java.io.Serializable

data class PassengerData(
    val firstName: String,
    val lastName: String,
    val gender: String,
    val suffix: String,
    val dob: String
) : Serializable


class PassengerDetailsContract {
    interface View {
        fun showError(message: String)
        fun navigateToReviewPay(passengers: List<PassengerData>, busNumber: String, userId: Int)
    }

    interface Presenter {
        fun onReviewPayClicked(
            passengers: List<PassengerData>,
            busNumber: String,
            userId: Int
        )
    }
}
