package edu.cit.tangpos.bustransport.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.database.sqlite.transaction

class DBHelper(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {
    companion object {
        private const val DATABASE_NAME = "bustransport.db"
        private const val DATABASE_VERSION = 4

        // Table and column names
        const val TABLE_USERS = "users"
        const val USERS_ID = "id"
        const val USERS_FIRST_NAME = "first_name"
        const val USERS_MIDDLE_NAME = "middle_name"
        const val USERS_LAST_NAME = "last_name"
        const val USERS_EMAIL = "email"
        const val USERS_PASSWORD = "password"


        // Bus routes table
        const val TABLE_BUS_ROUTES = "bus_routes"
        const val BUS_ID = "id"
        const val DESTINATION = "destination"      // e.g., "San Fernando", "Carcar"
        const val BUS_NUMBER = "bus_number"
        const val BUS_CARRIER = "carrier"
        const val BUS_TYPE = "bus_type"            // "Air Conditioned" or "Ordinary"
        const val ROUTE_FARE = "route_fare"        // INTEGER (PHP)
        const val ROUTE_ETA = "route_eta"          // TEXT (e.g., "1 hr 5 mins")
        const val ON_TIME = "on_time"              // INTEGER (percentage)
        const val DEPARTURE_TIME = "departure_time"
        const val ARRIVAL_TIME = "arrival_time"


        // User book table
        const val TABLE_PASSENGERS = "passengers"
        const val USER_ID = "user_id"
        const val TRAVEL_DATE = "travel_date"
        const val PASSENGER_NAME = "passenger_name"
        const val PASSENGER_GENDER = "passenger_gender"
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

        val createBusRoutesTable = """
            CREATE TABLE $TABLE_BUS_ROUTES (
                $BUS_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $DESTINATION TEXT NOT NULL,
                $BUS_NUMBER TEXT NOT NULL,
                $BUS_CARRIER TEXT NOT NULL,
                $BUS_TYPE TEXT NOT NULL,
                $ROUTE_FARE INTEGER NOT NULL,
                $ROUTE_ETA TEXT NOT NULL,
                $ON_TIME INTEGER NOT NULL,
                $ARRIVAL_TIME TEXT NOT NULL,
                $DEPARTURE_TIME TEXT NOT NULL
            )
        """.trimIndent()

        val createPassengerTable = """
            CREATE TABLE $TABLE_PASSENGERS (
                $USER_ID INTEGER NOT NULL,
                $BUS_NUMBER TEXT NOT NULL,
                $PASSENGER_NAME TEXT NOT NULL,
                $PASSENGER_GENDER TEXT NOT NULL,
                $TRAVEL_DATE TEXT NOT NULL,

                FOREIGN KEY ($USER_ID) REFERENCES $TABLE_USERS($USERS_ID),
                FOREIGN KEY ($BUS_NUMBER) REFERENCES $TABLE_BUS_ROUTES($BUS_NUMBER)
            )
        """.trimIndent()

        db.execSQL(createUsersTable)
        db.execSQL(createBusRoutesTable)
        db.execSQL(createPassengerTable)

        seedBusRoutes(db)
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_BUS_ROUTES")

        if (db != null) {
            onCreate(db)
        }
    }

    private fun seedBusRoutes(db: SQLiteDatabase) {
        val cursor = db.rawQuery("SELECT COUNT(*) FROM $TABLE_BUS_ROUTES", null)

        cursor.use {
            it.moveToFirst()
            if (it.getInt(0) > 0) {
                return  // Data already seeded, no need to insert again
            }
        }

        val routes = listOf(
            // San Fernando
            Route(
                "San Fernando",
                "SF-102",
                "Ceres Bus Liner",
                "Air Conditioned",
                55,
                "1 hr 5 mins",
                93,
                "05:00",
                "06:05"

            ),
            Route(
                "San Fernando",
                "SF-214",
                "Metro Shuttle",
                "Ordinary",
                42,
                "1 hr 18 mins",
                88,
                "06:15",
                "07:33"
            ),
            Route(
                "San Fernando",
                "SF-318",
                "Sunrays Transit",
                "Ordinary",
                45,
                "1 hr 25 mins",
                85,
                "07:30",
                "08:55"
            ),

            // Carcar
            Route(
                "Carcar",
                "CC-120",
                "Ceres Bus Liner",
                "Air Conditioned",
                75,
                "1 hr 35 mins",
                95,
                "04:30",
                "06:05"
            ),
            Route(
                "Carcar",
                "CC-401",
                "Metro Shuttle",
                "Ordinary",
                58,
                "1 hr 45 mins",
                90,
                "05:45",
                "07:30"
            ),
            Route(
                "Carcar",
                "CC-244",
                "Metro Shuttle",
                "Ordinary",
                60,
                "1 hr 50 mins",
                87,
                "06:30",
                "08:20"
            ),
            Route(
                "Carcar",
                "CC-517",
                "Sunrays Transit",
                "Ordinary",
                55,
                "1 hr 58 mins",
                84,
                "08:00",
                "09:58"
            ),
            Route(
                "Carcar",
                "CC-882",
                "Sunrays Transit",
                "Ordinary",
                62,
                "2 hrs 5 mins",
                82,
                "09:15",
                "11:20"
            ),

            // Sibonga
            Route(
                "Sibonga",
                "SB-155",
                "Ceres Bus Liner",
                "Air Conditioned",
                88,
                "2 hrs 10 mins",
                94,
                "06:00",
                "08:10"
            ),
            Route(
                "Sibonga",
                "SB-302",
                "V-Hire Operators",
                "Air Conditioned",
                95,
                "1 hr 55 mins",
                91,
                "05:15",
                "07:10"
            ),
            Route(
                "Sibonga",
                "SB-445",
                "Metro Shuttle",
                "Ordinary",
                72,
                "2 hrs 25 mins",
                86,
                "07:15",
                "09:40"
            ),
            Route(
                "Sibonga",
                "SB-581",
                "Sunrays Transit",
                "Ordinary",
                78,
                "2 hrs 30 mins",
                84,
                "08:45",
                "11:15"
            ),

            // Argao
            Route(
                "Argao",
                "AG-220",
                "Ceres Bus Liner",
                "Air Conditioned",
                98,
                "2 hrs 35 mins",
                93,
                "04:45",
                "11:15"
            ),
            Route(
                "Argao",
                "AG-344",
                "Metro Shuttle",
                "Ordinary",
                85,
                "2 hrs 50 mins",
                86,
                "06:15",
                "09:15"
            ),
            Route(
                "Argao",
                "AG-611",
                "Sunrays Transit",
                "Ordinary",
                88,
                "3 hrs",
                82,
                "07:45",
                "10:45"
            ),

            // Dalaguete
            Route(
                "Dalaguete",
                "DG-801",
                "Ceres Bus Liner",
                "Air Conditioned",
                145,
                "3 hrs 40 mins",
                95,
                "06:45",
                "10:25"
            ),
            Route(
                "Dalaguete",
                "DG-244",
                "V-Hire Operators",
                "Air Conditioned",
                155,
                "3 hrs 20 mins",
                92,
                "05:30",
                "08:50"
            ),
            Route(
                "Dalaguete",
                "DG-521",
                "Metro Shuttle",
                "Ordinary",
                125,
                "4 hrs",
                85,
                "08:15",
                "12:15"
            ),
            Route(
                "Dalaguete",
                "DG-638",
                "Sunrays Transit",
                "Ordinary",
                130,
                "4 hrs 15 mins",
                81,
                "09:30",
                "01:45"
            ),

            // Oslob
            Route(
                "Oslob",
                "OS-111",
                "Ceres Bus Liner",
                "Air Conditioned",
                205,
                "4 hrs 45 mins",
                94,
                "05:15",
                "10:00"
            ),
            Route(
                "Oslob",
                "OS-214",
                "V-Hire Operators",
                "Air Conditioned",
                215,
                "4 hrs 20 mins",
                91,
                "04:00",
                "08:20"
            ),
            Route(
                "Oslob",
                "OS-522",
                "Metro Shuttle",
                "Ordinary",
                182,
                "5 hrs 10 mins",
                84,
                "07:00",
                "12:10"
            ),

            // Santander
            Route(
                "Santander",
                "ST-770",
                "Ceres Bus Liner",
                "Air Conditioned",
                245,
                "5 hrs 40 mins",
                95,
                "05:30",
                "11:10"
            ),
            Route(
                "Santander",
                "ST-331",
                "V-Hire Operators",
                "Air Conditioned",
                255,
                "5 hrs 15 mins",
                92,
                "04:15",
                "09:30"
            ),
            Route(
                "Santander",
                "ST-422",
                "Sunrays Transit",
                "Ordinary",
                220,
                "6 hrs",
                83,
                "07:15",
                "13:15"
            ),

            // Samboan
            Route(
                "Samboan",
                "SM-290",
                "Ceres Bus Liner",
                "Air Conditioned",
                210,
                "5 hrs 5 mins",
                93,
                "05:00",
                "10:05"
            ),
            Route(
                "Samboan",
                "SM-501",
                "Metro Shuttle",
                "Ordinary",
                190,
                "5 hrs 30 mins",
                85,
                "06:30",
                "12:00"
            ),
            Route(
                "Samboan",
                "SM-688",
                "Sunrays Transit",
                "Ordinary",
                195,
                "5 hrs 40 mins",
                82,
                "08:00",
                "13:40"
            ),

            // Badian
            Route(
                "Badian",
                "BD-422",
                "Ceres Bus Liner",
                "Air Conditioned",
                175,
                "4 hrs 10 mins",
                94,
                "05:45",
                "09:55"
            ),
            Route(
                "Badian",
                "BD-611",
                "Metro Shuttle",
                "Ordinary",
                158,
                "4 hrs 35 mins",
                86,
                "07:00",
                "11:35"
            ),
            Route(
                "Badian",
                "BD-704",
                "Sunrays Transit",
                "Ordinary",
                165,
                "4 hrs 45 mins",
                83,
                "08:30",
                "13:15"
            ),

            // Moalboal
            Route(
                "Moalboal",
                "MB-201",
                "Ceres Bus Liner",
                "Air Conditioned",
                195,
                "3 hrs 50 mins",
                95,
                "06:15",
                "10:05"
            ),
            Route(
                "Moalboal",
                "MB-310",
                "V-Hire Operators",
                "Air Conditioned",
                205,
                "3 hrs 30 mins",
                91,
                "05:15",
                "08:45"
            ),
            Route(
                "Moalboal",
                "MB-566",
                "Metro Shuttle",
                "Ordinary",
                165,
                "4 hrs 20 mins",
                85,
                "07:30",
                "11:50"
            ),
            Route(
                "Moalboal",
                "MB-633",
                "Sunrays Transit",
                "Ordinary",
                170,
                "4 hrs 30 mins",
                82,
                "09:00",
                "13:30"
            ),

            // Aloguinsan
            Route(
                "Aloguinsan",
                "AL-144",
                "Ceres Bus Liner",
                "Air Conditioned",
                145,
                "3 hrs 10 mins",
                92,
                "06:00",
                "09:10"
            ),
            Route(
                "Aloguinsan",
                "AL-377",
                "Metro Shuttle",
                "Ordinary",
                125,
                "3 hrs 35 mins",
                84,
                "07:30",
                "11:05"
            ),
            Route(
                "Aloguinsan",
                "AL-599",
                "Sunrays Transit",
                "Ordinary",
                130,
                "3 hrs 45 mins",
                82,
                "09:00",
                "12:45"
            ),

            // Barili
            Route(
                "Barili",
                "BR-202",
                "Ceres Bus Liner",
                "Air Conditioned",
                135,
                "2 hrs 50 mins",
                93,
                "05:30",
                "08:20"
            ),
            Route(
                "Barili",
                "BR-414",
                "Metro Shuttle",
                "Ordinary",
                115,
                "3 hrs 15 mins",
                86,
                "07:00",
                "10:15"
            ),
            Route(
                "Barili",
                "BR-688",
                "Sunrays Transit",
                "Ordinary",
                120,
                "3 hrs 25 mins",
                83,
                "08:30",
                "11:55"
            )
        )

        val insertStmt = """
            INSERT INTO $TABLE_BUS_ROUTES 
            ($DESTINATION, $BUS_NUMBER, $BUS_CARRIER, $BUS_TYPE, 
             $ROUTE_FARE, $ROUTE_ETA, $ON_TIME, $DEPARTURE_TIME, $ARRIVAL_TIME)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """.trimIndent()

        db.transaction {
            val statement = compileStatement(insertStmt)
            for (route in routes) {
                statement.bindString(1, route.destination)
                statement.bindString(2, route.busNumber)
                statement.bindString(3, route.carrier)
                statement.bindString(4, route.busType)
                statement.bindLong(5, route.fare.toLong())
                statement.bindString(6, route.eta)
                statement.bindLong(7, route.onTime.toLong())
                statement.bindString(8, route.departureTime)
                statement.bindString(9, route.arrivalTime)
                statement.executeInsert()
            }
        }
    }

    // Helper data class
    data class Route(
        val destination: String,
        val busNumber: String,
        val carrier: String,
        val busType: String,
        val fare: Int,
        val eta: String,
        val onTime: Int,
        val departureTime: String,
        val arrivalTime: String
    )
}
