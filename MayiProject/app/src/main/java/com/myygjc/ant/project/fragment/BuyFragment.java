package com.myygjc.ant.project.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.myygjc.ant.project.R;
import com.myygjc.ant.project.activity.LoginActivity;
import com.myygjc.ant.project.activity.ShopActivity;
import com.myygjc.ant.project.adapter.BuyRecommendAdapter;
import com.myygjc.ant.project.base.BaseFragment;
import com.myygjc.ant.project.base.LoadingPager;
import com.myygjc.ant.project.doman.NoLogin;
import com.myygjc.ant.project.doman.RecommendBuy;
import com.myygjc.ant.project.procotol.HotSearchProcotol;
import com.myygjc.ant.project.util.DesUtils;
import com.myygjc.ant.project.util.PrefUtils;
import com.myygjc.ant.project.util.UIUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/3/16.
 */

public class BuyFragment extends BaseFragment {
    @Bind(R.id.lv_hot)
    ListView lvHot;


    private HotSearchProcotol procotol;
    private List<RecommendBuy> data;
    private BuyRecommendAdapter adapter;

    @Override
    public LoadingPager.LoadedResult initData() {
        EventBus.getDefault().register(this);
        if (PrefUtils.getBoolean(UIUtils.getContext(), "islogin", false)) {
            procotol = new HotSearchProcotol();
            data = procotol.getData(PrefUtils.getString(UIUtils.getContext(), "userId", DesUtils.encryptDes("0")));
            return checkResult(data);
        } else {
            return LoadingPager.LoadedResult.NOLOAD;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshUI(NoLogin noLogin) {
        EventBus.getDefault().unregister(this);
        Intent intent = new Intent(UIUtils.getContext(), LoginActivity.class);
        startActivityForResult(intent, 3);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent zz) {
        switch (requestCode) {
            case 3:
                LoadingPager loadingPager = getLoadingPager();
                loadingPager.triggerLoadData();
                break;
        }
    }

    @Override
    public View initSuccessView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_buy, null);
        ButterKnife.bind(this, view);

        /**
         * 上拉加载时用
         */

        adapter = new BuyRecommendAdapter(data);
        lvHot.setAdapter(adapter);

        /**
         * 每个条目的点击事件，根据库存数量判断可以点击还是不可以点击
         */
        lvHot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (("" + data.get(position).getQty()).equals("0.0")) {
                    Toast.makeText(UIUtils.getContext(), "库存为0，不可购买", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Intent intent = new Intent(getActivity(), ShopActivity.class);
                    intent.putExtra("name", data.get(position).getName());
                    intent.putExtra("price", data.get(position).getPrice());
                    intent.putExtra("total", data.get(position).getQty());
                    intent.putExtra("id", data.get(position).getId());
                    intent.putExtra("TP", data.get(position).getTp());
                    if (data.get(position).getMstp() == null) {
                        intent.putExtra("mstp", "没有");
                    } else {
                        intent.putExtra("mstp", data.get(position).getMstp());
                    }
                    startActivity(intent);
                }
            }
        });
        return view;
    }

}
