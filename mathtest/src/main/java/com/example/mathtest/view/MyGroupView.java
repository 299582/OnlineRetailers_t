package com.example.mathtest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class MyGroupView extends ViewGroup {

    private int width;
    private int height;
    private View v;

    public MyGroupView(Context context) {
        super(context);
    }

    public MyGroupView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGroupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        //布局的宽和高
        width = this.getMeasuredWidth();
        height = this.getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {



        //拿到子控件的个数
        int childCount = getChildCount();
        //定义一个临时高度
        int left=0;
        int top=0;
        //循环遍历出每一个View
        for(int a=0;a<childCount;a++){
            v = getChildAt(a);
            //给每一个view设置自己的位置  左  上  右  下

            if(left+v.getMeasuredWidth()<=width){
                v.layout(left,top,left+v.getMeasuredWidth(),top+v.getMeasuredHeight());
                left+=v.getMeasuredWidth();
                top+=v.getMeasuredHeight();
                Log.d("dddd",left+v.getMeasuredWidth()+""+width);

            } else if(left+v.getMeasuredWidth()>width){
                left = 0;
                top = v.getMeasuredHeight()+top;
                v.layout(left,top,left+v.getMeasuredWidth(),top+v.getMeasuredHeight());
                left+=v.getMeasuredWidth();
                top+=v.getMeasuredHeight();
                Log.d("qqq",left+"    "+top);

            }




        }


    }


}
