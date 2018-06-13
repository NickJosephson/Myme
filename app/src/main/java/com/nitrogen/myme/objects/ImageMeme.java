package com.nitrogen.myme.objects;
import java.util.List;

public class ImageMeme extends Meme {
    String imagePath;

    public ImageMeme(String name, String source, final List<Tag> tags) {
        super(name,tags);
        imagePath = source;
    }

    @Override
    public String getThumbnailPath() {
        return imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }
}
