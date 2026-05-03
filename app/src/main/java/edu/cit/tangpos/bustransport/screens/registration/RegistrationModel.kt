package edu.cit.tangpos.bustransport.screens.registration

import android.content.ContentValues
import android.content.Context
import edu.cit.tangpos.bustransport.Utility
import edu.cit.tangpos.bustransport.database.DBHelper

class RegistrationModel(private val context: Context) : RegistrationContract.Model {
    override fun registerUser(
        firstName: String,
        middleName: String,
        lastName: String,
        email: String,
        password: String
    ): Long {
        val db = DBHelper(context).writableDatabase
        val userValues = ContentValues().apply {
            put(DBHelper.USERS_FIRST_NAME, firstName)
            put(DBHelper.USERS_MIDDLE_NAME, middleName)
            put(DBHelper.USERS_LAST_NAME, lastName)
            put(DBHelper.USERS_PASSWORD, Utility.hashPassword(password))
            put(DBHelper.USERS_EMAIL, email)
        }
        return db.insert(DBHelper.TABLE_USERS, null, userValues)
    }
}
