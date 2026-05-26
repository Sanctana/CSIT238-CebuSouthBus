package edu.cit.tangpos.bustransport.screens.tripSummary

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import edu.cit.tangpos.bustransport.R
import edu.cit.tangpos.bustransport.database.DBHelper
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TripSummaryFragment : Fragment(R.layout.fragment_trip_summary), TripSummaryContract.View {

    private val presenter: TripSummaryContract.Presenter by lazy { TripSummaryPresenter(this) }
    private var fare: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val busNumber = arguments?.getString("busNumber") ?: ""
        val passengers = arguments?.getInt("passengers") ?: 1

        presenter.loadTripDetails(busNumber, passengers)

        view.findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            findNavController().popBackStack()
        }

        view.findViewById<CardView>(R.id.btnProceed).setOnClickListener {
            val bundle = Bundle().apply {
                putString("busNumber", busNumber)
                putInt("passengers", passengers)
                putInt("fare", fare)
            }
            findNavController().navigate(R.id.nav_passenger_details, bundle)
        }

        view.findViewById<CardView>(R.id.btnEdit).setOnClickListener {
            findNavController().navigate(R.id.nav_select_trip)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun displayTripDetails(trip: DBHelper.Route, passengers: Int) {
        val view = view ?: return
        this.fare = trip.fare

        // Header and Trip Info
        view.findViewById<TextView>(R.id.tvTripRoute).text = "Cebu to ${trip.destination}"

        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("EEEE, MMMM d", Locale.getDefault())
        view.findViewById<TextView>(R.id.tvTripDate).text = dateFormat.format(calendar.time)

        view.findViewById<TextView>(R.id.tvPassengerCount).text =
            "$passengers Passenger${if (passengers > 1) "s" else ""}"

        // Schedule
        view.findViewById<TextView>(R.id.tvBusNumber).text = "BUS ${trip.busNumber}"
        view.findViewById<TextView>(R.id.tvDuration).text = trip.eta
        view.findViewById<TextView>(R.id.tvDepartureTime).text = formatTime(trip.departureTime)
        view.findViewById<TextView>(R.id.tvArrivalTime).text = formatTime(trip.arrivalTime)
        view.findViewById<TextView>(R.id.tvDepartureLocation).text = "CEBU"
        view.findViewById<TextView>(R.id.tvArrivalLocation).text = trip.destination.uppercase()
        view.findViewById<TextView>(R.id.tvOperator).text = "Operated by: ${trip.carrier}"
        view.findViewById<TextView>(R.id.tvBusType).text = trip.busType

        // Cost Summary
        view.findViewById<TextView>(R.id.tvPassengerCountSummary).text = passengers.toString()
        view.findViewById<TextView>(R.id.tvPricePerPassenger).text = "₱${trip.fare}"
        view.findViewById<TextView>(R.id.tvTotalPrice).text = "₱${trip.fare * passengers}"
    }

    private fun formatTime(time24: String): String {
        return try {
            val parts = time24.split(":")
            val hour = parts[0].toInt()
            val minute = parts[1]
            val ampm = if (hour >= 12) "pm" else "am"
            val hour12 = if (hour % 12 == 0) 12 else hour % 12
            "$hour12:$minute$ampm"
        } catch (_: Exception) {
            time24
        }
    }

    override fun getContext(): Context = requireActivity()
}
