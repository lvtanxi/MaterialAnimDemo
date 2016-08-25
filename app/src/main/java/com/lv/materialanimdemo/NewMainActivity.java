package com.lv.materialanimdemo;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.Spinner;

import com.lv.materialanimdemo.transitiondemo.LAnimUtils;


public class NewMainActivity extends AppCompatActivity {
    private int spinDirection = 1;
    private int spinDirectionRotationPoint = 1;

    private ObjectAnimator createSlideMeAnimator(View v, TimeInterpolator timeInterpolator) {
        float xLocation = v.getX();

        float buttonWidth = v.getWidth();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;
        float halfWidth = width / 2.0f;

        float startLocation;
        float endLocation;
        if (xLocation < halfWidth) {
            startLocation = xLocation;
            endLocation = xLocation + width - (buttonWidth * 1.5f);
        } else {
            startLocation = xLocation;
            endLocation = xLocation - (width - (buttonWidth * 1.5f));
        }

        ObjectAnimator slideAnimator = ObjectAnimator.ofFloat(v, View.X, startLocation, endLocation);
        if (timeInterpolator != null) {
            slideAnimator.setInterpolator(timeInterpolator);
        }
        return slideAnimator;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);

        final ViewGroup mainView = (ViewGroup) findViewById(R.id.mainView);
        final Button slideMeButton = (Button) findViewById(R.id.slideMeButton);

        final Button slideMeOptionsButton = (Button) findViewById(R.id.slideMeInterpolatorButton);
        final Spinner slideMeInterpolationOptions = (Spinner) findViewById(R.id.slideMeInterpolationOptions);

        final Button clickMeButton = (Button) findViewById(R.id.clickMeButton);
        final Button spinReverseButton = (Button) findViewById(R.id.spinReverseButton);
        final Button spinRotationPointButton = (Button) findViewById(R.id.spinRotationPointButton);
        spinRotationPointButton.setPivotY(spinRotationPointButton.getPivotY() + (spinRotationPointButton.getHeight() / 2.0f));

        final Button growingButton = (Button) findViewById(R.id.growingButton);
        final Button movingButton = (Button) findViewById(R.id.movingButton);


        slideMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator slideAnimator = createSlideMeAnimator(v, null);
                slideAnimator.start();
            }
        });

        slideMeOptionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int interpolationOptionPosition = slideMeInterpolationOptions.getSelectedItemPosition();
                TimeInterpolator timeInterpolator = null;
                switch (interpolationOptionPosition) {
                    case 0:
                        timeInterpolator = null;
                        break;
                    case 1:
                        timeInterpolator = new AnticipateOvershootInterpolator();
                        break;
                    case 2:
                        timeInterpolator = new BounceInterpolator();
                        break;
                    case 3:
                        timeInterpolator = new LinearInterpolator();
                        break;
                    case 4:
                        timeInterpolator = new FastOutSlowInInterpolator();
                        break;
                }
                ObjectAnimator slideAnimator = createSlideMeAnimator(v, timeInterpolator);
                slideAnimator.start();
            }
        });


        clickMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(v, View.ROTATION_X, 360 * spinDirection);
                objectAnimator.setDuration(1000);
                objectAnimator.start();
                spinDirection = spinDirection * -1;
            }
        });

        spinReverseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(v, View.ROTATION_X, 360);
                objectAnimator.setRepeatCount(1);
                objectAnimator.setRepeatMode(ObjectAnimator.REVERSE);
                objectAnimator.start();
            }
        });

        spinRotationPointButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(v, View.ROTATION_X, 360 * spinDirectionRotationPoint);
                objectAnimator.setDuration(1000);
                objectAnimator.start();
                spinDirectionRotationPoint = spinDirectionRotationPoint * -1;
            }
        });

        PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0.5f);
        PropertyValuesHolder pvhScaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.5f);
        final ObjectAnimator scalingAnimator = ObjectAnimator.ofPropertyValuesHolder(growingButton, pvhScaleX, pvhScaleY);
        scalingAnimator.setRepeatCount(3);
        scalingAnimator.setRepeatMode(ObjectAnimator.REVERSE);

        growingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scalingAnimator.start();
            }
        });

        movingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float originalX = v.getX();
                float originalY = v.getY();

                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(v, View.Y, originalY, mainView.getBottom() - v.getHeight() * 1.5f);
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(v, View.X, originalX, mainView.getRight() - v.getWidth() * 1.5f);
                ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(v, View.Y, mainView.getBottom() - v.getHeight() * 1.5f, mainView.getTop() + v.getHeight() * 0.5f);
                ObjectAnimator objectAnimator4 = ObjectAnimator.ofFloat(v, View.X, mainView.getRight() - v.getWidth() * 1.5f, originalX);
                ObjectAnimator objectAnimator5 = ObjectAnimator.ofFloat(v, View.Y, mainView.getTop() + v.getHeight() * 0.5f, originalY);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.setDuration(1000);
                animatorSet.playSequentially(objectAnimator1, objectAnimator2, objectAnimator3, objectAnimator4, objectAnimator5);
                animatorSet.start();
            }
        });
    }


    public void testValueAnimator(final View view) {
        final View viewById = findViewById(R.id.imagexx);
  /*      ObjectAnimator animator = ObjectAnimator.ofFloat(view, View.SCALE_Y, 1, 1, 1,0.5f)
                .setDuration(800);
        animator.setInterpolator(new BounceInterpolator());
        animator.start();*/
        Animation scaleAnimation = new ScaleAnimation(1, 1, 1,0.5f );
        scaleAnimation.setDuration(800);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                boolean toRight = viewById.getTag() == null;
    /*    TransitionManager.beginDelayedTransition(mTop,new Rotate());
        boolean toRight = view.getTag() == null;
        view.setRotation(toRight?180:0);
        view.setTag(toRight ? "asdf" : null);*/
                LAnimUtils.viewRotate(viewById,toRight ? 0 : 180, toRight ? 180 : 0);
                viewById.setTag(toRight ? "asdf" : null);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        scaleAnimation.setInterpolator(new BounceInterpolator());
        view.startAnimation(scaleAnimation);
    }

}
