package com.example.thuviensach1.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.thuviensach1.dao.SachDAO;
import com.example.thuviensach1.dao.ThanhVienDAO;
import com.example.thuviensach1.model.PhieuMuon;
import com.example.thuviensach1.model.Sach;
import com.example.thuviensach1.model.ThanhVien;
import com.example.thuviensach1.R;
import java.text.SimpleDateFormat;
import java.util.List;

public class PhieuMuonAdapter extends ArrayAdapter<PhieuMuon>{
    private Context context;
    private int resource;
    private List<PhieuMuon> objects;
    private LayoutInflater inflater;
    ThanhVienDAO thanhVienDAO;
    SachDAO sachDAO;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    public PhieuMuonAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView==null){
            convertView = inflater.inflate(R.layout.item_lv_phieumuon,null);
            holder.tvmapm = (TextView)convertView.findViewById(R.id.item_phieumuon_ma);
            holder.tvtentv = (TextView)convertView.findViewById(R.id.item_phieumuon_tentv);
            holder.tvtensach = (TextView)convertView.findViewById(R.id.item_phieumuon_tensach);
            holder.tvtienthue = (TextView)convertView.findViewById(R.id.item_phieumuon_tienthue);
            holder.tvngay = (TextView)convertView.findViewById(R.id.item_phieumuon_ngaymuon);
            holder.tvtrasach = (TextView)convertView.findViewById(R.id.item_phieumuon_trangthai);

            convertView.setTag(holder);
        }else{
            holder =(ViewHolder) convertView.getTag();
        }
        PhieuMuon obj = objects.get(position);
        holder.tvmapm.setText("MÃ: "+obj.maPM);

        thanhVienDAO = new ThanhVienDAO(context);
        ThanhVien thanhVien = thanhVienDAO.getID(String.valueOf(obj.maTV));
        holder.tvtentv.setText(thanhVien.hoTen);

        sachDAO = new SachDAO(context);
        Sach sach = sachDAO.getID(String.valueOf(obj.maSach));
        holder.tvtensach.setText("SÁCH:  "+sach.tenSach);

        holder.tvtienthue.setText("GIÁ: "+obj.tienThue);
        holder.tvngay.setText(sdf.format(obj.ngay));

        if (obj.traSach==1){
            holder.tvtrasach.setTextColor(Color.BLUE);
            holder.tvtrasach.setText("Đã trả sách");
        }else{
            holder.tvtrasach.setTextColor(Color.RED);
            holder.tvtrasach.setText("Chưa trả sách");
        }

        return convertView;
    }

    public class ViewHolder{
        TextView tvmapm,tvtentv,tvtensach,tvtienthue,tvngay,tvtrasach;
    }
}
