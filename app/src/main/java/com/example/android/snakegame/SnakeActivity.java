package com.example.android.snakegame;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SnakeActivity extends AppCompatActivity {

    private static SnakeScreen mSnakeScreen;
    private static SnakeInput mSnakeInput;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake);

        mSnakeScreen = (SnakeScreen) findViewById(R.id.snake_screen);

        mSnakeInput = new SnakeInput(this);
        mSnakeInput.buttonFunc();
    }

    public static void whichDirection() {
        if (mSnakeInput.isUp) {
            mSnakeScreen.y -= 50;
        } else if (mSnakeInput.isLeft) {
            mSnakeScreen.x -= 50;
        } else if (mSnakeInput.isRight) {
            mSnakeScreen.x += 50;
        } else if (mSnakeInput.isDown) {
            mSnakeScreen.y += 50;
        }
        mSnakeScreen.moveSnake();
    }
}
