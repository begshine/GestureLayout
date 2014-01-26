package com.example.app;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by javen on 14-1-26.
 */
public class GestureLayout extends RelativeLayout {
    private static final String TAG = "GestureLayout";

    private static final Boolean DEBUG = true;

    public GestureLayout(Context context) {
        super(context);
    }

    public GestureLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GestureLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    private float mlastMotionX;
    private float mlastMotionY;
    private int sloup = 30;

    private boolean canswipe = false;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mlastMotionX = ev.getX();
                mlastMotionY = ev.getY();
                canswipe = false;
                break;
            case MotionEvent.ACTION_MOVE:
                int diffX = (int) Math.abs(ev.getX() - mlastMotionX);
                int diffy = (int) Math.abs(ev.getY() - mlastMotionY);
                mlastMotionX = ev.getX();
                mlastMotionY = ev.getY();
                if (diffX >= sloup || diffy >= sloup) {
                    canswipe = true;
                    requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }

        return canswipe;
    }

    private boolean evented = false;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                evented = false;
                canswipe = true;
                log("down");
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = (int) (ev.getX() - mlastMotionX);
                int dy = (int) (ev.getY() - mlastMotionY);
                int diffX = (int) Math.abs(dx);
                int diffy = (int) Math.abs(dy);
                if ((diffX >= sloup || diffy >= sloup) && !evented) {
                    evented = true;
                    if (diffX != diffy) {
                        boolean dir = diffX > diffy;
                        if (dir && dx > 0) {
                            onSwipeToRight();
                        } else if (dir && dx < 0) {
                            onSwipeToLeft();
                        } else if (!dir && dy < 0) {
                            onSwipeToTop();
                        } else if (!dir && dy > 0) {
                            onSwipeToBottom();
                        }
                    }
                    canswipe = false;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                break;
        }
        mlastMotionX = ev.getX();
        mlastMotionY = ev.getY();
        return canswipe;
    }


    private void log(String message) {
        if (DEBUG)
            Log.d(TAG, message);
    }

    private void onSwipeToBottom() {
        log("向下滑动!");
    }

    private void onSwipeToTop() {
        log("向上滑动!");
    }

    private void onSwipeToLeft() {
        log("向左滑动!");
    }

    private void onSwipeToRight() {
        log("向右滑动!");
    }
}
