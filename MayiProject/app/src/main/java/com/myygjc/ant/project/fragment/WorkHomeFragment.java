package com.myygjc.ant.project.fragment;

import android.content.Intent;
import android.os.Handler;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.myygjc.ant.project.R;
import com.myygjc.ant.project.activity.LoginActivity;
import com.myygjc.ant.project.activity.UserDetailActivity;
import com.myygjc.ant.project.base.BaseFragment;
import com.myygjc.ant.project.base.LoadingPager;
import com.myygjc.ant.project.base.MyApplication;
import com.myygjc.ant.project.doman.AddMaterialCar;
import com.myygjc.ant.project.doman.DelectMaterialCar;
import com.myygjc.ant.project.doman.GetCarBean;
import com.myygjc.ant.project.doman.GetMaterialCount;
import com.myygjc.ant.project.doman.NoLogin;
import com.myygjc.ant.project.doman.ResultMessage;
import com.myygjc.ant.project.doman.ShopDetail;
import com.myygjc.ant.project.doman.UserId;
import com.myygjc.ant.project.procotol.DelectCarProcotol;
import com.myygjc.ant.project.procotol.GetCarProcotol;
import com.myygjc.ant.project.procotol.GetMaterialInventoryProcotol;
import com.myygjc.ant.project.util.ArrayListDig;
import com.myygjc.ant.project.util.DesUtils;
import com.myygjc.ant.project.util.PrefUtils;
import com.myygjc.ant.project.util.TextBoolean;
import com.myygjc.ant.project.util.UIUtils;
import com.myygjc.ant.project.view.AmountView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Response;

;


/**
 * Created by zy2 on 2016/12/27.
 */

public class WorkHomeFragment extends BaseFragment {


    @Bind(R.id.cb_all)
    CheckBox cbAll;

    @Bind(R.id.ll_all)
    LinearLayout llAll;
    @Bind(R.id.tv_privetotal)
    TextView tvPrivetotal;
    @Bind(R.id.tv_totalAll)
    TextView tvTotalAll;

    @Bind(R.id.ll_quanxuan)
    LinearLayout llQuanxuan;
    @Bind(R.id.shopcar)
    ListView shopcar;
    @Bind(R.id.tv_delect)
    TextView tvDelect;
    @Bind(R.id.ll_net_request)
    LinearLayout llNetRequest;
    private ShopCarAdapter adapter;

    private double totalPrice;
    private ArrayList<ShopDetail> query;
    private ArrayList<GetCarBean> data;
    private HashMap<Integer, Boolean> isSelectedC;
    private DelectCarProcotol delectCarProcotol;
    private UserId delectData;
    private GetCarProcotol getCarProcotol;

    private Handler mHandler=new Handler();



