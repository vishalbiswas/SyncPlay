package com.syncplay;

import java.util.ArrayList;

/**
 * Created by vishal on 10/14/16.
 */

class PlaybackManager {
    static PlaybackState globalPlaybackState = PlaybackState.UNKNOWN;

    static void play() {
        for (PlaybackDevice device:
                DeviceManager.devices) {
            if (device.getState() != PlaybackState.ERROR && device.getState() != PlaybackState.DROP) {
                device.play();
            }
        }

        PlaybackManager.globalPlaybackState = PlaybackState.PLAY;
    }

    static void pause() {
        for (PlaybackDevice device :
                DeviceManager.devices) {
            if (device.getState() == PlaybackState.PLAY) {
                device.pause();
            }
        }

        PlaybackManager.globalPlaybackState = PlaybackState.PAUSE;
    }

    static void stop() {
        for (PlaybackDevice device :
                DeviceManager.devices) {
            if (device.getState() == PlaybackState.PLAY || device.getState() == PlaybackState.PAUSE) {
                device.stop();
            }
        }

        PlaybackManager.globalPlaybackState = PlaybackState.STOP;
    }

    static void smartPlay() {
        if (PlaybackManager.globalPlaybackState == PlaybackState.UNKNOWN
                || PlaybackManager.globalPlaybackState == PlaybackState.READY
                || PlaybackManager.globalPlaybackState == PlaybackState.PAUSE
                || PlaybackManager.globalPlaybackState == PlaybackState.STOP) {
            play();
        } else {
            pause();
        }
    }
}
