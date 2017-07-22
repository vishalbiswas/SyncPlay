package com.syncplay

internal abstract class PlaybackDevice(var name: String) {
    var deviceID: String = name.hashCode().toString()
        protected set
    var state: PlaybackState = PlaybackState.UNKNOWN
    var isAttached: Boolean = false
        private set

    protected abstract fun playInternal()

    protected abstract fun pauseInternal()

    protected abstract fun stopInternal()

    protected abstract fun preplay()

    fun play() {
        if (this.state != PlaybackState.READY) {
            preplay()
        }
        if (isAttached) {
            playInternal()
        }
    }

    fun pause() {
        if (isAttached) {
            pauseInternal()
        }
    }

    fun stop() {
        if (isAttached) {
            stopInternal()
        }
    }

    fun attach() {
        isAttached = true
    }

    fun deattach() {
        isAttached = false
    }
}
