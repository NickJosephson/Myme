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
    private final String DEFAULT_SINGLELINE = "Place text here";
    private final String DEFAULT_MULTILINE = "Place\ntext\nhere";
    private final int DEFAULT_WIDTH = 600;
    private final int DEFAULT_HEIGHT = 1200;
    private final String DEFAULT_FONT = "Helvetica";

    private PointF position;
    private String text;
    private int width, height;
    private String fontName;

    //**************************************************
    // Constructors
    //**************************************************

    public Placeholder(final PointF position) {
        this.position = position;
        this.text = DEFAULT_SINGLELINE;
        this.width = DEFAULT_WIDTH;
        this.height = DEFAULT_HEIGHT;
        this.fontName = DEFAULT_FONT;
    }

    protected Placeholder(Parcel in) {
        position = in.readParcelable(PointF.class.getClassLoader());
        text = in.readString();
        width = in.readInt();
        height = in.readInt();
        fontName = in.readString();
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
    public String getFontName() { return this.fontName; }

    //**************************************************
    // Mutators
    //**************************************************

    public void setWidth(final int width) { this.width = width; }
    public void setHeight(final int height) { this.height = height; }
    public void setPosition(final PointF p) { position.set(p.x, p.y); }
    public void setText(final String text) { this.text = text; }
    public void setFontName(final String fontName) { this.fontName = fontName; }
    public void setMultiLine() { this.text = DEFAULT_MULTILINE; }
    public void setSingleLine() { this.text = DEFAULT_MULTILINE; }

    //**************************************************
    // Methods
    //**************************************************

    /* isMultiline
     *
     *  purpose: Determine if the placeholder has multiple lines.
     *
     * */
    public boolean isMultiLine() { return this.text.equals(DEFAULT_MULTILINE);}

    @Override
    public Placeholder clone(){
        Placeholder p = new Placeholder(this.position);
        p.setWidth(this.width);
        p.setHeight(this.height);
        p.setText(this.text);
        p.setFontName(this.fontName);

        return p;
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
        dest.writeString(fontName);
    }
}