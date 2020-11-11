package com.example.android.snakegame;

class Gameloop implements Runnable {

    private final short FPS = 60;
    private short tickNumber = 0;
    private long targetTime = 1000 / FPS;
    private SnakeActivity snakeActivity = new SnakeActivity();

    @Override
    public void run() {
        while(SnakeInput.isRunning) {

            tick();
            tickNumber++;
            if (tickNumber == 60) tickNumber = 0;

            try {
                Thread.sleep(targetTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void tick() {
        if (tickNumber % 20 == 0) {
            snakeActivity.whichDirection();
        }
    }
}