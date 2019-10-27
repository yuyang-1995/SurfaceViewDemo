package com.yuy.surfaceviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Author: yuyang
 * Date:2019/10/27 12:46
 * Description: SurfaceView 基础模板
 * Version:
 */
public class SurfaceViewTemplate extends SurfaceView implements Runnable{


    private Thread mThread;
    private boolean isRunning = false;

    private Paint mPaint;

    //
    private int mMinRadius;
    private int mMaxRadius;
    private int mRadius;
    private int mFlag;


    public SurfaceViewTemplate(Context context, AttributeSet attrs) {
        super(context, attrs);

        //步骤1： 获取SurfaceHolder 对象
        SurfaceHolder surfaceHolder = getHolder();

        //步骤2：  给SurfaceHolder 对象添加监听
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                //在该函数中做些和绘制界面相关的初始化工作
                isRunning = true;

                //监听到Surface 创建完毕
                mThread = new Thread(SurfaceViewTemplate.this);

                //步骤4 ： 开启子线程 在线程中使用循环处理绘制
                mThread.start();

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                //当Surface的状态（大小和格式）发生变化的时候会调用该函数
           }



            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

                //当Surface被摧毁前会调用该函数，一般在该函数中来清理使用的资源。

                isRunning = false;

            }
        });

        //给Surface 设置属性
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);

        initPaint();
    }



    //初始化最大半径和最小半径
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //控件第一次创建和控件大小发生改变时调用
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e("TAG", "onSizeChanged" + "");

        //最大半径
        mMaxRadius = Math.min(w, h) / 2 - 20;//1080p

        Log.e("mMaxRadius = ", mMaxRadius + "");
        //最小半径
        mRadius = mMinRadius = 30;

        Log.e("mRadius = ", mRadius + "");
     }


     //初始化画笔
    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(6);
        mPaint.setColor(Color.GREEN);
    }


    @Override
    public void run() {

        //开启线程绘制
        while (isRunning) {

            drawSelf();
        }
    }

    private void drawSelf() {

        Canvas canvas = null;
        SurfaceHolder holder = getHolder();
        //获取Holder 中的Canvas
        canvas = holder.lockCanvas();

        try {
            if (canvas != null) {
                //cavas
                drawCircle(canvas);
            }

        } catch (Exception ex) {

            ex.printStackTrace();

        }finally {

            if (canvas != null) {
                //对Canvas 进行释放
                holder.unlockCanvasAndPost(canvas);

            }
        }

    }

    private void drawCircle(Canvas canvas) {

        canvas.drawColor(Color.WHITE);

        canvas.drawCircle(getWidth() / 2, getHeight() / 2, mRadius, mPaint);

        //不断改变元半径大小
        if (mRadius >= mMaxRadius) {
            mFlag = -1;
        } else if (mRadius <= mMinRadius) {
            mFlag = 1;
        }

        mRadius += mFlag * 2;
    }
}
