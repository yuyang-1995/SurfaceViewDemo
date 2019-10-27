package com.yuy.surfaceviewdemo.game;

import android.content.Context;
import android.util.TypedValue;

import java.lang.reflect.TypeVariable;

/**
 * Author: yuyang
 * Date:2019/10/27 14:48
 * Description:
 * Version:
 */
public class Utils {

    public static int dp2px(Context context, int dpVal) {

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal,
                context.getResources().getDisplayMetrics());
    }
}
