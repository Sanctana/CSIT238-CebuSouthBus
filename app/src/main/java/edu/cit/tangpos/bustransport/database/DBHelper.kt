package edu.cit.tangpos.bustransport.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper (
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {
    companion object {
        private const val DATABASE_NAME = "bustransport.db"
        private const val DATABASE_VERSION = 1

        // Table and column names
        const val TABLE_USERS = "users"
        const val USERS_ID = "id"
        const val USERS_FIRST_NAME = "first_name"
        const val USERS_MIDDLE_NAME = "middle_name"
        const val USERS_LAST_NAME = "last_name"
        const val USERS_EMAIL = "email"
        const val USERS_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createUsersTable = """
            CREATE TABLE $TABLE_USERS (
                $USERS_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $USERS_FIRST_NAME TEXT NOT NULL,
                $USERS_MIDDLE_NAME TEXT,
                $USERS_LAST_NAME TEXT NOT NULL,
                $USERS_EMAIL TEXT UNIQUE,
                $USERS_PASSWORD TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(createUsersTable)
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
    }
}
