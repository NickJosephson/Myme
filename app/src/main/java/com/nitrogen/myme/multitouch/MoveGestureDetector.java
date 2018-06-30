package com.nitrogen.myme.multitouch;

import android.content.Context;
import android.graphics.PointF;
import android.view.MotionEvent;

public class MoveGestureDetector extends BaseGestureDetector {

    private static final PointF FOCUS_DELTA_ZERO = new PointF();
    private final OnMoveGestureListener mListener;
    private PointF mFocusExternal = new PointF();
    private PointF mFocusDeltaExternal = new PointF();
    public MoveGestureDetector(Context context, OnMoveGestureListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    protected void handleStartProgressEvent(int actionCode, MotionEvent event) {
        switch (actionCode) {
            case MotionEvent.ACTION_DOWN:
                resetState(); // In case we missed an UP/CANCEL event

                mPrevEvent = MotionEvent.obtain(event);
                mTimeDelta = 0;

                updateStateByEvent(event);
                break;

            case MotionEvent.ACTION_MOVE:
                mGestureInProgress = mListener.onMoveBegin(this);
                break;
        }
    }

    @Override
    protected void handleInProgressEvent(int actionCode, MotionEvent event) {
        switch (actionCode) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mListener.onMoveEnd(this);
                resetState();
                break;

            case MotionEvent.ACTION_MOVE:
                updateStateByEvent(event);

                // Only accept the event if our relative pressure is within
                // a certain limit. This can help filter shaky data as a
                // finger is lifted.
                if (mCurrPressure / mPrevPressure > PRESSURE_THRESHOLD) {
                    final boolean updatePrevious = mListener.onMove(this);
                    if (updatePrevious) {
                        mPrevEvent.recycle();
                        mPrevEvent = MotionEvent.obtain(event);
                    }
                }
                break;
        }
    }

    protected void updateStateByEvent(MotionEvent curr) {
        super.updateStateByEvent(curr);

        final MotionEvent prev = mPrevEvent;

        // focus internal
        PointF mCurrFocusInternal = determineFocalPoint(curr);
        PointF mPrevFocusInternal = determineFocalPoint(prev);

        // focus external
        // prevent skipping of focus delta when a finger is added or removed
        boolean mSkipNextMoveEvent = prev.getPointerCount() != curr.getPointerCount();
        mFocusDeltaExternal = mSkipNextMoveEvent ? FOCUS_DELTA_ZERO : new PointF(mCurrFocusInternal.x - mPrevFocusInternal.x, mCurrFocusInternal.y - mPrevFocusInternal.y);

        // don't directly use mFocusInternal (or skipping will occur)
        // add unskipped delta values to mFocusExternal instead.
        mFocusExternal.x += mFocusDeltaExternal.x;
        mFocusExternal.y += mFocusDeltaExternal.y;
    }

    /* determineFocalPoint
     *
     * purpose: Determine (multi)finger focal point (center point between all fingers)
     */
    private PointF determineFocalPoint(MotionEvent e) {
        // number of fingers on screen
        final int pCount = e.getPointerCount();
        float x = 0f;
        float y = 0f;

        for (int i = 0; i < pCount; i++) {
            x += e.getX(i);
            y += e.getY(i);
        }

        return new PointF(x / pCount, y / pCount);
    }

    public PointF getFocusDelta() {
        return mFocusDeltaExternal;
    }

    /* OnMoveGestureListener
     *
     * purpose: Listener which must be implemented which is used by MoveGestureDetector to perform
     *     callbacks to any implementing class which is registered to a MoveGestureDetector via the
     *     constructor.
     */
    public interface OnMoveGestureListener {
        boolean onMove(MoveGestureDetector detector);

        boolean onMoveBegin(MoveGestureDetector detector);

        void onMoveEnd(MoveGestureDetector detector);
    }

    /* SimpleOnMoveGestureListener
     *
     * purpose: Helper class which may be extended and where the methods may be implemented. This
     *     way it is not necessary to implement all methods of OnMoveGestureListener.
     */
    public static class SimpleOnMoveGestureListener implements OnMoveGestureListener {
        public boolean onMove(MoveGestureDetector detector) {
            return false;
        }

        public boolean onMoveBegin(MoveGestureDetector detector) {
            return true;
        }

        public void onMoveEnd(MoveGestureDetector detector) {
            // do nothing, overridden implementation may be used
        }
    }

}
