package com.example.thuviensach1.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.thuviensach1.R;
import com.example.thuviensach1.model.ThanhVien;

import java.util.List;

public class ThanhVienAdapter extends ArrayAdapter<ThanhVien> {
    private Context context;
    private int resource;
    private List<ThanhVien> objects;
    private LayoutInflater inflater;

    public ThanhVienAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView==null){
            convertView = inflater.inflate(R.layout.item_lv_addtv,null);
            holder.tvmatv = (TextView)convertView.findViewById(R.id.item_lv_username);
            holder.tvtentv = (TextView)convertView.findViewById(R.id.item_lv_name);
            holder.tvnamsinhtv = (TextView)convertView.findViewById(R.id.item_lv_pass);

            holder.temp1 = (TextView)convertView.findViewById(R.id.temp_1);
            holder.temp2 = (TextView)convertView.findViewById(R.id.temp_2);
            holder.temp3 = (TextView)convertView.findViewById(R.id.temp_3);

            convertView.setTag(holder);
        }else{
            holder =(ViewHolder) convertView.getTag();
        }
        ThanhVien tv = objects.get(position);



            if (tv.maTV%2==0){
                holder.tvmatv.setTextColor(ContextCompat.getColor(context,R.color.green));
                holder.tvtentv.setTextColor(ContextCompat.getColor(context,R.color.green));
                holder.tvnamsinhtv.setTextColor(ContextCompat.getColor(context,R.color.green));
                holder.temp1.setTextColor(ContextCompat.getColor(context,R.color.green));
                holder.temp2.setTextColor(ContextCompat.getColor(context,R.color.green));
                holder.temp3.setTextColor(ContextCompat.getColor(context,R.color.green));
            }
            else {
                holder.tvmatv.setTextColor(ContextCompat.getColor(context,R.color.red));
                holder.tvtentv.setTextColor(ContextCompat.getColor(context,R.color.red));
                holder.tvnamsinhtv.setTextColor(ContextCompat.getColor(context,R.color.red));
                holder.temp1.setTextColor(ContextCompat.getColor(context,R.color.red));
                holder.temp2.setTextColor(ContextCompat.getColor(context,R.color.red));
                holder.temp3.setTextColor(ContextCompat.getColor(context,R.color.red));
            }





        holder.tvmatv.setText(String.valueOf(tv.maTV));
        holder.tvtentv.setText(tv.hoTen);
        holder.tvnamsinhtv.setText(tv.namSinh);

        holder.temp1.setText("Mã Thành Viên: ");
        holder.temp2.setText("Tên Thành Viên: ");
        holder.temp3.setText("Năm Sinh: ");

        return convertView;
    }

    public class ViewHolder{
        TextView tvmatv,tvtentv,tvnamsinhtv,temp1,temp2,temp3;
    }
}
