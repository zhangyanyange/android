package com.mvc.dresssup.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mvc.dresssup.R;
import com.mvc.dresssup.activity.AddNewLocationActivity;
import com.mvc.dresssup.adapter.GondiAdapter;
import com.mvc.dresssup.base.BaseFragment;
import com.mvc.dresssup.base.LoadingPager;
import com.mvc.dresssup.base.MyApplication;
import com.mvc.dresssup.domain.AddressBean;
import com.mvc.dresssup.domain.AddressStateBean;
import com.mvc.dresssup.procotol.ChangeAddressProcotol;
import com.mvc.dresssup.procotol.GetAddressListProcotol;
import com.mvc.dresssup.procotol.RemoveAddressProcotol;
import com.mvc.dresssup.util.UIUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/10/25.
 */

public class LocationFragment extends BaseFragment {
    @BindView(R.id.lv_gongdi)
    ListView lvGongdi;
    @BindView(R.id.loadView)
    ProgressBar loadView;
    private ArrayList<AddressBean> data;
    private GondiAdapter adapter;

    @Override
    public LoadingPager.LoadedResult initData() {
        EventBus.getDefault().register(this);
        data = GetAddressListProcotol.getAddressList();
        return checkResult(data);
    }

    @Override
    public View initSuccessView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_gongdi, null);
        ButterKnife.bind(this, view);
        adapter = new GondiAdapter(data, getActivity());
        lvGongdi.setAdapter(adapter);

        //单选的点击事件
        adapter.setOnInnerItemOnClickListener(new GondiAdapter.InnerItemOnclickListener() {
            @Override
            public void itemClick(View view, int position) {
                if(data.get(position).isIsDefault()==1){
                    return;
                }
                loadView.setVisibility(View.VISIBLE);

                final AddressBean bean=new AddressBean();
                bean.setIsDefault(1);
                bean.setTelephone(data.get(position).getTelephone());
                bean.setAddress(data.get(position).getAddress());
                bean.setConsignee(data.get(position).getConsignee());
                bean.setId(data.get(position).getId());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final AddressStateBean addressStateBean = ChangeAddressProcotol.changeAddress(bean);
                        if(addressStateBean.getResult()==1){
                            MyApplication.getHandler().post(new Runnable() {
                                @Override
                                public void run() {
                                    loadView.setVisibility(View.GONE);
                                    Toast.makeText(UIUtils.getContext(), "更改状态成功", Toast.LENGTH_SHORT).show();
                                    data.clear();
                                    data.addAll(addressStateBean.getListContent());
                                    adapter.notifyDataSetChanged();
                                }
                            });
                        }else{
                            MyApplication.getHandler().post(new Runnable() {
                                @Override
                                public void run() {
                                    loadView.setVisibility(View.GONE);
                                    Toast.makeText(UIUtils.getContext(), "更改状态失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();

            }
        });

        //删除事件
        adapter.setDeleteItemOnclickListener(new GondiAdapter.DeleteItemOnclickListener() {
            @Override
            public void deleteItem(View view, int position) {
                loadView.setVisibility(View.VISIBLE);

//                final AddressBean bean=new AddressBean();
//                bean.setIsDefault(1);
//                bean.setTelephone(data.get(position).getTelephone());
//                bean.setAddress(data.get(position).getAddress());
//                bean.setConsignee(data.get(position).getConsignee());
//                bean.setId(data.get(position).getId());
                final AddressBean bean = data.get(position);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final AddressStateBean addressStateBean = RemoveAddressProcotol.deleteAddress(bean);
                        if(addressStateBean.getResult()==1){
                            MyApplication.getHandler().post(new Runnable() {
                                @Override
                                public void run() {
                                    loadView.setVisibility(View.GONE);
                                    Toast.makeText(UIUtils.getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                                    data.clear();
                                    data.addAll(addressStateBean.getListContent());
                                    adapter.notifyDataSetChanged();
                                }
                            });
                        }else{
                            MyApplication.getHandler().post(new Runnable() {
                                @Override
                                public void run() {
                                    loadView.setVisibility(View.GONE);
                                    Toast.makeText(UIUtils.getContext(), "删除失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();
            }
        });

        adapter.setChangeItemOnclickListener(new GondiAdapter.ChangeItemOnclickListener() {
            @Override
            public void changeItem(View view, int position) {
                Intent intent=new Intent(UIUtils.getContext(), AddNewLocationActivity.class);
                intent.putExtra("location",data.get(position));
                startActivity(intent);

            }
        });
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshUI(ArrayList<AddressBean> bean) {
        data.clear();
        data.addAll(bean);
        adapter.notifyDataSetChanged();
    }

}
