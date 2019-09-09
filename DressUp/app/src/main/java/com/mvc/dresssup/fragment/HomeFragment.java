package com.mvc.dresssup.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mvc.dresssup.R;
import com.mvc.dresssup.SearchActivity;
import com.mvc.dresssup.activity.PopularbrandsActivity;
import com.mvc.dresssup.adapter.CaiLiaoAdapter;
import com.mvc.dresssup.base.BaseFragment;
import com.mvc.dresssup.base.LoadingPager;
import com.mvc.dresssup.util.UIUtils;
import com.mvc.dresssup.view.GlideImageLoader;
import com.mvc.dresssup.view.NoScroolGridView;
import com.youth.banner.Banner;
import com.zhy.android.percent.support.PercentLinearLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zy2 on 2017/9/6.
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener {


    @BindView(R.id.rl_fukuan)
    RelativeLayout rlFukuan;
    @BindView(R.id.tv_search_bg)
    TextView tvSearchBg;
    @BindView(R.id.search)
    LinearLayout search;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.ll_head)
    LinearLayout llHead;
    @BindView(R.id.gridView)
    NoScroolGridView gridView;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_shenghuo)
    TextView tvShenghuo;
    @BindView(R.id.id1)
    ImageView id1;
    @BindView(R.id.id2)
    ImageView id2;
    @BindView(R.id.id3)
    ImageView id3;
    @BindView(R.id.id4)
    ImageView id4;
    @BindView(R.id.ll_paizi)
    PercentLinearLayout llPaizi;
    @BindView(R.id.id5)
    ImageView id5;
    @BindView(R.id.id6)
    ImageView id6;
    @BindView(R.id.id7)
    ImageView id7;
    @BindView(R.id.id8)
    ImageView id8;
    Unbinder unbinder;
    private String str;

    @Override
    public LoadingPager.LoadedResult initData() {
        return LoadingPager.LoadedResult.SUCCESS;
    }

    @Override
    public View initSuccessView() {

        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_home, null);
        ButterKnife.bind(this, view);

        ArrayList<Integer> images = new ArrayList<>();
        images.add(R.drawable.banner1);
        images.add(R.drawable.banner2);
        images.add(R.drawable.banner3);
        banner.setImages(images).setImageLoader(new GlideImageLoader()).start();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UIUtils.getContext(), SearchActivity.class));
            }
        });

        final String[] girdString = {"1",
                "4",
                "8",
                "186e7caa-14b3-e711-8118-00155d58e105", "bdb3d8fa-17b3-e711-8118-00155d58e105",
                "de384e6b-19b3-e711-8118-00155d58e105",
                "82c1df62-b6bf-46d9-be13-a81100df3c4d",
                "3303780d-12a2-4e3f-bec8-a81100e00d25"};

        final String[] girdName = {"涂料", "壁纸", "灯具", "洁具", "涂料服务", "辅帖服务", "安装服务", "保洁服务"};
        gridView.setAdapter(new CaiLiaoAdapter());

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(UIUtils.getContext(), PopularbrandsActivity.class);
                intent.putExtra("brand", girdString[position]);
                intent.putExtra("brandName", girdName[position]);
                startActivity(intent);
            }
        });
        id1.setOnClickListener(this);
        id2.setOnClickListener(this);
        id3.setOnClickListener(this);
        id4.setOnClickListener(this);
        id5.setOnClickListener(this);
        id6.setOnClickListener(this);
        id7.setOnClickListener(this);
        id8.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id1:
                str = "position1";
                toast(str);
                break;
            case R.id.id2:
                str = "position2";
                toast(str);
                break;
            case R.id.id3:
                str = "position3";
                toast(str);
                break;
            case R.id.id4:
                str = "position4";
                toast(str);
                break;
            case R.id.id5:
                str = "position5";
                toast(str);
                break;
            case R.id.id6:
                str = "position6";
                toast(str);
                break;
            case R.id.id7:
                str = "position7";
                toast(str);
                break;
            case R.id.id8:
                str = "position8";
                toast(str);
                break;

        }
    }

    private void toast(String msg) {
        Toast.makeText(UIUtils.getContext(), msg, Toast.LENGTH_SHORT).show();
    }


}
