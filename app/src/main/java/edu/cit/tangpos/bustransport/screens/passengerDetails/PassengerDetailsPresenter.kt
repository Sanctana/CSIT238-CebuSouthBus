package edu.cit.tangpos.bustransport.screens.passengerDetails

class PassengerDetailsPresenter(
    private var view: PassengerDetailsContract.View?
) : PassengerDetailsContract.Presenter {

    override fun onReviewPayClicked(
        passengers: List<PassengerData>,
        busNumber: String,
        userId: Int
    ) {
        if (userId == -1) {
            view?.showError("User not logged in")
            return
        }

        if (passengers.any { it.firstName.isEmpty() || it.lastName.isEmpty() || it.gender.isEmpty() || it.gender == "Select Gender" }) {
            view?.showError("Please fill all required fields")
            return
        }

        view?.navigateToReviewPay(passengers, busNumber, userId)
    }

    fun detachView() {
        view = null
    }
}
