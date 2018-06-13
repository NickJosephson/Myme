package com.nitrogen.myme.objects;

public class Tag {
    private String name;

    public Tag (final String name) {
        this.name = name;
    }

    // accessor
    public String getTagName () {
        return name;
    }


    /* equals
     *
     * purpose: override Object's equals method so equality is validated by tag names
     */
    @Override
    public boolean equals(Object tag) {
        return this.name.equals( ((Tag)tag).getTagName());
    }
}
