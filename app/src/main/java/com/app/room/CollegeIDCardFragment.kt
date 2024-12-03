package com.app.room

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.teampanlogic.R

class CollegeIDCardFragment : Fragment() {

    private lateinit var ivStudentPhoto: ImageView
    private lateinit var tvFullName: TextView
    private lateinit var tvRollNumber: TextView
    private lateinit var tvCourse: TextView
    private lateinit var tvDepartment: TextView
    private lateinit var tvFooter: TextView

    // Dummy data for the student (you can replace this with actual data)
    private val studentName = "John Doe"
    private val studentRollNumber = "12345"
    private val studentCourse = "B.Tech"
    private val studentDepartment = "CSE"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the fragment layout
        val rootView = inflater.inflate(R.layout.college_card, container, false)

        // Initialize views
        ivStudentPhoto = rootView.findViewById(R.id.ivStudentPhoto)
        tvFullName = rootView.findViewById(R.id.tvFullName)
        tvRollNumber = rootView.findViewById(R.id.tvRollNumber)
        tvCourse = rootView.findViewById(R.id.tvCourse)
        tvDepartment = rootView.findViewById(R.id.tvDepartment)
        tvFooter = rootView.findViewById(R.id.tvFooter)

        // Set the student's information dynamically
        tvFullName.text = studentName?:"Akshay "
        tvRollNumber.text = "Roll No: $studentRollNumber"?:"21025645"
        tvCourse.text = "Course: $studentCourse"?:"B.Tech"
        tvDepartment.text = "Department: $studentDepartment"?:"CSE"
        tvFooter.text = "ID Card"?:"ID Card"

        // Set the student's photo dynamically (you can replace this with an actual image or URL)
        ivStudentPhoto.setImageResource(R.drawable.circle_inactive)  // Replace with actual image or dynamic loading

        return rootView
    }
}
