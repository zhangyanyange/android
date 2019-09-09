package com.microfeel.meiquetiliao.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.microfeel.meiquetiliao.domain.ShopCat;

import java.util.ArrayList;

/**
 * Created by zy2 on 2017/1/14.
 */

public class ShopCartManager {

    private ShopCartManagerDbHelper dbHelper;

    public ShopCartManager(Context context) {
        dbHelper = new ShopCartManagerDbHelper(context);
    }

    // 插入记录  
    public boolean insert(String name,int count,double price,String unit,String materialid,String projectid){
        if(query1(projectid, materialid)){
            return true;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",name);
        values.put("count",count);
        values.put("price",price);
        values.put("unit",unit);
        values.put("materialid",materialid);
        values.put("projectid",projectid);
        long shop = db.insert("shop", null, values);
        db.close();
        if(shop!=0){
            return true;
        }
        return false;
    }
    public boolean delete(String materialid,String projectid){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int shop = db.delete("shop", "materialid=? and projectid=?", new String[]{materialid, projectid});
        db.close();
        if(shop!=0){
            return true;
        }
        return false;
    }

    public boolean update(String count,String projectid,String materid){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("count",count);
        int shop = db.update("shop", values, "projectid=? and materialid=?", new String[]{projectid, materid});
        db.close();
        if(shop==1){
            return true;
        }
        return false;
    }

    public boolean deleteAll(String projectid){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int shop = db.delete("shop", "projectid=?", new String[]{projectid});
        db.close();
        if(shop==1){
            return true;
        }
        return false;
    }
    public ArrayList<ShopCat> query(String projectid){
        ArrayList<ShopCat>data=new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from shop where projectid=?", new String []{projectid});
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int count = cursor.getInt(2);
            Double price = cursor.getDouble(3);
            String unit=cursor.getString(4);
            String materialid=cursor.getString(5);
            ShopCat shopcat=new ShopCat();
            shopcat.setName(name);
            shopcat.setPrice(price);
            shopcat.setCount(count);
            shopcat.setUnit(unit);
            shopcat.setMaterialid(materialid);
            data.add(shopcat);
        }
        cursor.close();
        db.close();
        return data;
    }
    public boolean query1(String projectid,String materialid){
        ArrayList<ShopCat>data=new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from shop where projectid=? and materialid=?", new String []{projectid,materialid});
        if(cursor.moveToNext()){
            cursor.close();
            db.close();
            return true;
        }else{
            cursor.close();
            db.close();
            return false;
        }
    }
}
