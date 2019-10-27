package com.yuy.surfaceviewdemo.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Author: yuyang
 * Date:2019/10/27 14:14
 * Description:
 * Version:
 */
public abstract class DrawablePart {


    protected  Context mContext;
    protected int mGameWidth;
    protected int mGameHeight;
    protected Bitmap mBitmap;



    DrawablePart(Context context, int gameW, int gameH, Bitmap bitmap) {
        mContext = context;
        mGameWidth = gameW;
        mGameHeight = gameH;
        mBitmap = bitmap;
    }

    public abstract void draw(Canvas canvas);


}
