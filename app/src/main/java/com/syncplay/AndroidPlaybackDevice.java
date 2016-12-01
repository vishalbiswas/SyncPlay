package com.syncplay;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;

import javax.security.auth.login.LoginException;

import static android.content.ContentValues.TAG;

/**
 * Created by vishal on 11/4/16.
 */

class AndroidPlaybackDevice extends PlaybackDevice {
    private Context appContext;
    private static PlayerService serviceHandle;
    private static boolean mBound = false;
    private static Intent startIntent;

    AndroidPlaybackDevice(String Name, Context appContext) {
        super(Name);
        this.appContext = appContext;
        startIntent = new Intent(appContext, PlayerService.class);
    }

    @Override
    protected void playInternal() {
        if (mBound) {
            serviceHandle.play();
            setState(PlaybackState.PLAY);
        }
    }

    @Override
    protected void pauseInternal() {
        if (mBound) {
            serviceHandle.pause();
            setState(PlaybackState.PAUSE);
        }
    }

    @Override
    protected void stopInternal() {
        if (mBound) {
            try {
                serviceHandle.stop();
            } catch (IOException e) {
                Log.e(TAG, "playbackDevice: Unable to stop service");
            }
            appContext.unbindService(mConnection);
            setState(PlaybackState.STOP);
        }
    }

    @Override
    protected void preplay() {
        if (!mBound) {
            setState(PlaybackState.READY);
            appContext.startService(startIntent);
            appContext.bindService(startIntent, mConnection, Context.BIND_AUTO_CREATE);
        }
    }

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to PlayerService, cast the IBinder and get PlayerService instance
            PlayerService.LocalBinder binder = (PlayerService.LocalBinder) service;
            serviceHandle = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };
}
