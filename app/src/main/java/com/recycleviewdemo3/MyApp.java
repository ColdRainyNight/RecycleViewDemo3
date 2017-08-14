package com.recycleviewdemo3;

import android.app.Application;

import org.xutils.*;
import org.xutils.BuildConfig;

/**
 * 类描述：
 * 创建人：xuyaxi
 * 创建时间：2017/8/14 19:09
 */
public class MyApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
    }
}
