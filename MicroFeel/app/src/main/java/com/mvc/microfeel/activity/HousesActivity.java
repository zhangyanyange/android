package com.mvc.microfeel.activity;

import android.Manifest;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.mvc.microfeel.R;
import com.mvc.microfeel.util.PermissionFail;
import com.mvc.microfeel.util.PermissionSuccess;
import com.mvc.microfeel.util.PermissionUtil;
import com.mvc.microfeel.util.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HousesActivity extends Activity implements SensorEventListener {

    @Bind(R.id.bmapView)
    MapView mMapView;

    BaiduMap mBaiduMap;


    public static final int openPression=100;
    private MyLocationData locData;
    public MyLocationListener myListener = new MyLocationListener();
    private MyLocationConfiguration.LocationMode mCurrentMode;
    LocationClient mLocClient;
    boolean isFirstLoc = true; // 是否首次定位
    private SensorManager mSensorManager;
    private Double lastX = 0.0;
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private float mCurrentAccracy;

    //开启位置权限集合
    String []locationPremission={Manifest.permission.READ_PHONE_STATE,Manifest.permission.ACCESS_COARSE_LOCATION,
     Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_EXTERNAL_STORAGE,
   Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_houses);
       ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT <23) {
            initBaiduMap();
        }else{
            PermissionUtil.needPermission(HousesActivity.this, openPression,locationPremission);
        }

    }

    private void initBaiduMap() {
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);//获取传感器管理服务

        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_UI);

        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;

        mBaiduMap = mMapView.getMap();


        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);

        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        int span=1000;
        option.setScanSpan(span);
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    /**
     * 申请权限代码成功
     */
    @PermissionSuccess(requestCode = openPression)
    private void grantPermissionSuccess() {
       initBaiduMap();
    }
    /**
     * 申请权限代码失败
     */
    @PermissionFail(requestCode = openPression)
    private void grantPersmissionFail() {
        Toast.makeText(UIUtils.getContext(), "请在设置里，添加打电话权限", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionUtil.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        double x = sensorEvent.values[SensorManager.DATA_X];
        if (Math.abs(x - lastX) > 1.0) {
            mCurrentDirection = (int) x;
            locData = new MyLocationData.Builder()
                    .accuracy(mCurrentAccracy)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(mCurrentLat)
                    .longitude(mCurrentLon).build();
            mBaiduMap.setMyLocationData(locData);
        }
        lastX = x;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    /**
     * 定位SDK监听函数, 需实现BDLocationListener里的方法
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            mCurrentLat = location.getLatitude();
            mCurrentLon = location.getLongitude();
            mCurrentAccracy = location.getRadius();
            locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }

//        @Override
//        public void onConnectHotSpotMessage(String s, int i) {
//
//        }
    }
}
