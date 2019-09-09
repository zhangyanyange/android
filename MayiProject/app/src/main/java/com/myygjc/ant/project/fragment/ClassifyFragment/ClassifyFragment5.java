package com.myygjc.ant.project.fragment.ClassifyFragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.myygjc.ant.project.R;
import com.myygjc.ant.project.activity.MaterialAllActivity;
import com.myygjc.ant.project.adapter.ClassiftyAdapter;
import com.myygjc.ant.project.base.BaseFragment;
import com.myygjc.ant.project.base.LoadingPager;
import com.myygjc.ant.project.doman.Producer;
import com.myygjc.ant.project.procotol.ProducerProcotol;
import com.myygjc.ant.project.util.UIUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/3/16.
 */

public class ClassifyFragment5 extends BaseFragment{
    @Bind(R.id.lv_fty)
    ListView lvFty;
    private List<Producer> producers;

    @Override
    public LoadingPager.LoadedResult initData() {
        producers = ProducerProcotol.getProducer(267);
        return LoadingPager.LoadedResult.SUCCESS;
    }

    @Override
    public View initSuccessView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_classifty, null);
        ButterKnife.bind(this, view);
        lvFty.setAdapter(new ClassiftyAdapter(producers));
        lvFty.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(UIUtils.getContext(), MaterialAllActivity.class);
                intent.putExtra("id",producers.get(position).getId());
                startActivity(intent);
            }
        });
        return view;
    }
}
