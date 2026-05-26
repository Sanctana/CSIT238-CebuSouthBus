package edu.cit.tangpos.bustransport.screens.tripSummary

import android.content.Context
import edu.cit.tangpos.bustransport.database.DBHelper

class TripSummaryModel(context: Context) : TripSummaryContract.Model {
    private val dbHelper = DBHelper(context)

    override fun getTripByBusNumber(busNumber: String): DBHelper.Route? {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM ${DBHelper.TABLE_BUS_ROUTES} WHERE ${DBHelper.BUS_NUMBER} = ?",
            arrayOf(busNumber)
        )

        return cursor.use {
            if (it.moveToFirst()) {
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
            } else {
                null
            }
        }
    }
}
