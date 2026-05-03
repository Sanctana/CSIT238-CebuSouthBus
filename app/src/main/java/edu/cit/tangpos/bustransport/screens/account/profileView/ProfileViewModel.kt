package edu.cit.tangpos.bustransport.screens.account.profileView

import android.content.Context
import edu.cit.tangpos.bustransport.Utility
import edu.cit.tangpos.bustransport.database.DBHelper

class ProfileViewModel(private val context: Context) : ProfileViewContract.Model {
    val db = DBHelper(context)

    override fun getUserId(): String? {
        return context.getSharedPreferences(Utility.APP_SHARED_PREFERENCES, Context.MODE_PRIVATE).getString("userId", null)
    }

    override fun getUserData(userId: String): ProfileViewContract.UserData? {
        db.readableDatabase.rawQuery("SELECT * FROM ${DBHelper.TABLE_USERS} WHERE ${DBHelper.USERS_ID} = ?", arrayOf(userId)).use { cursor ->
            if (cursor.moveToFirst()){
                val firstName = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.USERS_FIRST_NAME))
                val middleName = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.USERS_MIDDLE_NAME))
                val lastName = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.USERS_LAST_NAME))

                return ProfileViewContract.UserData(cursor.getString(cursor.getColumnIndexOrThrow(
                    DBHelper.USERS_EMAIL)), "$firstName $middleName $lastName")
            }
        }

        return null
    }
}