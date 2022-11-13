package com.example.thuviensach1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thuviensach1.dao.ThuThuDAO;
import com.google.android.material.textfield.TextInputLayout;

public class ManHinhChinhActivity extends AppCompatActivity {
    EditText edUserName,edPassword;
    CheckBox chkPass;
    String strUser,strPass;
    ThuThuDAO dao;
    TextInputLayout tilpass,tiluser;
    int temp = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chinh);
        edUserName = findViewById(R.id.login_edusername);
        edPassword = findViewById(R.id.login_edpassword);
        chkPass = findViewById(R.id.login_checkBox);
        tilpass = findViewById(R.id.login_tilpassword);
        tiluser = findViewById(R.id.login_tilusername);
        dao = new ThuThuDAO(this);
        SharedPreferences preferences = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        edUserName.setText(preferences.getString("USERNAME",""));
        edPassword.setText(preferences.getString("PASSWORD",""));
        chkPass.setChecked(preferences.getBoolean("REMEMBER",false));

        findViewById(R.id.login_btnlogin).setOnClickListener(v -> {
            checkLogin();
        });
    }
    public void checkLogin(){
        strUser = edUserName.getText().toString();
        strPass =edPassword.getText().toString();
        if (strUser.isEmpty()){
            tiluser.setError("Ten dang nhap khong duoc de trong");
            temp++;
        }else{
            tiluser.setError("");
        }
        if(strPass.isEmpty()){
            tilpass.setError("Mat khau khong duoc de trong");
            temp++;
        }else{
            tilpass.setError("");
        }
        if (temp ==0){
            if (dao.checkLogin(strUser,strPass) > 0){
                tiluser.setError("");
                tilpass.setError("");
                Toast.makeText(this, "Login thanh cong", Toast.LENGTH_SHORT).show();
                rememberUser(strUser,strPass,chkPass.isChecked());

                Intent intent = new Intent(ManHinhChinhActivity.this,MainActivity.class);
                intent.putExtra("user",strUser);
                intent.putExtra("pass",strPass);
                startActivity(intent);
                finish();
            }else{
                tiluser.setError("Ten dang nhap khong dung");
                tilpass.setError("Mat khau khong dung");
            }
        }else {
            temp =0;
        }
    }

    public void rememberUser(String u,String p,boolean status){
        SharedPreferences preferences = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (!status){
            editor.clear();
        }else{
            editor.putString("USERNAME",u);
            editor.putString("PASSWORD", p);
            editor.putBoolean("REMEMBER",status);
        }
        editor.commit();
    }

}