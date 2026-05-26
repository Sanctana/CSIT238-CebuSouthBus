package edu.cit.tangpos.bustransport.screens.passengerDetails

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import edu.cit.tangpos.bustransport.R
import edu.cit.tangpos.bustransport.utilities.Utility
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class PassengerDetailsFragment : Fragment(R.layout.activity_passenger_details), PassengerDetailsContract.View {

    private lateinit var passengerContainer: LinearLayout
    private val passengerViews = mutableListOf<View>()
    
    private var busNumber: String = ""
    private var passengersCount: Int = 1
    private var fare: Int = 0

    private lateinit var presenter: PassengerDetailsContract.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        busNumber = arguments?.getString("busNumber") ?: ""
        passengersCount = arguments?.getInt("passengers") ?: 1
        fare = arguments?.getInt("fare") ?: 0

        presenter = PassengerDetailsPresenter(this)

        passengerContainer = view.findViewById(R.id.passengerContainer)
        
        setupPassengerInputs()

        view.findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            findNavController().popBackStack()
        }

        view.findViewById<Button>(R.id.btnReviewPay).setOnClickListener {
            onReviewPayClicked()
        }
    }

    private fun setupPassengerInputs() {
        val inflater = LayoutInflater.from(requireContext())
        
        for (i in 1..passengersCount) {
            val passengerView = inflater.inflate(R.layout.item_passenger_input, passengerContainer, false)
            
            passengerView.findViewById<TextView>(R.id.tvPassengerNumber).text = "Passenger $i Details"
            
            setupGenderDropdown(passengerView.findViewById(R.id.autoCompleteGender))
            setupSuffixDropdown(passengerView.findViewById(R.id.autoCompleteSuffix))
            setupDatePicker(passengerView.findViewById(R.id.etDateOfBirth))
            
            passengerContainer.addView(passengerView)
            passengerViews.add(passengerView)
        }
    }

    private fun setupGenderDropdown(autoCompleteTextView: AutoCompleteTextView) {
        val genders = arrayOf("Male", "Female", "Other")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, genders)
        autoCompleteTextView.setAdapter(adapter)
    }

    private fun setupSuffixDropdown(autoCompleteTextView: AutoCompleteTextView) {
        val suffixes = arrayOf("None", "Jr.", "Sr.", "II", "III", "IV")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, suffixes)
        autoCompleteTextView.setAdapter(adapter)
    }

    private fun setupDatePicker(editText: EditText) {
        editText.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, month, dayOfMonth)
                    val format = SimpleDateFormat("MM/dd/yyyy", Locale.US)
                    editText.setText(format.format(selectedDate.time))
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun onReviewPayClicked() {
        val sharedPref = requireContext().getSharedPreferences(Utility.APP_SHARED_PREFERENCES, Context.MODE_PRIVATE)
        val userId = sharedPref.getString("userId", null)?.toInt() ?: -1
        
        val passengers = passengerViews.map { view ->
            PassengerData(
                firstName = view.findViewById<EditText>(R.id.etFirstName).text.toString(),
                lastName = view.findViewById<EditText>(R.id.etLastName).text.toString(),
                gender = view.findViewById<AutoCompleteTextView>(R.id.autoCompleteGender).text.toString(),
                suffix = view.findViewById<AutoCompleteTextView>(R.id.autoCompleteSuffix).text.toString(),
                dob = view.findViewById<EditText>(R.id.etDateOfBirth).text.toString()
            )
        }

        presenter.onReviewPayClicked(passengers, busNumber, userId)
    }

    override fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun navigateToReviewPay(passengers: List<PassengerData>, busNumber: String, userId: Int) {
        val bundle = Bundle().apply {
            putString("busNumber", busNumber)
            putSerializable("passengerList", passengers.toTypedArray())
            putInt("userId", userId)
            putInt("fare", fare)
        }
        findNavController().navigate(R.id.action_passengerDetails_to_reviewPay, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (::presenter.isInitialized) {
            (presenter as? PassengerDetailsPresenter)?.detachView()
        }
    }
}
