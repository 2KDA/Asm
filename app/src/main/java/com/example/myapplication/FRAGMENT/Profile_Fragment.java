package com.example.myapplication.FRAGMENT;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.Login;
import com.example.myapplication.SignUp;
import com.example.myapplication.Model.UserData;
import com.example.myapplication.R;


public class Profile_Fragment extends Fragment {
    private UserData userData;

    private Button btn_login;
    private Button btn_sigup, btn_td;
    private TextView txtname,txtid;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile_, container, false);

        // Lấy dữ liệu truyền vào từ ViewPager2Adapter
        if (getArguments() != null) {
            userData = getArguments().getParcelable("userData");
            if (userData != null) {
                String username = userData.getUsername();
                String userId = userData.getId();
                // Tiếp tục xử lý dữ liệu username và userId theo ý muốn

            }
        }

        // Tiếp tục cài đặt Fragment theo ý muốn
        anhXa();
        return view;
    }

    public static Profile_Fragment newInstance(UserData userData) {
        Profile_Fragment fragment = new Profile_Fragment();
        Bundle args = new Bundle();
        args.putParcelable("userData", userData);
        fragment.setArguments(args);
        return fragment;
    }

    private void anhXa() {
        //btn
        btn_login = view.findViewById(R.id.btn_login);
        btn_td = view.findViewById(R.id.btn_td);
        txtname = view.findViewById(R.id.nameText);
        txtid = view.findViewById(R.id.idUser);
        btn_sigup = view.findViewById(R.id.btn_signup);

        // Kiểm tra nếu userData không null thì hiển thị chào mừng và ẩn nút đăng nhập và đăng ký
        if (userData != null && userData.getUsername() != null) {
            txtname.setText("Xin chào " + userData.getUsername());
            txtid.setText("Xin chào " + userData.getId());
            btn_login.setVisibility(View.GONE);
            btn_sigup.setVisibility(View.GONE);
        } else {
            txtname.setText("Bạn chưa đăng nhập");
            btn_td.setVisibility(View.GONE);
        }
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Login.class));
            }
        });
        btn_sigup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SignUp.class));
            }
        });
        btn_td.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Login.class));
            }
        });
    }
}