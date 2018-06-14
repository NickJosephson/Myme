package com.nitrogen.myme.objects;

/* Tag
 *
 * purpose: This class represents a tag for use in classifying Memes.
 */
public class Tag {
    private String name;
    private final int tagID = lastTagID++; //unique ID

    private static int lastTagID = 0; //used to ensure all tags have a unique ID

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
        return otherTag instanceof Tag && tagID == ((Tag) otherTag).getTagID();
    }

    @Override
    public String toString() {
        return name;
    }

    //**************************************************
    // Accessor Methods
    //**************************************************

    public String getName() { return name; }

    public int getTagID() { return tagID; }

}
