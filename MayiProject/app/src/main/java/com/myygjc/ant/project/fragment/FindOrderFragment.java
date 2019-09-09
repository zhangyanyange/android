package com.myygjc.ant.project.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.myygjc.ant.project.R;
import com.myygjc.ant.project.activity.LookOrderActivity;
import com.myygjc.ant.project.adapter.FindOrderAdapter;
import com.myygjc.ant.project.base.BaseFragment;
import com.myygjc.ant.project.base.LoadingPager;
import com.myygjc.ant.project.doman.FindOrder;
import com.myygjc.ant.project.procotol.FindOrderProcotol;
import com.myygjc.ant.project.util.DesUtils;
import com.myygjc.ant.project.util.PrefUtils;
import com.myygjc.ant.project.util.UIUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/3/6.
 */

public class FindOrderFragment extends BaseFragment {

    @Bind(R.id.lv_findorder)
    ListView lvFindorder;
    private ArrayList<FindOrder> data;

    @Override
    public LoadingPager.LoadedResult initData() {
        data = FindOrderProcotol.getOrderInfo(PrefUtils.getString(UIUtils.getContext(), "userId", DesUtils.encryptDes("0")));
        return checkResult(data);
    }

    @Override
    public View initSuccessView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_find_order, null);
        ButterKnife.bind(this, view);
        FindOrderAdapter adapter = new FindOrderAdapter(data);
        lvFindorder.setAdapter(adapter);

        lvFindorder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(UIUtils.getContext(),LookOrderActivity.class);
                intent.putExtra("id",data.get(position).getID());
                intent.putExtra("shdz",data.get(position).getShdz());
                System.out.println("运费"+(data.get(position).getCzyf()+data.get(position).getSpyf()+data.get(position).getEcdyyf()));
                intent.putExtra("zyf",data.get(position).getCzyf()+data.get(position).getSpyf()+data.get(position).getEcdyyf());
                startActivity(intent);
            }
        });
        return view;
    }


}
