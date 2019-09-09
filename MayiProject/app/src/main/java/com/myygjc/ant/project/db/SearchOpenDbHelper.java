package com.myygjc.ant.project.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zy2 on 2017/1/14.
 */

public class SearchOpenDbHelper extends SQLiteOpenHelper {
    public SearchOpenDbHelper(Context context) {
        super(context, "text.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table search(name varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
