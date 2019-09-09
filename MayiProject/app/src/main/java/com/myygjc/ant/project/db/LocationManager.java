package com.myygjc.ant.project.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.myygjc.ant.project.doman.LocationInfo;

import java.util.ArrayList;

/**
 * Created by zy2 on 2017/1/14.
 */

public class LocationManager {

    private LocationManagerDbHelper dbHelper;

    public LocationManager(Context context) {
        dbHelper = new LocationManagerDbHelper(context);
    }

    // 插入记录  
    public void insert(String name,String tellphone,String location){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",name);
        values.put("tellphone",tellphone);
        values.put("location",location);
        db.insert("location",null,values);
        db.close();
    }
    public void delete(String id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("location","id=?", new String[]{id});
        db.close();
    }

//    public void update(String name,String newName,String tellphone,String location){
//        delete(name);
//        insert(newName,tellphone,location);
//    }
    public ArrayList<LocationInfo> query(){
        ArrayList<LocationInfo>data=new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from location", null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String tellphone = cursor.getString(2);
            String location = cursor.getString(3);
            LocationInfo locationInfo=new LocationInfo();
            locationInfo.setId(id);
            locationInfo.setName(name);
            locationInfo.setTellphone(tellphone);
            locationInfo.setLocation(location);
            data.add(locationInfo);
        }
        cursor.close();
        db.close();
        return data;
    }

}
