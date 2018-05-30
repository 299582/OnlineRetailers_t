package com.example.mathtest.view;

import android.os.Handler;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkhttpManger {
    //////////////////////初始化变量//////////////////
    private static Handler mHandler;
    private static OkhttpManger manger;
    private static OkHttpClient client;
    /////////////////////定义构造/////////////////////
    private OkhttpManger(){
         client = new OkHttpClient.Builder()
                 .connectTimeout(10, TimeUnit.SECONDS)
                 .readTimeout(10,TimeUnit.SECONDS)
                 .writeTimeout(10,TimeUnit.SECONDS)
                 .build();
         mHandler = new Handler();
    }

    /////////////////////单例模式//////////////////////

    public static OkhttpManger getInstance(){
        if(manger == null){
            manger = new OkhttpManger();
        }
        return manger;
    }

    ////////////////////创建接口///////////////////////
    interface Func1{
        void onResponse(String result);
    }
    ///////////////////将代码运行到主线程////////////////
    private static void onSuccessJsonStringMethod(final String jsonValue,final Func1 icallBack){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if(icallBack != null){
                    icallBack.onResponse(jsonValue);
                }
            }
        });
    }

    /////////////////暴露方法/////////////////////////
    public void asyncJsonStringByUrl(String url,final Func1 icallBack){
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                onSuccessJsonStringMethod(response.body().string(),icallBack);
            }
        });
    }
}
