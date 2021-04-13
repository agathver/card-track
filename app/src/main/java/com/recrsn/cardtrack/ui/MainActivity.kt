package com.recrsn.cardtrack.ui

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.recrsn.cardtrack.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (isSmsPermissionAvailable()) {
            return continueToApp()
        }

        val getStartedButton: Button = findViewById(R.id.splash_getting_started_button)
        getStartedButton.visibility = View.VISIBLE
        getStartedButton.setOnClickListener {
            requestSmsPermission()
        }
    }


    private fun isSmsPermissionAvailable() =
        ContextCompat.checkSelfPermission(
            this, Manifest.permission.READ_SMS
        ) == PackageManager.PERMISSION_GRANTED

    private fun requestSmsPermission() {
        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    showDisclaimerUI { _, _ ->
                        continueToApp()
                    }
                } else {
                    showRequiresSmsPermissionUI(null)
                }
            }
        showRequiresSmsPermissionUI() { _, _ ->
            requestPermissionLauncher.launch(Manifest.permission.READ_SMS)
        }
    }

    private fun showDisclaimerUI(then: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this)
            .setTitle(R.string.disclaimer_title)
            .setMessage(R.string.disclaimer_text)
            .setPositiveButton(R.string.disclaimer_confirmation, then)
            .create()
            .show()
    }

    private fun continueToApp() {
        startActivity(Intent(this, CardTrackActivity::class.java))
        finish()
    }

    private fun showRequiresSmsPermissionUI(okListener: DialogInterface.OnClickListener?) {
        AlertDialog.Builder(this)
            .setTitle(R.string.require_sms_permission_title)
            .setMessage(R.string.require_sms_permission_text)
            .setPositiveButton(R.string.ok, okListener)
            .create()
            .show()
    }
}
