package com.nitrogen.myme.presentation.touchDetection;

import android.graphics.PointF;
import android.view.MotionEvent;

public class MoveGestureDetector {

    private static final PointF FOCUS_DELTA_ZERO = new PointF();
    private final OnMoveGestureListener mListener;
    private PointF mFocusExternal = new PointF();
    private PointF mFocusDeltaExternal = new PointF();

    private static final float PRESSURE_THRESHOLD = 0.67f;
    private boolean mGestureInProgress;
    private MotionEvent mPrevEvent;
    private MotionEvent mCurrEvent;
    private float mCurrPressure;
    private float mPrevPressure;
    private long mTimeDelta;

    public MoveGestureDetector(OnMoveGestureListener listener) {
        mListener = listener;
    }

    private void handleStartProgressEvent(int actionCode, MotionEvent event) {
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

    private void handleInProgressEvent(int actionCode, MotionEvent event) {
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

    private void updateStateByEvent(MotionEvent curr) {
        final MotionEvent prev = mPrevEvent;

        // Reset mCurrEvent
        if (mCurrEvent != null) {
            mCurrEvent.recycle();
            mCurrEvent = null;
        }
        mCurrEvent = MotionEvent.obtain(curr);

        // Delta time
        mTimeDelta = curr.getEventTime() - prev.getEventTime();

        // Pressure
        mCurrPressure = curr.getPressure(curr.getActionIndex());
        mPrevPressure = prev.getPressure(prev.getActionIndex());

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

    private void resetState() {
        if (mPrevEvent != null) {
            mPrevEvent.recycle();
            mPrevEvent = null;
        }
        if (mCurrEvent != null) {
            mCurrEvent.recycle();
            mCurrEvent = null;
        }
        mGestureInProgress = false;
    }

    /**
     * All gesture detectors need to be called through this method to be able to
     * detect gestures. This method delegates work to handler methods
     * (handleStartProgressEvent, handleInProgressEvent) implemented in
     * extending classes.
     *
     * @param event
     * @return
     */
    public boolean onTouchEvent(MotionEvent event) {
        final int actionCode = event.getAction() & MotionEvent.ACTION_MASK;
        if (!mGestureInProgress) {
            handleStartProgressEvent(actionCode, event);
        } else {
            handleInProgressEvent(actionCode, event);
        }
        return true;
    }
}
