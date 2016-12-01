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
    String path;

    AudioSource(String path) {
        this.path = path;
    }

    InputStream getStream() throws FileNotFoundException {
        return new FileInputStream(new File(path));
    }
}
