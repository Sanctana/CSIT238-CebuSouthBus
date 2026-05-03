package edu.cit.tangpos.bustransport.screens.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import edu.cit.tangpos.bustransport.R

class HomeActivity : AppCompatActivity(), HomeContract.View {

    lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        navController = (supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController

        // Handle Navbar Clicks
        findViewById<View>(R.id.navHome).setOnClickListener {
            navigateToHome()
        }
        findViewById<View>(R.id.navBook).setOnClickListener {
            navigateToBooking();
        }
        findViewById<View>(R.id.navTrips).setOnClickListener {
            navigateToTrips()
        }
        findViewById<View>(R.id.navAccount).setOnClickListener {
            navigateToAccount()
        }
    }

    override fun navigateToHome() {
        navController.navigate(R.id.nav_home)
    }

    override fun navigateToBooking() {
        navController.navigate(R.id.nav_book)
    }

    override fun navigateToTrips() {
        navController.navigate(R.id.nav_trips)
    }

    override fun navigateToAccount() {
        navController.navigate(R.id.nav_account)
    }
}