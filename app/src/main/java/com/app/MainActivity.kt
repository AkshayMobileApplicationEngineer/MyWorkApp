package com.app

import AdsTrackerFragment
import CertificateFragment
import android.content.Intent
import android.os.Bundle
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.teampanlogic.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.statusBarColor = resources.getColor(R.color.appOne, theme)

        // Enable edge-to-edge support for devices with Android 10 and above


        // Automatically navigate to MasterFragment if this is the first time creating the activity
        if (savedInstanceState == null) {
            navigateToFragment("Master")
        }

        // Handle intents sent from elsewhere (e.g., navigating to a different fragment)
        handleIntent(intent)
    }

    private fun navigateToFragment(fragmentName: String) {
        val fragment: Fragment = when (fragmentName) {
            "Home" -> HomeFragment()
            "Example" -> ExampleFragment()
            "About" -> AboutFragment()
            "Dashboard" -> DashboardFragment()
            "Retrofit" -> RetrofitFragment()
            "adstracker" -> AdsTrackerFragment()
            "form" -> CertificateFragment()
            "shareCertificate" ->createCertificateShareFragment()
            else -> MasterFragment() // Default fragment
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun createCertificateShareFragment(): Fragment {
        // Retrieve the data passed from the previous fragment via Intent
        val recipientName = intent.getStringExtra("recipientName")
        val email = intent.getStringExtra("email")
        val registrationDate = intent.getStringExtra("registrationDate")
        val courseName = intent.getStringExtra("courseName")

        // Create an instance of CertificateShareFragment and pass the data using Bundle
        val certificateShareFragment = CertificateShareFragment()
        val bundle = Bundle().apply {
            putString("recipientName", recipientName)
            putString("email", email)
            putString("registrationDate", registrationDate)
            putString("courseName", courseName)
        }

        certificateShareFragment.arguments = bundle
        return certificateShareFragment
    }

    private fun handleIntent(intent: Intent) {
        val displayFragment = intent.getStringExtra("Display")
        displayFragment?.let {
            navigateToFragment(it)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }
}
