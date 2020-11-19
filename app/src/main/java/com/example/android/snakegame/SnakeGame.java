package com.example.android.snakegame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class SnakeGame extends View implements Runnable {

    private static final String TAG = "SnakeGame";

    private final long MILLIS_PER_SECOND = 1000;
    private final short NUM_BLOCKS_WIDE = 20;
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

    private Rect _backgroundScreenRect;
    private Rect _backgroundInputRect;
    private Paint _backgroundScreenPaint;
    private Paint _backgroundInputPaint;

    private int _screenWidth;
    private int _screenHeight;
    private int _snakeHeightScreen;
    private int _snakeBlockSize;
    private int _snakeWidthBlockFits;

    private Rect _upBtn;
    private Rect _rightBtn;
    private Rect _downBtn;
    private Rect _leftBtn;
    private Paint _btnPaint;

    private ArrayList<Rect> _snake = new ArrayList<>();
    private Paint _snakePaint;
    private Directions _snakeDirection;
    private int _posX;
    private int _posY;

    public SnakeGame(Context context, Point size) {
        super(context);

        _screenWidth = size.x;
        _screenHeight = size.y;

        _snakeHeightScreen = _screenHeight / 10 * 7;
        _snakeBlockSize = _snakeHeightScreen / NUM_BLOCKS_WIDE;
        _snakeWidthBlockFits = _screenWidth / _snakeBlockSize;

        Log.v(TAG, "Snake Height Screen = " + _snakeHeightScreen);
        Log.v(TAG, "Snake Block Size = " + _snakeBlockSize);
        Log.v(TAG, "Snake Width Block Fits = " + _snakeWidthBlockFits);

        // TODO add/init grid for snake game, add snake block size.
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

        _backgroundScreenRect = new Rect(0, 0, _screenWidth, _snakeHeightScreen);
        _backgroundInputRect = new Rect(0, _snakeHeightScreen, _screenWidth, _screenHeight);
        _backgroundScreenPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        _backgroundInputPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        _backgroundScreenPaint.setColor(_backgroundScreenColor);
        _backgroundInputPaint.setColor(_backgroundInputColor);

        _upBtn = new Rect(
                _screenWidth / 2 - (_screenHeight / 10 / 2),
                _snakeHeightScreen,
                _screenWidth / 2 + (_screenHeight / 10 / 2),
                _snakeHeightScreen + (_screenHeight / 10));
        _rightBtn = new Rect(
                _screenWidth / 2 + (_screenHeight / 10 / 2),
                _snakeHeightScreen + (_screenHeight / 10),
                _screenWidth / 2 + (_screenHeight / 10 / 2) + (_screenHeight / 10),
                _snakeHeightScreen + (_screenHeight / 10 * 2));
        _downBtn = new Rect(
                _screenWidth / 2 - (_screenHeight / 10 / 2),
                _snakeHeightScreen + (_screenHeight / 10 * 2),
                _screenWidth / 2 + (_screenHeight / 10 / 2),
                _snakeHeightScreen + (_screenHeight / 10 * 3));
        _leftBtn = new Rect(
                _screenWidth / 2 - (_screenHeight / 10 / 2) - (_screenHeight / 10),
                _snakeHeightScreen + (_screenHeight / 10),
                _screenWidth / 2 - (_screenHeight / 10 / 2),
                _snakeHeightScreen + (_screenHeight / 10 * 2));
        _btnPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        _btnPaint.setColor(_controllersColor);

        _posX = 0;
        _posY = 0;
        _snake.add(new Rect(_posX, _posY, _snakeBlockSize, _snakeBlockSize));
        _snakePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        _snakePaint.setColor(_snakeColor);
        _snakeDirection = Directions.RIGHT;

        _nextFrameTime = System.currentTimeMillis();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(_backgroundBeginColor);
        if (_isPlaying) {
            canvas.drawRect(_backgroundScreenRect, _backgroundScreenPaint);
            canvas.drawRect(_backgroundInputRect, _backgroundInputPaint);
            canvas.drawRect(_upBtn, _btnPaint);
            canvas.drawRect(_rightBtn, _btnPaint);
            canvas.drawRect(_downBtn, _btnPaint);
            canvas.drawRect(_leftBtn, _btnPaint);
            canvas.drawRect(_snake.get(0), _snakePaint);
        }
    }

    @Override
    public void run() {
        while(_isRunning) {
            if(updateRequired()) {
                if (_isPlaying) {
                    update();
                }
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

    /**
     * Note: in this explanation, I say current time instead of System.currentTimeMillis().
     *
     * This method is for the game loop.
     * This method is being used in the run method from Runnable.
     * If the current time is bigger than the value of _nextFrameTime.
     * Then _nextFrameTime will have a bigger value, how this happens is by
     * adding the current time plus next frame time, in other words:
     *
     * _nextFrameTime = current time + 1000 / FPS.
     *
     * @return most of the time false, because _nextFrameTime is usually bigger than current time.
     */
    public boolean updateRequired() {
        if (_nextFrameTime <= System.currentTimeMillis()) {
            _nextFrameTime = System.currentTimeMillis() + MILLIS_PER_SECOND / FPS;
            return true;
        }
        return false;
    }

    public void update() {
        moveSnake();
    }

    public void startGame() {
        _nextFrameTime = System.currentTimeMillis();
        _isPlaying = true;
    }

    public void moveSnake() {

        if (_snakeDirection == Directions.UP) {
            _posY -= _snakeBlockSize;
        } else if (_snakeDirection == Directions.RIGHT) {
            _posX += _snakeBlockSize;
        } else if (_snakeDirection == Directions.DOWN) {
            _posY += _snakeBlockSize;
        } else {
            _posX -= _snakeBlockSize;
        }

//        _posX += _snakeBlockSize;
//        if (_posX >= _screenWidth) {
//            _posX -= _snakeBlockSize;
//        }
        _snake.get(0).set(_posX, _posY, _posX + _snakeBlockSize, _posY + _snakeBlockSize);
    }

    public enum Directions {
        UP,
        RIGHT,
        DOWN,
        LEFT
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            if (_isPlaying) {

                int posX = Math.round(motionEvent.getX());
                int posY = Math.round(motionEvent.getY());
                Log.v(TAG, "click position: x = " + posX + " /// click position y = " + posY);

                // Up Button.
                if (posX >= _screenWidth / 2 - (_screenHeight / 10 / 2) &&
                    posX <= _screenWidth / 2 + (_screenHeight / 10 / 2) &&
                    posY >= _snakeHeightScreen &&
                    posY <= _snakeHeightScreen + (_screenHeight / 10) &&
                    _snakeDirection != Directions.DOWN) {
                    _snakeDirection = Directions.UP;
                    Log.v(TAG, "snake direction = " + _snakeDirection);
                }
                // Right Button.
                if (posX >= _screenWidth / 2 + (_screenHeight / 10 / 2) &&
                    posX <= _screenWidth / 2 + (_screenHeight / 10 / 2) + (_screenHeight / 10) &&
                    posY >= _snakeHeightScreen + (_screenHeight / 10) &&
                    posY <= _snakeHeightScreen + (_screenHeight / 10 * 2) &&
                    _snakeDirection != Directions.LEFT) {
                    _snakeDirection = Directions.RIGHT;
                    Log.v(TAG, "snake direction = " + _snakeDirection);
                }
                // Down Button.
                if (posX >= _screenWidth / 2 - (_screenHeight / 10 / 2) &&
                    posX <= _screenWidth / 2 + (_screenHeight / 10 / 2) &&
                    posY >= _snakeHeightScreen + (_screenHeight / 10 * 2) &&
                    posY <= _snakeHeightScreen + (_screenHeight / 10 * 3) &&
                    _snakeDirection != Directions.UP) {
                    _snakeDirection = Directions.DOWN;
                    Log.v(TAG, "snake direction = " + _snakeDirection);
                }
                // Left Button.
                if (posX >= _screenWidth / 2 - (_screenHeight / 10 / 2) - (_screenHeight / 10) &&
                    posX <= _screenWidth / 2 - (_screenHeight / 10 / 2) &&
                    posY >= _snakeHeightScreen + (_screenHeight / 10) &&
                    posY <= _snakeHeightScreen + (_screenHeight / 10 * 2) &&
                    _snakeDirection != Directions.RIGHT) {
                    _snakeDirection = Directions.LEFT;
                    Log.v(TAG, "snake direction = " + _snakeDirection);
                }
            } else {
                startGame();
            }
        }

        return super.onTouchEvent(motionEvent);
    }
}
