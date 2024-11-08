package com.teampanlogic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.android.material.navigation.NavigationView
import com.teampanlogic.R

class ProjectFragment : Fragment() {
    private lateinit var toolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_project, container, false)

        toolbar = view.findViewById(R.id.toolbar)
        toolbar.title = "Project"
        toolbar.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.white))

        // Set the navigation icon (e.g., a home icon)
        toolbar.setNavigationIcon(R.drawable.ic_home)

        // Set left padding (paddingLeft is not a property, use setPadding)
        toolbar.setPadding(10, toolbar.paddingTop, toolbar.paddingRight, toolbar.paddingBottom)
        toolbar.setNavigationOnClickListener {
            // Open navigation view and click item
            Toast.makeText(context, "Feature not implemented", Toast.LENGTH_SHORT).show()
        }
        return view
    }
}