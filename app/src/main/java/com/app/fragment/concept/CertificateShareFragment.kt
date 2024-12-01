package com.app

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.util.Log
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

class CertificateShareFragment : Fragment() {

    private val TAG = "CertificateShareFrag"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView")
        val binding = inflater.inflate(R.layout.fragment_certificate_share, container, false)

        val recipientNameText = binding.findViewById<TextView>(R.id.recipientNameText)
        val emailText = binding.findViewById<TextView>(R.id.emailText)
        val registrationDateText = binding.findViewById<TextView>(R.id.registrationDateText)
        val courseNameText = binding.findViewById<TextView>(R.id.courseNameText)
        val shareButton = binding.findViewById<Button>(R.id.shareButton)
        val shareButtonPDF = binding.findViewById<Button>(R.id.shareButtonPDF)
        val certificateLayout = binding.findViewById<View>(R.id.CetrifaceteLayout)

        val recipientName = arguments?.getString("recipientName") ?: "N/A"
        val email = arguments?.getString("email") ?: "N/A"
        val registrationDate = arguments?.getString("registrationDate") ?: "N/A"
        val courseName = arguments?.getString("courseName") ?: "N/A"
        val certificationText = """
            This certificate is awarded in recognition of the successful completion of the course and the dedication shown by the participant in gaining knowledge and skills. 
            It is a testament to the hard work and commitment demonstrated throughout the course period. 
            The recipient of this certificate has proven their abilities in the field and is fully capable of applying the skills learned.
        """.trimIndent()

        val certificationParagraph = binding.findViewById<TextView>(R.id.certificationParagraph)
        certificationParagraph?.text = certificationText

        Log.d(TAG, "recipientName: $recipientName")
        Log.d(TAG, "email: $email")
        Log.d(TAG, "registrationDate: $registrationDate")
        Log.d(TAG, "courseName: $courseName")

        recipientNameText.text = "Name: $recipientName"
        emailText.text = "Email: $email"
        registrationDateText.text = "Registration Date: $registrationDate"
        courseNameText.text = "Course: $courseName"

        shareButton.setOnClickListener {
            Log.d(TAG, "shareButton clicked")
            val bitmap = getBitmapFromView(certificateLayout)
            Log.d(TAG, "Bitmap obtained")
            saveBitmap(bitmap)
        }

        shareButtonPDF.setOnClickListener {
            Log.d(TAG, "shareButtonPDF clicked")
            val bitmap = getBitmapFromView(certificateLayout)
            Log.d(TAG, "Bitmap obtained")
            savePDFAndShare(bitmap)
        }


        return binding
    }

    private fun savePDFAndShare(bitmap: Bitmap) {
        Log.d(TAG, "savePDFAndShare")
        val file = File(requireContext().externalCacheDir, "certificate${System.currentTimeMillis()}.pdf")
        try {
            val pdfDocument = PdfDocument()
            val pageInfo = PdfDocument.PageInfo.Builder(bitmap.width, bitmap.height, 1).create()
            val page = pdfDocument.startPage(pageInfo)
            val canvas = page.canvas
            canvas.drawBitmap(bitmap, 0f, 0f, null)
            pdfDocument.finishPage(page)

            val outputStream = FileOutputStream(file)
            pdfDocument.writeTo(outputStream)
            pdfDocument.close()
            outputStream.close()

            Toast.makeText(requireContext(), "PDF saved to ${file.absolutePath}", Toast.LENGTH_SHORT).show()

            // Share the PDF using FileProvider
            val pdfUri = FileProvider.getUriForFile(
                requireContext(),
                "${requireContext().packageName}.provider",
                file
            )
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "application/pdf"
                putExtra(Intent.EXTRA_STREAM, pdfUri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            startActivity(Intent.createChooser(shareIntent, "Share PDF"))
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


    private fun getBitmapFromView(view: View): Bitmap {
        Log.d(TAG, "getBitmapFromView")
        val measuredWidth = View.MeasureSpec.makeMeasureSpec(view.width, View.MeasureSpec.EXACTLY)
        val measuredHeight = View.MeasureSpec.makeMeasureSpec(view.height, View.MeasureSpec.EXACTLY)
        view.measure(measuredWidth, measuredHeight)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        val bitmap = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(Color.WHITE) // Set background color to white
        view.draw(canvas)
        return bitmap
    }

    private fun saveBitmap(bitmap: Bitmap) {
        Log.d(TAG, "saveBitmap")
        val file = File(requireContext().externalCacheDir, "certificate.png")
        try {
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()

            val uri = FileProvider.getUriForFile(requireContext(), "${requireContext().packageName}.provider", file)

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "image/png"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            startActivity(Intent.createChooser(shareIntent, "Share Certificate"))
            Toast.makeText(requireContext(), "Certificate saved", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d(TAG, "Error saving certificate")
            Toast.makeText(requireContext(), "Error saving certificate", Toast.LENGTH_SHORT).show()
        }
    }
}