package com.nitrogen.myme.objects;
import java.util.List;

import android.net.Uri;

public class ImageMeme extends Meme {
    Uri imagePath;

    public ImageMeme(String name, Uri source, final List<Tag> tags) {
        super(name,tags);
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
