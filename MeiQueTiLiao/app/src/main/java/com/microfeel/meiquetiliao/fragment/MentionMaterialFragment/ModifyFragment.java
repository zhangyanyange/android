package com.microfeel.meiquetiliao.fragment.MentionMaterialFragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.microfeel.meiquetiliao.R;
import com.microfeel.meiquetiliao.activity.MaterialDetailActivity;
import com.microfeel.meiquetiliao.adapter.MentionMaterialsAdapter;
import com.microfeel.meiquetiliao.base.BaseFragment;
import com.microfeel.meiquetiliao.base.Constants;
import com.microfeel.meiquetiliao.base.LoadingPager;
import com.microfeel.meiquetiliao.base.MyApplication;
import com.microfeel.meiquetiliao.domain.DeleteBean;
import com.microfeel.meiquetiliao.domain.MentionMaterial;
import com.microfeel.meiquetiliao.procotol.GetProjectStatusProcotol;
import com.microfeel.meiquetiliao.util.PrefUtils;
import com.microfeel.meiquetiliao.util.ShowToastTime;
import com.microfeel.meiquetiliao.util.UIUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by zy2 on 2017/12/4.
 */

public class ModifyFragment extends BaseFragment {

    @Bind(R.id.lv_modify)
    ListView lvModify;
    private MentionMaterial mentionMaterial;
    private MentionMaterialsAdapter adapter;

    private boolean isReturnOrder=false;



    @Override
    public LoadingPager.LoadedResult initData() {
        mentionMaterial = GetProjectStatusProcotol.getProjectStatus(1, PrefUtils.getString(UIUtils.getContext(), "projectId", ""));
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

        adapter = new MentionMaterialsAdapter(getActivity(), mentionMaterial.getListContent());
        lvModify.setAdapter(adapter);

        adapter.setClickDeleteListener(new MentionMaterialsAdapter.onClickDeleteListener() {
            @Override
            public void delete(final int position) {
                OkHttpUtils.delete().url(Constants.BASE_URL + "Order/" + mentionMaterial.getListContent().get(position).getID()).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Toast.makeText(UIUtils.getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        DeleteBean deleteBean = gson.fromJson(response, DeleteBean.class);
                        if (deleteBean.getResult() == 0) {
//                            Toast.makeText(UIUtils.getContext(),, Toast.LENGTH_SHORT).show();
                            ShowToastTime.showTextToast(deleteBean.getMessage());
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    mentionMaterial.getListContent().clear();
                                    MentionMaterial mentionMaterial1 = GetProjectStatusProcotol.getProjectStatus(1, PrefUtils.getString(UIUtils.getContext(), "projectId", ""));
                                    mentionMaterial.getListContent().addAll(mentionMaterial1.getListContent());
                                    MyApplication.getHandler().post(new Runnable() {
                                        @Override
                                        public void run() {
                                            adapter.notifyDataSetChanged();
                                        }
                                    });
                                }
                            }).start();

                        }
                    }
                });
            }
        });

        lvModify.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                isReturnOrder=mentionMaterial.getListContent().get(i).getRemark().split("---").length==3;
                Intent intent = new Intent(UIUtils.getContext(), MaterialDetailActivity.class);
                intent.putExtra("Code", mentionMaterial.getListContent().get(i).getCode());
                intent.putExtra("tijiao", "1");
                if(isReturnOrder){
                    intent.putExtra("tuiliao",mentionMaterial.getListContent().get(i).getRemark());
                }else{
                    intent.putExtra("tuiliao","");
                }
                intent.putExtra("time", mentionMaterial.getListContent().get(i).getCreateTime());
                startActivity(intent);
            }
        });
        return view;
    }

}
