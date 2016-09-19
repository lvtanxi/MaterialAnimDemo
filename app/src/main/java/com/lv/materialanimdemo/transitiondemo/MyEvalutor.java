package com.lv.materialanimdemo.transitiondemo;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

public class MyEvalutor implements TypeEvaluator<PointF> {

    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        PointF point = new PointF();
        //x方向移动函数，线性轨迹，匀速移动
        point.x = 300*fraction;
        // y方向移动函数，抛物线轨迹，加速移动
        point.y = 500 * fraction * fraction * fraction;
        return point;
    }
}