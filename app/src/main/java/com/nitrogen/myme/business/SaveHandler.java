package com.nitrogen.myme.business;

import com.nitrogen.myme.objects.BitmapContext;
import com.nitrogen.myme.persistence.ImageSaver;


public class SaveHandler {
    private ImageSaver imageSaver;

//    public SaveHandler(Context context){
//        imageSaver = new ImageSaver(context);
//    }

    public SaveHandler(BitmapContext bitmapContext){
        imageSaver = new ImageSaver(bitmapContext);
    }

    public String writeToFile(BitmapContext bitmapContext){
        return imageSaver.saveInternally(bitmapContext);
    }

    public static String getMemePicturePath(String name){
        return ImageSaver.getCreatedMemePath()+name;
    }

}
