package com.syncplay

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log

import java.io.IOException

import android.content.ContentValues.TAG

internal class AndroidPlaybackDevice(Name: String, appContext: Context) : PlaybackDevice(Name) {
    private val appContext: Context = appContext.applicationContext
    private val startIntent:Intent = Intent(this.appContext, PlayerService::class.java)


    private val mConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as PlayerService.LocalBinder
            serviceHandle = binder.service
            mBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }

    override fun playInternal() {
        if (mBound) {
            serviceHandle!!.play()
            state = PlaybackState.PLAY
        }
    }

    override fun pauseInternal() {
        if (mBound) {
            serviceHandle!!.pause()
            state = PlaybackState.PAUSE
        }
    }

    override fun stopInternal() {
        if (mBound) {
            try {
                serviceHandle!!.stop()
            } catch (e: IOException) {
                Log.e(TAG, "playbackDevice: Unable to stop service")
            }

            appContext.unbindService(mConnection)
            state = PlaybackState.STOP
        }
    }

    override fun preplay() {
        if (!mBound) {
            state = PlaybackState.READY
            appContext.bindService(startIntent, mConnection, Context.BIND_AUTO_CREATE)
        }
    }

    companion object {
        private var serviceHandle: PlayerService? = null
        private var mBound = false
    }
}
