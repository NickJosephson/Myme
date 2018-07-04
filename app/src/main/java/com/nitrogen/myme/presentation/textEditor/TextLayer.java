package com.nitrogen.myme.presentation.textEditor;

public class TextLayer extends Layer {

    private String text;
    private Font font;

    public TextLayer() {
    }

    @Override
    protected void reset() {
        super.reset();
        this.text = "";
        this.font = new Font();
    }

    @Override
    protected float getMaxScale() {
        return Limits.MAX_SCALE;
    }

    @Override
    protected float getMinScale() {
        return Limits.MIN_SCALE;
    }

    @Override
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
}