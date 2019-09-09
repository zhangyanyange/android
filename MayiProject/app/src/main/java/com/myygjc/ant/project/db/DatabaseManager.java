package com.myygjc.ant.project.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by zy2 on 2017/1/14.
 */

public class DatabaseManager {

    private SearchOpenDbHelper dbHelper;

    public DatabaseManager(Context context) {
        dbHelper = new SearchOpenDbHelper(context);
    }

    // 插入记录  
    public void insert(String name){
        ArrayList<String> query = query();
        for (int i = 0; i < query.size(); i++) {
            if(name.equals(query.get(i))){
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.execSQL("delete from search where name=?",new Object[]{name});
                break;
            }
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",name);
        db.insert("search",null,values);
        db.close();
    }
    public void delete(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("search",null,null);
        db.close();
    }
    public ArrayList<String> query(){
        ArrayList<String>data=new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from search", null);
        while (cursor.moveToNext()){
            String name = cursor.getString(0);
            data.add(name);
        }
        cursor.close();
        db.close();
        return data;
    }

}
