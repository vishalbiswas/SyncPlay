package com.syncplay

import android.util.Log

import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.util.ArrayList

import android.content.ContentValues.TAG

internal object DataManager {
    private val srcList = ArrayList<AudioSource>()
    private var currentPathIndex = -1
    var currentFileStream: InputStream? = null
        private set

    fun addFile(source: AudioSource) {
        closeCurrentStream()
        srcList.add(source)
        currentPathIndex = srcList.size - 1
        openCurrentStream()
    }

    private fun openCurrentStream() {
        if (currentPathIndex >= 0) {
            try {
                currentFileStream = srcList[currentPathIndex].stream
            } catch (e: FileNotFoundException) {
                Log.e(TAG, "dataManager: Unable to find the audio file")
            }

        }
    }

    private fun closeCurrentStream() {
        if (currentPathIndex >= 0) {
            try {
                currentFileStream?.close()
            } catch (e: IOException) {
                Log.e(TAG, "dataManager: Unable to close current file stream")
            }

        }
    }
}
