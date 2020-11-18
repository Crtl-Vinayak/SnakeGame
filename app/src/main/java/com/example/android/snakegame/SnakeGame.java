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
    private final short NUM_BLOCKS_WIDE = 28;
    private final short FPS = 2;

    private Thread _thread = null;
    private volatile boolean _isRunning;
    private volatile boolean _isPlaying;
    private long _nextFrameTime;

    private String _currentScoreMsg;
    private String _lastScoreMsg;
    private String _startPromptMsg;
    private String _congratulationsMsg;

    private int _backgroundBeginColor;
    private int _backgroundScreenColor;
    private int _backgroundInputColor;
    private int _controllersColor;
    private int _foodColor;
    private int _snakeColor;
    private int _textColor;

    private int _screenWidth;
    private int _screenHeight;
    private int _snakeBlockSize;
    private int _snakeBlocksHigh;

    public SnakeGame(Context context, Point size) {
        super(context);

        _screenWidth = size.x;
        _screenHeight = size.y;

//        _snakeBlockSize = size.y /
        // TODO add/init grid for snake game, add snake block size.
        // TODO add background color for input and screen.
        // TODO add death screen.
        // TODO add score and highscore.
        // TODO save highscore.

        _currentScoreMsg = getContext().getString(R.string.current_score);
        _lastScoreMsg = getContext().getString(R.string.last_score);
        _startPromptMsg = getContext().getString(R.string.start_game_prompt);
        _congratulationsMsg = getContext().getString(R.string.congratulations);

        _backgroundBeginColor = getContext().getResources().getColor(R.color.background_snake_begin);
        _backgroundScreenColor = getContext().getResources().getColor(R.color.background_snake_screen);
        _backgroundInputColor = getContext().getResources().getColor(R.color.background_snake_input);
        _controllersColor = getContext().getResources().getColor(R.color.controllers);
        _foodColor = getContext().getResources().getColor(R.color.food);
        _snakeColor = getContext().getResources().getColor(R.color.snake);
        _textColor = getContext().getResources().getColor(R.color.text);

        _nextFrameTime = System.currentTimeMillis();
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
