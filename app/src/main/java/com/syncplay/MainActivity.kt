package com.syncplay

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageButton

import java.io.FileNotFoundException

class MainActivity : AppCompatActivity() {

    private val GET_CONTENT_ID = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById(R.id.playButton).setOnClickListener { v ->
            PlaybackManager.smartPlay()
            if (PlaybackManager.globalPlaybackState == PlaybackState.PLAY) {
                (v as ImageButton).setImageResource(android.R.drawable.ic_media_pause)
            } else {
                (v as ImageButton).setImageResource(android.R.drawable.ic_media_play)
            }
        }

        findViewById(R.id.selectFileButton).setOnClickListener {
            val selectFile = Intent(Intent.ACTION_GET_CONTENT)
            selectFile.type = "audio/*"
            startActivityForResult(selectFile, GET_CONTENT_ID)
        }

        findViewById(R.id.devicesButton).setOnClickListener {
            val showDevices = Intent(this@MainActivity, ListDevicesActivity::class.java)
            startActivity(showDevices)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            GET_CONTENT_ID -> if (resultCode == Activity.RESULT_OK) {
                val file = data.data
                try {
                    DataManager.addFile(AudioSource(contentResolver.openInputStream(file)!!, file.lastPathSegment))
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }

            }
        }
    }
}
