package com.example.android.snakegame;

import android.app.Activity;
import android.view.View;

public class SnakeInput {

    public Activity activity;

    // Default begin snake direction is go to the right
    public static boolean isUp = false;
    public static boolean isRight = true;
    public static boolean isDown = false;
    public static boolean isLeft = false;

    // Thread stuff and Gameloops stuff (:
    public static boolean isRunning;
    private Gameloop gameloop;
    private Thread gameloopThread;

    public SnakeInput(Activity activity) {
        this.activity = activity;
    }

    public void buttonFunc() {
        this.activity.findViewById(R.id.up_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isUp = true;
                isRight = false;
                isDown = false;
                isLeft = false;
            }
        });
        this.activity.findViewById(R.id.right_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isUp = true;
                isRight = false;
                isDown = false;
                isLeft = false;
            }
        });
        this.activity.findViewById(R.id.down_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isUp = false;
                isRight = false;
                isDown = true;
                isLeft = false;
            }
        });
        this.activity.findViewById(R.id.left_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isUp = false;
                isRight = false;
                isDown = false;
                isLeft = true;
            }
        });
        this.activity.findViewById(R.id.play_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isRunning) {
                    isRunning = true;
                    gameloop = new Gameloop();
                    gameloopThread = new Thread(gameloop);
                    gameloopThread.start();
                }
            }
        });
        this.activity.findViewById(R.id.exit_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
    }
}
