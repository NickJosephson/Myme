package com.nitrogen.myme.objects;

/* TemplateMeme
 *
 * purpose: This class creates a meme with a predefined image and
 *          text positions.
 */
public class TemplateMeme {

    private String name;
    private String imagePath;

    private final int templateID = ++lastTemplateID; //unique ID
    private static int lastTemplateID = 0; //used to ensure all memes have a unique ID

    //**************************************************
    // Constructor
    //**************************************************

    public TemplateMeme(String name, String source) {
        this.name = name;
        this.imagePath = source;
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
