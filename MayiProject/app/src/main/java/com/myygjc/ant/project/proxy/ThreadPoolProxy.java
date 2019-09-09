package com.myygjc.ant.project.proxy;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by aus on 2016/10/21.
 * 创建线程池代理类
 * 代理类的作用:1.对原来的api,进行增强
 */
public class ThreadPoolProxy {

    private ThreadPoolExecutor mExecutor; //线程池对象
    private int mCorePoolSize; //核心池大小
    private int mMaximumPoolSize; //最大线程数值

    /**
     * 生成构造方法,外界调用此方法,必须传入两个核心的数据
     * @param corePoolSize
     * @param maximumPoolSize
     */
    public ThreadPoolProxy(int corePoolSize, int maximumPoolSize) {
        mCorePoolSize = corePoolSize;
        mMaximumPoolSize = maximumPoolSize;
    }

    /**
     * 初始化线程池
     *
     * 简述双重锁:
     * 1.在多个对象同时调用初始化线程池方法,他们都能进去第一个判断
     * 2.这时就遇到了同步锁,必须一个一个执行,第一个对象创建完成了线程池对象
     * 3.之后一起调用的对象,进不去第二层判断,直接返回原有对象
     * 4.以后再有对象调用,第一层判断都进不去,同步锁保证只创建一次,节省内存
     */
    private void initThreadPoolExecutor() {
        //双重锁,保证线程池对象只被创建一次
        if(mExecutor==null || mExecutor.isShutdown() || mExecutor.isTerminated()) {

            synchronized (ThreadPoolProxy.class) {

                if (mExecutor == null || mExecutor.isShutdown() || mExecutor.isTerminated()) {
                    /**
                     * 我们创建两种线程池:1.正常线程池 5
                     *                 2.下载线程池 3
                     * 我们只要用一个无界队列就能搞定
                     * 保存时间 保存时间单位 线程工厂 队列 异常捕获器 与我们无关
                     */
                    long keepAliveTime = 0;   //保存时间
                    TimeUnit unit = TimeUnit.MICROSECONDS;        //保存时间单位
                    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue();  //队列
                    ThreadFactory threadFactory = Executors.defaultThreadFactory();    //线程工厂
                    RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();   //异常捕获器
                    mExecutor = new ThreadPoolExecutor(mCorePoolSize, mMaximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
                }
            }
        }
    }

    /**
     * 提交
     *
     * 简述提交与执行的区别
     * 1.提交有返回值,Future :未来 他会返回执行的结果
     * 2.Future 中有get方法,他会等待这个方法执行完成,是一个阻塞线程,他可以帮助我们处理,线程池执行任务时
     * 出现的异常,不会让程序再出异常时,爆掉.
     */
    public Future submit(Runnable task){
        initThreadPoolExecutor();

        Future<?> future = mExecutor.submit(task);

        return future;
    }

    /**
     * 执行
     */
    public void execute(Runnable task){
        initThreadPoolExecutor();

        mExecutor.execute(task);
    }

    /**
     * 移除任务
     * @param task 耗时操作,在子线程中执行
     */
    public void remove(Runnable task){
        initThreadPoolExecutor();

        mExecutor.remove(task);
    }


}
