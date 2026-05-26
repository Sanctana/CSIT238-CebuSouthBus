package edu.cit.tangpos.bustransport.screens.selectTrip

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import edu.cit.tangpos.bustransport.R
import edu.cit.tangpos.bustransport.database.DBHelper
import kotlin.properties.Delegates

class SelectTripFragment : Fragment(R.layout.fragment_select_trip), SelectTripContract.View {

    private val presenter: SelectTripContract.Presenter by lazy { SelectTripPresenter(this) }
    private lateinit var busListContainer: LinearLayout
    var isAirCondition = true

    lateinit var destination: String
    lateinit var time: String

    var passengers by Delegates.notNull<Int>()

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        busListContainer = view.findViewById(R.id.busListContainer)

        destination = arguments?.getString("destination") ?: ""
        time = arguments?.getString("time") ?: ""
        passengers = arguments?.getInt("passengers") ?: 1

        val btnAirCondition = view.findViewById<TextView>(R.id.btnAircon)
        val btnOrdinary = view.findViewById<TextView>(R.id.btnOrdinary)

        presenter.loadTrips(destination, time, isAirCondition)

        view.findViewById<TextView>(R.id.tvRoute).text = "CEBU → ${destination.uppercase()}"
        view.findViewById<TextView>(R.id.tvPassengerCount).text =
            "$passengers Passenger${if (passengers > 1) "s" else ""}"

        view.findViewById<View>(R.id.btnBack).setOnClickListener {
            findNavController().popBackStack()
        }

        btnAirCondition.setOnClickListener {
            if (isAirCondition) {
                return@setOnClickListener
            }

            btnAirCondition.setBackgroundColor(getColor(requireContext(), R.color.bus_blue))
            btnAirCondition.setTextColor(getColor(requireContext(), R.color.white))

            btnOrdinary.setBackgroundColor(getColor(requireContext(), R.color.bus_yellow))
            btnOrdinary.setTextColor(getColor(requireContext(), R.color.text_dark))

            switchTrip()
        }

        btnOrdinary.setOnClickListener {
            if (!isAirCondition) {
                return@setOnClickListener
            }

            btnOrdinary.setBackgroundColor(getColor(requireContext(), R.color.bus_blue))
            btnOrdinary.setTextColor(getColor(requireContext(), R.color.white))

            btnAirCondition.setBackgroundColor(getColor(requireContext(), R.color.bus_yellow))
            btnAirCondition.setTextColor(getColor(requireContext(), R.color.text_dark))

            switchTrip()
        }
    }

    private fun switchTrip() {
        isAirCondition = !isAirCondition

        presenter.loadTrips(destination, time, isAirCondition)
    }

    @SuppressLint("SetTextI18n")
    override fun displayTrips(trips: List<DBHelper.Route>) {
        busListContainer.removeAllViews()
        val inflater = LayoutInflater.from(context)

        for (trip in trips) {
            val itemView = inflater.inflate(R.layout.item_bus_route, busListContainer, false)

            itemView.findViewById<TextView>(R.id.tvBusNumber).text = trip.busNumber
            itemView.findViewById<TextView>(R.id.tvCarrier).text = "Operated by: ${trip.carrier}"
            itemView.findViewById<TextView>(R.id.tvOnTime).text = "${trip.onTime}% ON TIME"
            itemView.findViewById<TextView>(R.id.tvDepartureTime).text =
                formatTime(trip.departureTime)
            itemView.findViewById<TextView>(R.id.tvArrivalTime).text = formatTime(trip.arrivalTime)
            itemView.findViewById<TextView>(R.id.tvEta).text = trip.eta
            itemView.findViewById<TextView>(R.id.tvDestination).text = trip.destination.uppercase()
            itemView.findViewById<TextView>(R.id.tvFare).text = "P${trip.fare}"

            itemView.setOnClickListener {
                val bundle = Bundle().apply {
                    putString("busNumber", trip.busNumber)
                    putInt("passengers", passengers)
                }
                findNavController().navigate(R.id.nav_trip_summary, bundle)
            }

            busListContainer.addView(itemView)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun showNoTripsFound() {
        busListContainer.removeAllViews()
        val textView = TextView(context).apply {
            text = "No trips found for the selected time."
            gravity = android.view.Gravity.CENTER
            setPadding(0, 50, 0, 0)
        }
        busListContainer.addView(textView)
    }

    private fun formatTime(time24: String): String {
        // Converts "HH:mm" to "h:mma" (e.g., "04:30" to "4:30am")
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
