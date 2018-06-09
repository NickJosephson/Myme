package com.nitrogen.myme.objects;


import android.net.Uri;

public class ImageMeme extends Meme {
    Uri imagePath;

    public ImageMeme(String name, Uri source) {
        super(name);
        imagePath = source;
    }

    @Override
    public Uri getThumbnailPath() {
        return imagePath;
    }

    public Uri getImagePath() {
        return imagePath;
    }
}
