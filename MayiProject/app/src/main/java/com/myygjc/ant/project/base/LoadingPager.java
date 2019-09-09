package com.myygjc.ant.project.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.myygjc.ant.project.R;
import com.myygjc.ant.project.doman.NoLogin;
import com.myygjc.ant.project.factory.ThreadPoolProxyFactory;
import com.myygjc.ant.project.proxy.ThreadPoolProxy;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by aus on 2016/10/19.
 * LoadingPager:控制器
 * 1.返回视图
 * 2.返回数据
 * 3.将视图和数据绑定到一起
 */
public abstract class LoadingPager extends FrameLayout {

    //定义四种状态属性
    private static final int STATE_LODING = 0;
    private static final int STATE_ERROR = 1;
    private static final int STATE_SUCCESS = 2;
    private static final int STATE_EMPTY = 3;
    private static final int NO_LOGIN = 4;
    //定义一个变量,用来进行存储当前状态,默认当前状态为正在加载
    public int mCurState = STATE_LODING;

    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;
    private View mNoLoginView;
    private View mSuccessView;
    private LoadDataTask mTask;

    /**
     * 构造方法,没有在xml中用到这个自定义控件,不用写两个参数的
     *
     * @param context
     */
    public LoadingPager(Context context) {
        super(context);

        //创建方法,常规视图
        initCommentView();
    }

    /**
     * called 当初始化loadingpager时调用此方法
     */
    private void initCommentView() {
        //正在加载视图
        mLoadingView = View.inflate(getContext(), R.layout.pager_loading, null);
        this.addView(mLoadingView);

        //错误视图
        mErrorView = View.inflate(getContext(), R.layout.pager_error, null);
        this.addView(mErrorView);

        //设置错误视图点击事件
        mErrorView.findViewById(R.id.error_btn_retry).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击时重新加载数据
                triggerLoadData();
            }
        });

        //空视图
        mEmptyView = View.inflate(getContext(), R.layout.pager_empty, null);
        this.addView(mEmptyView);

        mNoLoginView = View.inflate(getContext(), R.layout.pager_nologin, null);
        this.addView(mNoLoginView);
        mNoLoginView.findViewById(R.id.noload).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                NoLogin noLogin=new NoLogin();
                EventBus.getDefault().post(noLogin);

            }
        });
        //根据状态刷新视图的显示还是隐藏
        refreshViewByState();
    }

    /**
     * @called 初始化构造函数时, 刷新状态
     * <p/>
     * 第二次触发事件,可能进行状态改变,可能有成功的视图显示
     */
    private void refreshViewByState() {
        if (mCurState == STATE_LODING) {
            mLoadingView.setVisibility(View.VISIBLE);
        } else {
            mLoadingView.setVisibility(View.GONE);
        }

        if (mCurState == STATE_ERROR) {
            mErrorView.setVisibility(View.VISIBLE);
        } else {
            mErrorView.setVisibility(View.GONE);
        }

        if (mCurState == STATE_EMPTY) {
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mEmptyView.setVisibility(View.GONE);
        }
        if (mCurState == NO_LOGIN ) {
            mNoLoginView.setVisibility(View.VISIBLE);
        } else {
            mNoLoginView.setVisibility(View.GONE);
        }

        //当前状态是成功状态,并且这个成功视图为空,那么创建成功视图
        if (mCurState == STATE_SUCCESS && mSuccessView == null) {
            mSuccessView = initSuccessView();
            //加载成功布局
            this.addView(mSuccessView);
        }
        if (mSuccessView != null) {
            //System.out.println("我输出了");
            if (mCurState == STATE_SUCCESS) {
                mSuccessView.setVisibility(View.VISIBLE);
            } else {
                mSuccessView.setVisibility(View.GONE);
            }
        }
    }


    /**
     * @called 触发加载数据, 此方法在外界调用, 用来进行加载数据的操作
     * 加载数据属于耗时操作,应当放在子线程操作
     */
    public void triggerLoadData() {

        //如果当前状态为成功状态,不进行任何操作
        if(mCurState!=STATE_SUCCESS) {
            //只有在没有异步任务时,才需要加载任务
            if(mTask==null) {

                //将当前状态设置为加载状态
                mCurState=STATE_LODING;

                //刷新界面
                refreshViewByState();

                mTask = new LoadDataTask();

                //new Thread(mTask).start();
                //利用线程池来完成任务
                ThreadPoolProxy normalThreadPoolProxy = ThreadPoolProxyFactory.getNormalThreadPoolProxy();
                normalThreadPoolProxy.submit(mTask);
            }
        }


    }
    /**
     * 让加载数据这个类进行异步操作
     */
    public class LoadDataTask implements Runnable {

        @Override
        public void run() {
            //得到返回的数据
            LoadedResult loadedResult = initData();

            //给当前状态赋值
            mCurState = loadedResult.getState();
            MyApplication.getHandler().post(new Runnable() {
                @Override
                public void run() {
                    //刷新状态需要在主线程工作
                    refreshViewByState();
                }
            });
            //置空任务
            mTask=null;
        }
    }

    /**
     * @return
     * @called 外界触发加载数据时调用此方法, 不知内部如何实现交给子类去完成
     */
    public abstract LoadedResult initData();

    /**
     * 有效的将数据和返回的视图结合起来
     *
     * @return
     * @called 此方法在外界调用触发事件, 并且状态为成功时调用
     */
    public abstract View initSuccessView();


    /**
     * 创建枚举的目的:1.为了固定参数
     */
    public enum LoadedResult {


        SUCCESS(STATE_SUCCESS), ERROR(STATE_ERROR), EMPTY(STATE_EMPTY),NOLOAD(NO_LOGIN);

        private int state;

        //生成构造方法
        LoadedResult(int state) {
            this.state = state;
        }

        public int getState() {
            return state;
        }
    }
}
