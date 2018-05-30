package com.example.mathtest.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.mathtest.R;
import com.example.mathtest.model.NewsBean;
import com.example.mathtest.model.UseDao;
import com.example.mathtest.utils.NetStateUtil;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecycListActivity extends AppCompatActivity {

    private int type;
    private String url;
    private OkhttpManger manger = OkhttpManger.getInstance();
    private XRecyclerView recyclerView;
    private UseDao useDao;

    private int opentype = 1;
    private List<NewsBean.DataBean> list = new ArrayList<NewsBean.DataBean>();
    private MyRecycAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyc_list);
        Intent intent = getIntent();
        recyclerView = findViewById(R.id.recycleview);
        type = intent.getIntExtra("type",0);
        useDao = new UseDao(RecycListActivity.this);
        url = "http://ttpc.dftoutiao.com/jsonpc/refresh?type="+type;

        recyclerView.setLayoutManager(new LinearLayoutManager(RecycListActivity.this,LinearLayoutManager.VERTICAL,false));


        if(NetStateUtil.isConn(this)){
            manger.asyncJsonStringByUrl(url, new OkhttpManger.Func1() {
                @Override
                public void onResponse(String result) {
                    String s = result.replace("null(", "").replace(")", "");
                    addDB(url,s);
                    getJsonData(s);

                }
            });
        }else{

            Toast.makeText(this, "请稍等，当前没有网络", Toast.LENGTH_SHORT).show();
            String str = useDao.queryData(url);
            getJsonData(str);

        }

        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                opentype = 1;
                type = 5010;
                url = "http://ttpc.dftoutiao.com/jsonpc/refresh?type="+type;
                manger.asyncJsonStringByUrl(url, new OkhttpManger.Func1() {
                    @Override
                    public void onResponse(String result) {
                        String s = result.replace("null(", "").replace(")", "");
                        getJsonData(s);
                    }
                });
            }

            @Override
            public void onLoadMore() {
                opentype = 2;
                type++;
                url = "http://ttpc.dftoutiao.com/jsonpc/refresh?type="+type;
                manger.asyncJsonStringByUrl(url, new OkhttpManger.Func1() {
                    @Override
                    public void onResponse(String result) {
                        String s = result.replace("null(", "").replace(")", "");
                        getJsonData(s);
                    }
                });
            }
        });

    }

    private void getJsonData(String datas) {
        Gson gson = new Gson();
        NewsBean newsBean = gson.fromJson(datas, NewsBean.class);
        final List<NewsBean.DataBean> data = newsBean.getData();
        if(opentype == 1){
            list.clear();
        }
        list.addAll(data);
        recyclerView.refreshComplete();
        setadapter();


        adapter.setOnItemClickListener(new MyRecycAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int Position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RecycListActivity.this);
                builder.setTitle("删除");
                builder.setMessage("确定要删除这条数据吗");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("ppp",Position+"");
                        list.remove(Position);
                        adapter.notifyItemRemoved(Position);

                    }
                });
                builder.setNegativeButton("取消",null);
                builder.show();
            }
        });


    }

    private void setadapter() {
        if(adapter == null){
            adapter = new MyRecycAdapter(RecycListActivity.this,list);
            recyclerView.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }
    }

    private void addDB(String url,String jsonData){
        useDao.addData(url,jsonData);
    }
}
