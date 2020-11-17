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

    private int _backgroundBeginColor;
    private int _backgroundScreenColor;
    private int _backgroundInputColor;
    private int _controllersColor;
    private int _foodColor;
    private int _snakeColor;
    private int _textColor;

    public SnakeGame(Context context, Point size) {
        super(context);
        _nextFrameTime = System.currentTimeMillis();

        _backgroundBeginColor = getContext().getResources().getColor(R.color.background_snake_begin);
        _backgroundScreenColor = getContext().getResources().getColor(R.color.background_snake_screen);
        _backgroundInputColor = getContext().getResources().getColor(R.color.background_snake_input);
        _controllersColor = getContext().getResources().getColor(R.color.controllers);
        _foodColor = getContext().getResources().getColor(R.color.food);
        _snakeColor = getContext().getResources().getColor(R.color.snake);
        _textColor = getContext().getResources().getColor(R.color.text);

//        Log.v(TAG, "size x and y = " + size.x + " x " + size.y);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(_backgroundBeginColor);
    }

    @Override
    public void run() {
        while(_isRunning) {
            if(updateRequired()) {
                if (_isPlaying) {
                    update();
                }
//                Log.v(TAG, "Thread is RUNNING!!!");
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

    public void startGame() {
        _nextFrameTime = System.currentTimeMillis();
        _isPlaying = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            if (_isPlaying) {

                int posX = Math.round(motionEvent.getX());
                int posY = Math.round(motionEvent.getY());
                Log.v(TAG, "click position: x = " + posX + " /// click position y = " + posY);

                //TODO add controllers for the 4 directions.
            } else {
                startGame();
            }
        }

        return super.onTouchEvent(motionEvent);
    }
}
