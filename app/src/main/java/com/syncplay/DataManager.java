package com.syncplay;

import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by vishal on 11/3/16.
 */

class DataManager {
    private static ArrayList<AudioSource> srcList = new ArrayList<>();

    private static int currentPathIndex = -1;

    private static InputStream currentFileStream;

    static void addFile(AudioSource source) {
        closeCurrentStream();
        srcList.add(source);
        currentPathIndex = srcList.size() - 1;
        openCurrentStream();
    }

    static void openCurrentStream() {
        if (currentPathIndex >= 0) {
            try {
                currentFileStream = srcList.get(currentPathIndex).getStream();
            } catch (FileNotFoundException e) {
                Log.e(TAG, "dataManager: Unable to find the audio file");
            }
        }
    }
    
    static void closeCurrentStream() {
        if (currentPathIndex >= 0) {
            try {
                getCurrentFileStream().close();
            } catch (IOException e) {
                Log.e(TAG, "dataManager: Unable to close current file stream");
            }
        }
    }

    static InputStream getCurrentFileStream() {
        return currentFileStream;
    }
}
