package com.mackosoft.testtrax

import android.app.Application
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.crashes.Crashes

class MyMoviesApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // init App Center integration
        if (BuildConfig.USES_APP_CENTER) {
            AppCenter.start(
                this,
                getString(R.string.app_center_app_secret),
                Crashes::class.java
            )
        }
    }

}