package com.example.android.snakegame;

public class Snake {

    public int[] bodyXs;
    public int[] bodyYs;
    private int _snakeLength;

    public void moveSnake() {



//        if (_snakeDirection == Directions.UP) {
//            _snakeYs.set(0, _snakeYs.get(0) - _snakeBlockSize);
//        } else if (_snakeDirection == Directions.RIGHT) {
//            _snakeXs.set(0, _snakeXs.get(0) + _snakeBlockSize);
//        } else if (_snakeDirection == Directions.DOWN) {
//            _snakeYs.set(0, _snakeYs.get(0) + _snakeBlockSize);
//        } else {
//            _snakeXs.set(0, _snakeXs.get(0) - _snakeBlockSize);
//        }
//
//        _snake.get(0).set(
//                _snakeXs.get(0),
//                _snakeYs.get(0),
//                _snakeXs.get(0) + _snakeBlockSize,
//                _snakeYs.get(0) + _snakeBlockSize
//        );
//
//        if (_snake.size() > 1) {
//            for (int i = 1; i < _snake.size(); i++) {
//                _snake.get(i).set(
//                        _snake.get(i - 1).left,
//                        _snake.get(i - 1).top,
//                        _snake.get(i - 1).right,
//                        _snake.get(i - 1).bottom);
//            }
//        }
//
//        _snake.add(new Rect());
    }

    public int getHeadX() {
        return bodyXs[0];
    }

    public int getHeadY() {
        return bodyYs[0];
    }
}
