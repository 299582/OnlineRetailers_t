package com.example.mathtest.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UseDao {

    private SqliteHelper helper;

    public UseDao(Context context){
        helper = new SqliteHelper(context);
    }

    public void addData(String url,String data){

        SQLiteDatabase db = helper.getWritableDatabase();

        //先进行删除
        db.delete("biao","path=?",new String[]{url});

        ContentValues contentValues=new ContentValues();
        contentValues.put("path",url);
        contentValues.put("jsondata",data);


        //进行插入
        long rows = db.insert("biao", null, contentValues);
        //Log.d("zzz","addData-rows:"+rows);

    }

    /**
     * 根据指定的url 来查询数据
     *
     */
    public String queryData(String url){
        String json="";
        //得到一个可读可写的数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.query("biao", null, " path=?", new String[]{url}, null, null, null);


        while (cursor.moveToNext()){
            json=cursor.getString(cursor.getColumnIndex("jsondata"));
        }
        //Log.d("zzz","---从数据库中获取---"+json);


        return  json;

    }
}
