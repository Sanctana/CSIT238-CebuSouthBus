package edu.cit.tangpos.bustransport.screens.trips

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import edu.cit.tangpos.bustransport.R

class TripsFragment : Fragment(), TripsContract.View {

    private lateinit var presenter: TripsPresenter
    private lateinit var container: FrameLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.container = FrameLayout(requireContext())
        this.container.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        return this.container
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val model = TripsModel(requireContext())
        presenter = TripsPresenter(this, model)
        presenter.onViewCreated()
    }

    override fun showTripsList(trips: List<Trip>) {
        container.removeAllViews()
        val view = layoutInflater.inflate(R.layout.fragment_mytrips_with, container, false)
        container.addView(view)

        val tripsContainer = view.findViewById<LinearLayout>(R.id.tripsContainer)
        
        if (trips.isEmpty()) {
            // If no trips, we could optionally show the empty state layout
            // but for now we follow the instruction to focus on 'with' layout.
        }

        for (trip in trips) {
            val cardView = layoutInflater.inflate(R.layout.item_trip_card, tripsContainer, false)
            cardView.findViewById<TextView>(R.id.tvDestination).text = trip.destination
            cardView.findViewById<TextView>(R.id.tvDate).text = trip.travelDate
            cardView.findViewById<TextView>(R.id.tvBusInfo).text = "${trip.busNumber} - ${trip.busType}"
            cardView.findViewById<TextView>(R.id.tvDepartureTime).text = "Departs: ${trip.departureTime}"
            cardView.findViewById<TextView>(R.id.tvPassengerCount).text = "${trip.passengers.size} Passengers"
            
            cardView.findViewById<Button>(R.id.btnViewDetails).setOnClickListener {
                presenter.onDetailClicked(trip)
            }
            tripsContainer.addView(cardView)
        }

        view.findViewById<View>(R.id.btnBack)?.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
    }

    override fun showTripDetails(trip: Trip) {
        container.removeAllViews()
        val view = layoutInflater.inflate(R.layout.fragment_mytrips_details, container, false)
        container.addView(view)

        view.findViewById<TextView>(R.id.tvDestinationDetail).text = trip.destination
        view.findViewById<TextView>(R.id.tvBusCarrierDetail).text = trip.carrier
        view.findViewById<TextView>(R.id.tvTravelDateDetail).text = trip.travelDate
        view.findViewById<TextView>(R.id.tvBusNumberDetail).text = "${trip.busNumber} (${trip.busType})"
        view.findViewById<TextView>(R.id.tvPassengerCountDetail).text = trip.passengers.size.toString()
        view.findViewById<TextView>(R.id.tvDepartureTimeDetail).text = trip.departureTime
        view.findViewById<TextView>(R.id.tvArrivalTimeDetail).text = trip.arrivalTime
        view.findViewById<TextView>(R.id.tvTotalAmountDetail).text = "₱${trip.totalFare}"

        val passengersContainer = view.findViewById<LinearLayout>(R.id.passengersContainerDetail)
        trip.passengers.forEachIndexed { index, passenger ->
            val pView = layoutInflater.inflate(R.layout.item_passenger_detail, passengersContainer, false)
            pView.findViewById<TextView>(R.id.tvPassengerName).text = "${index + 1}. ${passenger.name}"
            pView.findViewById<TextView>(R.id.tvPassengerGenderAge).text = passenger.gender // Gender only for now
            passengersContainer.addView(pView)
        }

        view.findViewById<View>(R.id.btnBack)?.setOnClickListener {
            presenter.onBackClicked()
        }
    }

    override fun navigateBack() {
        presenter.onViewCreated() // Refresh and show list
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }
}
