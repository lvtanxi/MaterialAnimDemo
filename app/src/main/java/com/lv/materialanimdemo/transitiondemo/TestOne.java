package com.lv.materialanimdemo.transitiondemo;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
    private ImageView ivBake;
    private ImageView ivMusic;
    private ImageView ivOutdoor;

    private Animator oneSet;
    private Animator twoSet;
    private Animator threeSet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_one);
        initView();
        initAnimation();
    }

    public void tochange(View view) {
        LAnimUtils.startActivityAsCircular(this, TestTwo.class, view, R.color.colorAccent);
    }

    private void initView() {
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mChangeBtn = (Button) findViewById(R.id.change_btn);
        ivBake = (ImageView) findViewById(R.id.iv_bake);
        ivMusic = (ImageView) findViewById(R.id.iv_music);
        ivOutdoor = (ImageView) findViewById(R.id.iv_outdoor);
        mChangeBtn.setOnClickListener(this);
        mProgressBar.setOnClickListener(this);
        mTop = (LinearLayout) findViewById(R.id.top);
    }

    private void initAnimation() {
        oneSet = AnimatorInflater.loadAnimator(this, R.animator.obja_one);
        twoSet = AnimatorInflater.loadAnimator(this, R.animator.obja_two);
        threeSet = AnimatorInflater.loadAnimator(this, R.animator.obja_three);
        oneSet.setTarget(ivOutdoor);
        twoSet.setTarget(ivMusic);
        threeSet.setTarget(ivBake);
        twoSet.setStartDelay(100);
        threeSet.setStartDelay(250);
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
        final int move = toRight ? 500 : 0;
        final int start = toRight ? 0 : 500;
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "translationX", start, move);
        objectAnimator.setDuration(1000);
        objectAnimator.start();
        view.setTag(toRight ? "asdf" : null);
    }

    public void move90(View view) {
        boolean toRight = view.getTag() == null;
    /*    TransitionManager.beginDelayedTransition(mTop,new Rotate());
        boolean toRight = view.getTag() == null;
        view.setRotation(toRight?180:0);
        view.setTag(toRight ? "asdf" : null);*/
        LAnimUtils.viewRotate(view, toRight ? 0 : 180, toRight ? 180 : 0);
        view.setTag(toRight ? "asdf" : null);

    }

    public void testObj(View view) {
        boolean toRight = view.getTag() == null;
        LAnimUtils.viewUpDownRotate(view, toRight ? 0 : 180, toRight ? 180 : 0);
        view.setTag(toRight ? "asdf" : null);
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.image);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animator);
        set.start();

        AnimatorSet set2 = new AnimatorSet();
        set2.playTogether(oneSet, twoSet, threeSet);
        set2.start();
    }

    public void startPropertyValuesHolder(View view) {
        PropertyValuesHolder p1 = PropertyValuesHolder.ofFloat("scaleX", 0.5f, 1);
        PropertyValuesHolder p2 = PropertyValuesHolder.ofFloat("scaleY", 0.5f, 1);
        PropertyValuesHolder p3 = PropertyValuesHolder.ofFloat("alpha", 0, 1f);
        ObjectAnimator.ofPropertyValuesHolder(view, p1, p2, p3).setDuration(500).start();
    }

    public void startPlayWithAfter(View view) {
        ObjectAnimator moveIn = ObjectAnimator.ofFloat(view, "translationX", -500f, 0f);
        ObjectAnimator rotate = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f);
        ObjectAnimator fadeInOut = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f, 1f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(rotate).with(fadeInOut).after(moveIn);
        animSet.setDuration(1000);
        animSet.start();
    }

    public void startValueAnimator(final View view) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(3000);
//PointF数值为float,Point数值为int,我们这里选择PointF
        valueAnimator.setObjectValues(new PointF(0, 0));
//设置自定义的估值器
        valueAnimator.setEvaluator(new MyEvalutor());
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF point = (PointF) animation.getAnimatedValue();
                //设置动画
                view.setX(point.x);
                view.setY(point.y);
            }
        });
    }
}
