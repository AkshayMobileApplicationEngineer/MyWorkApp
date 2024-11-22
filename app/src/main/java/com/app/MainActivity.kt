package com.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import com.teampanlogic.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        window.statusBarColor = resources.getColor(R.color.appOne, theme)


        // Automatically navigate to MasterFragment
        if (savedInstanceState == null) {
            navigateToFragment("Master")
        }

        // Handle intents sent from elsewhere
        handleIntent(intent)
    }

    private fun navigateToFragment(fragmentName: String) {
        val fragment: Fragment = when (fragmentName) {
            "Home" -> HomeFragment()
            "Example" -> ExampleFragment()
            "About" -> AboutFragment()
            "Dashboard" -> DashboardFragment()
            "Retrofit" -> RetrofitFragment()
            else -> MasterFragment() // Default fragment
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun handleIntent(intent: Intent) {
        val displayFragment = intent.getStringExtra("Display")
        if (displayFragment != null) {
            navigateToFragment(displayFragment)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }
}
