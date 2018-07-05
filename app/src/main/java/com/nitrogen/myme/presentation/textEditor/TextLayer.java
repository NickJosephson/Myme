package com.nitrogen.myme.presentation.textEditor;

public class TextLayer {

    private String text;
    private Font font;

    private float scale;
    // top left X coordinate, relative to parent canvas
    private float x;
    // top left Y coordinate, relative to parent canvas
    private float y;
    // is layer flipped horizontally (by X-coordinate)
    private boolean isFlipped;

    public TextLayer() {
    }

    public float getMaxScale() {
        return Limits.MAX_SCALE;
    }

    public float getMinScale() {
        return Limits.MIN_SCALE;
    }

    public float initialScale() {
        return Limits.INITIAL_SCALE;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public interface Limits {
        // limits the font size
        float MAX_SCALE = 2.0F;
        float MIN_SCALE = 0.1F;

        float MIN_BITMAP_HEIGHT = 0.13F;

        float INITIAL_FONT_SIZE = 0.075F;
        int INITIAL_FONT_COLOR = 0xff000000;

        float INITIAL_SCALE = 0.8F; // set the same to avoid text scaling
    }

    public void postScale(float scaleDiff) {
        float newVal = scale + scaleDiff;
        if (newVal >= getMinScale() && newVal <= getMaxScale()) {
            scale = newVal;
        }
    }

    public void postTranslate(float dx, float dy) {
        this.x += dx;
        this.y += dy;
    }

    public void flip() {
        this.isFlipped = !isFlipped;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public boolean isFlipped() {
        return isFlipped;
    }

    public void setFlipped(boolean flipped) {
        isFlipped = flipped;
    }
}
