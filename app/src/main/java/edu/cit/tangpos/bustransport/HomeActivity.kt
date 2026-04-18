package edu.cit.tangpos.bustransport

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Handle Navbar Clicks
        findViewById<View>(R.id.navHome).setOnClickListener { navController.navigate(R.id.nav_home) }
        findViewById<View>(R.id.navBook).setOnClickListener { navController.navigate(R.id.nav_book) }
        findViewById<View>(R.id.navTrips).setOnClickListener { navController.navigate(R.id.nav_trips) }
        findViewById<View>(R.id.navAccount).setOnClickListener { navController.navigate(R.id.nav_account) }
    }
}