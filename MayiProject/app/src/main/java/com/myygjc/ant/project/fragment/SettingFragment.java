package com.myygjc.ant.project.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.google.gson.Gson;
import com.myygjc.ant.project.MainActivity;
import com.myygjc.ant.project.R;
import com.myygjc.ant.project.activity.FindOrderActivity;
import com.myygjc.ant.project.activity.LocationActivity;
import com.myygjc.ant.project.activity.LoginActivity;
import com.myygjc.ant.project.activity.MyMoneyActivity;
import com.myygjc.ant.project.base.BaseFragment;
import com.myygjc.ant.project.base.LoadingPager;
import com.myygjc.ant.project.base.MyApplication;
import com.myygjc.ant.project.doman.GetVersion;
import com.myygjc.ant.project.doman.UserId;
import com.myygjc.ant.project.util.DesUtils;
import com.myygjc.ant.project.util.HttpUtils;
import com.myygjc.ant.project.util.PermissionFail;
import com.myygjc.ant.project.util.PermissionSuccess;
import com.myygjc.ant.project.util.PermissionUtil;
import com.myygjc.ant.project.util.PrefUtils;
import com.myygjc.ant.project.util.UIUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;


/**
 * Created by zy2 on 2016/12/27.
 */

public class SettingFragment extends BaseFragment {


    public static final int SHOW_UPDATE_DIALOG = 1;
    @SuppressLint("SdCardPath")
    private static String path = "/sdcard/myHead/";// sd路径
    @Bind(R.id.call_kefu)
    RelativeLayout callKefu;
    @Bind(R.id.rl_location)
    RelativeLayout rlLocation;
    @Bind(R.id.iv_or)
    ImageView ivOr;
    private Bitmap head;// 头像Bitmap
    @Bind(R.id.rb_banben)
    TextView rbBanben;
    @Bind(R.id.rl_version)
    RelativeLayout rlVersion;
    @Bind(R.id.money_shenyu)
    TextView moneyShenyu;

    @Bind(R.id.loginout)
    RelativeLayout loginout;
    @Bind(R.id.rl_money)
    RelativeLayout rlMoney;
    @Bind(R.id.rl_orderstate)
    RelativeLayout rlOrderstate;
    @Bind(R.id.call_phone)
    RelativeLayout callPhone;
    @Bind(R.id.rl_yue)
    RelativeLayout rlYue;
    @Bind(R.id.touxiang)
    CircleImageView touxiang;
    private ProgressDialog pd;
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
    private MainActivity activity;
    private Uri uriByFileDirAndFileName;

    @Override
    public LoadingPager.LoadedResult initData() {
        return LoadingPager.LoadedResult.SUCCESS;
    }

