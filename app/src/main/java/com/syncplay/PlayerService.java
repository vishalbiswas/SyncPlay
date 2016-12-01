package com.syncplay;

import android.app.Service;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

import static android.content.ContentValues.TAG;

public class PlayerService extends Service {
    static InputStream fileStream = null;
    static AudioTrack player = null;
    static int bufferSize;
    static byte[] buffer;
    static boolean isPaused = false;

    /**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    public class LocalBinder extends Binder {
        PlayerService getService() {
            return PlayerService.this;
        }
    }

    // This is the object that receives interactions from clients
    private final IBinder mBinder = new LocalBinder();

    @Override
    public void onCreate() {
        super.onCreate();

        bufferSize = AudioTrack.getMinBufferSize(
                AudioTrack.getNativeOutputSampleRate(AudioManager.STREAM_MUSIC),
                AudioFormat.CHANNEL_OUT_STEREO,
                AudioFormat.ENCODING_DEFAULT);

        buffer = new byte[bufferSize];

        player = new AudioTrack(
                AudioManager.STREAM_MUSIC,
                AudioTrack.getNativeOutputSampleRate(AudioManager.STREAM_MUSIC),
                AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_DEFAULT,
                bufferSize,
                AudioTrack.MODE_STREAM);

        fileStream = DataManager.getCurrentFileStream();

        play();

    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    void play() {
        isPaused = false;
        player.play();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (!isPaused && fileStream != null && fileStream.available() > 0) {
                        fileStream.read(buffer);
                        player.write(buffer, 0, bufferSize);
                    }

                    pause();
                } catch (IOException e) {
                    Log.e(TAG, "playerService: An error occurred while playing");
                }

            }
        });
    }

    void pause() {
        isPaused = true;
        player.stop();
    }

    void stop() throws IOException{
        if (fileStream != null) {
            fileStream.close();
        }
        stopSelf();
    }
}
