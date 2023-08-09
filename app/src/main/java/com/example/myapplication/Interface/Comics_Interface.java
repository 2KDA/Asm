package com.example.myapplication.Interface;


import com.example.myapplication.Model.Comics;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Comics_Interface {

    @GET("comics")
    Call<List<Comics>> danh_sach_comic();
}
