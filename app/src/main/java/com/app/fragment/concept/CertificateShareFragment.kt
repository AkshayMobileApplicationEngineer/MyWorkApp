package com.app

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.teampanlogic.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

class CertificateShareFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = inflater.inflate(R.layout.fragment_certificate_share, container, false)

        val recipientNameText = binding.findViewById<TextView>(R.id.recipientNameText)
        val emailText = binding.findViewById<TextView>(R.id.emailText)
        val registrationDateText = binding.findViewById<TextView>(R.id.registrationDateText)
        val courseNameText = binding.findViewById<TextView>(R.id.courseNameText)
        val shareButton = binding.findViewById<Button>(R.id.shareButton)
        val certificateLayout = binding.findViewById<View>(R.id.CetrifaceteLayout)

        val recipientName = arguments?.getString("recipientName") ?: "N/A"
        val email = arguments?.getString("email") ?: "N/A"
        val registrationDate = arguments?.getString("registrationDate") ?: "N/A"
        val courseName = arguments?.getString("courseName") ?: "N/A"

        recipientNameText.text = "Name: $recipientName"
        emailText.text = "Email: $email"
        registrationDateText.text = "Registration Date: $registrationDate"
        courseNameText.text = "Course: $courseName"

        shareButton.setOnClickListener {
            val bitmap = getBitmapFromView(certificateLayout)
            saveBitmap(bitmap)
        }

        return binding
    }

    private fun getBitmapFromView(view: View): Bitmap {
        val measuredWidth = View.MeasureSpec.makeMeasureSpec(view.width, View.MeasureSpec.EXACTLY)
        val measuredHeight = View.MeasureSpec.makeMeasureSpec(view.height, View.MeasureSpec.EXACTLY)
        view.measure(measuredWidth, measuredHeight)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        val bitmap = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    private fun saveBitmap(bitmap: Bitmap) {
        val file = File(requireContext().externalCacheDir, "certificate.png")
        try {
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()

            val uri = FileProvider.getUriForFile(requireContext(), "com.example.app.fileprovider", file)

            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "image/png"
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
            startActivity(Intent.createChooser(shareIntent, "Share Certificate"))
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}

