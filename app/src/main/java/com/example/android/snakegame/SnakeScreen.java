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

    public int x = 0;
    public int y = 0;

    private int rectSides = 50;

    private Rect bgRect;
    private Paint bgPaint;
    private Rect rect;
    private Paint paint;

    private Rect tRect;
    private Paint tPaint;

    private Rect tail;

//    private ArrayList<Rect> snake = new ArrayList<>();
//    private int bodyLength = 1;
//
//    private ArrayList<Rect> border = new ArrayList<>();
//    private ArrayList<Rect> snakeBody = new ArrayList<>();
//    private Rect food = new Rect();
//
//    private Paint borderPaint1, borderPaint2;
//    private Paint snakePaint;
//
//    private int offsetX;
//    private int offsetY;
//    private int blockScreenWidth;
//    private int blockScreenHeight;

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
//        offsetX = getScreenWidth() % rectSides;
//        offsetY = getScreenHeight() / 2 % rectSides;
//
//        borderPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
//        borderPaint1.setColor(Color.RED);
//        borderPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
//        borderPaint2.setColor(Color.BLUE);
//
//        blockScreenWidth = ((getScreenWidth() - (getScreenWidth() % rectSides)) / rectSides) + 1;
//        blockScreenHeight = ((getScreenHeight() / 2 - (getScreenHeight() / 2 % rectSides)) / rectSides) + 1;
//
//        int snakeScreenWidth = getScreenWidth() - (getScreenWidth() % 50);
//        int snakeScreenHeight = getScreenHeight() / 2 - ((getScreenHeight() / 2) % 50);

        bgRect = new Rect();
        bgRect.set(0, 0, getScreenWidth(), getScreenHeight());
        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setColor(Color.CYAN);

        x = 0;
        y = 0;
        rect = new Rect();
        rect.set(x, y, x + rectSides, y + rectSides);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.rgb(33, 30, 84));

        tRect = new Rect();
        tRect.set(550, 300, 550 + rectSides, 300 + rectSides);
        tPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        tPaint.setColor(Color.RED);

        tail = new Rect();
    }

    public void moveSnake() {
        rect.set(x, y, x + rectSides, y + rectSides);
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(bgRect, bgPaint);
        canvas.drawRect(tRect, tPaint);
        canvas.drawRect(rect, paint);
        if (rect.left == tRect.left && rect.top == tRect.top) {
            tail.set(rect.left - rectSides, rect.top - rectSides, rect.left, rect.top);
            canvas.drawRect(tail, paint);
        }
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}
