package com.example.myapplication.FRAGMENT;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.Adapter.viewpager2.CommentAdapter;
import com.example.myapplication.Interface.CommentInterface;
import com.example.myapplication.Model.Comics;
import com.example.myapplication.Model.Comment;
import com.example.myapplication.R;



import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class fragment_gt extends Fragment {
    private Comics commic; // Thêm biến để lưu thông tin Commic
    private View view; // Khai báo biến view ở đây

    public fragment_gt() {
        // Required empty public constructor
    }

    public static fragment_gt newInstance(Comics commic) {
        fragment_gt fragment = new fragment_gt();
        Bundle args = new Bundle();
        args.putParcelable("commic", (Parcelable) commic); // Đặt Commic vào arguments
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            commic = getArguments().getParcelable("commic"); // Nhận Commic từ arguments
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_gt, container, false);
        TextView txtDescription = view.findViewById(R.id.txtDescription);
        TextView txtAuther = view.findViewById(R.id.txtAuther);
        TextView txtYear = view.findViewById(R.id.txtYear);




        if (commic != null) {
            // Hiển thị thông tin Commic lên TextView
            txtAuther.setText("Tác giả :" + commic.getName_author());
            txtYear.setText("Năm xuất bản :  " + commic.getYear() );
            txtDescription.setText("Nội dung : "+ commic.getDesc());
        }

        return view;
    }


}