package com.nitrogen.myme.business;
import android.content.Context;
import android.graphics.Bitmap;

import com.nitrogen.myme.persistence.ImageSaver;

import java.io.ByteArrayOutputStream;
import java.util.Date;

public class SaveHandler {
    //private Meme meme;
    private ImageSaver imageSaver;

    public SaveHandler(Context context){
        imageSaver = new ImageSaver(context);
    }

    public String writeToFile(Bitmap bitmap){
        String name = "meme" + (new Date()).toString() + ".png";
        imageSaver.setFileName(name);
        imageSaver.setDirectoryName("db");
        imageSaver.save(bitmap);
        imageSaver.setExternal(false);
        return name;
    }

    public static String getMemePicturePath(String name){
        return ImageSaver.getCreatedMemePath()+name;
    }



}
