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
    private ArrayList<Placeholder> placeholders;
    private float coordinates[][];

    private final int templateID = lastTemplateID++; //unique ID
    private static int lastTemplateID = 0; //used to ensure all memes have a unique ID

    //**************************************************
    // Constructor
    //**************************************************

    public TemplateMeme(String name, String source, PointF points[]) {
        this.name = name;
        this.imagePath = source;

        placeholders = new ArrayList<>();

        coordinates = pointsToInt(points);
    }

    public TemplateMeme(String name, String source, ArrayList<Placeholder> placeholders) {
        this.name = name;
        this.imagePath = source;

        this.placeholders = new ArrayList<>();
        copyPlaceholders(placeholders);
    }

    private void copyPlaceholders(ArrayList<Placeholder> placeholders){
        for(Placeholder p : placeholders){
            this.placeholders.add(p.clone());
        }
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

    public float[][] getCoordinates() { return this.coordinates; }

    public String getImagePath() {
        return imagePath;
    }

    public ArrayList<Placeholder> getPlaceholders() { return this.placeholders; }

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

}
