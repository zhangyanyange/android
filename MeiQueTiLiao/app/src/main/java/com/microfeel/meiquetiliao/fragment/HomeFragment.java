package com.microfeel.meiquetiliao.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.microfeel.meiquetiliao.R;
import com.microfeel.meiquetiliao.activity.MentionMaterialActivity;
import com.microfeel.meiquetiliao.activity.SearchProjectActivity;
import com.microfeel.meiquetiliao.adapter.ProjectAdapter;
import com.microfeel.meiquetiliao.base.BaseFragment;
import com.microfeel.meiquetiliao.base.LoadingPager;
import com.microfeel.meiquetiliao.domain.GetProjectList;
import com.microfeel.meiquetiliao.procotol.GetProjectListProcotol;
import com.microfeel.meiquetiliao.util.IntentLoginActivity;
import com.microfeel.meiquetiliao.util.PrefUtils;
import com.microfeel.meiquetiliao.util.UIUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/8/10.
 */
public class HomeFragment extends BaseFragment {
    @Bind(R.id.lvProgect)
    ListView lvProgect;
    @Bind(R.id.ib_search)
    ImageButton ibSearch;


    private ArrayList<GetProjectList> projectList;
    private ProjectAdapter adapter;

    @Override
    public LoadingPager.LoadedResult initData() {
        projectList = GetProjectListProcotol.getProjectList();
        if(IntentLoginActivity.intentLoginActivity(getActivity())){
            return LoadingPager.LoadedResult.ERROR;
        }

        return checkResult(projectList);
    }

    @Override
    public View initSuccessView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_home, null);

        ButterKnife.bind(this, view);

        adapter = new ProjectAdapter(getActivity(), projectList);

        lvProgect.setAdapter(adapter);

        lvProgect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(UIUtils.getContext(), MentionMaterialActivity.class);

                PrefUtils.putString(UIUtils.getContext(), "projectId", projectList.get(i).getID());
                PrefUtils.putString(UIUtils.getContext(), "customerName", projectList.get(i).getCustomerName());
                PrefUtils.putString(UIUtils.getContext(), "customerPhone", projectList.get(i).getPhone().substring(0,11));
                PrefUtils.putString(UIUtils.getContext(), "location", projectList.get(i).getAddress());
                startActivity(intent);
            }
        });

        ibSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(UIUtils.getContext(),SearchProjectActivity.class);
                intent.putParcelableArrayListExtra("projectList", projectList);
                startActivity(intent);
            }
        });
        return view;
    }
}
