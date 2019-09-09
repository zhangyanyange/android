package com.mvc.microfeel.fragment;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mvc.microfeel.R;
import com.mvc.microfeel.activity.DiscussActivity;
import com.mvc.microfeel.activity.ExIncomeAllActivity;
import com.mvc.microfeel.activity.SearchActivity;
import com.mvc.microfeel.adapter.ProjectAdapter;
import com.mvc.microfeel.base.BaseFragment;
import com.mvc.microfeel.base.LoadingPager;
import com.mvc.microfeel.domain.ProjectList;
import com.mvc.microfeel.protocol.GetProjectListProcotol;
import com.mvc.microfeel.util.UIUtils;
import com.mvc.microfeel.view.MenuItem;
import com.mvc.microfeel.view.TopRightMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/8/10.
 */
public class HomeFragment extends BaseFragment {
    @Bind(R.id.lvProgect)
    ListView lvProgect;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.menu)
    LinearLayout menu;
    @Bind(R.id.tv_project)
    TextView tvProject;
    @Bind(R.id.ll_fenlei)
    LinearLayout llFenlei;
    @Bind(R.id.ib_search)
    ImageButton ibSearch;


    private ArrayList<ProjectList> projectList;

    ArrayList<String> type = new ArrayList<>();

    ArrayList<ProjectList> projectAll = new ArrayList<>();
    private ProjectAdapter adapter;
    private String s;
    private String s1;

    @Override
    public LoadingPager.LoadedResult initData() {
        projectList = GetProjectListProcotol.getProjectList(-1);
        type.add("全部项目");

        for (int i = 0; i < projectList.size(); i++) {
            String s = "";
            for (int j = 0; j < type.size(); j++) {
                s = s + type.get(j);
            }
            if (!s.contains(projectList.get(i).getProjectType())) {
                type.add(projectList.get(i).getProjectType());
            }

            projectAll.add(projectList.get(i));
        }


        return checkResult(projectList);
    }

    @Override
    public View initSuccessView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_home, null);
        ButterKnife.bind(this, view);
        adapter = new ProjectAdapter(getActivity(), projectList);
        lvProgect.setAdapter(adapter);
        ibSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UIUtils.getContext(), SearchActivity.class);
                intent.putParcelableArrayListExtra("allProject", projectAll);
                startActivity(intent);
            }
        });
        lvProgect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int postion, long l) {
                if(s1==null){
                    if(projectAll.get(postion).getProjectType().equals("施工项目")){
                        goExin(postion);
                    }else{
                        goDiscuss();
                    }
                    return;
                }
               if(s1.equals("施工项目")){
                   goExin(postion);
               }else if(s1.equals("全部项目")){
                   if(projectAll.get(postion).getProjectType().equals("施工项目")){
                       goExin(postion);
                   }else{
                       goDiscuss();
                   }
               }else {
                   goDiscuss();
               }
            }
        });

        llFenlei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopuwindow();
            }
        });
        return view;
    }

    private void goExin(int postion) {
        Intent intent = new Intent(UIUtils.getContext(), ExIncomeAllActivity.class);
        intent.putExtra("projectID", projectList.get(postion).getNO());
        startActivity(intent);
    }

    private void goDiscuss() {
        Intent intent = new Intent(UIUtils.getContext(), DiscussActivity.class);
        startActivity(intent);
    }

    private void createPopuwindow() {
        TopRightMenu topRightMenu = new TopRightMenu(getActivity());
        List<MenuItem> menuItems = new ArrayList<>();

        for (int i = 0; i < type.size(); i++) {
            menuItems.add(new MenuItem(type.get(i)));
        }
        topRightMenu
                .setHeight(RecyclerView.LayoutParams.WRAP_CONTENT)     //默认高度480
                //默认宽度wrap_content
                .showIcon(true)     //显示菜单图标，默认为true
                .dimBackground(false)           //背景变暗，默认为true
                .needAnimationStyle(false)   //显示动画，默认为true
                .setAnimationStyle(R.style.TRM_ANIM_STYLE)  //默认为R.style.TRM_ANIM_STYLE
                .addMenuList(menuItems)
                .setOnMenuItemClickListener(new TopRightMenu.OnMenuItemClickListener() {
                    @Override
                    public void onMenuItemClick(int position) {
                        s1 = type.get(position);
                        tvProject.setText(s1);

                        projectList.clear();
                        if(s1.equals("全部项目")){
                            projectList.addAll(projectAll);
                        }else{
                            projectList.addAll(getProjectList(s1));
                        }

                        adapter.notifyDataSetChanged();
                    }
                })
                .showAsDropDown(llFenlei, -225, 0);

    }

    //根据类型获取项目列表
    private ArrayList<ProjectList> getProjectList(String type){
        ArrayList<ProjectList> projectType=new ArrayList<>();
        for (ProjectList project: projectAll) {
            if(type.equals(project.getProjectType())){
                projectType.add(project);
            }
        }
        return projectType;
    }
}
