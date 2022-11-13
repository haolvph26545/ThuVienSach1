package com.example.thuviensach1.model;

public class Sach {
    public int maSach;
    public String tenSach;
    public int giaThue;
    public int maLoai;
    public  String khuyenMai;


    public Sach() {
    }

    public Sach(int maSach, String tenSach, int giaThue, int maLoai,String khuyenMai) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.giaThue = giaThue;
        this.maLoai = maLoai;
        this.khuyenMai = khuyenMai;
    }
}
