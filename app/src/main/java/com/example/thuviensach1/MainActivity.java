package com.example.thuviensach1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.thuviensach1.dao.ThuThuDAO;
import com.example.thuviensach1.fragment.DoanhThuFragment;
import com.example.thuviensach1.fragment.LoaiSachFragment;
import com.example.thuviensach1.fragment.MatKhauFragment;
import com.example.thuviensach1.fragment.PhieuThuFragment;
import com.example.thuviensach1.fragment.SachFragment;
import com.example.thuviensach1.fragment.ThanhVienFragment;
import com.example.thuviensach1.fragment.ThemFragment;
import com.example.thuviensach1.fragment.TopFragment;
import com.example.thuviensach1.model.ThuThu;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private FrameLayout frameLayout;

    View view;
    TextView nameuser;
    ThuThu thu;
    ThuThuDAO thuThuDAO;
    List<ThuThu> thuThuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.id_drawerLayout);
        toolbar = findViewById(R.id.id_toolbar);

        thu = new ThuThu();
        thuThuDAO = new ThuThuDAO(getApplicationContext());
        thuThuList = new ArrayList<>();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.opendrawer ,R.string.closedrawer);
        toggle.syncState();
        FragmentManager manager = getSupportFragmentManager();
        PhieuThuFragment phieuThuFragment = new PhieuThuFragment();
        manager.beginTransaction().replace(R.id.id_frameLayout,phieuThuFragment).commit();
        navigationView = findViewById(R.id.id_naviView);
        navigationView.setNavigationItemSelectedListener(this);

        view = navigationView.getHeaderView(0);
        nameuser = view.findViewById(R.id.login_username);

        Intent intent = getIntent();
        String user = intent.getStringExtra("user");
        thuThuList =thuThuDAO.getAll();
        if (user.equalsIgnoreCase("chuongdk")){
            navigationView.getMenu().findItem(R.id.nav_them).setVisible(true);
        }
        for(int i = 0;i<thuThuList.size();i++){
            if (thuThuList.get(i).maTT.equals(user)){
                nameuser.setText("Xin Chao " + thuThuList.get(i).hoTen);
                return;
            }
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_qlphieuthu){
            replaceFragment(PhieuThuFragment.newInstance());
        }else if(id == R.id.nav_qlloaisach){
            replaceFragment(LoaiSachFragment.newInstance());
        }
        else if(id == R.id.nav_qlsach){
            replaceFragment(SachFragment.newInstance());
        }else if(id == R.id.nav_qlthanhvien){
            replaceFragment(ThanhVienFragment.newInstance());
        }else if(id == R.id.nav_top){
            replaceFragment(TopFragment.newInstance());
        }else if(id == R.id.nav_doanhthu){
            replaceFragment(DoanhThuFragment.newInstance());
        }else if(id == R.id.nav_them){
            replaceFragment(ThemFragment.newInstance());
        }else if(id == R.id.nav_matkhau){
            replaceFragment(MatKhauFragment.newInstance());
        }else if(id == R.id.nav_dangxuat){
//            Intent intent = new Intent(MainActivity.this,ManHinhChinhActivity.class);
//            startActivity(intent);
            startActivity(new Intent(this,ManHinhOutActivity.class));
        }
        drawerLayout.closeDrawer(navigationView);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(navigationView)){
            drawerLayout.closeDrawer(navigationView);
        }else {
            super.onBackPressed();
        }
    }
    public void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.id_frameLayout,fragment);
        transaction.commit();
    }

}