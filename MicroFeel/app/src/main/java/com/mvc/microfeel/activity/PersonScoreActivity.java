package com.mvc.microfeel.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.mvc.microfeel.R;
import com.mvc.microfeel.adapter.PersonScoreAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PersonScoreActivity extends Activity {

    @Bind(R.id.lv_score)
    ListView lvScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_score);
        ButterKnife.bind(this);
        PersonScoreAdapter adapter = new PersonScoreAdapter(this);
        lvScore.setAdapter(adapter);
    }
}
