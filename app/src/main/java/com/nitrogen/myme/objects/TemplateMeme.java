package com.nitrogen.myme.objects;

/* TemplateMeme
 *
 * purpose: This class creates a meme with a predefined image and
 *          text positions.
 */
public class TemplateMeme {

    private String name;
    private String imagePath;

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

    public String getImagePath() {
        return imagePath;
    }


    //**************************************************
    // Methods
    //**************************************************

    public String toString() {
        return "Template{" +
                "name='" + name + '\'' +
                '}';
    }

}
