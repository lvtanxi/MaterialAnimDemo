package com.lv.materialanimdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * User: 吕勇
 * Date: 2016-09-19
 * Time: 09:12
 * Description:
 */
public class AnimateLayoutChangesAct extends AppCompatActivity{
    public static void startAnimateLayoutChangesAct(Activity activity) {
        activity.startActivity(new Intent(activity, AnimateLayoutChangesAct.class));
    }
    private ViewGroup mContainerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_animate_layoutchanges);

        mContainerView = (ViewGroup) findViewById(R.id.container);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        //响应添加事件的menu
        getMenuInflater().inflate(R.menu.activity_layout_changes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpTo(this, new Intent(this, AnimateLayoutChangesAct.class));
                return true;

            case R.id.action_add_item:
                //添加子View
                findViewById(android.R.id.empty).setVisibility(View.GONE);
                addItem();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addItem() {
        //实例化一个子View
        final ViewGroup newView = (ViewGroup) LayoutInflater.from(this).inflate(
                R.layout.list_item_example, mContainerView, false);

        // 随机设置子View的内容
        ((TextView) newView.findViewById(android.R.id.text1)).setText(
                COUNTRIES[(int) (Math.random() * COUNTRIES.length)]);

        //设置删除按钮的监听
        newView.findViewById(R.id.delete_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContainerView.removeView(newView);

                // If there are no rows remaining, show the empty view.
                if (mContainerView.getChildCount() == 0) {
                    findViewById(android.R.id.empty).setVisibility(View.VISIBLE);
                }
            }
        });
        //添加子View
        mContainerView.addView(newView, 0);
    }

    /**
     * A static list of country names.
     */
    private static final String[] COUNTRIES = new String[]{
            "Belgium", "France", "Italy", "Germany", "Spain",
            "Austria", "Russia", "Poland", "Croatia", "Greece",
            "Ukraine",
    };
}
