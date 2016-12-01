package com.syncplay;

import java.util.ArrayList;

/**
 * Created by vishal on 10/14/16.
 */

class DeviceManager {
    static ArrayList<PlaybackDevice> devices = new ArrayList<>();

    static void addDevice(PlaybackDevice device) {
        device.attach();
        devices.add(device);
    }

    static void removeDevice(int index) {
        attachDevice(index);
        devices.remove(index);
    }

    static int size() {
        return devices.size();
    }

    static PlaybackDevice getDevice(int index) {
        return devices.get(index);
    }

    static void attachDevice(int index) {
        devices.get(index).attach();
    }

    static void deattachDevice(int index) {
        devices.get(index).deattach();
    }
}
