package com.myygjc.ant.project.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.myygjc.ant.project.R;
import com.myygjc.ant.project.db.LocationManager;
import com.myygjc.ant.project.doman.LocationInfo;
import com.myygjc.ant.project.util.PrefUtils;
import com.myygjc.ant.project.util.UIUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LocationActivity extends Activity {

    @Bind(R.id.back)
    ImageButton back;
    @Bind(R.id.lv_location)
    ListView lvLocation;
    @Bind(R.id.ll_location)
    LinearLayout llLocation;
    private LocationManager locationManager;
    private ArrayList<LocationInfo> query;
    private LocationAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        ButterKnife.bind(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        llLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LocationActivity.this, NewLocationActivity.class);
                startActivityForResult(intent, 3);
            }
        });
        locationManager = new LocationManager(UIUtils.getContext());
        query = locationManager.query();
        adapter = new LocationAdapter(query);
        lvLocation.setAdapter(adapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 3:
                ArrayList<LocationInfo> query1 = locationManager.query();
//                PrefUtils.putInt(UIUtils.getContext(),"zj",PrefUtils.getInt(UIUtils.getContext(),"zj",-5)+(query1.size()- query.size()));
                query.clear();
                query.addAll(query1);
                adapter.notifyDataSetChanged();
                break;
        }
    }

    class LocationAdapter extends BaseAdapter {
        private ArrayList<LocationInfo> data;

        public LocationAdapter(ArrayList<LocationInfo> data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
           final ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(UIUtils.getContext(), R.layout.item_location, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.cbSigle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        PrefUtils.putInt(UIUtils.getContext(),"zj",data.get(position).getId());
                        notifyDataSetChanged();
                    }else{
                        notifyDataSetChanged();
                    }
                }
            });
            System.out.println(PrefUtils.getInt(UIUtils.getContext(),"zj",-1));
            if (PrefUtils.getInt(UIUtils.getContext(),"zj",-1)== data.get(position).getId()) {
                holder.cbSigle.setChecked(true);
            } else {
                holder.cbSigle.setChecked(false);
            }

            holder.llDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    locationManager.delete(data.get(position).getId()+"");
                    data=locationManager.query();
                    query=data;
                    notifyDataSetChanged();
                }
            });

            holder.tvName.setText(data.get(position).getName());
            holder.tvTellphone.setText(data.get(position).getTellphone());
            holder.tvLocation.setText(data.get(position).getLocation());
            return convertView;
        }

        class ViewHolder {
            @Bind(R.id.cb_sigle)
            CheckBox cbSigle;
            @Bind(R.id.tv_name)
            TextView tvName;
            @Bind(R.id.tv_tellphone)
            TextView tvTellphone;
            @Bind(R.id.tv_location)
            TextView tvLocation;
            @Bind(R.id.ll_delete)
            LinearLayout llDelete;
            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
