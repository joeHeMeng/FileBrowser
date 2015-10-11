package com.example.zyxu.filebrowser;

import android.util.Log;

/**
 * Created by zyxu on 15/10/9.
 */
public class File {
    private String name;
    private int imageId;
    private String filePath;

    public File(String name, int imageId, String filePath)
    {
        this.name=name;
        this.imageId=imageId;
        this.filePath=filePath;
    }

    public int getImageId() {
        return imageId;
    }

    public String getName() {
        return name;
    }

    public String getFilePath() {
        return filePath;
    }
}
