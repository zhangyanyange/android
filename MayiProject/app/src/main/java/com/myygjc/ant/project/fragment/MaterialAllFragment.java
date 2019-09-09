package com.myygjc.ant.project.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.myygjc.ant.project.R;
import com.myygjc.ant.project.activity.LoginActivity;
import com.myygjc.ant.project.activity.ShopActivity;
import com.myygjc.ant.project.adapter.FenleiAdapter1;
import com.myygjc.ant.project.adapter.FenleiAdapter2;
import com.myygjc.ant.project.adapter.FenleiAdapter3;
import com.myygjc.ant.project.adapter.MaterialAllAdapter;
import com.myygjc.ant.project.base.BaseFragment;
import com.myygjc.ant.project.base.LoadingPager;
import com.myygjc.ant.project.base.MyApplication;
import com.myygjc.ant.project.doman.MaterialAll;
import com.myygjc.ant.project.doman.Producer;
import com.myygjc.ant.project.procotol.MaterialTypeAllProcotol;
import com.myygjc.ant.project.procotol.ProducerProcotol;
import com.myygjc.ant.project.util.DesUtils;
import com.myygjc.ant.project.util.PrefUtils;
import com.myygjc.ant.project.util.UIUtils;
import com.myygjc.ant.project.view.HorizontalListView;
import com.myygjc.ant.project.view.SwipeRefreshView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by zy2 on 2017/3/27.
 */

