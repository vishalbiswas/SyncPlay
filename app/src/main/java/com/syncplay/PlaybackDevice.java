package com.syncplay;

/**
 * Created by vishal on 10/14/16.
 */

abstract class PlaybackDevice {
    private String Name;
    private String DeviceID;
    private PlaybackState State;
    private boolean isAttached;

    PlaybackDevice(String Name) {
        this.Name = Name;
        this.DeviceID = String.valueOf(Name.hashCode());
        this.State = PlaybackState.UNKNOWN;
        this.isAttached = true;
    }

    protected abstract void playInternal();

    protected abstract void pauseInternal();

    protected abstract void stopInternal();

    protected abstract void preplay();

    void play() {
        if (this.getState() != PlaybackState.READY) {
            preplay();
        }
        if (attached()) {
            playInternal();
        }
    }

    void pause() {
        if (attached()) {
            pauseInternal();
        }
    }

    void stop() {
        if (attached()) {
            stopInternal();
        }
    }

    String getName() {
        return Name;
    }

    void setName(String name) {
        Name = name;
    }

    String getDeviceID() {
        return DeviceID;
    }

    protected void setDeviceID(String deviceID) {
        DeviceID = deviceID;
    }

    PlaybackState getState() {
        return State;
    }

    void setState(PlaybackState state) {
        State = state;
    }

    void attach() {
        isAttached = true;
    }

    void deattach() {
        isAttached = false;
    }

    boolean attached() {
        return isAttached;
    }
}
