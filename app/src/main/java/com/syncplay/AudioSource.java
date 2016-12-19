package com.syncplay;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import android.content.ContentResolver;
import android.content.Context;

/**
 * Created by vishal on 11/3/16.
 */

class AudioSource {
    String fileName;
    InputStream fileStream;

    AudioSource(InputStream fileStream, String fileName) {
        this.fileStream = fileStream;
        this.fileName = fileName;
    }

    InputStream getStream() throws FileNotFoundException {
        return this.fileStream;
    }
}
