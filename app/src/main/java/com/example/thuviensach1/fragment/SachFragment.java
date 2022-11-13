package com.example.thuviensach1.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.thuviensach1.R;
import com.example.thuviensach1.adapter.SachAdapter;
import com.example.thuviensach1.adapter.SpinnerLoaiSachAdapter;
import com.example.thuviensach1.dao.LoaiSachDAO;
import com.example.thuviensach1.dao.PhieuMuonDAO;
import com.example.thuviensach1.dao.SachDAO;
import com.example.thuviensach1.model.LoaiSach;
import com.example.thuviensach1.model.PhieuMuon;
import com.example.thuviensach1.model.Sach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SachFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SachFragment extends Fragment {

    FloatingActionButton fab;
    SachDAO dao;
    ListView listView;
    SachAdapter sachAdapter;
    List<Sach> list;
    Sach sach;
    int a;
    int temp=0;
    String maloaisach;

    List<LoaiSach> ListMaLoai;
    LoaiSachDAO loaiSachDAO;
    SpinnerLoaiSachAdapter spinnerLoaiSachAdapter;

    EditText txtmasach, txtname, txtgiathue,txtkm;
    TextInputLayout tilmasach, tilname, tilgiathue, tilkm;

    List<PhieuMuon> phieuMuonList;
    PhieuMuonDAO phieuMuonDAO;

    public SachFragment() {
        // Required empty public constructor
    }


    public static SachFragment newInstance() {
        SachFragment fragment = new SachFragment();
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
        return inflater.inflate(R.layout.fragment_sach, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.sach_listview);
        ListMaLoai = new ArrayList<>();
        loaiSachDAO = new LoaiSachDAO(getActivity());
        ListMaLoai = loaiSachDAO.getAll();

        phieuMuonList = new ArrayList<>();
        phieuMuonDAO = new PhieuMuonDAO(getActivity());

        loadTable();

        view.findViewById(R.id.sach_fab).setOnClickListener(v -> {
            a = -1;
            if (ListMaLoai.size() == 0){
                Toast.makeText(getActivity(), "Ban chua them loai sach", Toast.LENGTH_SHORT).show();
            }else{
                openDialog(Gravity.CENTER);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                a = position;
                openDialog(Gravity.CENTER);
            }
        });
    }

    private void openDialog(int gravity) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_themsach);

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

        TextView tvTile = (TextView) dialog.findViewById(R.id.dialog_sach_tile);


        txtmasach = dialog.findViewById(R.id.dialog_sach_txtmasach);
        txtname = dialog.findViewById(R.id.dialog_sach_txtname);
        txtgiathue = dialog.findViewById(R.id.dialog_sach_txtgiathue);
        txtkm = dialog.findViewById(R.id.dialog_sach_txtkm);
        Spinner spinner = (Spinner) dialog.findViewById(R.id.dialog_spn_loaisach);

        tilmasach = dialog.findViewById(R.id.dialog_sach_til_masach);
        tilname = dialog.findViewById(R.id.dialog_sach_til_name);
        tilgiathue = dialog.findViewById(R.id.dialog_sach_til_giathue);
        tilkm = dialog.findViewById(R.id.dialog_sach_til_km);


        Button btnadd = dialog.findViewById(R.id.dialog_sach_add);
        Button btncancel = dialog.findViewById(R.id.dialog_sach_cancel);

        txtgiathue.setInputType(InputType.TYPE_CLASS_NUMBER);


        spinnerLoaiSachAdapter = new SpinnerLoaiSachAdapter(getContext(), (ArrayList<LoaiSach>) loaiSachDAO.getAll());

        spinner.setAdapter(spinnerLoaiSachAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                maloaisach = String.valueOf(ListMaLoai.get(i).maLoai);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        dao = new SachDAO(getActivity());

        if (a==-1){
            tilmasach.setEnabled(false);

            if (list.size()==0){
                txtmasach.setText("1");
            }else {
                sach = dao.getAll().get(list.size() - 1);
                txtmasach.setText(String.valueOf(sach.maSach + 1));
            }

            btnadd.setOnClickListener(new View.OnClickListener() {
                Sach sach = new Sach();
                @Override
                public void onClick(View view) {
                    validate();
                    if (temp==0){
                        sach.tenSach = txtname.getText().toString();
                        sach.giaThue = Integer.parseInt(txtgiathue.getText().toString());
                        sach.maLoai = Integer.parseInt(maloaisach);
                        sach.khuyenMai = txtkm.getText().toString();

                        if (dao.insert(sach)>0){
                            Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            loadTable();
                        }else{
                            Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        temp=0;
                    }

                }
            });
            btncancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), "Huỷ thêm", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
        }else{
            sach = dao.getAll().get(a);

            tvTile.setText("SỬA/XOÁ SÁCH");
            btnadd.setText("Sửa");
            btncancel.setText("Xoá");

            txtmasach.setText(String.valueOf(sach.maSach));
            txtmasach.setEnabled(false);
            txtname.setText(sach.tenSach);
            txtgiathue.setText(String.valueOf(sach.giaThue));
            txtkm.setText(sach.khuyenMai);

            for (int i=0;i<spinner.getCount();i++){
                if (list.get(a).maLoai == ListMaLoai.get(i).maLoai){
                    spinner.setSelection(i);
                }
            }


            btnadd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (temp==0){
                        sach = new Sach();
                        sach.maSach = Integer.parseInt(txtmasach.getText().toString());
                        sach.tenSach = txtname.getText().toString();
                        sach.giaThue = Integer.parseInt(txtgiathue.getText().toString());
                        sach.maLoai = Integer.parseInt(maloaisach);
                        sach.khuyenMai = txtkm.getText().toString();
                        if (dao.update(sach)<0){
                            Toast.makeText(getActivity(), "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getActivity(), "Sửa thành công", Toast.LENGTH_SHORT).show();
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
                        if (phieuMuonList.get(i).maSach == sach.maSach){
                            temp++;
                            Toast.makeText(getActivity(), "Không thể xoá sách có trong phiếu mượn", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                    if (temp==0){
                        if (dao.delete(String.valueOf(sach.maSach))<0){
                            Toast.makeText(getActivity(), "Xoá thất bại", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getActivity(), "Xoá thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            loadTable();
                        }
                    }

                }
            });
        }

        dialog.show();
    }

    private void loadTable() {
        dao = new SachDAO(getActivity());
        list = dao.getAll();
        sachAdapter = new SachAdapter(getActivity(),R.layout.item_lv_addtv,list);
        listView.setAdapter(sachAdapter);
    }
    private void validate(){
        if(txtname.getText().length()==0){
            tilname.setError("Tên sách không được để trống");
            temp++;
        }else{
            tilname.setError("");
        }
        if(txtgiathue.getText().length()==0){
            tilgiathue.setError("Giá thuế không được để trống");
            temp++;
        }else{
            tilgiathue.setError("");
        }
    }
}