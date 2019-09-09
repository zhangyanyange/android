package com.mvc.microfeel.factory;


import com.mvc.microfeel.proxy.ThreadPoolProxy;

/**
 * Created by zhangyan on 2016/10/21.
 * 创建线程池工厂
 * 工厂目的:创建两种线程池的代理类
 * 1.正常线程池代理类
 */
public class ThreadPoolProxyFactory {

    public static ThreadPoolProxy mNormalThreadPoolProxy;


    /**
     * 正常线程池代理类
     */
    public static ThreadPoolProxy getNormalThreadPoolProxy() {

        if (mNormalThreadPoolProxy == null) {
            synchronized (ThreadPoolProxyFactory.class) {
                if (mNormalThreadPoolProxy == null) {
                    mNormalThreadPoolProxy = new ThreadPoolProxy(5, 5);
                }
            }
        }
        return mNormalThreadPoolProxy;
    }

}
