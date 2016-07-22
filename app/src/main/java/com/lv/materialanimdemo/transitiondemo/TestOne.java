package com.lv.materialanimdemo.transitiondemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.lv.materialanimdemo.R;

/**
 * User: 吕勇
 * Date: 2016-07-22
 * Time: 13:46
 * Description:
 */
public class TestOne extends AppCompatActivity implements View.OnClickListener {
    private ProgressBar mProgressBar;
    private Button mChangeBtn;
    private LinearLayout mTop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_one);
        initView();
    }

    public void tochange(View view) {
        LAnimUtils.startActivityAsCircular(this, TestTwo.class, view, R.color.colorAccent);
    }

    private void initView() {
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mChangeBtn = (Button) findViewById(R.id.change_btn);

        mChangeBtn.setOnClickListener(this);
        mProgressBar.setOnClickListener(this);
        mTop = (LinearLayout) findViewById(R.id.top);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_btn:
                mProgressBar.setVisibility(View.VISIBLE);
                LAnimUtils.hideAsCircular(mChangeBtn);
                break;
            case R.id.progressBar:
                mProgressBar.setVisibility(View.GONE);
                // 伸展按钮
                LAnimUtils.showAsCircular(mChangeBtn);
                break;
        }
    }

    public void horizontalMove(final View view) {
        boolean toRight = view.getTag() == null;
       /* Transition transition = new ChangeBounds();
        transition.setDuration(toRight ? 700 : 300);
        transition.setInterpolator(toRight ? new FastOutSlowInInterpolator() : new AccelerateInterpolator());
        transition.setStartDelay(toRight ? 0 : 50);
        TransitionManager.beginDelayedTransition(mTop, transition);*/
        final int move = toRight ? 500 : -500;
        TranslateAnimation translateAnimation = new TranslateAnimation(0 , move, 0, 0);
        translateAnimation.setDuration(500);
        translateAnimation.setFillAfter(true);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //animation.setFillAfter(true); 只是将view移动到了目标位置，但是view绑定的点击事件还在原来位置,所以要移動位置
                int left = view.getLeft() +move;
                int top = view.getTop();
                int width = view.getWidth();
                int height = view.getHeight();
                view.clearAnimation();
                view.layout(left, top, left + width, top + height);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(translateAnimation);
        view.setTag(toRight ? "asdf" : null);
    }

    public void move90(View view) {
        boolean toRight = view.getTag() == null;
    /*    TransitionManager.beginDelayedTransition(mTop,new Rotate());
        boolean toRight = view.getTag() == null;
        view.setRotation(toRight?180:0);
        view.setTag(toRight ? "asdf" : null);*/
        LAnimUtils.viewRotate(view,toRight ? 0 : 180, toRight ? 180 : 0);
        view.setTag(toRight ? "asdf" : null);

    }
}
