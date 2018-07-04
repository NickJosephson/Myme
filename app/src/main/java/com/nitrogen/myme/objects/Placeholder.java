package com.nitrogen.myme.objects;

import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;

/* Placeholder
 *
 *  purpose: Serves as a temporary caption that the user can later edit.
 *
 * */
public class Placeholder implements Parcelable{
    private final String DEFAULT_TEXT = "Place text here";
    private final String DEFAULT_TEXT_MULTILINE = "Place\ntext\nhere";
    private PointF position;
    private String text;
    private int width, height;

    //**************************************************
    // Constructors
    //**************************************************

    public Placeholder(final int width, final int height, final boolean multiline, final PointF position) {
        this.width = width;
        this.height = height;
        this.position = position;
        if(multiline) {
            this.text = DEFAULT_TEXT_MULTILINE;
        } else {
            this.text = DEFAULT_TEXT;
        }
    }

    protected Placeholder(Parcel in) {
        position = in.readParcelable(PointF.class.getClassLoader());
        text = in.readString();
        width = in.readInt();
        height = in.readInt();
    }

    //**************************************************
    // Creator
    //**************************************************

    public static final Creator<Placeholder> CREATOR = new Creator<Placeholder>() {
        @Override
        public Placeholder createFromParcel(Parcel in) {
            return new Placeholder(in);
        }

        @Override
        public Placeholder[] newArray(int size) {
            return new Placeholder[size];
        }
    };

    //**************************************************
    // Accessors
    //**************************************************

    public int getWidth() { return this.width; }
    public int getHeight() { return this.height; }
    public PointF getPosition() { return this.position; }
    public String getText() { return this.text; }

    //**************************************************
    // Methods
    //**************************************************

    /* isMultiline
     *
     *  purpose: Determine if the placeholder has multiple lines.
     *
     * */
    public boolean isMultiline() { return this.text.equals(DEFAULT_TEXT_MULTILINE);}

    @Override
    public Placeholder clone(){
        return new Placeholder(this.width, this.height, this.isMultiline(), this.position);
    }

    /* updateText
     *
     *  purpose: Change the text.
     *
     * */
    public void updateText(final String updated) {
        this.text = updated;
    }

    /* updatePosition
     *
     *  purpose: Reposition the text.
     *
     * */
    public void updatePosition(final float newX, final float newY) {
        position.set(newX, newY);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(position, flags);
        dest.writeString(text);
        dest.writeInt(width);
        dest.writeInt(height);
    }
}