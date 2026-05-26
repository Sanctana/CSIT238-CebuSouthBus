package edu.cit.tangpos.bustransport.screens.selectTrip

import android.content.Context
import edu.cit.tangpos.bustransport.database.DBHelper

class SelectTripModel(context: Context) : SelectTripContract.Model {
    private val dbHelper = DBHelper(context)

    override fun getTrips(
        destination: String,
        time: String,
        isAirCondition: Boolean
    ): List<DBHelper.Route> {
        val trips = mutableListOf<DBHelper.Route>()
        val db = dbHelper.readableDatabase

        val airConditionClause = "${DBHelper.BUS_TYPE} = " + if (isAirCondition) {
            "'Air Conditioned'"
        } else {
            "'Ordinary'"
        }

        // Querying for trips to the destination that depart AFTER or AT the selected time
        // Note: Time is in "HH:mm" format. SQLite can compare strings for time if they are in 24h format.
        val cursor = db.rawQuery(
            "SELECT * FROM ${DBHelper.TABLE_BUS_ROUTES} WHERE ${DBHelper.DESTINATION} = ? AND ${DBHelper.DEPARTURE_TIME} >= ? AND $airConditionClause ORDER BY ${DBHelper.DEPARTURE_TIME} ASC",
            arrayOf(destination, time)
        )

        cursor.use {
            while (it.moveToNext()) {
                trips.add(
                    DBHelper.Route(
                        it.getString(it.getColumnIndexOrThrow(DBHelper.DESTINATION)),
                        it.getString(it.getColumnIndexOrThrow(DBHelper.BUS_NUMBER)),
                        it.getString(it.getColumnIndexOrThrow(DBHelper.BUS_CARRIER)),
                        it.getString(it.getColumnIndexOrThrow(DBHelper.BUS_TYPE)),
                        it.getInt(it.getColumnIndexOrThrow(DBHelper.ROUTE_FARE)),
                        it.getString(it.getColumnIndexOrThrow(DBHelper.ROUTE_ETA)),
                        it.getInt(it.getColumnIndexOrThrow(DBHelper.ON_TIME)),
                        it.getString(it.getColumnIndexOrThrow(DBHelper.DEPARTURE_TIME)),
                        it.getString(it.getColumnIndexOrThrow(DBHelper.ARRIVAL_TIME))
                    )
                )
            }
        }
        return trips
    }
}
