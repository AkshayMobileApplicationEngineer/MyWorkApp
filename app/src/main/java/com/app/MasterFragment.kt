package com.app

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.teampanlogic.R

class MasterFragment : Fragment() {

    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Required for menu integration in fragments
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_master, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window?.statusBarColor = resources.getColor(R.color.appOne)
        // Setup Toolbar
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        drawerLayout = view.findViewById(R.id.drawer_layout)
        navigationView = view.findViewById(R.id.navigation_view)
        bottomNavigation = view.findViewById(R.id.bottom_navigation)

        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
        toolbar.setBackgroundColor(resources.getColor(R.color.appOne))
        toolbar.setTitleTextColor(resources.getColor(R.color.white))
        toolbar.setSubtitleTextColor(resources.getColor(R.color.white))
        toolbar.title = getString(R.string.app_name)
        // Setup Drawer Toggle
        toggle = ActionBarDrawerToggle(
            requireActivity(), drawerLayout, toolbar,
            R.string.drawer_open, R.string.drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Setup Bottom Navigation
        bottomNavigation.itemIconTintList = null
        bottomNavigation.alpha = 0f
        bottomNavigation.menu.getItem(0).isChecked = true

        bottomNavigation.setBackgroundColor(resources.getColor(R.color.appOne))
        bottomNavigation.alpha = 1f

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> loadFragment(ChildFragment.newInstance("Home"))
                R.id.nav_dashboard -> loadFragment(ChildFragment.newInstance("Dashboard"))
                R.id.nav_notifications -> loadFragment(ChildFragment.newInstance("Notifications"))
            }
            true
        }

        // Setup Drawer Navigation
        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.drawer_home -> {
                    val itemIntent = Intent(requireActivity(), MainActivity::class.java)
                    itemIntent.putExtra("Display", "Home") // Navigate to HomeFragment
                    startActivity(itemIntent)
                }
                R.id.drawer_settings -> loadFragment(ChildFragment.newInstance("Settings"))
                R.id.drawer_about -> loadFragment(ChildFragment.newInstance("About"))
            }
            drawerLayout.closeDrawers()
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                // Handle search action
                true
            }
            R.id.action_plus -> {
                // Handle plus action
                dialogPlus()
                true
            }
            R.id.action_theme -> {
                // Handle theme action
                themeDayNight()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun themeDayNight() {
        val sharedPreferences = requireActivity().getSharedPreferences("panlogic", AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val mode = if (requireContext().resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_NO) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            editor.putInt("theme", AppCompatDelegate.MODE_NIGHT_YES)
            R.drawable.ic_night
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            editor.putInt("theme", AppCompatDelegate.MODE_NIGHT_NO)
            R.drawable.ic_mode
        }
        editor.apply()
        requireActivity().window.statusBarColor = resources.getColor(R.color.appOne)
        val toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar)
        toolbar.menu.findItem(R.id.action_theme).icon = resources.getDrawable(mode, null)
    }

    private fun dialogPlus() {
        AlertDialog.Builder(requireContext())
            .setTitle("Add Activity or Fragment")
            .setMessage("We can add activity or fragment")
            .setPositiveButton("Activity") { _, _ ->
                // Handle positive button click
                Toast.makeText(context, "feature coming soon", Toast.LENGTH_SHORT).show()
                val itemIntent = Intent(requireActivity(), IDActivity::class.java)
                startActivity(itemIntent)
            }
            .setNegativeButton("Fragment") { _, _ ->
                // Handle negative button click
                val itemIntent = Intent(requireActivity(), MainActivity::class.java)
                itemIntent.putExtra("Display", "form") // Navigate to HomeFragment
                startActivity(itemIntent)
            }
            .create()
            .show()
    }
}
