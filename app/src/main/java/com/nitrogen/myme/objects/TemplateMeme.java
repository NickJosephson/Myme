package com.nitrogen.myme.objects;

import java.util.ArrayList;

/* TemplateMeme
 *
 * purpose: This class creates a meme with a predefined image and
 *          text positions.
 */
public class TemplateMeme {

    private String name;
    private String imagePath;
    private ArrayList<Placeholder> placeholders;

    private final int templateID = ++lastTemplateID; //unique ID
    private static int lastTemplateID = 0; //used to ensure all memes have a unique ID

    //**************************************************
    // Constructor
    //**************************************************

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

    public String getImagePath() {
        return imagePath;
    }

    public ArrayList<Placeholder> getPlaceholders() { return this.placeholders; }

    //**************************************************
    // Methods
    //**************************************************

    public String toString() {
        return "Template{" +
                "name='" + name + '\'' +
                ", templateID=" + templateID +
                '}';
    }

}
