package edu.cit.tangpos.bustransport.screens.trips

data class Trip(
    val busNumber: String,
    val destination: String,
    val carrier: String,
    val busType: String,
    val travelDate: String,
    val departureTime: String,
    val arrivalTime: String,
    val passengers: List<PassengerDetail>,
    val totalFare: Int
)

data class PassengerDetail(
    val name: String,
    val gender: String
)
