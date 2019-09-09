package com.myygjc.ant.project.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zy2 on 2017/1/14.
 */

public class LocationManagerDbHelper extends SQLiteOpenHelper {
    public LocationManagerDbHelper(Context context) {
        super(context, "location.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table location(id integer primary key autoincrement,name varchar(20),tellphone varchar(20),location varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
