package edu.cit.tangpos.bustransport.screens.reviewPay

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import edu.cit.tangpos.bustransport.R
import edu.cit.tangpos.bustransport.screens.passengerDetails.PassengerData

class ReviewPayFragment : Fragment(R.layout.fragment_review_pay), ReviewPayContract.View {

    private lateinit var presenter: ReviewPayContract.Presenter
    private var busNumber: String = ""
    private var passengerList: List<PassengerData> = emptyList()
    private var userId: Int = -1
    private var fare: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        busNumber = arguments?.getString("busNumber") ?: ""
        val passengersArray = arguments?.getSerializable("passengerList") as? Array<PassengerData>
        passengerList = passengersArray?.toList() ?: emptyList()
        userId = arguments?.getInt("userId") ?: -1
        fare = arguments?.getInt("fare") ?: 0

        val model = ReviewPayModel(requireContext())
        presenter = ReviewPayPresenter(this, model)

        setupUI(view)

        view.findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            findNavController().popBackStack()
        }

        view.findViewById<View>(R.id.btnConfirm).setOnClickListener {
            presenter.onConfirmButtonClicked(passengerList, busNumber, userId)
        }
    }

    private fun setupUI(view: View) {
        val passengerCount = passengerList.size
        val farePerPassenger = fare
        val totalFare = passengerCount * farePerPassenger

        view.findViewById<TextView>(R.id.tvPassengerCount).text = passengerCount.toString()
        view.findViewById<TextView>(R.id.tvFarePerPassenger).text = "₱$farePerPassenger"
        view.findViewById<TextView>(R.id.tvTotalAmount).text = "₱$totalFare"
    }

    override fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun showSuccess(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun navigateToHome() {
        findNavController().navigate(R.id.nav_home)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (::presenter.isInitialized) {
            (presenter as? ReviewPayPresenter)?.detachView()
        }
    }
}
