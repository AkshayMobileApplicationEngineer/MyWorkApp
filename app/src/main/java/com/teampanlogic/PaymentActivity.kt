package com.teampanlogic

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.razorpay.Checkout
import org.json.JSONObject

class PaymentActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        // Initialize Razorpay Checkout
        Checkout.preload(applicationContext)

        // Start the payment process
        startPayment()
    }

    private fun startPayment() {
        val checkout = Checkout()
        checkout.setKeyID("rzp_test_9h4gkU3RZx1234") // Replace with your Razorpay Key ID

        // Set the amount in paisa (e.g., 50000 refers to INR 500)
        val amount = 50000 // This should be in paisa
        val orderId = "order_id_from_your_server" // Replace with your order ID from the server

        // Create a JSON object for payment details
        val options = JSONObject()
        try {
            options.put("name", "Your Company Name")
            options.put("description", "Test Transaction")
            options.put("currency", "INR")
            options.put("amount", amount)
            options.put("order_id", orderId) // Use the order_id you get from your server
            options.put("theme.color", "#F37254")

            checkout.open(this, options)
        } catch (e: Exception) {
            Log.e("RazorpayError", "Error in starting Razorpay Checkout", e)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

//        if (requestCode == Checkout.RESULT_CODE) {
//            val response = data?.getStringExtra("response")
//            Log.d("RazorpayResponse", response ?: "No Response")
//            handlePaymentResponse(response)
//        }
    }

    private fun handlePaymentResponse(response: String?) {
        // Process the response here, e.g., verify the payment with your server
        // You can send the payment details to your server for verification
    }
}