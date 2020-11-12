package com.example.android.snakegame;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class SnakeScreen extends View {

    public int x = 80;
    public int y = 50;

    private Rect bgRect;
    private Paint bgPaint;
    private Rect rect;
    private Paint paint;

    private Rect tRect;
    private Paint tPaint;

    private RelativeLayout mSnakeScreenBg;

//    private ArrayList<Rect> snake = new ArrayList<>();
//    private int bodyLength = 1;

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
        int snakeScreenWidth = getScreenWidth() - (getScreenWidth() % 50);
        int snakeScreenHeight = getScreenHeight() / 2 - ((getScreenHeight() / 2) % 50);

        System.out.println("system size: " + getScreenWidth() + " x " + getScreenHeight());
        System.out.println("snake size: " + snakeScreenWidth + " x " + snakeScreenHeight);

        bgRect = new Rect();
        bgRect.set(getScreenWidth() % 50, (getScreenHeight() / 2) % 50, snakeScreenWidth, snakeScreenHeight);

        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setColor(Color.CYAN);

        x = getScreenWidth() % 50;
        y = (getScreenHeight() / 2) % 50;
        rect = new Rect();
        rect.set(x, y, x + 50, y + 50);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.YELLOW);

        tRect = new Rect();
        tRect.set(50, 300, 50 + 50, 300 + 50);

        tPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        tPaint.setColor(Color.RED);
    }

    public void moveSnake() {
        rect.set(x, y, x + 50, y + 50);
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(bgRect, bgPaint);
        canvas.drawRect(rect, paint);
        canvas.drawRect(tRect, tPaint);
        System.out.println("canvas size: " + canvas.getWidth() + " x " + canvas.getHeight());
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}
