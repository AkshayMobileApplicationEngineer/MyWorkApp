package com.example.collegeidcard

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.app.MainActivity
import com.teampanlogic.R

class RoomFragment : Fragment(R.layout.fragment_room) {

    private lateinit var etFullName: EditText
    private lateinit var etRollNumber: EditText
    private lateinit var etCourse: EditText
    private lateinit var ivPhoto: ImageView
    private lateinit var btnSubmit: Button

    private val photoPickerLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                ivPhoto.setImageURI(it)
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the views
        etFullName = view.findViewById(R.id.etFullName)
        etRollNumber = view.findViewById(R.id.etRollNumber)
        etCourse = view.findViewById(R.id.etCourse)
        ivPhoto = view.findViewById(R.id.ivPhoto)
        btnSubmit = view.findViewById(R.id.btnSubmit)

        // Open image picker when the photo ImageView is clicked
        ivPhoto.setOnClickListener {
            photoPickerLauncher.launch("image/*")
        }

        // Handle submit button click
        btnSubmit.setOnClickListener {
            val fullName = etFullName.text.toString()
            val rollNumber = etRollNumber.text.toString()
            val course = etCourse.text.toString()

            // Simple validation
            if (fullName.isEmpty() || rollNumber.isEmpty() || course.isEmpty()) {
                Toast.makeText(requireContext(), "All fields must be filled!", Toast.LENGTH_SHORT).show()
            } else {
                // Normally, you would submit the data to your backend or save it in the database
                Toast.makeText(requireContext(), "Registration Successful!", Toast.LENGTH_SHORT).show()
                val intent= Intent(requireContext(), MainActivity::class.java)
                intent.putExtra("Display", "College_Id_Card")
                startActivity(intent)
                // You can also clear the form or navigate to another screen
                clearForm()
            }
        }
    }

    private fun clearForm() {
        etFullName.text.clear()
        etRollNumber.text.clear()
        etCourse.text.clear()
        ivPhoto.setImageResource(R.drawable.circle_inactive) // Reset photo
    }
}
