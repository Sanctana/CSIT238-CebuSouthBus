package edu.cit.tangpos.bustransport.screens.login

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.core.content.edit
import edu.cit.tangpos.bustransport.Utility
import edu.cit.tangpos.bustransport.database.DBHelper

class LoginModel(private val context: Context) : LoginContract.Model {
    override fun userExists() : Boolean {
        return context.getSharedPreferences(Utility.APP_SHARED_PREFERENCES, MODE_PRIVATE).getString("userId", null) != null
    }

    override fun login(email: String, password: String): Boolean {
        DBHelper(context).readableDatabase.rawQuery("SELECT * FROM ${DBHelper.TABLE_USERS} WHERE ${DBHelper.USERS_EMAIL} = ? AND ${DBHelper.USERS_PASSWORD} = ?",
            arrayOf(email, Utility.hashPassword(password))).use { cursor ->
            if (cursor.moveToFirst()) {
                saveId(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.USERS_ID)))
                return true
            }
        }
        return false
    }

    override fun saveId(userId: String) {
        context.getSharedPreferences(Utility.APP_SHARED_PREFERENCES, MODE_PRIVATE).edit {
            putString("userId", userId)
        }
    }
}
