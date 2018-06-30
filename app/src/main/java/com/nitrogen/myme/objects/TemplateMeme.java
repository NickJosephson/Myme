package com.nitrogen.myme.objects;

import android.graphics.Point;
import android.graphics.PointF;

import java.util.ArrayList;

/* TemplateMeme
 *
 * purpose: This class creates a meme with a predefined image and
 *          text positions.
 */
public class TemplateMeme extends Meme {
    private String imagePath;
    private ArrayList<Placeholder> captions;

    //**************************************************
    // Constructor
    //**************************************************

    public TemplateMeme(String name, String source, PointF points[]) {
        super(name);
        imagePath = source;
        captions = new ArrayList<>();

        for(int i = 0 ; i < points.length ; i++) {
            captions.add(new Placeholder(points[i]));
        }
    }

    // accessors
    public ArrayList<Placeholder> getCaptions() { return this.captions; }

    public void updateCaptionText(Placeholder caption, final String text) {
        caption.updateText(text);
    }

    public void updateCaptionPos(Placeholder caption, final PointF position) {
        caption.updatePosition(position.x, position.y);
    }

    public void updateCaptionPos(Placeholder caption, final float posX, final float posY) {
        caption.updatePosition(posX, posY);
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
        private final String DEFAULT_TEXT = "Place text here";
        private PointF position;
        private String text;

        // Constructor 1 - Takes in a PointF
        public Placeholder(final PointF position) {
            this.position = position;
            this.text = DEFAULT_TEXT;
        }

        // Constructor 2 - Takes in an x and y coordinate
        public Placeholder(final float posX, final float posY) {
            this.position = new PointF(posX, posY);
            this.text = DEFAULT_TEXT;
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
