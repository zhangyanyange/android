package com.microfeel.meiquetiliao;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewTreeObserver;
import android.webkit.MimeTypeMap;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.microfeel.meiquetiliao.base.Constants;
import com.microfeel.meiquetiliao.base.LoadingPager;
import com.microfeel.meiquetiliao.domain.AndroidVerion;
import com.microfeel.meiquetiliao.fragment.HomeFragment;
import com.microfeel.meiquetiliao.fragment.SettingFragment;
import com.microfeel.meiquetiliao.util.UIUtils;
import com.microfeel.meiquetiliao.view.NoScrollView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

public class MainActivity extends FragmentActivity {


    @Bind(R.id.vp_content)
    NoScrollView vpContent;
    @Bind(R.id.rb_home)
    RadioButton rbHome;
    @Bind(R.id.rb_setting)
    RadioButton rbSetting;
    @Bind(R.id.rgGroup)
    RadioGroup rgGroup;
    private MyOnPageChangeListener listener;
    private HomeFragment baseFragment;
    public static final int SHOW_UPDATE_DIALOG = 1;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_UPDATE_DIALOG:// 显示应用更新对话框
                    showUpdateDialog();
                    break;
            }
        }

        ;
    };
    private long mTaskId;
    private DownloadManager downloadManager;
    private AndroidVerion androidVerion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        EventBus.getDefault().register(this);

        ButterKnife.bind(this);
        OkHttpUtils.get().url(Constants.BASE_URL+"Account/getAndroidServerVersion").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                Gson gson=new Gson();
                androidVerion = gson.fromJson(response, AndroidVerion.class);

                PackageManager pm = UIUtils.getContext().getPackageManager();
                PackageInfo pi = null;
                try {
                    pi = pm.getPackageInfo(MainActivity.this.getPackageName(), 0);
                    int versionCode = pi.versionCode;

                    if (!androidVerion.getVersion().equals(""+versionCode)) {
                        Message msg = Message.obtain();
                        msg.what = SHOW_UPDATE_DIALOG;
                        handler.sendMessage(msg);
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });
        initListener();
    }

    private void initListener() {


        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        vpContent.setCurrentItem(0, false);
                        break;
                    case R.id.rb_setting:
                        vpContent.setCurrentItem(1, false);
                        break;
                }
            }
        });

        vpContent.setAdapter(new MainFragmentStaticPagerAdapter(getSupportFragmentManager()));
        listener = new MyOnPageChangeListener();
        vpContent.setOnPageChangeListener(listener);
        vpContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                listener.onPageSelected(0);
                vpContent.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });


    }


    public class MainFragmentStaticPagerAdapter extends FragmentStatePagerAdapter {

        public MainFragmentStaticPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                baseFragment = new HomeFragment();
                return baseFragment;
            }
            SettingFragment fragment = new SettingFragment();
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        /**
         * 页面被选中时调用方法,数据应该在此时加载
         *
         * @param position 第几页数据
         */
        @Override
        public void onPageSelected(int position) {
            if (position == 0) {
//               HomeFragment baseFragment=new HomeFragment();
                //取出LoadingPager对象
                LoadingPager loadingPager = baseFragment.getLoadingPager();
                //调用LoadingPager里面的触摸方法
                loadingPager.triggerLoadData();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
//      EventBus.getDefault().post(new FinisnLogin());
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void payComplete(FinisnMain payComplete) {
//        finish();
//    }

    //广播接受者，接收下载状态
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkDownloadStatus();//检查下载状态
        }
    };



    /**
     * 显示自动更新的对话框
     *
     * @param
     */
    protected void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("升级提醒");
        builder.setMessage("有新版本");
        builder.setPositiveButton("立刻升级", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)) {
                    downloadAPK("http://microfeel.net/webMvc/files/apk/app-meiquetiliao.apk",androidVerion.getVersion());
                } else {
                    // 进入应用程序主界面.
                    Toast.makeText(UIUtils.getContext(), "sd卡不可用,无法自动更新", Toast.LENGTH_SHORT)
                            .show();

                }
            }
        });
        builder.setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    //使用系统下载器下载
    private void downloadAPK(String versionUrl, String versionName) {
        //创建下载任务
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(versionUrl));
        request.setAllowedOverRoaming(true);//漫游网络是否可以下载

        //设置文件类型，可以在下载结束后自动打开该文件
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        String mimeString = mimeTypeMap.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(versionUrl));
        request.setMimeType(mimeString);

        //在通知栏中显示，默认就是显示的
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setVisibleInDownloadsUi(true);
        request.setTitle("美雀提料");
        request.setDescription("优化用户体验");
        //sdcard的目录下的download文件夹，必须设置
        request.setDestinationInExternalPublicDir("/download/", versionName);
        //request.setDestinationInExternalFilesDir(),也可以自己制定下载路径

        //将下载请求加入下载队列
        downloadManager = (DownloadManager) MainActivity.this.getSystemService(Context.DOWNLOAD_SERVICE);
        //加入下载队列后会给该任务返回一个long型的id，
        //通过该id可以取消任务，重启任务等等，看上面源码中框起来的方法
        mTaskId = downloadManager.enqueue(request);

        //注册广播接收者，监听下载状态
        MainActivity.this.registerReceiver(receiver,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    //检查下载状态
    private void checkDownloadStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(mTaskId);//筛选下载任务，传入任务ID，可变参数
        Cursor c = downloadManager.query(query);
        if (c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                case DownloadManager.STATUS_PAUSED:
                    break;
                case DownloadManager.STATUS_PENDING:
                    break;
                case DownloadManager.STATUS_RUNNING:

                    break;
                case DownloadManager.STATUS_SUCCESSFUL:

                    //下载完成安装APK
                    String downloadPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + androidVerion.getVersion();
                    installAPK(new File(downloadPath));
                    break;
                case DownloadManager.STATUS_FAILED:

                    break;
            }
        }
    }

    //下载到本地后执行安装
    protected void installAPK(File file) {
        if (!file.exists()) return;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse("file://" + file.toString());
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        //在服务中开启activity必须设置flag,后面解释
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MainActivity.this.startActivity(intent);
    }

}
