package com.syncplay

internal object PlaybackManager {
    var globalPlaybackState = PlaybackState.UNKNOWN

    fun play() {
        DeviceManager.devices
                .filter { it.state != PlaybackState.ERROR && it.state != PlaybackState.DROP }
                .forEach { it.play() }

        PlaybackManager.globalPlaybackState = PlaybackState.PLAY
    }

    fun pause() {
        DeviceManager.devices
                .filter { it.state == PlaybackState.PLAY }
                .forEach { it.pause() }

        PlaybackManager.globalPlaybackState = PlaybackState.PAUSE
    }

    fun stop() {
        DeviceManager.devices
                .filter { it.state == PlaybackState.PLAY || it.state == PlaybackState.PAUSE }
                .forEach { it.stop() }

        PlaybackManager.globalPlaybackState = PlaybackState.STOP
    }

    fun smartPlay() {
        if (PlaybackManager.globalPlaybackState == PlaybackState.UNKNOWN
                || PlaybackManager.globalPlaybackState == PlaybackState.READY
                || PlaybackManager.globalPlaybackState == PlaybackState.PAUSE
                || PlaybackManager.globalPlaybackState == PlaybackState.STOP) {
            play()
        } else {
            pause()
        }
    }
}
