package com.lv.materialanimdemo;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.transition.ChangeClipBounds;
import android.transition.ChangeTransform;
import android.transition.Explode;
import android.transition.Fade;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.lv.materialanimdemo.transitiondemo.LAnimUtils;
import com.lv.materialanimdemo.transitiondemo.TestOne;

public class MainActivityTwo extends AppCompatActivity implements View.OnClickListener {
    private ATLoginButton tesLogin;
    private boolean loaginStatus = true;

    private Toolbar toolbar=null;
    private SearchView searchView=null;

    private Toolbar.OnMenuItemClickListener onMenuItemClickListener=new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.action_search:
                    Snackbar.make(toolbar,"Click Search",Snackbar.LENGTH_SHORT).show();
                    break;
                case R.id.action_share:
                    Snackbar.make(toolbar,"Click Share",Snackbar.LENGTH_SHORT).show();
                    break;
                case R.id.action_more:
                    Snackbar.make(toolbar,"Click More",Snackbar.LENGTH_SHORT).show();
                    break;
            }


            return true;
        }
    };

    private void initToolbar(){
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        //title
        toolbar.setTitle("  Material Design ToolBar");
        //sub title
        toolbar.setSubtitle("  ToolBar subtitle");
        //以上3个属性必须在setSupportActionBar(toolbar)之前调用
        setSupportActionBar(toolbar);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);

        //设置导航Icon，必须在setSupportActionBar(toolbar)之后设置
        toolbar.setNavigationIcon(R.mipmap.ic_menu_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(toolbar,"Click setNavigationIcon",Snackbar.LENGTH_SHORT).show();
            }
        });
        toolbar.setOnMenuItemClickListener(onMenuItemClickListener);
    }


    private void assignViews() {
        initToolbar();
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
                        tesLogin.buttonLoaginResultAciton(loaginStatus, new ATLoginButton.AnimationEndListener() {
                            @Override
                            public void animationEnd() {
                                LAnimUtils.startActivityAsCircular(MainActivityTwo.this, DirectionAct.class, tesLogin, R.color.colorAccent);
                            }
                        });
                        String notice = loaginStatus ? "登陆成功,重置button状态" : "登录失败,显示失败状态";
                        Toast.makeText(getApplicationContext(), notice, Toast.LENGTH_SHORT).show();
                    }
                }, 800);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //用MenuItem的`getActionView()`方法获取`android:actionViewClass`对应的实例,这里是`android.widget.SearchView`
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
//        searchView.setSubmitButtonEnabled(true);//是否显示确认搜索按钮
//        searchView.setIconifiedByDefault(true);//设置展开后图标的样式,这里只有两种,一种图标在搜索框外,一种在搜索框内
//        searchView.setIconified(false);//设置
//        searchView.clearFocus();//清除焦点
        /*
        这里是重点,SearchView并没有提供样式的修改方法,所以只能
        1.用获取到的实例调用getContext()方法,返回当前view的上下文
        2.调用getResources()方法,获取该view的资源实例(Return a Resources instance)
        3.调用getIdentifier()方法,获取相同名字的ID,(Return a resource identifier for the given resource name)
        4.通过findViewById()获取该ID的实例,然后就可以做相应的操作了
        */
//        int search_mag_icon_id = searchView.getContext().getResources().getIdentifier("android:id/search_mag_icon", null, null);
//        ImageView search_mag_icon = (ImageView)searchView.findViewById(search_mag_icon_id);//获取搜索图标
//        search_mag_icon.setImageResource(R.mipmap.ic_search_white_24dp);//图标都是用src的



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //当点击搜索按钮,输入法搜索按钮,会触发这个方法.在这里做相应的搜索事件,query为用户输入的值
                //当输入框为空或者""时,此方法没有被调用
                Log.e("wzj", "搜索文本-->"+s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //当输入的文字发生变化的时候,会触发这个方法.在这里做匹配提示的操作等
                Log.e("wzj", "当输入的文字发生变化的时候,会触发这个方法-->"+s);
                return false;
            }
        });
//        String str=searchView.getQuery().toString();

        return true;
    }
}
