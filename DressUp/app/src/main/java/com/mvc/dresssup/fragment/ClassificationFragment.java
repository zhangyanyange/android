package com.mvc.dresssup.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mvc.dresssup.R;
import com.mvc.dresssup.activity.ProductDetailActivity;
import com.mvc.dresssup.adapter.ProductAdapter;
import com.mvc.dresssup.base.BaseFragment;
import com.mvc.dresssup.base.LoadingPager;
import com.mvc.dresssup.domain.ClassificationProduct;
import com.mvc.dresssup.util.UIUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/10/26.
 */

public class ClassificationFragment extends BaseFragment {
    @BindView(R.id.listview)
    ListView listview;
    private ArrayList<ClassificationProduct.ProductsBean> product;
    private ProductAdapter adapter;

    @Override
    public LoadingPager.LoadedResult initData() {
        EventBus.getDefault().register(this);
        Bundle bundle = getArguments();
        product = bundle.getParcelableArrayList("product");
        return checkResult(product);

    }

    @Override
    public View initSuccessView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_product, null);
        ButterKnife.bind(this, view);
        adapter = new ProductAdapter(product, getActivity());
        listview.setAdapter(adapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(UIUtils.getContext(), ProductDetailActivity.class);
                intent.putExtra("productId",product.get(i).getId());
                startActivity(intent);
            }
        });
        return view;
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshUI(ArrayList<ClassificationProduct.ProductsBean> bean) {
        product.clear();
        product.addAll(bean);
        adapter.notifyDataSetChanged();
        listview.setSelection(0);
    }
}
