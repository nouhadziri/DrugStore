package com.example.nouha.mapharmacie;

import android.os.Environment;

import java.io.File;

/**
 * Created by Nouha on 07/03/2015.
 */
public class BaseAlbumDirFactory  extends AlbumStorageDirFactory {

    // Standard storage location for digital camera files
    private static final String CAMERA_DIR = "/dcim/";

    @Override
    public File getAlbumStorageDir(String albumName) {
        return new File(
                Environment.getExternalStorageDirectory()
                        + CAMERA_DIR
                        + albumName
        );
    }
}