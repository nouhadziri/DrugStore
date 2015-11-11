package com.example.nouha.mapharmacie;

import java.io.File;

/**
 * Created by Nouha on 07/03/2015.
 */
public abstract class AlbumStorageDirFactory {
    public abstract File getAlbumStorageDir(String albumName);
}
