package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Adapter.viewpager2.TabsPagerAdapter;
import com.example.myapplication.FRAGMENT.fragment_gt;
import com.example.myapplication.FRAGMENT.fragment_ml;
import com.example.myapplication.Model.Comics;
import com.example.myapplication.Model.UserData;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Chitiet_Comics extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabsPagerAdapter tabsPagerAdapter;
    private Comics comic;
    private UserData userData;
    List<UserData> userDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet_comics);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        TextView txtname = findViewById(R.id.name);
        ImageView avt = findViewById(R.id.avt);
        Button btnread = findViewById(R.id.btnread);
        comic = getIntent().getParcelableExtra("COMIC_EXTRA");
        userData = getIntent().getParcelableExtra("USER_DATA_EXTRA");
        if (comic != null) {

            // Hiển thị chi tiết thông tin Commic
            // Ví dụ: gán giá trị cho các TextView
            txtname.setText(comic.getName());

            Picasso.get().load(comic.getImg()).into(avt);
            Log.d("zzzzzzzz", " aa" + comic.getImg());

        }
        btnread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Chitiet_Comics.this, ReadComics.class);
                // Đính kèm thông tin comic vào Intent
                intent.putExtra("COMIC_EXTRA", comic);
                startActivity(intent);
            }
        });
        // Khởi tạo và cấu hình Adapter cho ViewPager
        tabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        fragment_gt fragmentGT = fragment_gt.newInstance(comic); // Truyền Commic vào FragmentGT
        fragment_ml fragmentML = fragment_ml.newInstance(comic, userData, userDataList);
        tabsPagerAdapter.addFragment(fragmentGT, "Giới Thiệu");
        tabsPagerAdapter.addFragment(fragmentML, "Bình luận");
        viewPager.setAdapter(tabsPagerAdapter);

        // Kết nối ViewPager với TabLayout
        tabLayout.setupWithViewPager(viewPager);
    }
}