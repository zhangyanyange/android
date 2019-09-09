package com.mvc.microfeel.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;

import com.baidu.mapapi.SDKInitializer;
import com.hyphenate.easeui.EaseUI;
import com.tencent.bugly.crashreport.CrashReport;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;

/**
 * Created by zy2 on 2017/8/10.
 */

public class MyApplication extends Application {

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

       initBaiduMapSdk();

        initBuggy();

        initOkHttpUtils();

        initChat();

    }
    public static MyApplication getInstance(){
        if(instance==null) {
            instance = new MyApplication();
        }
        return instance;
    }

    private void initBaiduMapSdk(){
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
//        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
//       SDKInitializer.setCoordType(CoordinateConverter.CoordType.BD09LL);
    }

    private  void initBuggy(){
        CrashReport.initCrashReport(getApplicationContext(), "f56f399b7e", false);
    }

    public void initOkHttpUtils(){
        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                //其他配置
                .build();

        OkHttpUtils.getInstance(okHttpClient);//持久化存储cookie
    }

    private void initChat(){
        EaseUI.getInstance().init(this, null);
//        EMOptions options = new EMOptions();
//        EMClient.getInstance().init(this, options);
    }
}
