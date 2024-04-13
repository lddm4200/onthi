package com.example.onthi.services;

import com.example.onthi.Modal.Response;
import com.example.onthi.Modal.XeMay;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    String DOMAIN = "http://192.168.1.5:3000/api/";

    ApiService apiService  = new Retrofit.Builder()
            .baseUrl(ApiService.DOMAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService.class);
    @GET("list")
    Call<Response<List<XeMay>>> getData();
    
    @Multipart
    @POST("add")
    Call<Response<XeMay>> addStudent(
            @Part("ten_xe_ph42469") RequestBody ten_xe_ph42469,
            @Part("mau_sac_ph42469") RequestBody mau_sac_ph42469,
            @Part("gia_ban_ph42469") RequestBody gia_ban_ph42469,
            @Part("mo_ta_ph42469") RequestBody mo_ta_ph42469,
            @Part MultipartBody.Part hinh_anh_ph42469);

    @DELETE("delete/{id}")
    Call<Response<XeMay>> deleteStudent(@Path("id") String idStudent);

    @Multipart
    @PUT("update/{id}")
    Call<Response<XeMay>> updateStudent(
            @Path("id") String idStudent,
            @Part("ten_xe_ph42469") RequestBody ten_xe_ph42469,
            @Part("mau_sac_ph42469") RequestBody mau_sac_ph42469,
            @Part("gia_ban_ph42469") RequestBody gia_ban_ph42469,
            @Part("mo_ta_ph42469") RequestBody mo_ta_ph42469,
            @Part MultipartBody.Part hinh_anh_ph42469);


    @GET("search")
    Call<Response<List<XeMay>>> searchStudent(@Query("key") String key);

    @GET("sort")
    Call<Response<List<XeMay>>> sortStudent(@Query("type") Integer type);
}
