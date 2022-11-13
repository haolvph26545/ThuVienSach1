package com.example.thuviensach1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thuviensach1.adapter.ThuThuAdapter;
import com.example.thuviensach1.dao.PhieuMuonDAO;
import com.example.thuviensach1.dao.ThuThuDAO;
import com.example.thuviensach1.model.PhieuMuon;
import com.example.thuviensach1.model.ThuThu;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class DangKyActivity extends AppCompatActivity {
    ThuThuDAO dao;
    ListView listView;
    ThuThuAdapter thuAdapter;
    List<ThuThu> list;
    ThuThu thuThu;
    int a;
    int temp = 0;


    EditText txtnameuser, txtname, txtpass;
    TextInputLayout tilusername, tilname, tilpass;

    List<PhieuMuon> phieuMuonList;
    PhieuMuonDAO phieuMuonDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        txtnameuser = findViewById(R.id.item_txtnameuserdk);
        txtname = findViewById(R.id.item_txtnamedk);
        txtpass = findViewById(R.id.item_txtpassdk);

        tilusername = findViewById(R.id.add_til_usernamedk);
        tilname = findViewById(R.id.add_til_namedk);
        tilpass = findViewById(R.id.add_til_passdk);

        Button btnadd = findViewById(R.id.dialog_add_adddk);
        Button btncancel = findViewById(R.id.dialog_add_canceldk);

        dao = new ThuThuDAO(DangKyActivity.this);
        btnadd.setOnClickListener(new View.OnClickListener() {
            ThuThu thu = new ThuThu();
            @Override
            public void onClick(View v) {
                validate();
                if(temp == 0) {
                    thu.maTT = txtnameuser.getText().toString();
                    thu.hoTen = txtname.getText().toString();
                    thu.matKhau = txtpass.getText().toString();
                    if (dao.insert(thu) > 0) {
                        String strUser = txtnameuser.getText().toString();
                        Toast.makeText(DangKyActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DangKyActivity.this,MainActivity.class);
                        intent.putExtra("user",strUser);
                        startActivity(intent);
                    }
                }
            }
        });
    }
    private void validate(){
        if(txtnameuser.getText().length()==0){
            tilusername.setError("Mã Thủ Thư không được để trống");
            temp++;
        }else{
            tilusername.setError("");
        }
        if(txtname.getText().length()==0){
            tilname.setError("Tên Thủ Thư không được để trống");
            temp++;
        }else{
            tilname.setError("");
        }
        if(txtpass.getText().length()==0){
            tilpass.setError("Mật khẩu không được để trống");
            temp++;
        }else{
            tilpass.setError("");
        }
    }

}