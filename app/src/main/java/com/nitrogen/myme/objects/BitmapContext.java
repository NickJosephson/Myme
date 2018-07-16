package com.nitrogen.myme.objects;


import android.content.Context;
import android.graphics.Bitmap;

public class BitmapContext {
    private Bitmap bitmap;
    private Context context;

    public BitmapContext(){

    }

    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    public void setContext(Context context){
        this.context = context;
    }

    public Bitmap  getBitmap(){
        return this.bitmap;
    }

    public Context getContext(){
        return this.context;
    }
}
