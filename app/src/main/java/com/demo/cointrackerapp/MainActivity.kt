package com.demo.cointrackerapp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.demo.cointrackerapp.services.NotificationService
import com.demo.cointrackerapp.utils.isMyServiceRunning
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val controller = navHostFragment.navController
        navController = controller

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_list
            )
        )
        setupActionBarWithNavController(controller, appBarConfiguration)


        if (!this.isMyServiceRunning(NotificationService::class.java)) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(Intent(applicationContext, NotificationService::class.java))
            } else {
                startService(Intent(applicationContext, NotificationService::class.java))
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                navController!!.navigateUp()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}