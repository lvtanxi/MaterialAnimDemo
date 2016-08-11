package com.lv.materialanimdemo.transitiondemo;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lv.materialanimdemo.R;

/**
 * User: 吕勇
 * Date: 2016-07-22
 * Time: 13:50
 * Description:
 */
public class TestTwo extends AppCompatActivity{

    private FrameLayout flContainer;
    private LinearLayout llyFront;
    private LinearLayout llyBack;
    private boolean isShowFront = true;

    private AnimatorSet mFrontAnimator;
    private AnimatorSet mBackAnimator;

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        initView();
        initAnimator();
        setAnimatorListener();
        setCameraDistance();
    }

    /**
     * 初始化View
     */
    private void initView() {

        flContainer = (FrameLayout)findViewById(R.id.fl_container);
        llyFront = (LinearLayout) findViewById(R.id.lly_front);
        llyBack = (LinearLayout) findViewById(R.id.lly_back);
    }

    /**
     * 1.初始化动画
     */
    private void initAnimator() {
        mFrontAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(this,
                R.animator.anim_in);
        mBackAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(this,
                R.animator.anim_out);
    }

    /**
     * 2.设置视角间距，防止旋转时超出边界区域
     */

    private void setCameraDistance() {
        int distance = 6000;
        float scale = getResources().getDisplayMetrics().density * distance;
        llyFront.setCameraDistance(scale);
        llyBack.setCameraDistance(scale);
    }

    /**
     * 3.设置动画监听事件
     */
    private void setAnimatorListener() {

        mFrontAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                flContainer.setClickable(false);
            }
        });

        mBackAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                flContainer.setClickable(true);
            }
        });
    }

    /**
     * 4.开启动画
     * @param view
     */
    public void startAnimation(View view) {

        //显示正面
        if(!isShowFront) {
            mFrontAnimator.setTarget(llyFront);
            mBackAnimator.setTarget(llyBack);
            mFrontAnimator.start();
            mBackAnimator.start();
            isShowFront = true;
        } else {
            mFrontAnimator.setTarget(llyBack);
            mBackAnimator.setTarget(llyFront);
            mFrontAnimator.start();
            mBackAnimator.start();
            isShowFront = false;
        }
    }
}
