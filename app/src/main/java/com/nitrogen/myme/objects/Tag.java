package com.nitrogen.myme.objects;

/* Tag
 *
 * purpose: This class represents a tag for use in classifying Memes.
 */
public class Tag {
    private String name;
    private int tagID = lastTagID++; //unique ID

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
        if (otherTag instanceof Tag) {
            return tagID == ((Tag) otherTag).getTagID();
        } else {
            return false;
        }
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
