package com.teampanlogic

private fun saveReferralCode(referralCode: String) {
        // रिफ़रल कोड को SharedPreferences में सेव करें
        val sharedPreferences = requireContext().getSharedPreferences("app_prefs", android.content.Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("referral_code", referralCode).apply()
    }