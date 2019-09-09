package com.microfeel.meiquetiliao.fragment.MentionMaterialFragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.microfeel.meiquetiliao.R;
import com.microfeel.meiquetiliao.activity.ShippedDetailActivity;
import com.microfeel.meiquetiliao.adapter.ShippedAdapter;
import com.microfeel.meiquetiliao.base.BaseFragment;
import com.microfeel.meiquetiliao.base.LoadingPager;
import com.microfeel.meiquetiliao.domain.Shipped;
import com.microfeel.meiquetiliao.procotol.GetShippedProcotol;
import com.microfeel.meiquetiliao.util.PrefUtils;
import com.microfeel.meiquetiliao.util.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/12/7.
 */

public class ShippedFragment extends BaseFragment {

    @Bind(R.id.lv_modify)
    ListView lvModify;
    private Shipped shipped;

    @Override
    public LoadingPager.LoadedResult initData() {
        shipped = GetShippedProcotol.getShipped(PrefUtils.getString(UIUtils.getContext(), "location", ""));
        if(shipped==null){
            return LoadingPager.LoadedResult.ERROR;
        }else{
            return checkResult(shipped.getListContent());
        }
    }

    @Override
    public View initSuccessView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_modify, null);
        ButterKnife.bind(this, view);

        ShippedAdapter adapter=new ShippedAdapter(getActivity(),shipped.getListContent());
        lvModify.setAdapter(adapter);

        lvModify.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                Intent intent=new Intent(UIUtils.getContext(), ShippedDetailActivity.class);
                intent.putExtra("time",shipped.getListContent().get(i).getDate());
                intent.putExtra("orderid",shipped.getListContent().get(i).getID());
                startActivity(intent);
            }
        });
        return view;
    }


}
