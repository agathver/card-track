package com.agathver.cardtrack

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        if (isSmsPermissionUnavailable()) {
            requestForSmsPermission()
        }

    }

    private fun requestForSmsPermission() {
        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (!isGranted) {
                    showRequiresSmsPermissionUI(null)
                }
            }
        showRequiresSmsPermissionUI(DialogInterface.OnClickListener { _, _ ->
            requestPermissionLauncher.launch(Manifest.permission.READ_SMS)
        })
    }

    private fun showRequiresSmsPermissionUI(okListener: DialogInterface.OnClickListener?) {
        AlertDialog.Builder(this)
            .setMessage(R.string.require_sms_permission_test)
            .setPositiveButton("OK", okListener)
            .create()
            .show()
    }

    private fun isSmsPermissionUnavailable() =
        ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_SMS
        ) != PackageManager.PERMISSION_GRANTED
}
