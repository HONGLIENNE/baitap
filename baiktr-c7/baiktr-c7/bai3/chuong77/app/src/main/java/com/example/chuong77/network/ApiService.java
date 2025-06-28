package com.example.chuong77.network;

import com.example.chuong77.model.User;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("users")
    Call<List<User>> getUsers();
}