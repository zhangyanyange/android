package com.myygjc.ant.project.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.myygjc.ant.project.R;
import com.myygjc.ant.project.activity.LoginActivity;
import com.myygjc.ant.project.activity.MaterialDetailActivity;
import com.myygjc.ant.project.activity.ShopActivity;
import com.myygjc.ant.project.adapter.MaterialDetailAdapter;
import com.myygjc.ant.project.base.BaseFragment;
import com.myygjc.ant.project.base.LoadingPager;
import com.myygjc.ant.project.base.MyApplication;
import com.myygjc.ant.project.doman.MaterialTypeDetail;
import com.myygjc.ant.project.doman.MessageEvent;
import com.myygjc.ant.project.doman.ReSearch;
import com.myygjc.ant.project.procotol.MaterialTypeDetailProcotol;
import com.myygjc.ant.project.util.DesUtils;
import com.myygjc.ant.project.util.PrefUtils;
import com.myygjc.ant.project.util.UIUtils;
import com.myygjc.ant.project.view.SwipeRefreshView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/1/13.
 */

public class MaterialDetailFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.lv_detail)
    ListView lvDetail;
    @Bind(R.id.swipe_detail)
    SwipeRefreshView swipeDetail;
    @Bind(R.id.tv_reSearch)
    TextView tvReSearch;

    private List<MaterialTypeDetail> data;
    private String name;
    private MaterialTypeDetailProcotol procotol;
    public int i = 1;
    private MaterialDetailAdapter adapter;
    private List<MaterialTypeDetail> data1;
    private String tvNama;
    private MaterialDetailActivity activity;


    @Override
    public LoadingPager.LoadedResult initData() {
        Bundle bundle = getArguments();
        name = bundle.getString("name");
        procotol = new MaterialTypeDetailProcotol();
        EventBus.getDefault().register(this);
        tvNama=name;
        data = procotol.getData(name, 1, PrefUtils.getString(UIUtils.getContext(), "userId", DesUtils.encryptDes("0")));

        return checkResult(data);
    }

    @Override
    public View initSuccessView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_materialtype, null);
        ButterKnife.bind(this, view);

        activity = (MaterialDetailActivity) getActivity();
        swipeDetail.setOnRefreshListener(this);
        swipeDetail.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeDetail.setProgressBackgroundColor(R.color.refresh_bg);

        /**
         * 上拉加载时用
         */

        swipeDetail.setOnLoadListener(new SwipeRefreshView.OnLoadListener() {
            @Override
            public void onLoad() {

                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        i = i + 1;
                        List<MaterialTypeDetail> materialTypeDetails = procotol.getData(tvNama, i, PrefUtils.getString(UIUtils.getContext(), "userId", DesUtils.encryptDes("0")));
                        if (checkResult(materialTypeDetails).equals(LoadingPager.LoadedResult.SUCCESS)) {
                            //System.out.println("数量"+materialTypeDetails.size());
                            data.addAll(materialTypeDetails);

                            MyApplication.getHandler().post(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                    swipeDetail.setLoading(false);
                                }
                            });

                        } else {
                            MyApplication.getHandler().post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(UIUtils.getContext(), "没有更多数据了", Toast.LENGTH_SHORT).show();
                                    swipeDetail.setLoading(false);
                                }
                            });
                        }
                    }
                }.start();
                //swipeDetail.setLoading(false);
            }
        });
        adapter = new MaterialDetailAdapter(data,this);
        lvDetail.setAdapter(adapter);

        /**
         * 每个条目的点击事件，根据库存数量判断可以点击还是不可以点击
         */

            lvDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (("" + data.get(position).getQty()).equals("0.0")) {
                            Toast.makeText(UIUtils.getContext(), "库存为0，不可购买", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            //Toast.makeText(UIUtils.getContext(),"库存为"+data.get(position).getQty(),Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), ShopActivity.class);
                            intent.putExtra("name", data.get(position).getName());
                            intent.putExtra("price", data.get(position).getPrice());
                            intent.putExtra("total", data.get(position).getQty());
                            intent.putExtra("id", data.get(position).getId());
                            intent.putExtra("TP",data.get(position).getTp());
                            if(data.get(position).getMstp()==null) {
                                intent.putExtra("mstp", "没有");
                            }else {
                                intent.putExtra("mstp",data.get(position).getMstp());
                            }
                            startActivity(intent);
                        }
                }
            });



        return view;
    }

    /**
     * 下拉刷新时用
     */
    @Override
    public void onRefresh() {
        swipeDetail.setRefreshing(true);
        i=1;
        new Thread() {
            @Override
            public void run() {
                data1 = procotol.getData(tvNama, 1, PrefUtils.getString(UIUtils.getContext(), "userId", DesUtils.encryptDes("0")));
                data.clear();
                data.addAll(data1);
                MyApplication.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        swipeDetail.setRefreshing(false);
                        activity.tvSearch.setEnabled(true);
                        lvDetail.setSelection(0);
                    }
                });
            }
        }.start();

    }




    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showInfo(ReSearch messageEvent) {
        tvReSearch.setVisibility(View.GONE);
        activity.tvSearch.setEnabled(false);
        tvNama = messageEvent.getTvNama();
        swipeDetail.setProgressViewOffset(false, 0, UIUtils.dip2px(40));
        onRefresh();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshUI(MessageEvent messageEvent) {
        swipeDetail.setProgressViewOffset(false, 0, UIUtils.dip2px(40));
        onRefresh();
    }

    public  void login(){
        Intent intent=new Intent(UIUtils.getContext(), LoginActivity.class);
        startActivity(intent);
    }
}
