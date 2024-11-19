package com.teampanlogic

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.teampanlogic.MasterScreen.AccountFragment
import com.teampanlogic.MasterScreen.AdminPannelFragment
import com.teampanlogic.MasterScreen.HomeFragment
import com.teampanlogic.MasterScreen.NotificationFragment
import com.teampanlogic.MasterScreen.SettingFragment

class MasterFragment : Fragment() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var toolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.logo_color_ii)
        val view = inflater.inflate(R.layout.fragment_master, container, false)

        bottomNavigationView = view.findViewById(R.id.bottom_menu)
        toolbar = view.findViewById(R.id.toolbar)

        // Set up toolbar
        toolbar.title = "Home"
        toolbar.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        toolbar.setNavigationIcon(R.drawable.ic_home)
        toolbar.setNavigationOnClickListener {
            Toast.makeText(context, "Navigation icon clicked", Toast.LENGTH_SHORT).show()
        }

        toolbar.setOnMenuItemClickListener { item ->
            var fragment: Fragment? = null
            when (item.itemId) {
                R.id.bottom_navigation_profile -> fragment = ProjectFragment()
                R.id.toolbar_menu_project -> fragment = ProjectFragment()
                R.id.toolbar_menu_appsflyer->fragment=AppsFlyerFragment()
                R.id.toolbar_menu_mode -> setThemeForMode(item)
                R.id.toolbar_menu_lightmode -> setThemeForMode(item)
                R.id.toolbar_menu_ai_chat->fragment=ChatFragment()
                else -> Toast.makeText(context, "Feature not implemented", Toast.LENGTH_SHORT).show()
            }
            fragment?.let {
                val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container, it)
                transaction.addToBackStack(null)
                transaction.commit()
            }
            true
        }

        // Load initial fragment
        loadFragment(HomeFragment())

        // Set up bottom navigation listener
        bottomNavigationView.setOnItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.bottom_navigation_home -> HomeFragment()
                R.id.bottom_navigation_profile -> AccountFragment()
                R.id.bottom_navigation_settings -> SettingFragment()
                R.id.bottom_navigation_notification -> NotificationFragment()
                R.id.bottom_navigation_admin_pannel -> AdminPannelFragment()
                else -> {
                    Toast.makeText(context, "Feature not implemented", Toast.LENGTH_SHORT).show()
                    null
                }
            }

            fragment?.let {
                val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_master, it)
                transaction.addToBackStack(null)
                transaction.commit()
            }
            true
        }

        return view
    }

    // Function to load the fragment
    private fun loadFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_master, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    private fun setThemeForMode(menuItem: MenuItem) {
        when (menuItem.itemId) {
            R.id.toolbar_menu_mode -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                menuItem.isVisible = false
                toolbar.menu.findItem(R.id.toolbar_menu_lightmode).isVisible = true
            }
            R.id.toolbar_menu_lightmode -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                menuItem.isVisible = false
                toolbar.menu.findItem(R.id.toolbar_menu_mode).isVisible = true
            }
        }
    }

}
