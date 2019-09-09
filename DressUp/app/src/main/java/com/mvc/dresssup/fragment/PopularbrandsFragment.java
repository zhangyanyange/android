package com.mvc.dresssup.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.mvc.dresssup.R;
import com.mvc.dresssup.activity.ClassificationActivity;
import com.mvc.dresssup.adapter.PopularBrandsAdapter;
import com.mvc.dresssup.base.BaseFragment;
import com.mvc.dresssup.base.LoadingPager;
import com.mvc.dresssup.domain.PopularBrandsBean;
import com.mvc.dresssup.procotol.GetPopularBrandProcotol;
import com.mvc.dresssup.util.UIUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by zy2 on 2017/10/26.
 */

public class PopularbrandsFragment extends BaseFragment {

    @BindView(R.id.gridView)
    GridView gridView;
    private ArrayList<PopularBrandsBean> data;
    private String  category;

    @Override
    public LoadingPager.LoadedResult initData() {
        Bundle bundle = getArguments();
        category = bundle.getString("brand");
        data = GetPopularBrandProcotol.getPopularBrand( category);
        return checkResult(data);
    }

    @Override
    public View initSuccessView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_popularbrand, null);
        ButterKnife.bind(this, view);

        gridView.setAdapter(new PopularBrandsAdapter(data,getActivity()));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent =new Intent(UIUtils.getContext(), ClassificationActivity.class);
                intent.putExtra("bigcategory",category);
                intent.putExtra("brand",data.get(i).getId());
                startActivity(intent);
            }
        });

        return view;
    }

}
