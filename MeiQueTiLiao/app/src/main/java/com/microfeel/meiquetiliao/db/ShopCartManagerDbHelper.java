package com.microfeel.meiquetiliao.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zy2 on 2017/1/14.
 */

public class ShopCartManagerDbHelper extends SQLiteOpenHelper {
    public ShopCartManagerDbHelper(Context context) {
        super(context, "shop", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table shop(id integer primary key autoincrement,name varchar(20),count Integer,price real,unit varchar(20),materialid varchar(20),projectid varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
