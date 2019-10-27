package com.yuy.surfaceviewdemo.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;


/**
 * Author: yuyang
 * Date:2019/10/27 14:14
 * Description:
 * Version:
 */
public class Floor extends DrawablePart {
//
   private   int x;
   private int y;

   private static final float  RADIO_Y_POD = 4 /5F;

   private Paint mPaint;

   private BitmapShader mBitmapShader;

    Floor(Context context, int gameW, int gameH, Bitmap bitmap) {
        super(context, gameW, gameH, bitmap);

        y = (int) (gameH * RADIO_Y_POD);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);

        //创建BitmapShader 注入bitmap
        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();

        //x 默认为0 ，y 为高度4/5, 移动原点
        canvas.translate(x, y);
        mPaint.setShader(mBitmapShader);
        canvas.drawRect(x, 0, -x + mGameWidth, mGameHeight - y, mPaint);
        //退出qpp 后会y 重置
        canvas.restore();
        mPaint.setShader(null);

    }

    public int getX(){

        return x;
    }

    public void setX(int x){

        this.x = x;
        if (-x > mGameWidth) {
            this.x = x % mGameWidth;
            //保证x 小于 gameX
        }
    }

//    private int x;
//    private int y;
//    private static final float RADIO_Y_POS = 4 / 5F;
//    private Paint mPaint;
//    private BitmapShader mBitmapShader;
//
//    public Floor(Context context, int gameW, int gameH, Bitmap bitmap) {
//        super(context, gameW, gameH, bitmap);
//        y = (int) (gameH * RADIO_Y_POS);
//        mPaint = new Paint();
//        mPaint.setAntiAlias(true);
//        mPaint.setDither(true);
//        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
//    }
//
//    @Override
//    public void draw(Canvas canvas) {
//        canvas.save();
//        canvas.translate(x, y);
//        mPaint.setShader(mBitmapShader);
//        canvas.drawRect(x, 0, -x + mGameWidth, mGameHeight - y ,mPaint );
//        canvas.restore();
//        mPaint.setShader(null);
//    }
//
//    public int getX(){
//        return x ;
//    }
//
//    public void setX(int x){
//        this.x = x ;
//        if(-x > mGameWidth) {
//            this.x = x % mGameWidth;
//        }
//
//    }


}
