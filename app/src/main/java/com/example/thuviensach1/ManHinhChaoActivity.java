package com.example.thuviensach1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.thuviensach1.dao.ThuThuDAO;
import com.example.thuviensach1.model.ThuThu;

import java.util.ArrayList;
import java.util.List;

public class ManHinhChaoActivity extends AppCompatActivity {
    ConstraintLayout layout;
    ThuThuDAO dao;
    List<ThuThu> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chao);
        layout = findViewById(R.id.id_constraintlayout);

        dao = new ThuThuDAO(ManHinhChaoActivity.this);
        list = new ArrayList<>();
        list = dao.getAll();

        if (list.size() == 0){
            dao.insertadmin();
        }
        
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                layout.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(ManHinhChaoActivity.this,ManHinhChinhActivity.class);
                startActivity(intent);
            }
        },3000);
    }
}