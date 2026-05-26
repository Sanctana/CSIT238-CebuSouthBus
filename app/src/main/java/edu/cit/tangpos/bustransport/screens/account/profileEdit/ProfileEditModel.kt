package edu.cit.tangpos.bustransport.screens.account.profileEdit

import android.content.ContentValues
import android.content.Context
import edu.cit.tangpos.bustransport.utilities.Utility
import edu.cit.tangpos.bustransport.database.DBHelper

class ProfileEditModel(private var context: Context) : ProfileEditContract.Model {
    private val dbHelper = DBHelper(context)

    override fun getUserId(): String? {
        return context.getSharedPreferences(Utility.APP_SHARED_PREFERENCES, Context.MODE_PRIVATE).getString("userId", null)
    }

    override fun getUserData(userId: String): ProfileEditContract.UserData? {
        dbHelper.readableDatabase.rawQuery(
            "SELECT * FROM ${DBHelper.TABLE_USERS} WHERE ${DBHelper.USERS_ID} = ?",
            arrayOf(userId)
        ).use {
            if (it.moveToFirst()){
                return ProfileEditContract.UserData(
                    it.getString(it.getColumnIndexOrThrow(DBHelper.USERS_FIRST_NAME)),
                    it.getString(it.getColumnIndexOrThrow(DBHelper.USERS_MIDDLE_NAME)),
                    it.getString(it.getColumnIndexOrThrow(DBHelper.USERS_LAST_NAME)),
                    it.getString(it.getColumnIndexOrThrow(DBHelper.USERS_EMAIL))
                )
            }
        }
        return null
    }

    override fun getStoredPassword(userId: String): String? {
        dbHelper.readableDatabase.rawQuery(
            "SELECT ${DBHelper.USERS_PASSWORD} FROM ${DBHelper.TABLE_USERS} WHERE ${DBHelper.USERS_ID} = ?",
            arrayOf(userId)
        ).use {
            if (it.moveToFirst()) return it.getString(0)
        }
        return null
    }

    override fun updateProfile(userId: String, email: String?, newHashedPw: String?): Boolean {
        val values = ContentValues()
        if (email != null) values.put(DBHelper.USERS_EMAIL, email)
        if (newHashedPw != null) values.put(DBHelper.USERS_PASSWORD, newHashedPw)

        if (values.size() == 0) return false

        return dbHelper.writableDatabase.update(
            DBHelper.TABLE_USERS,
            values,
            "${DBHelper.USERS_ID} = ?",
            arrayOf(userId)
        ) > 0
    }
}
