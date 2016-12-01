package com.syncplay;

import android.app.Application;
import android.provider.Settings;

/**
 * Created by vishal on 11/8/16.
 */

public class StaticBaseApp extends Application {
    @Override
    public void onCreate() {
        //local device hostname
        String id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        String name = "android-".concat(id);

        DeviceManager.addDevice(new AndroidPlaybackDevice(name, this));
        super.onCreate();
    }
}
