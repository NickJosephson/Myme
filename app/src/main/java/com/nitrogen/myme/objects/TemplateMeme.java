package com.nitrogen.myme.objects;

import android.graphics.Point;
import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* TemplateMeme
 *
 * purpose: This class creates a meme with a predefined image and
 *          text positions.
 */
public class TemplateMeme {

    private String name;
    private String imagePath;
    private ArrayList<Placeholder> captions;
    private float coordinates[][];

    private final int templateID = lastTemplateID++; //unique ID
    private static int lastTemplateID = 0; //used to ensure all memes have a unique ID

    //**************************************************
    // Constructor
    //**************************************************

    public TemplateMeme(String name, String source, PointF points[]) {
        this.name = name;
        this.imagePath = source;

        captions = new ArrayList<>();

        for(int i = 0 ; i < points.length ; i++) {
            captions.add(new Placeholder(points[i]));
        }

        coordinates = pointsToInt(points);
    }

    //**************************************************
    // Mutator Methods
    //**************************************************
    public void setName(String name) { this.name = name; }

    public void setImagePath(String imagePath) { this.imagePath = imagePath; }


    //**************************************************
    // Accessor Methods
    //**************************************************

    public String getName() { return name; }

    public int getTemplateID() { return templateID; }

    public float[][] getTags() { return this.coordinates; }

    public String getImagePath() {
        return imagePath;
    }

    public ArrayList<Placeholder> getCaptions() { return this.captions; }

    //**************************************************
    // Instance Methods
    //**************************************************

    /* pointsToInt
     *
     * purpose: Converts a PointF array into a 2D float array.
     *
     * */
    private float[][] pointsToInt(PointF points[]){
        float coordinates[][] = new float[points.length][2];

        for(int i = 0 ; i < coordinates.length ; i++) {
            coordinates[i][0] = points[i].x;
            coordinates[i][1] = points[i].y;
        }

        return coordinates;
    }

    public void updateCaptionText(Placeholder caption, final String text) {
        caption.updateText(text);
    }

    public void updateCaptionPos(Placeholder caption, final PointF position) {
        caption.updatePosition(position.x, position.y);
    }

    public void updateCaptionPos(Placeholder caption, final float posX, final float posY) {
        caption.updatePosition(posX, posY);
    }

    public String toString() {
        return "Template{" +
                "name='" + name + '\'' +
                ", templateID=" + templateID +
                '}';
    }

    //**************************************************
    // Private Class
    //**************************************************

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
