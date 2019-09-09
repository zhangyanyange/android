package com.mvc.microfeel.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mvc.microfeel.R;
import com.mvc.microfeel.activity.ExIncomeActivity;
import com.mvc.microfeel.adapter.AllProjectExInAdapter;
import com.mvc.microfeel.base.BaseFragment;
import com.mvc.microfeel.base.LoadingPager;
import com.mvc.microfeel.domain.ExInAllProject;
import com.mvc.microfeel.protocol.ExIncomeAllProcotol;
import com.mvc.microfeel.util.UIUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/8/31.
 */

public class ExIncomeAllFragment extends BaseFragment {


    @Bind(R.id.lv_projectexinall)
    ListView lvProjectexinall;
//    @Bind(R.id.tv_jchte)
//    TextView tvJchte;
//    @Bind(R.id.jcysk)
//    TextView jcysk;
//    @Bind(R.id.jcjkbl)
//    TextView jcjkbl;
//    @Bind(R.id.cphte)
//    TextView cphte;
//    @Bind(R.id.cpysk)
//    TextView cpysk;
//    @Bind(R.id.cpjkbl)
//    TextView cpjkbl;

    private ArrayList<ExInAllProject> allProjectExIN;
    private String projectID;

    @Override
    public LoadingPager.LoadedResult initData() {
        Bundle bundle = getArguments();
        projectID = bundle.getString("projectID");
        allProjectExIN = ExIncomeAllProcotol.getAllProjectExIN(projectID);
        return checkResult(allProjectExIN);
    }

    @Override
    public View initSuccessView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_ex_in_all, null);
        ButterKnife.bind(this, view);
//        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CHINA);
//        DecimalFormat decimalFormat = new DecimalFormat("#.##%");
//        String jchte = format.format(allProjectExIN.get(0).getAmount());
//        tvJchte.setText(jchte);
//        String jcysk1 = format.format(allProjectExIN.get(2).getAmount());
//        jcysk.setText(jcysk1);
//
//        if(!(allProjectExIN.get(0).getAmount()>0)){
//            jcjkbl.setText("0.00%");
//        }else{
//            String format1 = decimalFormat.format(allProjectExIN.get(2).getAmount()/ allProjectExIN.get(0).getAmount());
//            jcjkbl.setText(format1);
//        }
//        String cphte1 = format.format(allProjectExIN.get(1).getAmount());
//        cphte.setText(cphte1);
//        String cpysk1 = format.format(allProjectExIN.get(3).getAmount());
//        cpysk.setText(cpysk1);
//
//        if(allProjectExIN.get(3).getAmount()==0){
//            cpjkbl.setText("0.00%");
//        }else{
//            String format1 = decimalFormat.format(allProjectExIN.get(3).getAmount() / allProjectExIN.get(1).getAmount());
//            cpjkbl.setText(format1);
//        }
        AllProjectExInAdapter adapter = new AllProjectExInAdapter(getActivity(), allProjectExIN);
        lvProjectexinall.setAdapter(adapter);
        lvProjectexinall.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int postion, long l) {
                Intent intent = new Intent(UIUtils.getContext(), ExIncomeActivity.class);
                intent.putExtra("projectID", projectID);
                intent.putExtra("accountID", allProjectExIN.get(postion).getAccountID());

                startActivity(intent);
            }
        });
        return view;
    }

}
