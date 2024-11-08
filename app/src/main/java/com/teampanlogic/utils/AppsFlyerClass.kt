package com.teampanlogic.utils

import android.app.Application


class AppsFlyerClass: Application() {

    override fun onCreate() {
        super.onCreate()
        val devKey = "YOUR_DEV_KEY" // अपने AppsFlyer डैशबोर्ड से Dev Key डालें


        /*

        AppsFlyerLib.getInstance().init(devKey, null, this)
        AppsFlyerLib.getInstance().start(this)


         */
    }

}