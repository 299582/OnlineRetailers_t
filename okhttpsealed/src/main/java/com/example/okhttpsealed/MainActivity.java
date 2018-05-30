package com.example.okhttpsealed;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private OkhttpManger manger = OkhttpManger.getInstance();
    private String url = "http://ww1.sinaimg.cn/large/0065oQSqly1frepsy47grj30qo0y97en.jpg";
    private String url2 = "http://gank.io/api/data/福利/10/3";
    //private String log_url = "http://169.254.53.96:8080/web/LoginServlet";
    private ImageView imageView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);

        HashMap<String, String> map = new HashMap<>();

        map.put("qq","10000");
        map.put("pwd","abcde");

//        manger.asyncByteByUrl(log_url, map, new OkhttpManger.Func3() {
//            @Override
//            public void onResponse(byte[] result) {
//
//            }
//        });



    }

    public void loadimg(View view){
        manger.asyncBitmapByUrl(url, new OkhttpManger.Func2() {
            @Override
            public void onResponse(Bitmap result) {
                imageView.setImageBitmap(result);
            }
        });
    }

    public void loaddata(View view){
        manger.asyncJsonStringByUrl(url2, new OkhttpManger.Func1() {
            @Override
            public void onResponse(String result) {
                //Gson gson = new Gson();
                textView.setText(result);
            }
        });
    }
}