    @Override
    public View initSuccessView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_person, null);
        ButterKnife.bind(this, view);

        if(PrefUtils.getBoolean(UIUtils.getContext(), "islogin", false)){
            loginout.setVisibility(View.VISIBLE);
        }else{
            loginout.setVisibility(View.GONE);
        }
        rlLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UIUtils.getContext(), LocationActivity.class);
                startActivity(intent);
            }
        });
        activity = (MainActivity) this.getActivity();
        if (PrefUtils.getBoolean(UIUtils.getContext(), "islogin", false)) {
            Bitmap bt = BitmapFactory.decodeFile(path + "head.jpg");// 从Sd中找头像，转换成Bitmap
            if (bt != null) {
                @SuppressWarnings("deprecation")
                Drawable drawable = new BitmapDrawable(toRoundBitmap(bt));// 转换成drawable
                touxiang.setImageDrawable(drawable);
            }
        } else {
            touxiang.setImageResource(R.drawable.af_ugc_icon_user);
        }
        touxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PrefUtils.getBoolean(UIUtils.getContext(), "islogin", false)) {
                    new AlertView("上传头像", null, "取消", null,
                            new String[]{"拍照", "从相册中选择"},
                            activity, AlertView.Style.ActionSheet, new OnItemClickListener() {
                        public void onItemClick(Object o, int position) {
                            if (position == 1) {
                                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                                startActivityForResult(intent1, 1);
                            } else if (position == 0) {
                                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "head.jpg")));
                                startActivityForResult(intent2, 2);// 采用ForResult打开
                            }
                        }
                    }).show();
                } else {
                    Intent intent = new Intent(UIUtils.getContext(), LoginActivity.class);
                    startActivityForResult(intent, 4);
                }
            }
        });
        rlMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PrefUtils.getBoolean(UIUtils.getContext(), "islogin", false)) {
                    Intent intent = new Intent(UIUtils.getContext(), MyMoneyActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(UIUtils.getContext(), "请登录之后在进行操作", Toast.LENGTH_SHORT).show();
                }
            }
        });
        callKefu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UIUtils.getContext(), "功能暂未开通", Toast.LENGTH_SHORT).show();
            }
        });
        loginout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PrefUtils.getBoolean(UIUtils.getContext(), "islogin", false)) {
                    PrefUtils.putString(UIUtils.getContext(), "userId", DesUtils.encryptDes("0"));
                    PrefUtils.putBoolean(UIUtils.getContext(), "islogin", false);
                    touxiang.setImageResource(R.drawable.af_ugc_icon_user);
                    Toast.makeText(UIUtils.getContext(), "退出登陆成功", Toast.LENGTH_SHORT).show();
                    loginout.setVisibility(View.GONE);
                } else {
                    Toast.makeText(UIUtils.getContext(), "请先登陆", Toast.LENGTH_SHORT).show();
                }
            }
        });

        callPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PrefUtils.getBoolean(UIUtils.getContext(), "islogin", false)) {
                    PermissionUtil.needPermission(SettingFragment.this, 3, Manifest.permission.CALL_PHONE);
                } else {
                    Toast.makeText(UIUtils.getContext(), "请登录之后在进行操作", Toast.LENGTH_SHORT).show();
                }
            }
        });
        rlOrderstate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PrefUtils.getBoolean(UIUtils.getContext(), "islogin", false)) {
                    Intent intent = new Intent(UIUtils.getContext(), FindOrderActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(UIUtils.getContext(), "请登录之后在进行操作", Toast.LENGTH_SHORT).show();
                }
            }
        });
        rlYue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PrefUtils.getBoolean(UIUtils.getContext(), "islogin", false)) {
                    Intent intent = new Intent(UIUtils.getContext(), MyMoneyActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(UIUtils.getContext(), "请登录之后在进行操作", Toast.LENGTH_SHORT).show();
                }
            }
        });
        rlVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {


                    @Override
                    public void run() {
                        try {
                            Response response = OkHttpUtils.get().url("http://123.206.107.160/webapiJD/api/JD/GetVersion").build().execute();
                            if (response.isSuccessful()) {
                                String string = response.body().string();
                                Gson gson = new Gson();
                                GetVersion getVersion = gson.fromJson(string, GetVersion.class);
                                int stringContent = getVersion.getContentResult();

                                PackageManager pm = UIUtils.getContext().getPackageManager();
                                PackageInfo pi = pm.getPackageInfo(UIUtils.getContext().getPackageName(), 0);
                                int versionCode = pi.versionCode;

                                if (stringContent == versionCode) {
                                    MyApplication.getHandler().post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(UIUtils.getContext(), "当前已经是最新版本", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    Message msg = Message.obtain();
                                    msg.what = SHOW_UPDATE_DIALOG;
                                    handler.sendMessage(msg);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });
        return view;
    }

    @PermissionSuccess(requestCode = 3)
    private void grantPermissionSuccess() {
        callPhone();
    }

    @PermissionFail(requestCode = 3)
    private void grantPersmissionFail() {
        Toast.makeText(UIUtils.getContext(), "请在设置里，添加打电话权限", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionUtil.onRequestPermissionsResult(SettingFragment.this, requestCode, permissions, grantResults);
    }

    private void callPhone() {
        new Thread() {
            @Override
            public void run() {
                //
                OkHttpUtils.get().url("http://123.206.107.160/WebApiJD/api/JD/GetSalesman?核算ID=" + PrefUtils.getString(UIUtils.getContext(), "userId", DesUtils.encryptDes("0"))).build().execute(new Callback() {
                    @Override
                    public String parseNetworkResponse(Response response) throws Exception {
                        String string = response.body().string();
                        return string;
                    }

                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(Object response) {
                        Gson gson = new Gson();
                        UserId userId = gson.fromJson((String) response, UserId.class);
                        if (userId.getStringContent().equals("")) {
                            MyApplication.getHandler().post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(UIUtils.getContext(), "对不起你现在还没有业务员", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            Uri data = Uri.parse("tel:" + userId.getStringContent());
                            intent.setData(data);
                            startActivity(intent);
                        }
                    }
                });
            }
        }.start();
    }

    /**
     * 显示自动更新的对话框
     *
     * @param
     */
    protected void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(false);
        builder.setTitle("升级提醒");
        builder.setMessage("有新版本");
        builder.setPositiveButton("立刻升级", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pd = new ProgressDialog(activity);
                pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pd.show();
                HttpUtils http = new HttpUtils();
                File sdDir = Environment.getExternalStorageDirectory();
                File file = new File(sdDir, SystemClock.uptimeMillis() + ".apk");
                if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)) {
                    OkHttpUtils.get().url("http://microfeel.net/webapiJD/api/JD/GetFile").build().execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "gson-2.2.1.jar") {
                        @Override
                        public void inProgress(float progress, long total) {
                            pd.setMax(100);
                            pd.setProgress((int) (progress * 100));
                        }

                        @Override
                        public void onError(Call call, Exception e) {
                            Toast.makeText(UIUtils.getContext(), "下载失败",
                                    Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        }

                        @Override
                        public void onResponse(File response) {
                            pd.dismiss();
                            Toast.makeText(UIUtils.getContext(), "下载成功",
                                    Toast.LENGTH_SHORT).show();
                            // 覆盖安装apk文件
                            Intent intent = new Intent();
                            intent.setAction("android.intent.action.VIEW");
                            intent.addCategory("android.intent.category.DEFAULT");
                            intent.setDataAndType(
                                    Uri.fromFile(response),
                                    "application/vnd.android.package-archive");
                            startActivity(intent);
                        }
                    });
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

    public void cropRawPhoto(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        // 设置裁剪  
        intent.putExtra("crop", "true");

        // aspectX , aspectY :宽高的比例  
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

    }

    /**
     * 把bitmap转成圆形
     */
    public Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int r = 0;
        // 取最短边做边长
        if (width < height) {
            r = width;
        } else {
            r = height;
        }
        // 构建一个bitmap
        Bitmap backgroundBm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        // new一个Canvas，在backgroundBmp上画图
        Canvas canvas = new Canvas(backgroundBm);
        Paint p = new Paint();
        // 设置边缘光滑，去掉锯齿
        p.setAntiAlias(true);
        RectF rect = new RectF(0, 0, r, r);
        // 通过制定的rect画一个圆角矩形，当圆角X轴方向的半径等于Y轴方向的半径时，
        // 且都等于r/2时，画出来的圆角矩形就是圆形
        canvas.drawRoundRect(rect, r / 2, r / 2, p);
        // 设置当两个图形相交时的模式，SRC_IN为取SRC图形相交的部分，多余的将被去掉
        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        // canvas将bitmap画在backgroundBmp上
        canvas.drawBitmap(bitmap, null, rect, p);
        return backgroundBm;
    }

    /**
     * 调用系统的裁剪
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());// 裁剪图片
                }
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        head = extras.getParcelable("data");
                    }
                    if (head != null) {
                        setPicToView(head);// 保存在SD卡中
                        touxiang.setImageBitmap(toRoundBitmap(head));// 用ImageView显示出来
                    }
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory() + "/head.jpg");
                    cropPhoto(Uri.fromFile(temp));// 裁剪图片
                }
                if (data != null) {
                    Bundle extras = data.getExtras();
                    Bitmap head = extras.getParcelable("data");
                    if (head != null) {
                        setPicToView(head);// 保存在SD卡中
                        touxiang.setImageBitmap(toRoundBitmap(head));// 用ImageView显示出来
                    }
                }
                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if (head != null) {
                        setPicToView(head);// 保存在SD卡中  
                        touxiang.setImageBitmap(head);// 用ImageView显示出来  
                        if (head != null && head.isRecycled()) {
                            head.recycle();
                        }
                    }
                }
                break;
            case 4:
                if (PrefUtils.getBoolean(UIUtils.getContext(), "islogin", false)) {
                    Bitmap bt = BitmapFactory.decodeFile(path + "head.jpg");// 从Sd中找头像，转换成Bitmap
                    if (bt != null) {
                        @SuppressWarnings("deprecation")
                        Drawable drawable = new BitmapDrawable(toRoundBitmap(bt));// 转换成drawable
                        touxiang.setImageDrawable(drawable);
                    }
                    loginout.setVisibility(View.VISIBLE);
                } else {
                    touxiang.setImageResource(R.drawable.af_ugc_icon_user);
                    loginout.setVisibility(View.GONE );
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    };

    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String fileName = path + "head.jpg";// 图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
