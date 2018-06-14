package com.nitrogen.myme.objects;

/* Tag
 *
 * purpose: This class represents a tag for use in classifying Memes.
 */
public class Tag {
    private String name;

    //**************************************************
    // Constructor
    //**************************************************

    public Tag (String name) {
        this.name = name;
    }

    //**************************************************
    // Helper Methods
    //**************************************************

    @Override
    public boolean equals(Object otherTag) {
        return otherTag instanceof Tag && name.equals(((Tag) otherTag).getName());
    }

    @Override
    public String toString() {
        return name;
    }

    //**************************************************
    // Accessor Methods
    //**************************************************

    public String getName() { return name; }

}
