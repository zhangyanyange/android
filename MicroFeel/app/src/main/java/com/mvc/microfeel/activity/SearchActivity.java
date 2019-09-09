package com.mvc.microfeel.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.mvc.microfeel.R;
import com.mvc.microfeel.adapter.ProjectAdapter;
import com.mvc.microfeel.domain.ProjectList;
import com.mvc.microfeel.util.UIUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchActivity extends Activity {

    @Bind(R.id.tv_hint)
    EditText tvHint;
    @Bind(R.id.iv_arrow)
    ImageView ivArrow;
    @Bind(R.id.lvProgect)
    ListView lvProgect;
    private ArrayList<ProjectList> allProject;

    private ArrayList<ProjectList> searchProject=new ArrayList<>();
    private ProjectAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        final Intent intent = getIntent();
        allProject = intent.getParcelableArrayListExtra("allProject");
        adapter = new ProjectAdapter(this, searchProject);
        lvProgect.setAdapter(adapter);
        lvProgect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(searchProject.get(i).getProjectType().equals("洽谈项目")){
                    goDiscuss();
                }else{
                    goExin(i);
                }
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

    private ArrayList<ProjectList> getSearchResult(String address) {
        searchProject.clear();
        if(address.equals("")){
            return searchProject;
        }
        for (ProjectList project : allProject) {
            if (project.getAdress().contains(address)) {
                searchProject.add(project);
            }
        }
        return searchProject;
    }

    private void goExin(int postion) {
        Intent intent = new Intent(UIUtils.getContext(), ExIncomeAllActivity.class);
        intent.putExtra("projectID", searchProject.get(postion).getNO());
        startActivity(intent);
    }

    private void goDiscuss() {
        Intent intent = new Intent(UIUtils.getContext(), DiscussActivity.class);
        startActivity(intent);
    }
}
