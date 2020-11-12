package com.example.android.snakegame;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SnakeActivity extends AppCompatActivity {

    private static SnakeScreen mSnakeScreen;
    private static SnakeInput mSnakeInput;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            final WindowInsetsController insetsController = this.getWindow().getInsetsController();
            if (insetsController != null) {
                insetsController.hide(WindowInsets.Type.statusBars());
            }
        } else {
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_snake);

        mSnakeScreen = (SnakeScreen) findViewById(R.id.snake_screen);
        mSnakeInput = new SnakeInput(this);
        mSnakeInput.buttonFunc();
    }

    public void whichDirection() {
//        if (mSnakeInput.isUp) {
//            mSnakeScreen.y -= 50;
//        } else if (mSnakeInput.isLeft) {
//            mSnakeScreen.x -= 50;
//        } else if (mSnakeInput.isRight) {
//            mSnakeScreen.x += 50;
//        } else if (mSnakeInput.isDown) {
//            mSnakeScreen.y += 50;
//        }
//        mSnakeScreen.moveSnake();
    }
}
