package com.teampanlogic

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast


class AppsFlyerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_apps_flyer, container, false)

        /*
        AppsFlyerLib.getInstance().subscribeForDeepLink { deepLinkResult: DeepLinkResult ->
            if (deepLinkResult.status == DeepLinkResult.Status.FOUND) {
                val deepLinkData = deepLinkResult.deepLink

                // रिफ़रल कोड प्राप्त करें
                val referralCode = deepLinkData?.getStringValue("referral_code")
                if (referralCode != null) {
                    saveReferralCode(referralCode)
                    Log.d("Referral Code", "रिफ़रल कोड प्राप्त हुआ: $referralCode")
                    Toast.makeText(requireContext(), "Referral Code: $referralCode", Toast.LENGTH_SHORT).show()
                } else {
                    Log.d("Referral Code", "कोई रिफ़रल कोड नहीं मिला")
                }
            } else {
                Log.d("DeepLink Error", "Deeplink नहीं मिला या डेटा में समस्या है")
            }
        }

         */

        return view
    }
    /*
    private fun saveReferralCode(referralCode: String) {
        // रिफ़रल कोड को SharedPreferences में सेव करें
        val sharedPreferences = requireContext().getSharedPreferences("app_prefs", android.content.Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("referral_code", referralCode).apply()
    }

     */


}