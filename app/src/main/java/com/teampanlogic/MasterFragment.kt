package com.teampanlogic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

    private lateinit var BottomNavigationView: BottomNavigationView
    private lateinit var Toolbar: Toolbar
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.logo_color_ii)
        val view= inflater.inflate(R.layout.fragment_master, container, false)
        BottomNavigationView= view.findViewById(R.id.bottom_menu)
        Toolbar= view.findViewById(R.id.toolbar)
        // Set up toolbar
        Toolbar.title = "Home"
        Toolbar.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        Toolbar.setOnMenuItemClickListener { item ->
            var fragment: Fragment? = null
            when (item.itemId) {
                R.id.bottom_navigation_profile -> fragment = AccountFragment()
                R.id.toolbar_menu_project -> fragment = SettingFragment()
            }
            fragment?.let {
                val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container, it)
                transaction.addToBackStack(null)
                transaction.commit()
            }
            true
        }
        // Set up fragment
        loadFragment(HomeFragment())
        // Set up bottom navigation listener
        BottomNavigationView.setOnItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.bottom_navigation_home -> HomeFragment() // No need to reload HomeFragment since it's already loaded initially
                R.id.bottom_navigation_profile -> AccountFragment()
                R.id.bottom_navigation_settings -> SettingFragment()
                R.id.bottom_navigation_notification -> NotificationFragment()
                R.id.bottom_navigation_admin_pannel -> AdminPannelFragment()
                else -> {
                    Toast.makeText(context, "Feature not implemented", Toast.LENGTH_SHORT).show()
                    null
                }
            }

            // Replace fragment if selected
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


}