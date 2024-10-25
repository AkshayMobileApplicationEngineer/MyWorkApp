package com.panlogicsolutionteam

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import com.teampanlogic.ProfileFragment
import com.teampanlogic.R
import com.teampanlogic.RazorPayFragment
import com.teampanlogic.RoomDatabaseFragment

class MasterFragment : Fragment() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var idConcept: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_master, container, false)

        // Initialize DrawerLayout and NavigationView
        drawerLayout = view.findViewById(R.id.drawer_layout)
        navigationView = view.findViewById(R.id.navigationView)
        idConcept = view.findViewById(R.id.idConcept)

        // Set status bar color from a color resource
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.logo_color_ii)

        // Find the toolbar and set it as the ActionBar
        val toolbar: androidx.appcompat.widget.Toolbar = view.findViewById(R.id.my_toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.title = "My Team"

        // Handle drawer toggle
        idConcept.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
                Toast.makeText(context, "Drawer Closed", Toast.LENGTH_SHORT).show()
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
                Toast.makeText(context, "Drawer Opened", Toast.LENGTH_SHORT).show()
            }
        }

        // Set up navigation item listener
        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            drawerLayout.closeDrawers()

            val fragment = when (menuItem.itemId) {
                R.id.nav_home -> this // Current fragment
                R.id.nav_profile -> ProfileFragment()
                R.id.nav_roomdatabase -> RoomDatabaseFragment()
                R.id.nav_Razorpay -> RazorPayFragment()
                else -> {
                    Toast.makeText(context, "Feature not implemented", Toast.LENGTH_SHORT).show()
                    null
                }
            }

            // Replace fragment if selected
            fragment?.let {
                val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container, it)
                transaction.addToBackStack(null)
                transaction.commit()
            }

            true
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        drawerLayout.closeDrawers() // Ensures the drawer is closed when the Fragment is destroyed
    }
}
