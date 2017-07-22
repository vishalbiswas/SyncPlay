package com.syncplay

import java.util.ArrayList

internal object DeviceManager {
    var devices = ArrayList<PlaybackDevice>()

    fun addDevice(device: PlaybackDevice) {
        device.attach()
        devices.add(device)
    }

    fun removeDevice(index: Int) {
        attachDevice(index)
        devices.removeAt(index)
    }

    fun size(): Int = devices.size

    fun getDevice(index: Int): PlaybackDevice = devices[index]

    fun attachDevice(index: Int) {
        devices[index].attach()
    }

    fun deattachDevice(index: Int) {
        devices[index].deattach()
    }
}
