package com.yuy.surfaceviewdemo.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Author: yuyang
 * Date:2019/10/27 14:17
 * Description:
 * Version:
 */
public class Bird extends DrawablePart {


    private   int x;
    private int y;

    private static final float  RADIO_Y_POD = 1 / 2F;

    //30dp
    private static final int WIDTH = 30;
    private Paint mPaint;

    private int mWidth;
    private int mHeight;

    private RectF mRectF = new RectF();


    Bird(Context context, int gameW, int gameH, Bitmap bitmap) {
        super(context, gameW, gameH, bitmap);

        y = (int) (gameH * RADIO_Y_POD);
        mWidth = Utils.dp2px(context, WIDTH);
        mHeight = (int) (mWidth * 1.0f/bitmap.getWidth() * bitmap.getHeight());

        x = gameW/2 - mWidth/2;


    }


    @Override
    public void draw(Canvas canvas) {


        mRectF.set(x, y, x + mWidth, y + mHeight);

        canvas.drawBitmap(mBitmap, null, mRectF, null);



    }


    public int getY(){
        return y;
    }


    public void setY(int y) {

        this.y = y;
    }

    public void reset(){
        this.y = (int) (mGameHeight * RADIO_Y_POD);
    }
}
