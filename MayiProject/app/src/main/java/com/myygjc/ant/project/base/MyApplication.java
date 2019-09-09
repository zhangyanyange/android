package com.myygjc.ant.project.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by zy2 on 2016/12/26.
 */

public class MyApplication extends Application{

    private static Context mContext;
    private static Handler mHandler;
    private static int mMainThreadId;


    //创建单例
    private Map<String, String> mMemProtocolCacheMap = new HashMap<>();
    private static MyApplication instance;

    public Map<String, String> getMemProtocolCacheMap() {
        return mMemProtocolCacheMap;
    }

    public static Context getContext() {
        return mContext;
    }

    public static Handler getHandler() {
        return mHandler;
    }

    public static int getMainThreadId() {
        return mMainThreadId;
    }
    @Override
    public void onCreate() {
        super.onCreate();

        //上下文
        mContext = getApplicationContext();
        //得到主线程中的handler
        mHandler = new Handler();
        //得到这个主线程中的线程id
        mMainThreadId = Process.myTid();

        //初始化sdk
        JPushInterface.setDebugMode(true);//正式版的时候设置false，关闭调试
        JPushInterface.init(this);
    }
    public static MyApplication getInstance(){
        if(instance==null) {
            instance = new MyApplication();
        }
        return instance;
    }

}
