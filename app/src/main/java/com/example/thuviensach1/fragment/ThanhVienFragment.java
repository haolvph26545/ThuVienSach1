package com.example.thuviensach1.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.thuviensach1.R;
import com.example.thuviensach1.adapter.ThanhVienAdapter;
import com.example.thuviensach1.dao.PhieuMuonDAO;
import com.example.thuviensach1.dao.ThanhVienDAO;
import com.example.thuviensach1.model.PhieuMuon;
import com.example.thuviensach1.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThanhVienFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThanhVienFragment extends Fragment {

    FloatingActionButton fab;
    ThanhVienDAO dao;
    ListView listView;
    List<ThanhVien> list;
    ThanhVien thanhVien;
    ThanhVienAdapter thanhVienAdapter;
    int a;
    int temp=0;

    EditText txtnameuser, txtname, txtpass;
    TextInputLayout tilusername, tilname,tilpass;

    List<PhieuMuon> phieuMuonList;
    PhieuMuonDAO phieuMuonDAO;

    public ThanhVienFragment() {
        // Required empty public constructor
    }

    public static ThanhVienFragment newInstance() {
        ThanhVienFragment fragment = new ThanhVienFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thanh_vien, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.qlthanhvien_listview);

        EditText ed_tim = view.findViewById(R.id.ed_tim);
        Button btn_tim = view.findViewById(R.id.btn_tim);

        btn_tim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list = dao.getListByUser(ed_tim.getText().toString());
                thanhVienAdapter = new ThanhVienAdapter(getActivity(),R.layout.item_lv_addtv,list);
                listView.setAdapter(thanhVienAdapter);
            }
        });



        phieuMuonList = new ArrayList<>();
        phieuMuonDAO = new PhieuMuonDAO(getActivity());

        loadTable();
        view.findViewById(R.id.qlthanhvien_fab).setOnClickListener(v -> {
            a = -1;
            openDialog(Gravity.CENTER);
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                a = position;
                openDialog(Gravity.CENTER);
            }
        });

    }
    private void loadTable(){
        dao = new ThanhVienDAO(getActivity());
        list = dao.getAll();
        thanhVienAdapter = new ThanhVienAdapter(getActivity(),R.layout.item_lv_addtv,list);
        listView.setAdapter(thanhVienAdapter);
    }
    private void openDialog(int gravity){

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_themtt);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if(Gravity.CENTER == gravity){
            dialog.setCancelable(true);
        }else{
            dialog.setCancelable(false);
        }

        TextView tvTile = (TextView) dialog.findViewById(R.id.item_tvtile);

        txtnameuser = dialog.findViewById(R.id.item_txtnameuser);
        txtname = dialog.findViewById(R.id.item_txtname);
        txtpass = dialog.findViewById(R.id.item_txtpass);

        tilusername = dialog.findViewById(R.id.add_til_username);
        tilname = dialog.findViewById(R.id.add_til_name);
        tilpass = dialog.findViewById(R.id.add_til_pass);

        Button btnadd = dialog.findViewById(R.id.dialog_add_add);
        Button btncancel = dialog.findViewById(R.id.dialog_add_cancel);

        dao = new ThanhVienDAO(getActivity());
        txtpass.setInputType(InputType.TYPE_CLASS_NUMBER);

        if (a==-1){
            tvTile.setText("TH??M TH??NH VI??N");

            tilusername.setHint("M?? Th??nh Vi??n");
            tilname.setHint("T??n Th??nh Vi??n");
            tilpass.setHint("N??m Sinh Th??nh Vi??n");

            txtnameuser.setEnabled(false);

            if (list.size()==0){
                txtnameuser.setText("1");
            }else {
                thanhVien = dao.getAll().get(list.size() - 1);
                txtnameuser.setText(String.valueOf(thanhVien.maTV + 1));
            }

            btnadd.setOnClickListener(new View.OnClickListener() {
                ThanhVien thanhVien = new ThanhVien();
                @Override
                public void onClick(View view) {
                    validate();
                    if (temp==0){

                        thanhVien.hoTen = txtname.getText().toString();
                        thanhVien.namSinh = txtpass.getText().toString();


                        int lastSpaceIndex = txtname.getText().toString().lastIndexOf(" ");
                        String firstName = txtname.getText().toString().substring(lastSpaceIndex + 1);
                        if (!firstName.matches("^[A-Z].*")) {
                            txtname.setError("Ch??? c??i ?????u ph???i vi???t hoa");
                        }else
                        if (txtname.getText().toString().length()<5){
                            txtname.setError("T??n kh??ng d?????i 5 k?? t???");
                        }else if (txtname.getText().toString().length()>15){
                            txtname.setError("T??n kh??ng tr??n 15 k?? t???");
                        }else{
                            if (dao.insert(thanhVien)>0){
                                Toast.makeText(getActivity(), "Th??m th??nh c??ng", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                loadTable();
                            }else{
                                Toast.makeText(getActivity(), "Th??m th???t b???i", Toast.LENGTH_SHORT).show();
                            }
                        }



                    }else {
                        temp=0;
                    }

                }
            });
            btncancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), "Hu??? th??m", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
        }else{
            tvTile.setText("S???a/X??a th??nh vi??n");

            tilusername.setHint("M?? Th??nh Vi??n");
            tilname.setHint("T??n Th??nh Vi??n");
            tilpass.setHint("N??m Sinh Th??nh Vi??n");

            btnadd.setText("S???a");
            btncancel.setText("Xo??");

            thanhVien = dao.getAll().get(a);

            txtnameuser.setText(String.valueOf(thanhVien.maTV));
            txtnameuser.setEnabled(false);
            txtname.setText(thanhVien.hoTen);
            txtpass.setText(thanhVien.namSinh);

            btnadd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    validate();
                    if (temp==0){
                        thanhVien = new ThanhVien();
                        thanhVien.maTV = Integer.parseInt(txtnameuser.getText().toString());
                        thanhVien.hoTen = txtname.getText().toString();
                        thanhVien.namSinh = txtpass.getText().toString();
                        if (dao.update(thanhVien)<0){
                            Toast.makeText(getActivity(), "S???a th???t b???i", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getActivity(), "S???a th??nh c??ng", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            loadTable();
                        }
                    }else {
                        temp=0;
                    }
                }
            });
            btncancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    phieuMuonList = phieuMuonDAO.getAll();

                    for (int i = 0; i <phieuMuonList.size(); i++) {
                        if (phieuMuonList.get(i).maTV == thanhVien.maTV){
                            temp++;
                            Toast.makeText(getActivity(), "Kh??ng th??? xo?? th??nh vi??n c?? trong phi???u m?????n", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                    if (temp==0){
                        if (dao.delete(String.valueOf(thanhVien.maTV))<0){
                            Toast.makeText(getActivity(), "Xo?? th???t b???i", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getActivity(), "Xo?? th??nh c??ng", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            loadTable();
                        }
                    }
                }
            });
        }
        dialog.show();
    }

    private void validate(){
        if(txtname.getText().length()==0){
            tilname.setError("T??n th??nh vi??n kh??ng ???????c ????? tr???ng");
            temp++;
        }else{
            tilname.setError("");
        }
        if(txtpass.getText().length()==0){
            tilpass.setError("N??m Sinh kh??ng ???????c ????? tr???ng");
            temp++;
        }else{
            tilpass.setError("");
        }
    }
}