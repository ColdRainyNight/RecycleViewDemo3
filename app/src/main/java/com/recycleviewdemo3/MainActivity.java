package com.recycleviewdemo3;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    XRecyclerView xrecyclerview;
    private List<Data.美女Bean> list = new ArrayList<>();
    String url = "http://c.3g.163.com/recommend/getChanListNews?channel=T1456112189138&size=20&passport=&devId=1uuFYbybIU2oqSRGyFrjCw%3D%3D&lat=%2F%2FOm%2B%2F8ScD%2B9fX1D8bxYWg%3D%3D&lon=LY2l8sFCNzaGzqWEPPgmUw%3D%3D&version=9.0&net=wifi&ts=1464769308&sign=bOVsnQQ6gJamli6%2BfINh6fC%2Fi9ydsM5XXPKOGRto5G948ErR02zJ6%2FKXOnxX046I&encryption=1&canal=meizu_store2014_news&mac=sSduRYcChdp%2BBL1a9Xa%2F9TC0ruPUyXM4Jwce4E9oM30%3D";

    private MyAdapter adapter;

    private int height = 640;// 滑动开始变色的高,真实项目中此高度是由广告轮播或其他首页view高度决定
    private int overallXScroll = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isNetworkAvailable(this);
        x.view().inject(this);

        xrecyclerview = (XRecyclerView) findViewById(R.id.xrecyclerview);

        adapter = new MyAdapter(list, this);
        xrecyclerview.setAdapter(adapter);
        initView();

        StaggeredGridLayoutManager manger = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        xrecyclerview.setLayoutManager(manger);

        //自定义分割线
        xrecyclerview.addItemDecoration(new ItemDecoration(MainActivity.this));

        xrecyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //刷新数据在这
                xrecyclerview.refreshComplete();
                initView();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onLoadMore() {
                //负载更这里数据
                xrecyclerview.loadMoreComplete();
                initView();
                adapter.notifyDataSetChanged();
            }
        });
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                initView();
            } else {
                new AlertDialog.Builder(MainActivity.this).setTitle("是否设置网络？")//设置对话框标题
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                            @Override
                            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                Intent wifiSettingsIntent = new Intent("android.settings.WIFI_SETTINGS");
                                startActivity(wifiSettingsIntent);
                            }

                        }).setNegativeButton("返回", new DialogInterface.OnClickListener() {//添加返回按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//响应事件
                        Toast.makeText(MainActivity.this, "当前无网络连接", Toast.LENGTH_LONG).show();
                        finish();
                    }

                }).show();//在按键响应事件中显示此对话框

            }
        }
        return false;
    }

    private void initView() {
        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                Data bean = new Gson().fromJson(result, Data.class);
                list.addAll(bean.get美女());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

}
