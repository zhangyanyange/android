package com.myygjc.ant.project.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.myygjc.ant.project.R;
import com.myygjc.ant.project.adapter.LookOrderAdapter;
import com.myygjc.ant.project.base.BaseFragment;
import com.myygjc.ant.project.base.LoadingPager;
import com.myygjc.ant.project.doman.LookOrder;
import com.myygjc.ant.project.procotol.LookOrderProcotol;
import com.myygjc.ant.project.util.UIUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/3/6.
 */

public class LookerOrderFragment extends BaseFragment {

    @Bind(R.id.lv_look_order)
    ListView lvLookOrder;
    private ArrayList<LookOrder> data;

    @Override
    public LoadingPager.LoadedResult initData() {
        Bundle bundle = getArguments();
        String id = bundle.getString("id");
        data = LookOrderProcotol.getOrderDetail(id);
        return checkResult(data);
    }

    @Override
    public View initSuccessView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_look_order, null);
        ButterKnife.bind(this, view);


        LookOrderAdapter adapter = new LookOrderAdapter(data);
        lvLookOrder.setAdapter(adapter);
        return view;
    }


}
