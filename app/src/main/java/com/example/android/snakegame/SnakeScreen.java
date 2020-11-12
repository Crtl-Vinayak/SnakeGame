package com.example.android.snakegame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class SnakeScreen extends View {

//    public int x = 80;
//    public int y = 50;
//
//    private Rect bgRect;
//    private Paint bgPaint;
//    private Rect rect;
//    private Paint paint;
//
//    private Rect tRect;
//    private Paint tPaint;

//    private ArrayList<Rect> snake = new ArrayList<>();
//    private int bodyLength = 1;

    private ArrayList<Rect> border = new ArrayList<>();
    private ArrayList<Rect> snakeBody = new ArrayList<>();
    private Rect food = new Rect();

    private Paint borderPaint1, borderPaint2;
    private Paint snakePaint;

    private int rectSides = 50;
    private int offsetX;
    private int offsetY;
    private int blockScreenWidth;
    private int blockScreenHeight;

    public SnakeScreen(Context context) {
        super(context);

        init(null);
    }

    public SnakeScreen(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }

    public SnakeScreen(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SnakeScreen(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(attrs);
    }


    private void init(@Nullable AttributeSet set) {
        offsetX = getScreenWidth() % rectSides;
        offsetY = getScreenHeight() / 2 % rectSides;

        borderPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint1.setColor(Color.RED);
        borderPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint2.setColor(Color.BLUE);

        blockScreenWidth = ((getScreenWidth() - (getScreenWidth() % rectSides)) / rectSides) + 1;
        blockScreenHeight = ((getScreenHeight() / 2 - (getScreenHeight() / 2 % rectSides)) / rectSides) + 1;

        for (int i = 0; i < blockScreenWidth + blockScreenHeight; i++) {
            border.add(new Rect());
            if (i < 22) {
                border.get(i).set(
                        -offsetX + (i * rectSides),
                        -offsetY,
                        -offsetX + (i * rectSides) + rectSides,
                        -offsetY + rectSides
                );
            }
            if (i == 22) {
                border.get(i).set(
                        -offsetX + (blockScreenWidth * rectSides),
                        -offsetY + (i * rectSides),
                        -offsetX + (blockScreenWidth * rectSides) + rectSides,
                        -offsetY + (i * rectSides) + rectSides
                );
            }
        }

        // border for bottom
//        for (int i = 0; i < ((getScreenWidth() - (getScreenWidth() % rectSides)) / rectSides) + 2; i++) {
//            border.add(new Rect());
//            border.get(i).set(
//                    -offsetX + (i * rectSides),
//                    -offsetY + (rectSides * ((getScreenHeight() / 2) - ((getScreenHeight() / 2) % rectSides)) / rectSides),
//                    -offsetX + (i * rectSides) + rectSides,
//                    -offsetY + -offsetY + (rectSides * ((getScreenHeight() / 2) - ((getScreenHeight() / 2) % rectSides)) / rectSides) + rectSides
//            );
//        }

//        int snakeScreenWidth = getScreenWidth() - (getScreenWidth() % 50);
//        int snakeScreenHeight = getScreenHeight() / 2 - ((getScreenHeight() / 2) % 50);
//
//        System.out.println("system size: " + getScreenWidth() + " x " + getScreenHeight());
//        System.out.println("snake size: " + snakeScreenWidth + " x " + snakeScreenHeight);
//
//        bgRect = new Rect();
//        bgRect.set(getScreenWidth() % 50, (getScreenHeight() / 2) % 50, snakeScreenWidth, snakeScreenHeight);
//
//        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        bgPaint.setColor(Color.CYAN);
//
//        x = getScreenWidth() % 50;
//        y = (getScreenHeight() / 2) % 50;
//        rect = new Rect();
//        rect.set(x, y, x + 50, y + 50);
//
//        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        paint.setColor(Color.YELLOW);
//
//        tRect = new Rect();
//        tRect.set(50, 300, 50 + 50, 300 + 50);
//
//        tPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        tPaint.setColor(Color.RED);
    }

    public void moveSnake() {
//        rect.set(x, y, x + 50, y + 50);
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < blockScreenWidth + blockScreenHeight; i++) {
            if (i % 2 == 0) {
                canvas.drawRect(border.get(i), borderPaint1);
            } else {
                canvas.drawRect(border.get(i), borderPaint2);
            }
        }

        Rect playR = new Rect();
        playR.set(
                -offsetX + 50,
                -offsetY + 50,
                offsetX - 50 + (getScreenWidth() - (getScreenWidth() % rectSides)),
                offsetY - 50 + (getScreenHeight() / 2 - (getScreenHeight() / 2 % rectSides)));
        Paint test = new Paint(Paint.ANTI_ALIAS_FLAG);
        test.setColor(Color.GRAY);
        canvas.drawRect(playR, test);

//        for (int i = 0; i < ((getScreenWidth() - (getScreenWidth() % rectSides)) / rectSides) + 2; i++) {
//            if (i % 2 == 0) {
//                canvas.drawRect(border.get(i), borderPaint2);
//            } else {
//                canvas.drawRect(border.get(i), borderPaint1);
//            }
//        }

//        canvas.drawRect(bgRect, bgPaint);
//        canvas.drawRect(rect, paint);
//        canvas.drawRect(tRect, tPaint);
//        System.out.println("canvas size: " + canvas.getWidth() + " x " + canvas.getHeight());
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}