    @Override
    public LoadingPager.LoadedResult initData() {
        EventBus.getDefault().register(this);
        if(PrefUtils.getBoolean(UIUtils.getContext(), "islogin", false)) {
        getCarProcotol = new GetCarProcotol();
            System.out.println(PrefUtils.getString(UIUtils.getContext(), "userId", DesUtils.encryptDes("0")) );
        data = getCarProcotol.getData(PrefUtils.getString(UIUtils.getContext(), "userId", DesUtils.encryptDes("0")));
        delectCarProcotol = new DelectCarProcotol();
        return checkResult(data);
        }else{
            return LoadingPager.LoadedResult.NOLOAD;
        }

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshUI(NoLogin noLogin) {
        EventBus.getDefault().unregister(this);
        Intent intent=new Intent(UIUtils.getContext(), LoginActivity.class);
        startActivityForResult(intent,3);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent zz) {
      switch (requestCode){
          case 3:
              LoadingPager loadingPager = getLoadingPager();
              loadingPager.triggerLoadData();
              break;
      }
    }



    @Override
    public View initSuccessView() {

        View view = View.inflate(UIUtils.getContext(), R.layout.balance, null);
        isSelectedC = new HashMap<>();
        ButterKnife.bind(this, view);adapter = new ShopCarAdapter(data);
        totalPrice = 0;
        for (int i = 0; i < data.size(); i++) {
            isSelectedC.put(i, true);
            llAll.setVisibility(View.VISIBLE);

            totalPrice = totalPrice + data.get(i).getDj() * data.get(i).getSl();

        }
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CHINA);
        tvPrivetotal.setText(Html.fromHtml("<font color ='#FF0000'>合计:"+format.format(totalPrice)+"</font>"+"<font color ='#A9A9A9'>(不含运费)</font>"));
        adapter.setIsSelected(isSelectedC);
        shopcar.setAdapter(adapter);

        tvDelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> choiceData = adapter.getChoiceData();
                final DelectMaterialCar car = new DelectMaterialCar();
                car.setHSID(PrefUtils.getString(UIUtils.getContext(), "userId", DesUtils.encryptDes("0")));
                ArrayList<Integer> list = new ArrayList<Integer>();
                for (int i = 0; i < choiceData.size(); i++) {
                    list.add(Integer.parseInt(data.get(choiceData.get(i)).getWlId()));
                }
                car.setWlIDS(list);

                new Thread() {
                    @Override
                    public void run() {
                        MyApplication.getHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                llNetRequest.setVisibility(View.VISIBLE);
                                llAll.setVisibility(View.GONE);
                            }
                        });
                        delectData = delectCarProcotol.delectData(car);
                    }
                }.start();


            }
        });

        /*回调*/
        delectCarProcotol.setOnSuccess(new DelectCarProcotol.OnNetFinish() {
            @Override
            public void onSuccessListener() {
                new Thread(){
                    @Override
                    public void run() {
                        if(delectData!=null){
                        if (delectData.getStringContent().equals("成功")) {
                            data.clear();
                            isSelectedC.clear();

                            data.addAll(getCarProcotol.getData(PrefUtils.getString(UIUtils.getContext(), "userId", DesUtils.encryptDes("0"))));
                            for (int i = 0; i < data.size(); i++) {
                                isSelectedC.put(i, false);
                            }


                            adapter.setIsSelected(isSelectedC);

                            MyApplication.getHandler().post(new Runnable() {
                                @Override
                                public void run() {
                                    llNetRequest.setVisibility(View.GONE);
                                    Toast.makeText(UIUtils.getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                                    NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CHINA);
                                    // 把转换后的货币String类型返回  
                                    tvPrivetotal.setText(format.format(totalPrice));
                                    adapter.notifyDataSetChanged();
                                    if (data.size() == 0) {
                                        llQuanxuan.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }
                    }
                    }
                }.start();

            }
        });

        delectCarProcotol.setOnNetFail(new DelectCarProcotol.OnNetFail() {
            @Override
            public void onFailListener() {
                Toast.makeText(UIUtils.getContext(),"删除失败",Toast.LENGTH_SHORT).show();
            }
        });
        getCarProcotol.setOnSuccess(new GetCarProcotol.OnNetFinish() {
            @Override
            public void onSuccessListener() {

            }
        });
        cbAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbAll.isChecked()) {
                    for (int i = 0; i < data.size(); i++) {
                        isSelectedC.put(i, true);
                    }
                    totalPrice = 0;
                    llAll.setVisibility(View.VISIBLE);

                } else {
                    for (int i = 0; i < data.size(); i++) {
                        isSelectedC.put(i, false);
                    }
                    llAll.setVisibility(View.GONE);
                }
                adapter.setIsSelected(isSelectedC);
                adapter.notifyDataSetChanged();
            }
        });


        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshUI(ResultMessage messageEvent) {
        llAll.setVisibility(View.GONE);
        data.clear();
        isSelectedC.clear();
        new Thread() {
            @Override
            public void run() {
                data.addAll(getCarProcotol.getData(PrefUtils.getString(UIUtils.getContext(), "userId", DesUtils.encryptDes("0"))));
                if (data != null) {
                    for (int i = 0; i < data.size(); i++) {
                        isSelectedC.put(i, false);
                    }
                }
                adapter.setIsSelected(isSelectedC);

                MyApplication.getHandler().post(new Runnable() {
                    @Override
                    public void run() {

                        adapter.notifyDataSetChanged();
                        if (data.size() == 0) {
                            llQuanxuan.setVisibility(View.GONE);
                        }
                    }
                });
            }
        }.start();

    }



    public class ShopCarAdapter extends BaseAdapter {
        int touchedPosition=-1;
        class MyRunnale implements Runnable {

            public int postion;

            public MyRunnale(int postion) {
                this.postion = postion;
            }

            @Override
            public void run() {
                AddMaterialCar car=new AddMaterialCar();
                car.setPrice(data.get(postion).getDj());
                car.setQty(data.get(postion).getSl());
                car.setHSID(PrefUtils.getString(UIUtils.getContext(),"userId",DesUtils.encryptDes("0")));
                car.setWlID(Integer.valueOf(data.get(postion).getWlId()));
                String s = new Gson().toJson(car);
                try {
               OkHttpUtils
                        .postString()
                        .url("http://123.206.107.160/WebApiJD/api/JD/UpdateCar")
                        .content(s)
                        .mediaType(MediaType.parse("application/json; charset=utf-8"))
                        .build()
                        .execute(new Callback() {
                            @Override
                            public Object parseNetworkResponse(Response response) throws Exception {
                                return null;
                            }

                            @Override
                            public void onError(Call call, Exception e) {

                            }

                            @Override
                            public void onResponse(Object response) {

                            }
                        });
                } catch (Exception e) {
                    System.out.println("错误"+e.toString());

                }
            }
        }
        private ArrayList<GetCarBean> data;

        private ArrayList<Integer> selectedPostion = new ArrayList<>();
        // 用来控制CheckBox的选中状况
        private HashMap<Integer, Boolean> isSelected;

        public ShopCarAdapter(ArrayList<GetCarBean> data) {
            this.data = data;
        }

        public HashMap<Integer, Boolean> getIsSelected() {
            return isSelected;
        }

        public void setIsSelected(HashMap<Integer, Boolean> isSelected) {
            this.isSelected = isSelected;
        }


        @Override
        public int getCount() {
            if (data != null) {
                return data.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView== null) {
                convertView = View.inflate(UIUtils.getContext(), R.layout.shop_car, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            selectedPostion = getChoiceData();
            if (selectedPostion.size() != 0) {
                llAll.setVisibility(View.VISIBLE);
                totalPrice = 0;
                for (int i = 0; i < selectedPostion.size(); i++) {
                    totalPrice = totalPrice + data.get(selectedPostion.get(i)).getDj() * data.get(selectedPostion.get(i)).getSl();
                }
                NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CHINA);

                tvPrivetotal.setText(Html.fromHtml("<font color ='#FF0000'>合计:"+format.format(totalPrice)+"</font>"+"<font color ='#A9A9A9'>(不含运费)</font>"));
            }
            holder.tvShopname.setText(data.get(position).getName());
            double dj = data.get(position).getDj();
            NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CHINA);
            // 把转换后的货币String类型返回  
            String price = format.format(dj);
            holder.tvShopprice.setText(price);
            holder.tvCount.setTag(position);
            holder.tvCount.setAmount((int)data.get(position).getSl());
            ArrayList<Boolean> checkBoolean = new ArrayList<Boolean>();
            for (Map.Entry<Integer, Boolean> entry : isSelected.entrySet()) {
                checkBoolean.add(entry.getValue());
            }
            if (TextBoolean.isHasFalse(checkBoolean)) {
                cbAll.setChecked(false);
            } else {
                cbAll.setChecked(true);
            }

            if (touchedPosition == position) {
                // 如果当前的行下标和点击事件中保存的index一致，手动为EditText设置焦点。
                holder.tvCount.etAmount.requestFocus();
            }else {
                holder.tvCount.etAmount.clearFocus();
            }
            holder.tvCount.etAmount.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        touchedPosition = position;
                    }
                    return false;
                }
            });

            holder.tvCount.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
                @Override
                public void onAmountChange(View view, int amount) {
                    Object tag = view.getTag();
                    if(data.get((int) tag).getSl()!=amount){
                        data.get((int) tag).setSl(amount);
                        selectedPostion = getChoiceData();
                        if (selectedPostion.size() != 0) {
                            llAll.setVisibility(View.VISIBLE);
                            totalPrice = 0;
                            for (int i = 0; i < selectedPostion.size(); i++) {
                                totalPrice = totalPrice + data.get(selectedPostion.get(i)).getDj() * data.get(selectedPostion.get(i)).getSl();
                            }
                            NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CHINA);

                            tvPrivetotal.setText(Html.fromHtml("<font color ='#FF0000'>合计:"+format.format(totalPrice)+"</font>"+"<font color ='#A9A9A9'>(不含运费)</font>"));
                        }
                            mHandler.postDelayed(new MyRunnale((int)tag), 1000);
                            if ((int)tag == position) {
                                mHandler.removeCallbacks(new MyRunnale((int)tag));
                                mHandler.postDelayed(new MyRunnale((int)tag), 1000);
                            }

                    }
                }
            });
            Glide
                    .with(UIUtils.getContext())
                    .load("http://123.206.107.160/"+data.get(position).getBz())
                    .error(R.drawable.zhanwei)
                    .placeholder(R.drawable.zhanwei)
                    .into(holder.ivTuPian);
            holder.cbSigle.setChecked(getIsSelected().get(position));
            // 监听checkBox并根据原来的状态来设置新的状态
            holder.cbSigle.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    if (isSelected.get(position)) {
                        isSelected.put(position, false);
                        setIsSelected(isSelected);
                    } else {
                        isSelected.put(position, true);
                        setIsSelected(isSelected);
                    }
                    ArrayList<Boolean> listBoolean = new ArrayList<Boolean>();
                    for (Map.Entry<Integer, Boolean> entry : isSelected.entrySet()) {
                        listBoolean.add(entry.getValue());
                    }
                    if (TextBoolean.isHasFalse(listBoolean)) {
                        cbAll.setChecked(false);
                    } else {
                        cbAll.setChecked(true);
                    }
                    selectedPostion = getChoiceData();
                    if (selectedPostion.size() != 0) {
                        llAll.setVisibility(View.VISIBLE);
                        totalPrice = 0;
                        for (int i = 0; i < selectedPostion.size(); i++) {
                            totalPrice = totalPrice + data.get(selectedPostion.get(i)).getDj() * data.get(selectedPostion.get(i)).getSl();
                        }
                        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CHINA);
                        // 把转换后的货币String类型返回  
                        tvPrivetotal.setText(Html.fromHtml("<font color ='#FF0000'>合计:"+format.format(totalPrice)+"</font>"+"<font color ='#A9A9A9'>(不含运费)</font>"));
                    } else {
                        llAll.setVisibility(View.GONE);
                    }
                }
            });
            tvTotalAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvTotalAll.setEnabled(false);
                    selectedPostion = getChoiceData();
                    final ArrayList<Integer> wlid = new ArrayList<Integer>();
                    final HashMap<Double, String> shopCount = new HashMap();
                    final HashMap<Double, String> shopMax = new HashMap<Double, String>();
                    for (int i = 0; i < selectedPostion.size(); i++) {
                        wlid.add(Integer.valueOf(data.get(selectedPostion.get(i)).getWlId()));
                        shopCount.put(data.get(selectedPostion.get(i)).getSl(), data.get(selectedPostion.get(i)).getWlId());
                    }
                    new Thread() {
                        @Override
                        public void run() {
                            DelectMaterialCar car = new DelectMaterialCar();
                            car.setHSID(PrefUtils.getString(UIUtils.getContext(), "userId", DesUtils.encryptDes("0")));
                            car.setWlIDS(wlid);
                            final ArrayList<GetMaterialCount> dataCount = GetMaterialInventoryProcotol.getSurplus(car);
                            for (int i = 0; i < dataCount.size(); i++) {
                                shopMax.put(dataCount.get(i).getQty(),""+dataCount.get(i).getId());
                            }
                            final String s = ArrayListDig.verityBigAndSmall(shopCount, shopMax);
                            if (s.equals("")) {
                                MyApplication.getHandler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(UIUtils.getContext(), UserDetailActivity.class);

                                        intent.putIntegerArrayListExtra("choice", selectedPostion);
                                        intent.putParcelableArrayListExtra("data",data);
                                        startActivity(intent);
                                        tvTotalAll.setEnabled(true);
                                    }
                                });
                            } else {
                                MyApplication.getHandler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        String[] split = s.split(",");
                                        for (int i = 0; i < data.size(); i++) {
                                            if(data.get(i).getWlId().equals(split[0])){
                                                Toast.makeText(UIUtils.getContext(), data.get(i).getName()+"最大库存为"+split[1]+",请减少数量购买", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        tvTotalAll.setEnabled(true);

                                    }
                                });

                            }
                        }
                    }.start();

                }
            });
            convertView.setTag(holder);
            return convertView;
        }

        class ViewHolder {
            @Bind(R.id.cb_sigle)
            CheckBox cbSigle;
            @Bind(R.id.tv_shopname)
            TextView tvShopname;
            @Bind(R.id.tv_shopprice)
            TextView tvShopprice;
            @Bind(R.id.tv_count)
            AmountView tvCount;
            @Bind(R.id.ll_shop_detail)
            RelativeLayout llShopDetail;
            @Bind(R.id.iv_tupian)
            ImageView ivTuPian;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }

        public ArrayList<Integer> getChoiceData() {
            selectedPostion.clear();
            for (Map.Entry<Integer, Boolean> entry : isSelected.entrySet()) {
                if (entry.getValue().equals(true)) {
                    selectedPostion.add(entry.getKey());
                }
            }

            return selectedPostion;
        }
    }

}
