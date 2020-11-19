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
    private int _maxBlocksOnScreen;

    private Rect _upBtn;
    private Rect _rightBtn;
    private Rect _downBtn;
    private Rect _leftBtn;
    private Paint _btnPaint;

    private Snake _snake;
    private Paint _snakePaint;
    private Directions _snakeDirection;

//    private Rect _foodRect;
//    private Paint _foodPaint;

    public SnakeGame(Context context, Point size) {
        super(context);

        // screen size
        _screenWidth = size.x;
        _screenHeight = size.y;

        // snake game screen is 7/10 of the display height.
        // snake block size is 7/10 of the display height divided by 20.
        // snake width block fits means how many snake blocks there fits in the display width.
        // max blocks on screen is just 20 times the (snake width block fits).
        _snakeHeightScreen = _screenHeight / 10 * 7;
        _snakeBlockSize = _snakeHeightScreen / NUM_BLOCKS_WIDE;
        _snakeWidthBlockFits = _screenWidth / _snakeBlockSize;
        _maxBlocksOnScreen = NUM_BLOCKS_WIDE * _snakeWidthBlockFits;

        Log.v(TAG, "Snake Height Screen = " + _snakeHeightScreen);
        Log.v(TAG, "Snake Block Size = " + _snakeBlockSize);
        Log.v(TAG, "Snake Width Block Fits = " + _snakeWidthBlockFits);

        // TODO make snake big
        // TODO death detector
        // TODO reserve place for food to spawn
        // TODO snake tail needs to follow snake head.
        // TODO add death screen.
        // TODO add score and highscore.
        // TODO save highscore.

        // Texts for the snake game.
        _currentScoreMsg = getContext().getString(R.string.current_score);
        _lastScoreMsg = getContext().getString(R.string.last_score);
        _startPromptMsg = getContext().getString(R.string.start_game_prompt);
        _congratulationsMsg = getContext().getString(R.string.congratulations);

        // Colors for the snake game.
        _backgroundBeginColor = getContext().getResources().getColor(R.color.background_snake_begin);
        _backgroundScreenColor = getContext().getResources().getColor(R.color.background_snake_screen);
        _backgroundInputColor = getContext().getResources().getColor(R.color.background_snake_input);
        _controllersColor = getContext().getResources().getColor(R.color.controllers);
        _foodColor = getContext().getResources().getColor(R.color.food);
        _snakeColor = getContext().getResources().getColor(R.color.snake);
        _textColor = getContext().getResources().getColor(R.color.text);

        // Background Color for Input and Screen for the snake game.
        _backgroundScreenRect = new Rect(0, 0, _screenWidth, _snakeHeightScreen);
        _backgroundInputRect = new Rect(0, _snakeHeightScreen, _screenWidth, _screenHeight);
        _backgroundScreenPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        _backgroundInputPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        _backgroundScreenPaint.setColor(_backgroundScreenColor);
        _backgroundInputPaint.setColor(_backgroundInputColor);

        // Buttons, it haves some complex coordinates...
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

        // Snake Color and Default Direction.
        _snakePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        _snakePaint.setColor(_snakeColor);
        _snakeDirection = Directions.RIGHT;

//        _foodRect = new Rect();
//        _foodPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        _foodPaint.setColor(_foodColor);

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

//            for (int i = 0; i < _snake.size(); i++) {
//                canvas.drawRect(_snake.get(i), _snakePaint);
//            }
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
        _snake.moveSnake();
    }

    public void startGame() {
        _nextFrameTime = System.currentTimeMillis();
        _snake = new Snake();
        _isPlaying = true;
    }

    public void spawnFood() {

    }

    public void eatFood() {

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

                // Touch X and Y position.
                int posX = Math.round(motionEvent.getX());
                int posY = Math.round(motionEvent.getY());

                // Up Button.
                if (posX >= _screenWidth / 2 - (_screenHeight / 10 / 2) &&
                    posX <= _screenWidth / 2 + (_screenHeight / 10 / 2) &&
                    posY >= _snakeHeightScreen &&
                    posY <= _snakeHeightScreen + (_screenHeight / 10) &&
                    _snakeDirection != Directions.DOWN) {
                    _snakeDirection = Directions.UP;
                }
                // Right Button.
                if (posX >= _screenWidth / 2 + (_screenHeight / 10 / 2) &&
                    posX <= _screenWidth / 2 + (_screenHeight / 10 / 2) + (_screenHeight / 10) &&
                    posY >= _snakeHeightScreen + (_screenHeight / 10) &&
                    posY <= _snakeHeightScreen + (_screenHeight / 10 * 2) &&
                    _snakeDirection != Directions.LEFT) {
                    _snakeDirection = Directions.RIGHT;
                }
                // Down Button.
                if (posX >= _screenWidth / 2 - (_screenHeight / 10 / 2) &&
                    posX <= _screenWidth / 2 + (_screenHeight / 10 / 2) &&
                    posY >= _snakeHeightScreen + (_screenHeight / 10 * 2) &&
                    posY <= _snakeHeightScreen + (_screenHeight / 10 * 3) &&
                    _snakeDirection != Directions.UP) {
                    _snakeDirection = Directions.DOWN;
                }
                // Left Button.
                if (posX >= _screenWidth / 2 - (_screenHeight / 10 / 2) - (_screenHeight / 10) &&
                    posX <= _screenWidth / 2 - (_screenHeight / 10 / 2) &&
                    posY >= _snakeHeightScreen + (_screenHeight / 10) &&
                    posY <= _snakeHeightScreen + (_screenHeight / 10 * 2) &&
                    _snakeDirection != Directions.RIGHT) {
                    _snakeDirection = Directions.LEFT;
                }
            } else {
                startGame();
            }
        }
        return super.onTouchEvent(motionEvent);
    }
}
