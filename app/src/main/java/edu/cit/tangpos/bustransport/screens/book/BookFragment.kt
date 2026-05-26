package edu.cit.tangpos.bustransport.screens.book

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import edu.cit.tangpos.bustransport.R
import edu.cit.tangpos.bustransport.utilities.getTextValue
import edu.cit.tangpos.bustransport.utilities.setTextValue
import edu.cit.tangpos.bustransport.utilities.showShortToast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class BookFragment : Fragment(R.layout.fragment_book), BookContract.View {
    private val presenter: BookContract.Presenter by lazy { BookPresenter(this) }

    private var selectedTime: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<LinearLayout>(R.id.btnSelectTo).setOnClickListener {
            displayLocations()
        }

        view.findViewById<LinearLayout>(R.id.btnSelectDate).setOnClickListener {
            showDateTimePicker()
        }

        view.findViewById<Button>(R.id.btnFindTrips).setOnClickListener {
            presenter.findTrips()
        }
    }

    override fun displayLocations() {
        val locations: List<String> = presenter.getAllLocations()

        AlertDialog.Builder(getContext()).setTitle("Select Location")
            .setItems(locations.toTypedArray()) { _, which ->
                requireActivity().setTextValue(
                    R.id.txtTo,
                    locations[which]
                )
            }.show()
    }

    override fun showDateTimePicker() {
        val calendar = Calendar.getInstance()

        // DATE PICKER
        val datePickerDialogue = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->

                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                // TIME PICKER
                TimePickerDialog(
                    requireContext(),
                    { _, hourOfDay, minute ->

                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        calendar.set(Calendar.MINUTE, minute)

                        val format = SimpleDateFormat(
                            "MMM dd, yyyy hh:mm a",
                            Locale.getDefault()
                        )

                        // Save time in HH:mm format for DB query
                        selectedTime =
                            String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute)

                        requireActivity().setTextValue(
                            R.id.txtDate,
                            format.format(calendar.time)
                        )

                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    false
                ).show()

            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialogue.datePicker.minDate = System.currentTimeMillis()

        datePickerDialogue.show()
    }

    override fun getDestination(): String = requireActivity().getTextValue(R.id.txtTo)


    override fun getTravelers(): String = requireActivity().getTextValue(R.id.txtTravelers)

    override fun invalidDestination() {
        requireActivity().showShortToast("Please select a destination")
    }

    override fun invalidDateAndTime() {
        requireActivity().showShortToast("Please select date and time")
    }

    override fun getSelectedTime(): String = selectedTime

    override fun navigateSelectTrip(bundle: Bundle) {
        findNavController().navigate(R.id.nav_select_trip, bundle)
    }

    override fun getContext(): Context = requireActivity()
}