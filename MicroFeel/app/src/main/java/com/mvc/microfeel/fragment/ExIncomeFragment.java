package com.mvc.microfeel.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.mvc.microfeel.R;
import com.mvc.microfeel.adapter.ProjectExInAdapter;
import com.mvc.microfeel.base.BaseFragment;
import com.mvc.microfeel.base.LoadingPager;
import com.mvc.microfeel.domain.ExInProject;
import com.mvc.microfeel.protocol.ExIncomeProcotol;
import com.mvc.microfeel.util.UIUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/8/31.
 */

public class ExIncomeFragment extends BaseFragment {

    @Bind(R.id.lv_projectexin)
    ListView lvProjectexin;
    private ArrayList<ExInProject> projectExIN;

    @Override
    public LoadingPager.LoadedResult initData() {
        Bundle bundle = getArguments();
        String projectID = bundle.getString("projectID");
        int accountID = bundle.getInt("accountID");
        projectExIN = ExIncomeProcotol.getProjectExIN(projectID,accountID);
        return checkResult(projectExIN);
    }

    @Override
    public View initSuccessView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_ex_in, null);
        ButterKnife.bind(this, view);
        ProjectExInAdapter adapter = new ProjectExInAdapter(getActivity(), projectExIN);
        lvProjectexin.setAdapter(adapter);
        return view;
    }


}
