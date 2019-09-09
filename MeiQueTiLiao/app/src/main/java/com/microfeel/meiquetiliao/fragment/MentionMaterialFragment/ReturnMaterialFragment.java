package com.microfeel.meiquetiliao.fragment.MentionMaterialFragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.microfeel.meiquetiliao.R;
import com.microfeel.meiquetiliao.activity.MaterialDetailActivity;
import com.microfeel.meiquetiliao.adapter.ReturnMaterialAdapter;
import com.microfeel.meiquetiliao.base.BaseFragment;
import com.microfeel.meiquetiliao.base.LoadingPager;
import com.microfeel.meiquetiliao.domain.MentionMaterial;
import com.microfeel.meiquetiliao.procotol.GetProjectStatusProcotol;
import com.microfeel.meiquetiliao.util.PrefUtils;
import com.microfeel.meiquetiliao.util.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/12/19.
 */

public class ReturnMaterialFragment extends BaseFragment {
    @Bind(R.id.lv_modify)
    ListView lvModify;
    private MentionMaterial mentionMaterial;

    @Override
    public LoadingPager.LoadedResult initData() {
        mentionMaterial = GetProjectStatusProcotol.getProjectStatus(5, PrefUtils.getString(UIUtils.getContext(),"projectId",""));
        if(mentionMaterial==null){
            return LoadingPager.LoadedResult.ERROR;
        }else{
            return checkResult(mentionMaterial.getListContent());
        }

    }

    @Override
    public View initSuccessView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_modify, null);
        ButterKnife.bind(this, view);

        ReturnMaterialAdapter adapter = new ReturnMaterialAdapter(getActivity(), mentionMaterial.getListContent());
        lvModify.setAdapter(adapter);

        lvModify.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(UIUtils.getContext(), MaterialDetailActivity.class);
                intent.putExtra("Code",mentionMaterial.getListContent().get(i).getCode());
                intent.putExtra("tijiao","2");
                intent.putExtra("time",mentionMaterial.getListContent().get(i).getCreateTime());
                intent.putExtra("tuiliao",mentionMaterial.getListContent().get(i).getRemark());
                startActivity(intent);
            }
        });
        return view;
    }
}
