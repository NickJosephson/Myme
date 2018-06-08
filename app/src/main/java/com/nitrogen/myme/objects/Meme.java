package com.nitrogen.myme.objects;

import java.util.ArrayList;

public class Meme {
    private String name;
    private String description;
    //tags
    //dank level
    //file location
    //  or resource identifier
    private int resourceID;

    public Meme(final String name)
    {
        this.name = name;
        this.description = null;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    private static int lastMemeId = 0;

    public static ArrayList<Meme> createMemesList(int numMemes) {
        ArrayList<Meme> memes = new ArrayList<Meme>();

        for (int i = 1; i <= numMemes; i++) {
            memes.add(new Meme("Person " + ++lastMemeId));
        }
        //test
        return memes;
    }
}
