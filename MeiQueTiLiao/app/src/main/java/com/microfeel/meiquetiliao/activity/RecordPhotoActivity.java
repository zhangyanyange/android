package com.microfeel.meiquetiliao.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.google.gson.Gson;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;
import com.microfeel.meiquetiliao.R;
import com.microfeel.meiquetiliao.adapter.PhotoAdapter;
import com.microfeel.meiquetiliao.base.Constants;
import com.microfeel.meiquetiliao.domain.ChangeTab;
import com.microfeel.meiquetiliao.domain.CreatOrder;
import com.microfeel.meiquetiliao.domain.MaterialPostBean;
import com.microfeel.meiquetiliao.domain.RetreactMaterial;
import com.microfeel.meiquetiliao.util.PrefUtils;
import com.microfeel.meiquetiliao.util.ShowToastTime;
import com.microfeel.meiquetiliao.util.UIUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class RecordPhotoActivity extends TakePhotoActivity {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.camera_photo)
    ImageButton cameraPhoto;
    @Bind(R.id.gridView)
    GridView gridView;
    @Bind(R.id.make_sure_photo)
    TextView makeSurePhoto;
    @Bind(R.id.loadView)
    ProgressBar loadView;
    private TakePhoto takePhoto;

    ArrayList<File> fileList = new ArrayList<>();
    ArrayList<File> files = new ArrayList<>();
    private PhotoAdapter adapter;
    private ArrayList<RetreactMaterial.ListContentBean> retreactMaterials;
    private int count;
    MaterialPostBean materialPost = new MaterialPostBean();


    private String ID = "00000000-0000-0000-0000-000000000000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_photo);
        ButterKnife.bind(this);
        final Intent intent = getIntent();
        retreactMaterials = intent.getParcelableArrayListExtra("retreatMaterial");
        takePhoto = getTakePhoto();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        String time2 = getTime(new Date());
        materialPost.setID(ID);
        materialPost.setCode("");
        materialPost.setProjectID(PrefUtils.getString(UIUtils.getContext(), "projectId", ""));
        materialPost.setCreaterID(ID);
        materialPost.setShippingCost(0);
        materialPost.setHShipCost(0);
        materialPost.setVShipCost(0);
        materialPost.setCreateTime(time2);
        materialPost.setSubmitTime(time2);
        materialPost.setCallBackTime(time2);
        materialPost.setPostTime(time2);
        materialPost.setRemark("---退料单");
        materialPost.setReceiver(PrefUtils.getString(UIUtils.getContext(), "tv_name", ""));
        materialPost.setContact(PrefUtils.getString(UIUtils.getContext(), "tv_phone", ""));
        materialPost.setAddress(PrefUtils.getString(UIUtils.getContext(), "location", ""));
        materialPost.setRequireTime(time2);
        materialPost.setIsSelfPick(true);
        materialPost.setLiftNum(0);
        materialPost.setFloorNum(0);
        materialPost.setShipDistance(0);

        ArrayList<MaterialPostBean.OrderDetailBean> list = new ArrayList<>();
        for (int i = 0; i < retreactMaterials.size(); i++) {
            MaterialPostBean.OrderDetailBean bean = new MaterialPostBean.OrderDetailBean();
            bean.setID(ID);
            bean.setActualNumber(0);
            bean.setUnit(retreactMaterials.get(i).getUnit());
            bean.setMaterialID(Integer.valueOf(retreactMaterials.get(i).getID()));
            bean.setName(retreactMaterials.get(i).getName());
            bean.setPrice(retreactMaterials.get(i).getPrice());
            bean.setNumber(-retreactMaterials.get(i).getNumber());
            list.add(bean);
        }
        materialPost.setOrderDetail(list);
        makeSurePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fileList.size() <= 0) {
                    ShowToastTime.showTextToast("请上传至少一张图片");
                    return;
                }
                makeSurePhoto.setEnabled(false);
                loadView.setVisibility(View.VISIBLE);
                PostFormBuilder postFormBuilder = OkHttpUtils.post();
                for (int i = 0; i < fileList.size(); i++) {
                    postFormBuilder.addFile("uploads", "file" + i + ".jpg", fileList.get(i));
                }

                Gson gson = new Gson();
                Map<String, String> map = new HashMap<String, String>();
                map.put("order", gson.toJson(materialPost));
                postFormBuilder.url(Constants.BASE_URL + "Order/PostOrder").params(map).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        System.out.print(e.toString());
                        loadView.setVisibility(View.GONE);
                        ShowToastTime.showTextToast("创建失败");
                        makeSurePhoto.setEnabled(true);
                    }

                    @Override
                    public void onResponse(String response) {
                        Gson gson=new Gson();
                        CreatOrder creatOrder = gson.fromJson(response, CreatOrder.class);
                        if(creatOrder.getResult()==0) {
                            loadView.setVisibility(View.GONE);
                            ShowToastTime.showTextToast("创建成功");
                            makeSurePhoto.setEnabled(true);
                            ChangeTab event = new ChangeTab();
                            event.setPosition(0);
                            EventBus.getDefault().post(event);
                            Intent intent1 = new Intent(RecordPhotoActivity.this, MentionMaterialActivity.class);
                            startActivity(intent1);
                        }
                    }
                });
            }
        });
        adapter = new PhotoAdapter(fileList, RecordPhotoActivity.this);
        gridView.setAdapter(adapter);

        adapter.setDeletePhotoListener(new PhotoAdapter.DeletePhotoListener() {
            @Override
            public void deletephoto(int position) {
                files.remove(position);
                fileList.clear();
                fileList.addAll(files);
                adapter.notifyDataSetChanged();
            }
        });

        cameraPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = 3 - fileList.size();
                new AlertView("上传头像", null, "取消", null,
                        new String[]{"拍照", "从相册中选择"},
                        RecordPhotoActivity.this, AlertView.Style.ActionSheet, new OnItemClickListener() {
                    public void onItemClick(Object o, int position) {
                        if (count == 0) {
                            ShowToastTime.showTextToast("最多可上传3张图片");
                            return;
                        }
                        RecordPhotoActivityPermissionsDispatcher.callWithCheck(RecordPhotoActivity.this, position, count);
//                       call(position,count);
                    }
                }).show();
            }
        });
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    void call(int position, int count) {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        Uri imageUri = Uri.fromFile(file);
        if (position == 1) {
            takePhoto.onPickMultiple(count);
            takePhoto.onEnableCompress(null, false);

            TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();

            builder.setWithOwnGallery(true);

            takePhoto.setTakePhotoOptions(builder.create());

        } else if (position == 0) {
            takePhoto.onPickFromCapture(imageUri);
        }
    }

    @OnShowRationale(Manifest.permission.CAMERA)
    void showWhy(final PermissionRequest request) {
        new AlertDialog.Builder(this).setMessage("开启此权限才可以上传图片").setPositiveButton("知道了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                request.proceed();
            }
        }).show();
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    void showDenied() {
        Toast.makeText(this, "请到设置打开权限上传照片", Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain(Manifest.permission.CALL_PHONE)
    void showNotAsk() {
        Toast.makeText(UIUtils.getContext(), "请到设置打开权限上传照片", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        RecordPhotoActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        fileList.clear();
        for (int i = 0; i < result.getImages().size(); i++) {
            files.add(new File(result.getImages().get(i).getOriginalPath()));
        }
        fileList.addAll(files);
        adapter.notifyDataSetChanged();
    }

    public String getTime(Date date) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}
