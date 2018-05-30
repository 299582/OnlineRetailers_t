package com.example.mathtest.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mathtest.R;
import com.example.mathtest.presenter.AnimationPresenter;
import com.example.mathtest.presenter.AnimationPresenterInterface;
import com.example.mathtest.presenter.Presenter;
import com.example.mathtest.presenter.presenterlistener;

public class MainActivity extends AppCompatActivity implements presenterlistener,AnimationPresenterInterface {

    private MyGroupView groupView;
    private int num = 1;
    private int width;
    private Integer in;
    private int t = 5010;
    private int type;
    private Presenter presenter;
    private TextView textView;
    private AnimationPresenter animationPresenter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        groupView = findViewById(R.id.mygroupview);
        presenter = new Presenter(this);
        animationPresenter = new AnimationPresenter(this);


    }

    public void jia(View view){
        //点击按钮就创建一个textview
        textView = new TextView(this);
        textView.setText(num+"");
        num++;
        //得到手机屏幕的宽度
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        //设置textview的宽度为屏幕宽度的三分之一
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width/3, 80);
        textView.setLayoutParams(lp);
        //得到textview的文本内容
        String s = textView.getText().toString();
        //string转int
        in = Integer.valueOf(s);
        //设置textview的背景颜色
        presenter.setTextColor(in);
        //设置textview的位置居中
        textView.setGravity(Gravity.CENTER);
        //让自定义 view布局加载textview
        groupView.addView(textView);
        //设置动画从左向右平移出来
        animationPresenter.setAnimation(textView);
        //textview的长按点击删除事件
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                groupView.removeView(textView);
                num = in;
                return true;
            }
        });
        //textview的点击跳转传值事件
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shu = textView.getText().toString();
                Integer value = Integer.valueOf(shu);
                type = t+value;
                Intent intent = new Intent(MainActivity.this,RecycListActivity.class);
                intent.putExtra("type",type);
                startActivity(intent);

            }
        });
    }

    @Override
    public void setcolorred() {
        textView.setBackgroundColor(Color.RED);
    }

    @Override
    public void setcolorgreen() {
        textView.setBackgroundColor(Color.GREEN);
    }

    @Override
    public void setcolorblue() {
        textView.setBackgroundColor(Color.BLUE);
    }

}
