package com.example.thuviensach1.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    static final String dbName = "PNLIB";
    static final int dbVersion = 4;
    public DbHelper( Context context) {
        super(context, dbName, null, dbVersion);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableThuThu = "create table ThuThu (" +
                "maTT TEXT PRIMARY KEY," +
                "hoTen TEXT NOT NULL," +
                "matKhau TEXT NOT NULL )";
        db.execSQL(createTableThuThu);

        String insert_tt = "Insert into ThuThu(maTT,hoTen,matKhau) values ('hihi','haole','hihi'),('admin','ADMIN','admin')";
        db.execSQL(insert_tt);

        String createTableThanhVien = "create table ThanhVien (" +
                "maTV INTEGER PRIMARY KEY AUTOINCREMENT," +
                "hoTen TEXT NOT NULL," +
                "namSinh TEXT NOT NULL )";
        db.execSQL(createTableThanhVien);

        String insert_tv = "Insert into ThanhVien(hoTen,namSinh) values ('Đặng Chương','2003'),('Đặng Chương Đẹp trai','2003')";
        db.execSQL(insert_tv);

        String createTableLoaiSach = "create table LoaiSach (" +
                "maLoai INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nhaSX TEXT NOT NULL ,"+
                "tenLoai TEXT NOT NULL )";
        db.execSQL(createTableLoaiSach);

        String insert_ls = "Insert into LoaiSach(nhaSX,tenLoai) values ('Đặng Chương','Truyện Cười'),('Đặng Chương Đẹp trai','Truyện Ma')";
        db.execSQL(insert_ls);

        String createTableSach = "create table Sach (" +
                "maSach INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenSach TEXT NOT NULL ," +
                "giaThue INTEGER NOT NULL ," +
                "maLoai INTEGER REFERENCES LoaiSach(maLoai) ,"+
                "khuyenMai TEXT NOT NULL)";
        db.execSQL(createTableSach);

        String insert_sach = "Insert into Sach(tenSach,giaThue,maLoai,khuyenMai) values ('Le Hao','2003',1,'10%'),('Le Hao Đẹp trai','2003',2,'5%')";
        db.execSQL(insert_sach);

        String createTablePhieuMuon = "create table PhieuMuon (" +
                "maPM INTEGER PRIMARY KEY AUTOINCREMENT," +
                "maTT TEXT REFERENCES ThuThu(maTT) ," +
                "maTV INTEGER REFERENCES ThanhVien(maTV) ," +
                "maSach INTEGER REFERENCES Sach(maSach) ," +
                "tienThue INTEGER NOT NULL ," +
                "ngay DATE NOT NULL ,"+
                "traSach INTEGER NOT NULL)";
        db.execSQL(createTablePhieuMuon);

        String insert_pm = "Insert into PhieuMuon(maTT,maTV,maSach,tienThue,ngay,traSach) values ('hihi',1,1,500000,'2003-11-20',1),('admin',2,2,3545,'2003-11-11',0)";
        db.execSQL(insert_pm);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS ThuThu");
        db.execSQL("DROP TABLE IF EXISTS ThanhVien");
        db.execSQL("DROP TABLE IF EXISTS LoaiSach");
        db.execSQL("DROP TABLE IF EXISTS Sach");
        db.execSQL("DROP TABLE IF EXISTS PhieuMuon");

        onCreate(db);
    }
}
