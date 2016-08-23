package com.lv.materialanimdemo.transitiondemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;

/**
 * User: 吕勇
 * Date: 2016-07-22
 * Time: 16:02
 * Description:普通的anim集合
 */
public class LAnimUtils {

    public static final long PERFECT_MILLS = 350;
    public static final int MINI_RADIUS = 0;


    /**
     * 向四周伸张，直到完成显示。
     */
    public static void showAsCircular(View myView, float startRadius, long durationMills) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            showView(myView);
        } else {
            int cx = (myView.getLeft() + myView.getRight()) / 2;
            int cy = (myView.getTop() + myView.getBottom()) / 2;

            int w = myView.getWidth();
            int h = myView.getHeight();

            // 勾股定理 & 进一法
            int finalRadius = (int) Math.sqrt(w * w + h * h) + 1;

            Animator anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, startRadius, finalRadius);
            myView.setVisibility(View.VISIBLE);
            anim.setDuration(durationMills);
            anim.start();
        }

    }

    /**
     * 向四周伸张，直到完成显示。
     */
    public static void showView(View view) {
        ObjectAnimator.ofFloat(view,View.SCALE_X,0,1)
                .setDuration(PERFECT_MILLS)
                .start();
        view.setVisibility(View.VISIBLE);
       /* Animation scaleAnimation = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(PERFECT_MILLS);
        scaleAnimation.setAnimationListener(new CustAnimationListener(view) {
            @Override
            public void onAnimationEnd(Animation animation) {
                mView.clearAnimation();
            }
        });
        view.startAnimation(scaleAnimation);*/
    }

    /**
     * 由满向中间收缩，直到隐藏。
     */
    public static void hideView(View view) {
        ObjectAnimator.ofFloat(view,View.SCALE_X,1f,0)
                .setDuration(PERFECT_MILLS)
                .start();
      /*  Animation scaleAnimation = new ScaleAnimation(1, 0, 1, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(PERFECT_MILLS);
        scaleAnimation.setAnimationListener(new CustAnimationListener(view) {
            @Override
            public void onAnimationEnd(Animation animation) {
                mView.setVisibility(View.GONE);
                mView.clearAnimation();
            }
        });
        view.startAnimation(scaleAnimation);*/
    }


    /**
     * 由满向中间收缩，直到隐藏。
     */
    public static void hideAsCircular(final View myView, float endRadius, long durationMills) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            hideView(myView);
        } else {
            int cx = (myView.getLeft() + myView.getRight()) / 2;
            int cy = (myView.getTop() + myView.getBottom()) / 2;
            int w = myView.getWidth();
            int h = myView.getHeight();

            // 勾股定理 & 进一法
            int initialRadius = (int) Math.sqrt(w * w + h * h) + 1;

            Animator anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius, endRadius);
            anim.setDuration(durationMills);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    myView.setVisibility(View.INVISIBLE);
                }
            });

            anim.start();
        }


    }

    /**
     * view旋转
     */
    public static void viewRotate(View view, int startAngle, int endAngle) {
        ObjectAnimator.ofFloat(view,View.ROTATION_X,startAngle,endAngle)
                .setDuration(PERFECT_MILLS)
                .start();
     /*   Animation rotateAnimation = new RotateAnimation(startAngle, endAngle, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);//显示动画
        rotateAnimation.setDuration(PERFECT_MILLS);
        rotateAnimation.setFillAfter(true);
        view.startAnimation(rotateAnimation);*/
    }


    /**
     * 从指定View开始向四周伸张(伸张颜色或图片为colorOrImageRes), 然后进入另一个Activity,
     * 返回至 @thisActivity 后显示收缩动画。
     */
    public static void startActivityForResultAsCircular(
            final Activity thisActivity, final Intent intent, final Integer requestCode, @Nullable final Bundle bundle,
            final View triggerView, int colorOrImageRes, final long durationMills) {

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            thisActivity.startActivity(intent);
        } else {
            int[] location = new int[2];
            triggerView.getLocationInWindow(location);
            final int cx = location[0] + triggerView.getWidth() / 2;
            final int cy = location[1] + triggerView.getHeight() / 2;
            final ImageView view = new ImageView(thisActivity);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setImageResource(colorOrImageRes);
            final ViewGroup decorView = (ViewGroup) thisActivity.getWindow().getDecorView();
            int w = decorView.getWidth();
            int h = decorView.getHeight();
            decorView.addView(view, w, h);
            final int finalRadius = (int) Math.sqrt(w * w + h * h) + 1;
            Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
            anim.setDuration(durationMills);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        if (requestCode == null)
                            thisActivity.startActivity(intent);
                        else if (bundle == null)
                            thisActivity.startActivityForResult(intent, requestCode);
                        else
                            thisActivity.startActivityForResult(intent, requestCode, bundle);

                        // 默认渐隐过渡动画.
                        thisActivity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        // 默认显示返回至当前Activity的动画.
                        triggerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                                    Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, finalRadius, 0);
                                    anim.setDuration(durationMills);
                                    anim.addListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            super.onAnimationEnd(animation);
                                            try {
                                                decorView.removeView(view);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                    anim.start();
                                }
                            }
                        }, PERFECT_MILLS);
                    }

                }
            });
            anim.start();
        }


    }


    /*下面的方法全是重载，用简化上面方法的构建*/


    public static void startActivityForResultAsCircular(
            Activity thisActivity, Intent intent, Integer requestCode, View triggerView, int colorOrImageRes) {
        startActivityForResultAsCircular(thisActivity, intent, requestCode, null, triggerView, colorOrImageRes, PERFECT_MILLS);
    }

    public static void startActivityAsCircular(
            Activity thisActivity, Intent intent, View triggerView, int colorOrImageRes, long durationMills) {
        startActivityForResultAsCircular(thisActivity, intent, null, null, triggerView, colorOrImageRes, durationMills);
    }

    public static void startActivityAsCircular(
            Activity thisActivity, Intent intent, View triggerView, int colorOrImageRes) {
        startActivityAsCircular(thisActivity, intent, triggerView, colorOrImageRes, PERFECT_MILLS);
    }

    public static void startActivityAsCircular(Activity thisActivity, Class<?> targetClass, View triggerView, int colorOrImageRes) {
        startActivityAsCircular(thisActivity, new Intent(thisActivity, targetClass), triggerView, colorOrImageRes, PERFECT_MILLS);
    }

    public static void showAsCircular(View myView) {
        showAsCircular(myView, MINI_RADIUS, PERFECT_MILLS);
    }

    public static void hideAsCircular(View myView) {
        hideAsCircular(myView, MINI_RADIUS, PERFECT_MILLS);
    }


    public static abstract class CustAnimationListener implements Animation.AnimationListener {
        protected View mView;

        public CustAnimationListener(View view) {
            mView = view;
        }

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

}
