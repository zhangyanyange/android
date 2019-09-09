package com.microfeel.meiquetiliao.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;

import com.microfeel.meiquetiliao.util.AddInterceptor;
import com.microfeel.meiquetiliao.util.TokenInterceptor;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;

/**
 * Created by zy2 on 2016/12/26.
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

        initOkHttpUtils();


    }
    public static MyApplication getInstance(){
        if(instance==null) {
            instance = new MyApplication();
        }
        return instance;
    }
    public void initOkHttpUtils(){
        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .addInterceptor(new AddInterceptor())
                .addInterceptor(new TokenInterceptor())
                //其他配置
                .build();

        OkHttpUtils.getInstance(okHttpClient);//持久化存储cookie
    }


}
