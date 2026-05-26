package edu.cit.tangpos.bustransport.screens.trips

import android.content.Context
import edu.cit.tangpos.bustransport.database.DBHelper
import edu.cit.tangpos.bustransport.utilities.Utility

class TripsModel(private val context: Context) {
    private val dbHelper = DBHelper(context)

    fun getTrips(callback: (List<Trip>) -> Unit) {
        val sharedPrefs = context.getSharedPreferences(Utility.APP_SHARED_PREFERENCES, Context.MODE_PRIVATE)
        val userId = sharedPrefs.getString("userId", null)?.toIntOrNull() ?: return callback(emptyList())

        val db = dbHelper.readableDatabase
        val tripsMap = mutableMapOf<String, MutableList<PassengerDetail>>()
        val tripDetailsMap = mutableMapOf<String, TripInfo>()

        // Query to get all passengers and their associated bus details for the user
        val query = """
            SELECT p.*, b.* 
            FROM ${DBHelper.TABLE_PASSENGERS} p
            JOIN ${DBHelper.TABLE_BUS_ROUTES} b ON p.${DBHelper.BUS_NUMBER} = b.${DBHelper.BUS_NUMBER}
            WHERE p.${DBHelper.USER_ID} = ?
        """.trimIndent()

        db.rawQuery(query, arrayOf(userId.toString())).use { cursor ->
            while (cursor.moveToNext()) {
                val busNumber = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.BUS_NUMBER))
                val travelDate = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.TRAVEL_DATE))
                val key = "${busNumber}_$travelDate"

                val passenger = PassengerDetail(
                    cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.PASSENGER_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.PASSENGER_GENDER))
                )

                if (!tripsMap.containsKey(key)) {
                    tripsMap[key] = mutableListOf()
                    tripDetailsMap[key] = TripInfo(
                        busNumber = busNumber,
                        destination = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.DESTINATION)),
                        carrier = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.BUS_CARRIER)),
                        busType = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.BUS_TYPE)),
                        travelDate = travelDate,
                        departureTime = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.DEPARTURE_TIME)),
                        arrivalTime = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ARRIVAL_TIME)),
                        fare = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.ROUTE_FARE))
                    )
                }
                tripsMap[key]?.add(passenger)
            }
        }

        val trips = tripDetailsMap.map { (key, info) ->
            val passengers = tripsMap[key] ?: emptyList()
            Trip(
                busNumber = info.busNumber,
                destination = info.destination,
                carrier = info.carrier,
                busType = info.busType,
                travelDate = info.travelDate,
                departureTime = info.departureTime,
                arrivalTime = info.arrivalTime,
                passengers = passengers,
                totalFare = info.fare * passengers.size
            )
        }

        callback(trips)
    }

    private data class TripInfo(
        val busNumber: String,
        val destination: String,
        val carrier: String,
        val busType: String,
        val travelDate: String,
        val departureTime: String,
        val arrivalTime: String,
        val fare: Int
    )
}
