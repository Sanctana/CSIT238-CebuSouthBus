package edu.cit.tangpos.bustransport.screens.reviewPay

import android.content.ContentValues
import android.content.Context
import edu.cit.tangpos.bustransport.database.DBHelper
import edu.cit.tangpos.bustransport.screens.passengerDetails.PassengerData
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ReviewPayModel(private val context: Context) : ReviewPayContract.Model {

    override fun savePassengers(
        passengers: List<PassengerData>,
        busNumber: String,
        userId: Int,
        callback: (Boolean, String?) -> Unit
    ) {
        val dbHelper = DBHelper(context)
        val db = dbHelper.writableDatabase
        val travelDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Calendar.getInstance().time)

        try {
            db.beginTransaction()
            var allSaved = true
            for (passenger in passengers) {
                val fullName = "${passenger.firstName} ${passenger.lastName}"
                
                val contentValues = ContentValues().apply {
                    put(DBHelper.USER_ID, userId)
                    put(DBHelper.BUS_NUMBER, busNumber)
                    put(DBHelper.PASSENGER_NAME, fullName)
                    put(DBHelper.PASSENGER_GENDER, passenger.gender)
                    put(DBHelper.TRAVEL_DATE, travelDate)
                }
                
                val result = db.insert(DBHelper.TABLE_PASSENGERS, null, contentValues)
                if (result == -1L) {
                    allSaved = false
                    break
                }
            }
            
            if (allSaved) {
                db.setTransactionSuccessful()
                callback(true, null)
            } else {
                callback(false, "Failed to insert some passengers")
            }
        } catch (e: Exception) {
            callback(false, e.message)
        } finally {
            if (db.inTransaction()) {
                db.endTransaction()
            }
            // Better not to close DB here if we want to be safe, 
            // but for a one-off it's usually okay. 
            // db.close()
        }
    }
}
