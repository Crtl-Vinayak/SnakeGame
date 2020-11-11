package com.example.android.snakegame;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SnakeActivity extends AppCompatActivity {

    private SnakeScreen mSnakeScreen;
    private SnakeInput mSnakeInput;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake);

        mSnakeScreen = new SnakeScreen(this);

        mSnakeInput = new SnakeInput(this);
        mSnakeInput.buttonFunc();
    }

    public void whichDirection() {
        if (SnakeInput.isUp) {
            mSnakeScreen.y -= 50;
        } else if (SnakeInput.isLeft) {
            mSnakeScreen.x -= 50;
        } else if (SnakeInput.isRight) {
            mSnakeScreen.x += 50;
        } else if (SnakeInput.isDown) {
            mSnakeScreen.y += 50;
        }
        mSnakeScreen.moveSnake();
    }
}
