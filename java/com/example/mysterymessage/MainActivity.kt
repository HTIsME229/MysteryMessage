package com.example.mysterymessage

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mysterymessage.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val requestPermissionLaucher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGrandted ->
        if (isGrandted) {
            postNotification()
        } else showMessage(R.string.message_denied, Snackbar.LENGTH_LONG)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.bottom_nav)) { v, insets ->
            val params = v.layoutParams as ViewGroup.MarginLayoutParams
            params.bottomMargin = 0 // Loại bỏ inset

            v.layoutParams = params
            insets
        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val botoomNavControllerView = mBinding.includeMain.bottomNav
        botoomNavControllerView.setupWithNavController(navController)



        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_box_time, R.id.nav_friend,R.id.nav_letter_to_you,R.id.nav_secret_message),

            )

        setupActionBarWithNavController(navController, appBarConfiguration)
        requestPermission()
        retriveToken()
    }



    private fun retriveToken() {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    MessengerUTILS.token = task.result
                    Log.d("FCM_TOKEN", "Token retrieved successfully: ${task.result}")
                } else {
                    Log.e("FCM_TOKEN", "Failed to retrieve token", task.exception)
                }
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) {
            val selfPermissions = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            )
            val shouldShowPrompt =
                shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)
            if (selfPermissions == PackageManager.PERMISSION_GRANTED) {
                postNotification()
            } else if (shouldShowPrompt) {
                showMessage(R.string.message_per_prompt, Snackbar.LENGTH_LONG, shouldShowPrompt)
            } else {
                requestPermissionLaucher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }

        }
    }

    private fun showMessage(messageId: Int, duration: Int, showAction: Boolean = false) {
        val snackbar = Snackbar.make(mBinding.root, getString(messageId), duration)
        if (showAction && Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) {
            snackbar.setAction("OK") {
                requestPermissionLaucher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
            snackbar.setAction("No Thank") {
                showMessage(R.string.message_denied, Snackbar.LENGTH_LONG)
            }
        }
        snackbar.show()
    }

    private fun postNotification() {

    }
}