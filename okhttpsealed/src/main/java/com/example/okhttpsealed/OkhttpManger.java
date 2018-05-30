package com.example.okhttpsealed;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkhttpManger {

    ///////////////////////////初始化成员变量////////////////////////

    private static Handler mhandler;
    private static OkHttpClient okHttpClient;
    private static OkhttpManger manger;

    ///////////////////////////定义构造方法//////////////////////
    private OkhttpManger(){
        okHttpClient = new OkHttpClient.Builder()
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .connectTimeout(10,TimeUnit.SECONDS)
                .build();

        mhandler = new Handler();
    }

    /////////////////////////单例模式暴漏一个方法,给外界提供方法///////////////////////////

    public static OkhttpManger getInstance(){
        if(manger == null){
            manger = new OkhttpManger();
        }
        return manger;
    }

    /////////////////////////定义接口////////////////////////////////
    interface Func1{
        void onResponse(String result);
    }

    interface Func2{
        void onResponse(Bitmap result);
    }

    interface Func3{
        void onResponse(byte[] result);
    }

    ////////////////////////编写代码运行到主线程///////////////////////
    private static void onSuccessjsonStringMethod(final String jsonValue,final Func1 icallBack){
        mhandler.post(new Runnable() {
            @Override
            public void run() {
                if(icallBack != null){
                    icallBack.onResponse(jsonValue);
                }
            }
        });
    }

    private static void onSuccessBitmapMethod(final Bitmap bpdata,final Func2 icallBack){

        mhandler.post(new Runnable() {
            @Override
            public void run() {
                if(icallBack != null){
                    icallBack.onResponse(bpdata);
                }
            }
        });

    }

    private static void onSuccessByteMethod(final byte[] bytes,final Func3 icallBack){
        mhandler.post(new Runnable() {
            @Override
            public void run() {
                if(icallBack != null){
                    icallBack.onResponse(bytes);
                }
            }
        });

    }
    /////////////////////暴露方法给外界调用/////////////////////////////
    public void asyncJsonStringByUrl(final String url, final Func1 icallBack){
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                onSuccessjsonStringMethod(response.body().string(),icallBack);
            }
        });

    }

    public void asyncBitmapByUrl(String url,final Func2 icallBack){
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
               onSuccessBitmapMethod(BitmapFactory.decodeStream(inputStream),icallBack);
            }
        });
    }

    public void asyncByteByUrl(String url, Map<String,String> parms,final Func3 icallBack){

        //表单队像
        FormBody.Builder formBody = new FormBody.Builder();
        //键值非空判断
        if(parms!=null && !parms.isEmpty()){
            for (Map.Entry<String,String> entry:parms.entrySet()) {
                formBody.add(entry.getKey(),entry.getValue());
            }
        }
        final  FormBody build = formBody.build();

        Request request = new Request.Builder().url(url).post(build).build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                onSuccessByteMethod(response.body().bytes(),icallBack);
            }
        });

    }


}
