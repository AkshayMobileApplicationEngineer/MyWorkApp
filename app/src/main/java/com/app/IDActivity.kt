package com.app
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.FileProvider
import com.teampanlogic.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class IDActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_id)

        val shareButton: Button = findViewById(R.id.shareButton)
        shareButton.setOnClickListener {
            val idCardImage = getBitmapFromView(findViewById(R.id.idCardLayout))
            shareImage(idCardImage)
        }
    }

    // Convert the view to Bitmap
    private fun getBitmapFromView(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = android.graphics.Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    // Share the image using FileProvider
    private fun shareImage(bitmap: Bitmap) {
        try {
            // Save the bitmap to a file
            val file = File(getExternalFilesDir(null), "college_id_card.png")
            val fileOutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
            fileOutputStream.flush()
            fileOutputStream.close()

            // Get the URI for the file using FileProvider
            val context = applicationContext
            val imageUri: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.provider", // Same as declared in the manifest
                    file
                )
            } else {
                Uri.fromFile(file)
            }

            // Create the share intent
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "image/png"
            intent.putExtra(Intent.EXTRA_STREAM, imageUri)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // Grant permission for reading the URI

            // Start the share activity
            startActivity(Intent.createChooser(intent, "Share ID Card"))
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
