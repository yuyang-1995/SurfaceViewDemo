package com.yuy.surfaceviewdemo.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.DrawableRes;

import com.yuy.surfaceviewdemo.R;

/**
 * Author: yuyang
 * Date:2019/10/27 12:46
 * Description: SurfaceView 基础模板
 * Version:
 */
public class FlappyBird extends SurfaceView implements Runnable{


    private Thread mThread;
    private boolean isRunning = false;


    private Bitmap mBg;
    private Bitmap mBi;
    private Bitmap mFloorBm;

    private RectF mDestRecf;

    private Paint mPaint;

    //
    private Floor mFloor;
    private Bird mBird;


    private int mSpeed;

    private static final int TOUCH_UP_SIZE = -16;
    private int mBirdUpDis;
    private  static final int SIZE_AUTO_DOWN = 2;
    private int mAutoDownDis;
    private int mTmpBirdDis;

    public FlappyBird(Context context, AttributeSet attrs) {
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
                mThread = new Thread(FlappyBird.this);

                //步骤4 ： 开启子线程 在线程中使用循环处理绘制
                mThread.start();

                //恢复
                mBird.reset();
                mTmpBirdDis = 0 ;

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                //当Surface的状态（大小和格式）发生变化的时候会调用该函数
                Log.e("TAG:", "surfaceChanged");
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

        mSpeed = Utils.dp2px(getContext(), 2);
        mAutoDownDis = Utils.dp2px(getContext(), SIZE_AUTO_DOWN);
        mBirdUpDis = Utils.dp2px(getContext(), TOUCH_UP_SIZE);

        initRes();
    }

    private void initRes() {
        mBg = loadBitmapByResId(R.drawable.bg1);
        mBi = loadBitmapByResId(R.drawable.b1);
        mFloorBm = loadBitmapByResId(R.drawable.floor_bg);
    }

    //加入DrawableRes 注解 表示 参数必须为 drawable id
    private Bitmap loadBitmapByResId(@DrawableRes int resId) {
        return BitmapFactory.decodeResource(getResources(), resId);

    }


    //初始化最大半径和最小半径
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //控件第一次创建和控件大小发生改变时调用
        super.onSizeChanged(w, h, oldw, oldh);

        //初始化 背景显示范围
        mDestRecf = new RectF(0, 0, w, h);

        mFloor = new Floor(getContext(), w, h, mFloorBm);
        mBird = new Bird(getContext(), w, h, mBi);
     }




    @Override
    public void run() {

        //开启线程绘制, 每隔50ms 绘制一次
        while (isRunning) {

            long star = System.currentTimeMillis();

            drawSelf();

            long end = System.currentTimeMillis();

            if (end - star <50) {

                try {
                    Thread.sleep(50 - (end - star));

                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

            }
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

                drawBg(canvas);

                logic();
                drawFloor(canvas);
                drawBird(canvas);
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

    //刷新背景 x
    private void logic() {

        //
        mFloor.setX(mFloor.getX() - mSpeed);

        mTmpBirdDis += mAutoDownDis;

        //
        mBird.setY(mBird.getY() + mTmpBirdDis);

    }

    //绘制地板
    private void drawFloor(Canvas canvas) {

        mFloor.draw(canvas);

    }

    //绘制背景
    private void drawBg(Canvas canvas) {

        canvas.drawBitmap(mBg, null, mDestRecf, mPaint);

    }

    private void drawBird(Canvas canvas) {

        mBird.draw(canvas);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN) {

            mTmpBirdDis = mBirdUpDis;

        }
        return true;
    }


}
