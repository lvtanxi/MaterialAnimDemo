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

import com.lv.materialanimdemo.transitiondemo.TestOne;

public class MainActivityTwo extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_main);
        Button btn1 = (Button)findViewById(R.id.one);
        Button btn2 = (Button)findViewById(R.id.two);
        Button btn3 = (Button)findViewById(R.id.three);
        Button btn4 = (Button)findViewById(R.id.four);
        Button btn5 = (Button)findViewById(R.id.five);
        Button btn6 = (Button)findViewById(R.id.six);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.one:
                getWindow().setExitTransition(new Explode());
                intent = new Intent(MainActivityTwo.this,TwoActivity.class);
                startActivity(intent,
                        ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.two:
                    getWindow().setExitTransition(new ChangeTransform());
                intent = new Intent(MainActivityTwo.this,TwoActivity.class);
                startActivity(intent,
                        ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.three:
                getWindow().setExitTransition(new ChangeClipBounds());
                intent = new Intent(MainActivityTwo.this,TwoActivity.class);
                startActivity(intent,
                        ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.four:
                ChangeBounds changeBounds = new ChangeBounds();
                getWindow().setExitTransition(changeBounds);
                intent = new Intent(MainActivityTwo.this,TwoActivity.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.five:
                getWindow().setExitTransition(new Fade());
                intent = new Intent(MainActivityTwo.this,TwoActivity.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.six:
                getWindow().setExitTransition(new Explode());
                intent = new Intent(MainActivityTwo.this,TwoActivity.class);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, Pair.create(v, "test"));

                 startActivity(intent,options.toBundle());
            default:
        }
    }

    public void toTest(View view) {
        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setExitTransition(new Explode());
            startActivity(new Intent(MainActivityTwo.this,TestOne.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            return;
        }
        startActivity(new Intent(MainActivityTwo.this,TestOne.class));
    }
}
