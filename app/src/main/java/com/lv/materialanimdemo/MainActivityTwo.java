package com.lv.materialanimdemo;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.transition.ChangeClipBounds;
import android.transition.ChangeTransform;
import android.transition.Explode;
import android.transition.Fade;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.lv.materialanimdemo.transitiondemo.TestOne;

public class MainActivityTwo extends AppCompatActivity implements View.OnClickListener {
    private ATLoginButton tesLogin;
    private boolean loaginStatus = true;

    private void assignViews() {
        tesLogin = (ATLoginButton) findViewById(R.id.tesLogin);
        tesLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 这里是登陆动画,然后去请求服务器接口
                tesLogin.buttonLoginAction();
                loaginStatus = !loaginStatus;
                // 加入三秒后,登陆失败或者成功
                tesLogin.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //这里调用View获取登陆状态的方法,成功或者失败
                        tesLogin.buttonLoaginResultAciton(loaginStatus);
                        String notice = loaginStatus ? "登陆成功,重置button状态" : "登录失败,显示失败状态";
                        Toast.makeText(getApplicationContext(), notice, Toast.LENGTH_SHORT).show();
                    }
                }, 3000);
            }
        });
        final CircularProgressButton circularButton1 = (CircularProgressButton) findViewById(R.id.circularButton1);
        circularButton1.setIndeterminateProgressMode(true);
        circularButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tesLogin.setViewState(ATLoginButton.LoginViewState.READY_STATE);
                if (circularButton1.getProgress() < 50) {
                    circularButton1.setProgress(50);
                } else if (circularButton1.getProgress() == 100) {
                    circularButton1.setProgress(0);
                } else {
                    circularButton1.setProgress(100);
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_main);
        Button btn1 = (Button) findViewById(R.id.one);
        Button btn2 = (Button) findViewById(R.id.two);
        Button btn3 = (Button) findViewById(R.id.three);
        Button btn4 = (Button) findViewById(R.id.four);
        Button btn5 = (Button) findViewById(R.id.five);
        Button btn6 = (Button) findViewById(R.id.six);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        assignViews();
    }

    @Override
    public void onClick(View v) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.one:
                    getWindow().setExitTransition(new Explode());
                    intent = new Intent(MainActivityTwo.this, TwoActivity.class);
                    startActivity(intent,
                            ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                    break;
                case R.id.two:
                    getWindow().setExitTransition(new ChangeTransform());
                    intent = new Intent(MainActivityTwo.this, TwoActivity.class);
                    startActivity(intent,
                            ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                    break;
                case R.id.three:
                    getWindow().setExitTransition(new ChangeClipBounds());
                    intent = new Intent(MainActivityTwo.this, TwoActivity.class);
                    startActivity(intent,
                            ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                    break;
                case R.id.four:
                    ChangeBounds changeBounds = new ChangeBounds();
                    getWindow().setExitTransition(changeBounds);
                    intent = new Intent(MainActivityTwo.this, TwoActivity.class);
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                    break;
                case R.id.five:
                    getWindow().setExitTransition(new Fade());
                    intent = new Intent(MainActivityTwo.this, TwoActivity.class);
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                    break;
                case R.id.six:
                    getWindow().setExitTransition(new Explode());
                    intent = new Intent(MainActivityTwo.this, TwoActivity.class);
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, Pair.create(v, "test"));

                    startActivity(intent, options.toBundle());
                default:
            }
        }
    }

    public void toTest(View view) {
        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setExitTransition(new Explode());
            startActivity(new Intent(MainActivityTwo.this, TestOne.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            return;
        }
        startActivity(new Intent(MainActivityTwo.this, TestOne.class));
    }
}
