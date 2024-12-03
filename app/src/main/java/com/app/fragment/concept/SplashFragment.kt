package com.app.fragment.concept

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.VideoView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.teampanlogic.R
import android.net.Uri
import android.webkit.WebView
import android.widget.Toast
import com.app.MasterFragment

class SplashFragment : Fragment() {

    private var videoView: VideoView? = null
    private var navigateButton: Button? = null
    private var navigateButtonWebView: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_splash, container, false)

        // Initialize VideoView and Button
        videoView = view.findViewById(R.id.videoView)
        navigateButton = view.findViewById(R.id.navigateButton)
        navigateButtonWebView = view.findViewById(R.id.navigateButtonWebView)

        // Set video source
        val videoUri = Uri.parse("https://tiny-gaufre-7c214f.netlify.app/maya.mp4")
        videoView?.setVideoURI(videoUri)

        // Play video
        videoView?.start()

        // Button click listener to navigate to Master Fragment
        navigateButton?.setOnClickListener {
            // Create a new instance of MasterFragment
            val masterFragment = MasterFragment()

            // Begin fragment transaction
            val transaction = parentFragmentManager.beginTransaction()

            // Replace the current fragment with MasterFragment
            transaction.replace(R.id.fragment_container, masterFragment)
            transaction.addToBackStack(null) // Add to back stack if you want to navigate back
            transaction.commit()
        }

        // WebView button click listener
        navigateButtonWebView?.setOnClickListener {
            // Show WebView in dialog full screen
            val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.video_webview, null)
            val webView = dialogView.findViewById<WebView>(R.id.webView)
            //webView.loadUrl("https://tiny-gaufre-7c214f.netlify.app/video.html")
            webView.loadUrl("https://tiny-gaufre-7c214f.netlify.app/maya.mp4")

            // Make the WebView full screen
            webView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)

            // Create and show the dialog
            val dialog = android.app.AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .create()

            dialog.show()
        }

        // Return the view
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Release resources when fragment view is destroyed (e.g., stop video playback if needed)
        videoView?.stopPlayback()
    }
}
