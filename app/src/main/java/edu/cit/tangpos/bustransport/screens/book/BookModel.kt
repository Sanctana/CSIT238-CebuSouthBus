package edu.cit.tangpos.bustransport.screens.book

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import edu.cit.tangpos.bustransport.database.DBHelper

class BookModel(context: Context) : BookContract.Model {
    val readableDatabase: SQLiteDatabase = DBHelper(context).readableDatabase

    override fun getAllLocations(): List<String> {
        val locations: MutableList<String> = mutableListOf();

        readableDatabase.rawQuery(
            "SELECT DISTINCT ${DBHelper.DESTINATION} FROM ${DBHelper.TABLE_BUS_ROUTES} ORDER BY ${DBHelper.DESTINATION} ASC",
            null
        ).use {
            while (it.moveToNext()) {
                locations.add(
                    it.getString(
                        it.getColumnIndexOrThrow(DBHelper.DESTINATION)
                    )
                )
            }
        }

        return locations
    }
}