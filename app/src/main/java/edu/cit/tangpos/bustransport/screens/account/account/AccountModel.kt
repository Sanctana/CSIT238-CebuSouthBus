package edu.cit.tangpos.bustransport.screens.account.account

import android.content.Context
import androidx.core.content.edit
import edu.cit.tangpos.bustransport.Utility
import edu.cit.tangpos.bustransport.database.DBHelper

class AccountModel(private val context: Context) : AccountContract.Model {
    override fun getUserId(): String? {
        return context.getSharedPreferences(Utility.APP_SHARED_PREFERENCES, Context.MODE_PRIVATE).getString("userId", null)
    }

    override fun getUserData(userId: String): AccountContract.UserData? {
        DBHelper(context).readableDatabase.rawQuery(
            "SELECT * FROM ${DBHelper.TABLE_USERS} WHERE ${DBHelper.USERS_ID} = ?",
            arrayOf(userId)
        ).use { cursor ->
            if (cursor.moveToFirst()) {
                val email = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.USERS_EMAIL))
                val firstName = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.USERS_FIRST_NAME))
                val middleName = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.USERS_MIDDLE_NAME)) ?: ""
                val lastName = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.USERS_LAST_NAME))

                return AccountContract.UserData(email, "$firstName $middleName $lastName".trim())
            }
        }
        return null
    }

    override fun clearUserId() {
        context.getSharedPreferences(Utility.APP_SHARED_PREFERENCES, Context.MODE_PRIVATE).edit {
            remove("userId")
        }
    }
}