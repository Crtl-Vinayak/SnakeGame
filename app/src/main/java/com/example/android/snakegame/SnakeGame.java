package com.example.android.snakegame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class SnakeGame extends View implements Runnable {

    private static final String TAG = "SnakeGame";

    private final long MILLIS_PER_SECOND = 1000;
    private final short NUM_BLOCKS_WIDE = 30;
    private final short FPS = 2;

    private Thread _thread = null;
    private volatile boolean _isRunning;
    private volatile boolean _isPlaying;
    private long _nextFrameTime;

    private Paint _backgroundPaint;
    private Rect _backgroundRect;

    public SnakeGame(Context context, Point size) {
        super(context);
        _nextFrameTime = System.currentTimeMillis();
//        Log.v(TAG, "size x and y = " + size.x + " x " + size.y);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    @Override
    public void run() {
        while(_isRunning) {
            if(updateRequired()) {
                if (_isPlaying) {
                    update();
                }
                Log.v(TAG, "Thread is RUNNING!!!");
                postInvalidate();
            }
        }
    }

    public void pause() {
        _isRunning = false;
        try {
            _thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        _isRunning = true;
        _thread = new Thread(this);
        _thread.start();
    }

    public boolean updateRequired() {
        if (_nextFrameTime <= System.currentTimeMillis()) {
            _nextFrameTime = System.currentTimeMillis() + MILLIS_PER_SECOND / FPS;
            return true;
        }
        return false;
    }

    public void update() {

    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

        }

        return super.onTouchEvent(motionEvent);
    }
}
