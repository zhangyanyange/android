package com.mvc.microfeel.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.mvc.microfeel.base.MyApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by zy2 on 2016/12/27.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    public static final String TAG="CrashHandler";

    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private static CrashHandler INSTANCE=new CrashHandler();

    private Context mContext;

    private Map<String,String> infos=new HashMap<>();

   private DateFormat formatter= new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    private CrashHandler(){

    }
    public static CrashHandler getInstance(){
        return INSTANCE;
    }

    public void init(Context context){
        mContext =context;
        mDefaultHandler= Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }


    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if(!handleException(ex)&&mDefaultHandler!=null){
            mDefaultHandler.uncaughtException(thread, ex);
        }else{
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e(TAG,"error :",e);
            }
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    private boolean handleException(Throwable ex){
        if(ex==null){
            return false;
        }

        new Thread(){
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext,"很抱歉，程序出现异常，即将退出", Toast.LENGTH_SHORT).show();;
                Looper.loop();;
            }
        }.start();
        collectDeviceInfo(mContext);

        saveCrashInfo2File(ex);
        return true;

    }

    public void collectDeviceInfo(Context ctx){
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if(pi!=null){
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName",versionName);
                infos.put("versionCode",versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG,"an error occured when collect crash info",e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for(Field field:fields){
            try {
                field.setAccessible(true);
                infos.put(field.getName(),field.get(null).toString());
                Log.d(TAG,field.getName()+" : "+field.get(null));
            } catch (IllegalAccessException e) {
               Log.e(TAG,"an error occured when collect crash info",e);
            }
        }
    }

    private String saveCrashInfo2File(Throwable ex){
        StringBuffer sb=new StringBuffer();
        for(Map.Entry<String,String> entry:infos.entrySet()){
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key+"="+value+"\n");
        }

        Writer writer=new StringWriter();
        PrintWriter printWriter=new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause=ex.getCause();
        while(cause!=null){
            cause.printStackTrace(printWriter);
            cause=cause.getCause();
        }
        printWriter.close();
        String result=writer.toString();
        sb.append(result);
        try {
        long timestamp = System.currentTimeMillis();
        String time = formatter.format(new Date());
        String fileName="crash-"+time+"-"+timestamp+".log";

            String path = getFIlePath();
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file=new File(path+fileName);
            if(!file.exists()){
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(sb.toString().getBytes());
            fos.close();


            return fileName;
            } catch (Exception e) {
            Log.e(TAG,"an error occured while writing file ....",e);
            }
        return null;
        }
        private String getFIlePath(){
            String file_dir="";
            boolean isSDCardExist= Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
            boolean isRootDirExist= Environment.getExternalStorageDirectory().exists();
            if(isSDCardExist&&isRootDirExist){
                file_dir= Environment.getExternalStorageDirectory().getAbsolutePath()+"crashlog/";
            }else{
                file_dir= MyApplication.getInstance().getFilesDir().getAbsolutePath()+"crashlog/";
            }
            return file_dir;
        }
    }


