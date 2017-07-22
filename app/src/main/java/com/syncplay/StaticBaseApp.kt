package com.syncplay

import android.app.Application
import android.provider.Settings

class StaticBaseApp : Application() {
    override fun onCreate() {
        //local device hostname
        val id = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        val name = "android-" + id

        DeviceManager.addDevice(AndroidPlaybackDevice(name, this))
        super.onCreate()
    }
}
