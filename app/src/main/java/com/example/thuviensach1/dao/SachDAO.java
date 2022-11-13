package com.example.thuviensach1.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.thuviensach1.database.DbHelper;
import com.example.thuviensach1.model.Sach;

import java.util.ArrayList;
import java.util.List;

public class SachDAO {
    private SQLiteDatabase db;

    public SachDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }


    public long insert(Sach odj){
        ContentValues values = new ContentValues();
        values.put("tenSach",odj.tenSach);
        values.put("giaThue",odj.giaThue);
        values.put("maLoai",odj.maLoai);
        values.put("khuyenMai",odj.khuyenMai);
        return db.insert("Sach",null,values);
    }

    public long update(Sach odj){
        ContentValues values = new ContentValues();
        values.put("tenSach",odj.tenSach);
        values.put("giaThue",odj.giaThue);
        values.put("maLoai",odj.maLoai);
        values.put("khuyenMai",odj.khuyenMai);
        return db.update("Sach",values,"maSach=?",new String[]{String.valueOf(odj.maSach)});
    }

    public int delete(String id){
        return db.delete("Sach","maSach=?",new String[]{id});
    }

    public List<Sach> getAll(){
        String sql = "SELECT * FROM Sach";
        return getData(sql);
    }

    public Sach getID(String id){
        String sql = "SELECT * FROM Sach WHERE maSach=?";
        List<Sach> list = getData(sql,id);
        return list.get(0);
    }

    private List<Sach> getData(String sql, String...selectionArgs){
        List<Sach> list = new ArrayList<Sach>();
        Cursor c = db.rawQuery(sql,selectionArgs);
        while (c.moveToNext()){
            Sach obj = new Sach();
            obj.maSach = Integer.parseInt(c.getString(0));
            obj.tenSach = c.getString(1);
            obj.giaThue = Integer.parseInt(c.getString(2));
            obj.maLoai = Integer.parseInt(c.getString(3));
            obj.khuyenMai = c.getString(4);
            list.add(obj);
        }
        return list;
    }
}
