package com.example.nouha.mapharmacie;

import android.os.Environment;

import java.io.File;

/**
 * Created by Nouha on 07/03/2015.
 */
public class FroyoAlbumDirFactory  extends AlbumStorageDirFactory {

    @Override
    public File getAlbumStorageDir(String albumName) {
        // TODO Auto-generated method stub
        return new File(
                Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES
                ),
                albumName
        );
    }
}

