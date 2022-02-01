package com.example.projectandroid.ApiRest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PaymentTypeApi
{
    @GET("list")
    Call<List<PaymentType>> findAll();
}
