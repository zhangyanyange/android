package com.microfeel.meiquetiliao.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

import com.microfeel.meiquetiliao.R;
import com.microfeel.meiquetiliao.adapter.ProjectAdapter;
import com.microfeel.meiquetiliao.domain.GetProjectList;
import com.microfeel.meiquetiliao.util.PrefUtils;
import com.microfeel.meiquetiliao.util.UIUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchProjectActivity extends Activity {


    @Bind(R.id.iv_arrow)
    ImageView ivArrow;
    @Bind(R.id.tv_hint)
    EditText tvHint;
    @Bind(R.id.frame_bg)
    FrameLayout frameBg;
    @Bind(R.id.lvProgect)
    ListView lvProgect;
    private ArrayList<GetProjectList> projectList;
    private ProjectAdapter adapter;
    private ArrayList<GetProjectList> searchProject=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_project);
        ButterKnife.bind(this);

        ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        final Intent intent = getIntent();
        projectList = intent.getParcelableArrayListExtra("projectList");
        adapter = new ProjectAdapter(this, searchProject);
        lvProgect.setAdapter(adapter);
        lvProgect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(UIUtils.getContext(), MentionMaterialActivity.class);
                System.out.println(searchProject.get(i).getID());
                System.out.println(searchProject.get(i).getAddress());
                PrefUtils.putString(UIUtils.getContext(), "projectId",searchProject.get(i).getID());
                PrefUtils.putString(UIUtils.getContext(), "customerName", searchProject.get(i).getCustomerName());
                PrefUtils.putString(UIUtils.getContext(), "customerPhone", searchProject.get(i).getPhone());
                PrefUtils.putString(UIUtils.getContext(), "location", searchProject.get(i).getAddress());
                startActivity(intent);

            }
        });
        tvHint.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String address = tvHint.getText().toString();
                getSearchResult(address);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private ArrayList<GetProjectList> getSearchResult(String address) {
        searchProject.clear();
        if(address.equals("")){
            return searchProject;
        }
        for (GetProjectList project : projectList) {
            if (project.getAddress().contains(address)) {
                searchProject.add(project);
            }
        }
        return searchProject;
    }
}
