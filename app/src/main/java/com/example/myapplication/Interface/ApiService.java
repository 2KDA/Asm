package com.example.myapplication.Interface;

import com.example.myapplication.Model.UserData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("users/login")
    Call<UserData> login(@Body UserData userData);
}
