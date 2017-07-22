package com.syncplay

import android.app.Service
import android.content.Intent
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.os.Binder
import android.os.IBinder
import android.util.Log

import java.io.IOException
import java.io.InputStream

import android.content.ContentValues.TAG

class PlayerService : Service() {
    internal val bufferSize = AudioTrack.getMinBufferSize(
            AudioTrack.getNativeOutputSampleRate(AudioManager.STREAM_MUSIC),
            AudioFormat.CHANNEL_OUT_STEREO,
            AudioFormat.ENCODING_DEFAULT)

    internal val buffer = ByteArray(bufferSize)

    internal val player = AudioTrack(
            AudioManager.STREAM_MUSIC,
            AudioTrack.getNativeOutputSampleRate(AudioManager.STREAM_MUSIC),
            AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_DEFAULT,
            bufferSize,
            AudioTrack.MODE_STREAM)

    internal val fileStream: InputStream? = DataManager.currentFileStream

    /**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    inner class LocalBinder : Binder() {
        internal val service: PlayerService
            get() = this@PlayerService
    }

    // This is the object that receives interactions from clients
    private val mBinder = LocalBinder()

    override fun onCreate() {
        super.onCreate()
        play()
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.d(TAG, "Service bound")
        return mBinder
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return Service.START_NOT_STICKY
    }

    internal fun play() {
        isPaused = false
        player.play()
        Thread(Runnable {
            try {
                while (!isPaused && fileStream != null && fileStream.available() > 0) {
                    fileStream.read(buffer)
                    player.write(buffer, 0, bufferSize)
                }

                pause()
            } catch (e: IOException) {
                Log.e(TAG, "playerService: An error occurred while playing")
            }
        })
    }

    internal fun pause() {
        isPaused = true
        player.stop()
    }

    @Throws(IOException::class)
    internal fun stop() {
        fileStream?.close()
        stopSelf()
    }

    companion object {
        internal var isPaused = false
    }
}
