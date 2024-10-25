package com.teampanlogic


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.razorpay.Checkout
import org.json.JSONObject


class RazorPayFragment : Fragment() {

    private lateinit var buyNowButton: Button
    private lateinit var productImage: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_razor_pay, container, false)
        initialization(view)
        setupListener()

        return view
    }

    private fun setupListener() {
        buyNowButton.setOnClickListener {
            val checkout = Checkout()
            checkout.setKeyID("rzp_test_6YJHs7dFV5QKZL")
            try {
                val options = JSONObject()
                options.put("name", "Team Panlogic Solution Team")
                options.put("description", "Payment for product")
                options.put("theme.color", "#3399CC")
                options.put("currency", "INR")
                options.put("amount", 1000) // Amount in paise (1000 paise = ₹10)

                val prefill = JSONObject()
                prefill.put("email", "pKx2F@example.com")
                prefill.put("contact", "9999999999")
                options.put("prefill", prefill)

                // Use requireActivity() to pass the current activity context
                checkout.open(requireActivity(), options)
            } catch (e: Exception) {
                Toast.makeText(activity, "Error in payment: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initialization(view: View) {
        productImage = view.findViewById(R.id.productImage)
        buyNowButton = view.findViewById(R.id.buyNowButton)
    }
}
