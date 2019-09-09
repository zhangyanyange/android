package com.mvc.microfeel.util;

import android.content.Context;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import com.mvc.microfeel.base.MyApplication;


/**
 * Created by zhanyan
 */
public class UIUtils {
    /**
     * 得到上下文,通过application
     */
    public static Context getContext(){
        return MyApplication.getContext();
    }
    /**
     * 得到资源
     */
    public static Resources getResources(){
        return getContext().getResources();
    }
    /**
     * 通过idString.xml的信息
     */
    public static String getString(int resId){
        return getResources().getString(resId);
    }
    /**
     * 通过id得到String.xml的信息数组
     */
    public static String[] getStrings(int resId){
        return getResources().getStringArray(resId);
    }
    /**
     * 得到Color.xml颜色信息
     */
    public static int getColor(int resId){
        return getResources().getColor(resId);
    }
    /**
     * 得到包名
     */
    public static String getPackageName() {
        return getContext().getPackageName();
    }

    /**
     * px转dp
     * @param px
     * @return
     */
    public static int px2dip(float px){
        //px/dip=density
        //屏幕密度
        float density = getResources().getDisplayMetrics().density;
        int dip= (int) (px/density+.5f);

        return dip;
    }

    /**
     * dp转px
     * @param dip
     * @return
     */
    public static int dip2px(float dip){
        //px/dip=density
        //屏幕密度
        float density = getResources().getDisplayMetrics().density;
        int px= (int) (dip*density+.5f);

        return px;
    }

    /**
     * 保存数据到sp
     * @param key
     * @param value
     */
    public static void savePreference(String key, String value) {
        PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext()).edit()
                .putString(key, value).commit();
    }


    /**
     * 读取配置参数
     *
     * @param key
     * @return
     */
    public static String getPreference(String key) {
        return PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext())
                .getString(key, "");
    }
}
