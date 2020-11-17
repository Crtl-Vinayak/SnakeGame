package com.example.android.snakegame;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

import androidx.appcompat.app.AppCompatActivity;

public class SnakeActivity extends AppCompatActivity {

    private SnakeGame _snakeGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        _snakeGame = new SnakeGame(this, size);
        setContentView(_snakeGame);
    }

    @Override
    protected void onPause() {
        super.onPause();
        _snakeGame.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        _snakeGame.resume();
    }
}
