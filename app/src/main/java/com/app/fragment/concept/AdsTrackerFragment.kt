// AdsTrackerFragment.kt
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.teampanlogic.R

class AdsTrackerFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var pageText: TextView
    private lateinit var circleIndicators: LinearLayout
    private val impressions = mutableMapOf<Int, Int>()
    private val clicks = mutableMapOf<Int, Int>()
    private val uniqueUsers = mutableSetOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ads_tracker, container, false)

        viewPager = view.findViewById(R.id.viewPager)
        pageText = view.findViewById(R.id.pageText)
        circleIndicators = view.findViewById(R.id.circleIndicators)

        // Example images
        val images = listOf(R.drawable.image1, R.drawable.image2, R.drawable.image3)
        val adapter = ImageAdapter(images) { position ->
            // Track click
            clicks[position] = (clicks[position] ?: 0) + 1
        }
        viewPager.adapter = adapter

        // Dynamically create circle indicators
        createCircleIndicators(images.size)

        // Automatically update circle indicators when page changes
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                // Track impressions and unique users
                impressions[position] = (impressions[position] ?: 0) + 1
                uniqueUsers.add("User${position}")

                // Update page text
                pageText.text = "Page ${position + 1}"

                // Automatically update the circle indicators
                updateCircleIndicators(position)
            }
        })

        return view
    }

    // Function to create circle indicators
    private fun createCircleIndicators(count: Int) {
        for (i in 0 until count) {
            val circle = ImageView(requireContext()).apply {
                setImageResource(R.drawable.circle_inactive) // Use your own inactive circle drawable
                layoutParams = ViewGroup.LayoutParams(
                    resources.getDimensionPixelSize(R.dimen.circle_size),
                    resources.getDimensionPixelSize(R.dimen.circle_size)
                )
                setPadding(8, 8, 8, 8)
            }
            circleIndicators.addView(circle)
        }
    }

    // Function to update the active/inactive state of circles automatically
    private fun updateCircleIndicators(position: Int) {
        for (i in 0 until circleIndicators.childCount) {
            val circle = circleIndicators.getChildAt(i) as ImageView
            if (i == position) {
                circle.setImageResource(R.drawable.circle_active) // Use your own active circle drawable
            } else {
                circle.setImageResource(R.drawable.circle_inactive) // Use your own inactive circle drawable
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Log or display tracking data
        println("Tracking Data - Impressions: $impressions")
        println("Tracking Data - Clicks: $clicks")
        println("Tracking Data - Unique Users: ${uniqueUsers.size}")
    }
}
