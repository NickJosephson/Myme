package com.nitrogen.myme.objects;

import android.graphics.Point;
import android.graphics.PointF;

/* TemplateMeme
 *
 * purpose: This class creates a meme with a predefined image and
 *          text positions.
 */
public class TemplateMeme extends Meme {
    private String imagePath;
    private Placeholder[] captions;

    //**************************************************
    // Constructor
    //**************************************************

    public TemplateMeme(String name, String source) {
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


    /* Placeholder
     *  //TODO
     *  purpose: Private class specific for TemplateMemes.
     *           Serves as a temporary caption to indicate
     *           to the user where they are able to put text based on the general layout
     * */
    private class Placeholder {
        private PointF position;
        private String text;

        public Placeholder(final float posX, final float posY, final String text) {
            this.position = new PointF(posX, posY);
            this.text = text;
        }

        // accessors
        public PointF getPosition() { return this.position; }
        public String getText() { return this.text; }

        /* updateText
         *
         *  purpose: Change the text
         *
         * */
        public void updateText(final String updated) {
            this.text = updated;
        }

        /* updatePosition
         *
         *  purpose: Reposition the text
         *  
         * */
        public void updatePosition(final float newX, final float newY) {
            position.set(newX, newY);
        }
    }

}
