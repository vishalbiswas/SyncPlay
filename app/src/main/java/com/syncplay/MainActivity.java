package com.syncplay;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {

    private final int GET_CONTENT_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.playButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlaybackManager.smartPlay();
                if (PlaybackManager.globalPlaybackState == PlaybackState.PLAY) {
                    ((ImageButton) v).setImageResource(android.R.drawable.ic_media_pause);
                } else {
                    ((ImageButton) v).setImageResource(android.R.drawable.ic_media_play);
                }
            }
        });

        findViewById(R.id.selectFileButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selectFile = new Intent(Intent.ACTION_GET_CONTENT);
                selectFile.setType("audio/*");
                startActivityForResult(selectFile, GET_CONTENT_ID);
            }
        });

        findViewById(R.id.devicesButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showDevices = new Intent(MainActivity.this, ListDevicesActivity.class);
                startActivity(showDevices);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case GET_CONTENT_ID:
                if (resultCode == RESULT_OK) {
                    Uri file = data.getData();
                    try {
                        DataManager.addFile(new AudioSource(getContentResolver().openInputStream(file), file.getLastPathSegment()));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
}
