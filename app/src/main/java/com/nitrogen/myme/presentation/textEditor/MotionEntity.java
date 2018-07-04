package com.nitrogen.myme.presentation.textEditor;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

@SuppressWarnings({"WeakerAccess"})
public abstract class MotionEntity {

    @NonNull
    protected final TextLayer textLayer;
    protected final Matrix matrix = new Matrix();
    private boolean isSelected;
    // maximum scale of the initial image
    protected float holyScale;

    @IntRange(from = 0)
    protected int canvasWidth;
    @IntRange(from = 0)
    protected int canvasHeight;

    /*
     * Destination points of the entity
     * 5 points. Size of array - 10; Starting upper left corner, clockwise
     * last point is the same as first to close the circle
     * NOTE: saved as a field variable in order to avoid creating array in draw()-like methods
     */
    private final float[] destPoints = new float[10]; // x0, y0, x1, y1, x2, y2, x3, y3, x0, y0
    /*
     * Initial points of the entity
     * similar to destPoints
     */
    protected final float[] srcPoints = new float[10];  // x0, y0, x1, y1, x2, y2, x3, y3, x0, y0

    @NonNull
    private Paint borderPaint = new Paint();

    public MotionEntity(@NonNull TextLayer textLayer,
                        @IntRange(from = 1) int canvasWidth,
                        @IntRange(from = 1) int canvasHeight) {
        this.textLayer = textLayer;
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
    }

    private boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    /* updateMatrix
     *
     * purpose: apply position & scale changes to matrix
     *
     * S - scale matrix, T - translate matrix,
     * L - result transformation matrix
     * The correct order of applying transformations is : L = S * R * T
     *
     * For more info: <a href="http://gamedev.stackexchange.com/questions/29260/transform-matrix-multiplication-order">Game Dev: Transform Matrix multiplication order</a>
     *
     * Preconcat works like M` = M * S, so we apply preScale -> preTranslate
     * the result will be the same: L = S * R * T
     * NOTE: postconcat (postScale, etc.) works the other way : M` = S * M, in order to use it
     * we'd need to reverse the order of applying
     * transformations : post holy scale ->  postTranslate -> postScale
     */
    protected void updateMatrix() {
        // init matrix to E - identity matrix
        matrix.reset();

        float topLeftX = textLayer.getX() * canvasWidth;
        float topLeftY = textLayer.getY() * canvasHeight;

        float centerX = topLeftX + getWidth() * holyScale * 0.5F;
        float centerY = topLeftY + getHeight() * holyScale * 0.5F;

        // calculate params
        float scaleX = textLayer.getScale();
        float scaleY = textLayer.getScale();

        // applying transformations : L = S * R * T

        // scale
        matrix.preScale(scaleX, scaleY, centerX, centerY);

        // translate
        matrix.preTranslate(topLeftX, topLeftY);

        // applying holy scale - S`, the result will be : L = S * R * T * S`
        matrix.preScale(holyScale, holyScale);
    }

    public float absoluteCenterX() {
        float topLeftX = textLayer.getX() * canvasWidth;
        return topLeftX + getWidth() * holyScale * 0.5F;
    }

    public float absoluteCenterY() {
        float topLeftY = textLayer.getY() * canvasHeight;

        return topLeftY + getHeight() * holyScale * 0.5F;
    }

    public PointF absoluteCenter() {
        float topLeftX = textLayer.getX() * canvasWidth;
        float topLeftY = textLayer.getY() * canvasHeight;

        float centerX = topLeftX + getWidth() * holyScale * 0.5F;
        float centerY = topLeftY + getHeight() * holyScale * 0.5F;

        return new PointF(centerX, centerY);
    }

    public void moveToCanvasCenter() {
        moveCenterTo(new PointF(canvasWidth * 0.5F, canvasHeight * 0.5F));
    }

    public void moveCenterTo(PointF moveToCenter) {
        PointF currentCenter = absoluteCenter();
        textLayer.postTranslate(1.0F * (moveToCenter.x - currentCenter.x) / canvasWidth,
                1.0F * (moveToCenter.y - currentCenter.y) / canvasHeight);
    }

    private final PointF pA = new PointF();
    private final PointF pB = new PointF();
    private final PointF pC = new PointF();
    private final PointF pD = new PointF();

    /* pointInLayerRect
     *
     * purpose: return true if point (x, y) is inside the triangle
     *
     * For more info:
     * <a href="http://math.stackexchange.com/questions/190111/how-to-check-if-a-point-is-inside-a-rectangle">StackOverflow: How to check point is in rectangle</a>
     *
     * NOTE: it's easier to apply the same transformation matrix (calculated before) to the
     * original source points, rather than calculate the result points ourselves
     */
    public boolean pointInLayerRect(PointF point) {

        updateMatrix();
        // map rect vertices
        matrix.mapPoints(destPoints, srcPoints);

        pA.x = destPoints[0];
        pA.y = destPoints[1];
        pB.x = destPoints[2];
        pB.y = destPoints[3];
        pC.x = destPoints[4];
        pC.y = destPoints[5];
        pD.x = destPoints[6];
        pD.y = destPoints[7];

        return MathUtils.pointInTriangle(point, pA, pB, pC) || MathUtils.pointInTriangle(point, pA, pD, pC);
    }

    /* draw
     *
     * purpose: draw on canvas
     *
     * For more info:
     * http://judepereira.com/blog/calculate-the-real-scale-factor-and-the-angle-of-rotation-from-an-android-matrix/
     */
    public final void draw(@NonNull Canvas canvas, @Nullable Paint drawingPaint) {

        updateMatrix();

        canvas.save();

        drawContent(canvas, drawingPaint);

        if (isSelected()) {
            // get alpha from drawingPaint
            int storedAlpha = borderPaint.getAlpha();
            if (drawingPaint != null) {
                borderPaint.setAlpha(drawingPaint.getAlpha());
            }
            drawSelectedBg(canvas);
            // restore border alpha
            borderPaint.setAlpha(storedAlpha);
        }

        canvas.restore();
    }

    private void drawSelectedBg(Canvas canvas) {
        matrix.mapPoints(destPoints, srcPoints);
        //noinspection Range
        canvas.drawLines(destPoints, 0, 8, borderPaint);
        //noinspection Range
        canvas.drawLines(destPoints, 2, 8, borderPaint);
    }

    @NonNull
    public TextLayer getTextLayer() {
        return textLayer;
    }

    public void setBorderPaint(@NonNull Paint borderPaint) {
        this.borderPaint = borderPaint;
    }

    protected abstract void drawContent(@NonNull Canvas canvas, @Nullable Paint drawingPaint);

    public abstract int getWidth();

    public abstract int getHeight();

    public void setCanvasWidth(int width) { this.canvasWidth = width; updateMatrix();}
    public void setCanvasHeight(int height) { this.canvasHeight= height; updateMatrix();}

    public void release() {
        // free resources here
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            release();
        } finally {
            //noinspection ThrowFromFinallyBlock
            super.finalize();
        }
    }
}