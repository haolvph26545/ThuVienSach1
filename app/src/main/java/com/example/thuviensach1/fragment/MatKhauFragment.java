package com.example.thuviensach1.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.thuviensach1.ManHinhChinhActivity;
import com.example.thuviensach1.R;
import com.example.thuviensach1.dao.ThuThuDAO;
import com.example.thuviensach1.model.ThuThu;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MatKhauFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MatKhauFragment extends Fragment {
    EditText oldpass,newpass,newpasscheck;

    ThuThuDAO dao;
    TextInputLayout tilOldpass,tilNewpass,tilNewpasscheck;
    public MatKhauFragment() {
        // Required empty public constructor
    }


    public static MatKhauFragment newInstance() {
        MatKhauFragment fragment = new MatKhauFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mat_khau,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        oldpass = view.findViewById(R.id.pass_oldpass);
        newpass = view.findViewById(R.id.pass_newpass);
        newpasscheck = view.findViewById(R.id.pass_newpasscheck);

        tilOldpass = view.findViewById(R.id.pass_tilOldpass);
        tilNewpass = view.findViewById(R.id.pass_tilnewpass);
        tilNewpasscheck = view.findViewById(R.id.pass_tilnewpasscheck);

        dao = new ThuThuDAO(getActivity());

        view.findViewById(R.id.pass_btncancel).setOnClickListener(v -> {
            oldpass.setText("");
            newpass.setText("");
            newpasscheck.setText("");


        });

        view.findViewById(R.id.pass_btnsave).setOnClickListener(v -> {
            SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
            String user = pref.getString("USERNAME","");
            if (user.length() == 0){
                Intent intent1 = getActivity().getIntent();
                String user1 = intent1.getStringExtra("user");
                user = user1;
            }
            if (validate() > 0){
                ThuThu thu = dao.getID(user);
                thu.matKhau = newpass.getText().toString();
                dao.update(thu);
                if (dao.update(thu) > 0){
                    Toast.makeText(getActivity(), "Thay doi mat khau thanh cong", Toast.LENGTH_SHORT).show();
                    oldpass.setText("");
                    newpass.setText("");
                    newpasscheck.setText("");
                    Intent intent = new Intent(getActivity(), ManHinhChinhActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(), "Thay doi mat khau that bai", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public int validate(){
        int check = 1;
        int temp = 0;
        if (oldpass.getText().length() == 0){
            tilOldpass.setError("Mat khau cu khong duoc de trong");
            temp++;
            check = -1;
        }else{
            tilOldpass.setError("");
        }

        if (newpass.getText().length() == 0){
            tilNewpass.setError("Mat khau moi khong duoc de trong");
            temp++;
            check = -1;
        }else{
            tilNewpass.setError("");
        }

        if (newpasscheck.getText().length() == 0){
            tilNewpasscheck.setError("Nhap lai mat khau moi khong de trong");
            temp++;
            check = -1;
        }else {
            tilNewpasscheck.setError("");
        }
        if (temp == 0){
            SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE",Context.MODE_PRIVATE);
            String oldPasscheck = pref.getString("PASSWORD","");
            if (oldPasscheck.length() == 0){
                Intent intent = getActivity().getIntent();
                String pass = intent.getStringExtra("pass");
                oldPasscheck = pass;
            }
            String newPass = newpass.getText().toString();
            String reNewpass = newpasscheck.getText().toString();
            if (!oldPasscheck.equals(oldpass.getText().toString())){
                tilOldpass.setError("Mat khau cu sai");
                check = -1;
            }
            if (!newPass.equals(reNewpass)){
                tilNewpasscheck.setError("Mat khau khong trung khop");
                check = -1;
            }
        }else {
            temp = 0;
        }
        return  check;
    }
}
// style="@style/Widget.MaterialComponents.TextInputLayout.FilledBox.Dense"