public class MaterialAllFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.fenlei1)
    HorizontalListView fenlei1;
    @Bind(R.id.fenlei2)
    HorizontalListView fenlei2;
    @Bind(R.id.fenlei3)
    HorizontalListView fenlei3;
    @Bind(R.id.lv_fenlei)
    ListView lvFenlei;
    @Bind(R.id.swipe_detail)
    SwipeRefreshView swipeDetail;
    private List<Producer> producer1;
    private List<Producer> producer2;
    private FenleiAdapter2 adapter2;
    private FenleiAdapter3 adapter3;
    private List<Producer> producer3;
    private Producer producerdoman;
    private MaterialAll data;
    private MaterialAllAdapter allAdapter;
    public int i = 0;
    public int j = 0;
    public int k = 0;
    private MaterialTypeAllProcotol procotol;
    private int p=0;
    @Override
    public LoadingPager.LoadedResult initData() {
        Bundle bundle = getArguments();
        final int id = bundle.getInt("id");
        producer1 = ProducerProcotol.getProducer(id);
        producerdoman = new Producer();
        producerdoman.setName("全部");
        if (producer1 .size()!=0) {
            producer2 = ProducerProcotol.getProducer(producer1.get(0).getId());

            procotol = new MaterialTypeAllProcotol();
            data = procotol.getData("" + producer1.get(0).getId(), 1, PrefUtils.getString(UIUtils.getContext(), "userId", DesUtils.encryptDes("0")));
            i=1;
            j=0;
            k=0;
            if((checkResult(producer2)==(LoadingPager.LoadedResult.EMPTY))){
                producer2.add(producerdoman);
            }
            if ((checkResult(producer2) == LoadingPager.LoadedResult.SUCCESS) && (checkResult(data) == LoadingPager.LoadedResult.SUCCESS)) {
                return LoadingPager.LoadedResult.SUCCESS;
            } else {
                return LoadingPager.LoadedResult.ERROR;
            }

        }

        return LoadingPager.LoadedResult.EMPTY;
    }

    @Override
    public View initSuccessView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_all, null);
        ButterKnife.bind(this, view);


        swipeDetail.setOnRefreshListener(this);
        swipeDetail.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeDetail.setProgressBackgroundColor(R.color.refresh_bg);


        final FenleiAdapter1 adapter1 = new FenleiAdapter1(producer1);
        adapter1.setSelectIndex(0);
        fenlei1.setAdapter(adapter1);


        adapter2 = new FenleiAdapter2(producer2);
        fenlei2.setAdapter(adapter2);

        producer3 = new ArrayList<>();
        producer3.add(producerdoman);
        adapter3 = new FenleiAdapter3(producer3);
        fenlei3.setAdapter(adapter3);

        fenlei1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                adapter1.setSelectIndex(position);
                adapter1.notifyDataSetChanged();
                p=position;
                new Thread() {
                    @Override
                    public void run() {
                        OkHttpUtils.get().url("http://123.206.107.160/webapiJD/api/JD/GetMaterialDetail?客户核算Id=" + PrefUtils.getString(UIUtils.getContext(), "userId", DesUtils.encryptDes("0")) + "&typeId=" + producer1.get(position).getId() + "&pageIndex=" + 1 + "&pageSize=10").build().execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e) {

                            }

                            @Override
                            public void onResponse(String response) {
                                Gson gson = new Gson();
                                data.getJh().clear();
                                MaterialAll all = gson.fromJson(response, MaterialAll.class);
                                i=1;
                                j=0;
                                k=0;
                                data.getJh().addAll(all.getJh());

                                MyApplication.getHandler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        allAdapter.notifyDataSetChanged();
                                        lvFenlei.setSelection(0);
                                    }
                                });
                            }
                        });
                    }
                }.start();

                new Thread() {
                    @Override
                    public void run() {
                        OkHttpUtils.get().url("http://123.206.107.160/WebApiJD/api/JD/GetMaterialType?typeId=" + producer1.get(position).getId()).build().execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e) {

                            }

                            @Override
                            public void onResponse(String response) {

                                producer2.clear();
                                Gson gson = new Gson();
                                ArrayList<Producer> data = gson.fromJson(response, new TypeToken<List<Producer>>() {
                                }.getType());
                                if(data.size()==0){
                                    producer2.add(producerdoman);
                                }else {
                                    producer2.addAll(data);
                                }
                                producer3.clear();
                                producer3.add(producerdoman);
                                MyApplication.getHandler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        fenlei2.setAdapter(adapter2);
                                        adapter2.setSelectIndex(-1);
                                        adapter2.notifyDataSetChanged();
                                        adapter3.setSelectIndex(-1);
                                        adapter3.notifyDataSetChanged();
                                    }
                                });
                            }
                        });
                    }
                }.start();


            }
        });


            fenlei2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                    if(!producer2.get(0).getName().equals("全部")) {
                        adapter2.setSelectIndex(position);
                        adapter2.notifyDataSetChanged();
                        p=position;
                        new Thread() {
                            @Override
                            public void run() {
                                OkHttpUtils.get().url("http://123.206.107.160/webapiJD/api/JD/GetMaterialDetail?客户核算Id=" + PrefUtils.getString(UIUtils.getContext(), "userId", DesUtils.encryptDes("0")) + "&typeId=" + producer2.get(position).getId() + "&pageIndex=" + 1 + "&pageSize=10").build().execute(new StringCallback() {
                                    @Override
                                    public void onError(Call call, Exception e) {

                                    }

                                    @Override
                                    public void onResponse(String response) {
                                        Gson gson = new Gson();
                                        data.getJh().clear();
                                        MaterialAll all = gson.fromJson(response, MaterialAll.class);
                                        data.getJh().addAll(all.getJh());
                                        i = 0;
                                        j = 1;
                                        k = 0;
                                        MyApplication.getHandler().post(new Runnable() {
                                            @Override
                                            public void run() {
                                                allAdapter.notifyDataSetChanged();
                                                lvFenlei.setSelection(0);
                                            }
                                        });
                                    }
                                });
                            }
                        }.start();
                        new Thread() {
                            @Override
                            public void run() {
                                OkHttpUtils.get().url("http://123.206.107.160/WebApiJD/api/JD/GetMaterialType?typeId=" + producer2.get(position).getId()).build().execute(new StringCallback() {
                                    @Override
                                    public void onError(Call call, Exception e) {

                                    }

                                    @Override
                                    public void onResponse(String response) {
                                        Gson gson = new Gson();
                                        producer3.clear();
                                        ArrayList<Producer> data2 = gson.fromJson(response, new TypeToken<List<Producer>>() {
                                        }.getType());
                                        if(data2.size()==0){
                                            producer3.add(producerdoman);
                                        }else {
                                            producer3.addAll(data2);
                                        }
                                        MyApplication.getHandler().post(new Runnable() {
                                            @Override
                                            public void run() {
                                                fenlei3.setAdapter(adapter3);
                                                adapter3.setSelectIndex(-1);
                                                adapter3.notifyDataSetChanged();
                                            }
                                        });
                                    }
                                });
                            }
                        }.start();
                    }else{
                        Toast.makeText(UIUtils.getContext(),"没有下层分类，不可点击",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        fenlei3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if(!producer3.get(0).getName().equals("全部")) {
                    adapter3.setSelectIndex(position);
                    adapter3.notifyDataSetChanged();
                    p=position;
                    new Thread() {
                        @Override
                        public void run() {
                            OkHttpUtils.get().url("http://123.206.107.160/webapiJD/api/JD/GetMaterialDetail?客户核算Id=" + PrefUtils.getString(UIUtils.getContext(), "userId", DesUtils.encryptDes("0")) + "&typeId=" + producer3.get(position).getId() + "&pageIndex=" + 1 + "&pageSize=10").build().execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e) {

                                }

                                @Override
                                public void onResponse(String response) {
                                    Gson gson = new Gson();
                                    data.getJh().clear();
                                    MaterialAll all = gson.fromJson(response, MaterialAll.class);
                                    data.getJh().addAll(all.getJh());
                                    i = 0;
                                    j = 0;
                                    k = 1;
                                    MyApplication.getHandler().post(new Runnable() {
                                        @Override
                                        public void run() {
                                            allAdapter.notifyDataSetChanged();
                                            lvFenlei.setSelection(0);
                                        }
                                    });
                                }
                            });
                        }
                    }.start();
                }else{
                    Toast.makeText(UIUtils.getContext(),"没有下层分类，不可点击",Toast.LENGTH_SHORT).show();
                }
            }
        });
        allAdapter = new MaterialAllAdapter(data.getJh(),this);

        lvFenlei.setAdapter(allAdapter);

        lvFenlei.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (("" + data.getJh().get(position).getQty()).equals("0.0")) {
                    Toast.makeText(UIUtils.getContext(), "库存为0，不可购买", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Intent intent = new Intent(getActivity(), ShopActivity.class);
                    intent.putExtra("name", data.getJh().get(position).getName());
                    intent.putExtra("price", data.getJh().get(position).getPrice());
                    intent.putExtra("total", data.getJh().get(position).getQty());
                    intent.putExtra("id", data.getJh().get(position).getId());
                    intent.putExtra("TP", data.getJh().get(position).getTp());
                    if(data.getJh().get(position).getMstp()==null||data.getJh().get(position).getMstp().equals("")) {
                        intent.putExtra("mstp", "没有");
                    }else {
                        intent.putExtra("mstp", data.getJh().get(position).getMstp());
                    }
                    startActivity(intent);
                }
            }
        });

        swipeDetail.setOnLoadListener(new SwipeRefreshView.OnLoadListener() {
            @Override
            public void onLoad() {

                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        if (i >= 1) {
                                i = i + 1;
                                MaterialAll all = procotol.getData("" + producer1.get(p).getId(), i, PrefUtils.getString(UIUtils.getContext(), "userId", DesUtils.encryptDes("0")));
                                if (checkResult(all.getJh()).equals(LoadingPager.LoadedResult.SUCCESS)) {
                                    //System.out.println("数量"+materialTypeDetails.size());
                                    data.getJh().addAll(all.getJh());

                                    MyApplication.getHandler().post(new Runnable() {
                                        @Override
                                        public void run() {
                                            allAdapter.notifyDataSetChanged();
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
                        }else if(j >= 1){
                                j = j + 1;
                                MaterialAll all = procotol.getData("" + producer2.get(p).getId(), j, PrefUtils.getString(UIUtils.getContext(), "userId", DesUtils.encryptDes("0")));
                                if (checkResult(all.getJh()).equals(LoadingPager.LoadedResult.SUCCESS)) {
                                    //System.out.println("数量"+materialTypeDetails.size());
                                    data.getJh().addAll(all.getJh());

                                    MyApplication.getHandler().post(new Runnable() {
                                        @Override
                                        public void run() {
                                            allAdapter.notifyDataSetChanged();
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
                        }else if(k >= 1){
                                k = k + 1;
                                MaterialAll all = procotol.getData("" + producer3.get(p).getId(), k, PrefUtils.getString(UIUtils.getContext(), "userId", DesUtils.encryptDes("0")));
                                if (checkResult(all.getJh()).equals(LoadingPager.LoadedResult.SUCCESS)) {
                                    //System.out.println("数量"+materialTypeDetails.size());
                                    data.getJh().addAll(all.getJh());

                                    MyApplication.getHandler().post(new Runnable() {
                                        @Override
                                        public void run() {
                                            allAdapter.notifyDataSetChanged();
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
                    }
                }.start();
                //swipeDetail.setLoading(false);
            }
        });
        return view;
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable(){
            public void run() {
                swipeDetail.setRefreshing(false);
             }
            },1000);
    }

    public void login(){
        Intent intent=new Intent(UIUtils.getContext(), LoginActivity.class);
        startActivity(intent);
    }
}
