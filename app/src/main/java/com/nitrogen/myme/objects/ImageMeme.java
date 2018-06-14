package com.nitrogen.myme.objects;

/* ImageMeme
 *
 * purpose: This class implements a single image format Meme.
 */
public class ImageMeme extends Meme {
    private String imagePath;

    //**************************************************
    // Constructor
    //**************************************************

    public ImageMeme(String name, String source) {
        super(name);
        imagePath = source;
    }

    //**************************************************
    // Required Meme Method
    //**************************************************

    @Override
    public String getThumbnailPath() {
        return imagePath;
    }

    //**************************************************
    // Mutator Method
    //**************************************************

    public void setImagePath(String imagePath) { this.imagePath = imagePath; }


    //**************************************************
    // Accessor Method
    //**************************************************

    public String getImagePath() {
        return imagePath;
    }

}
