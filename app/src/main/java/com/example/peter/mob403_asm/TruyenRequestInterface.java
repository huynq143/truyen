package com.example.peter.mob403_asm;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface TruyenRequestInterface {

    String BASE_URL = "https://androidnetworking.herokuapp.com/api/";

    @GET("danhsachtruyen")
    Call<ArrayList<TruyenItem>> getJSON();

    @GET("truyen/{id}")
    Call<TruyenItem> getTruyenTheoId(@Path("id")String id);

    @GET("danhsachtheloai")
    Call<ArrayList<TheLoaiItem>> getTheLoai();

    @GET("danhsachchuongtheoidtruyen/{id}")
    Call<ArrayList<ChuongItem>> getChuongTheoId(@Path("id")String id);

    @GET("soluongtrang")
    Call<String> getSoLuongTrang();

    @GET("truyentheoloai/{id}")
    Call<ArrayList<TruyenItem>> getTruyenTheoTheLoai(@Path("id")String id);

    @GET("danhsachtruyennhohon/{id}")
    Call<ArrayList<TruyenItem>> getTruyenIdNhoHon(@Path("id")String id);

    @GET("danhsachtruyentheoten/{ten}")
    Call<ArrayList<TruyenItem>> getTruyenTheoTen(@Path("ten")String ten);

//    @FormUrlEncoded
//    @POST("register")
//    Call<ProfileItem> register(
//            @Field("email") String email,
//            @Field("password") String password
//    );

    @Headers("Content-Type: application/json")
    @POST("register")
    Call<ProfileItem> register(@Body String userRequest);

    @Headers("Content-Type: application/json")
    @POST("login")
    Call<ProfileItem> login(@Body String userRequest);

    @GET("validateToken")
    Call<ProfileItem> getLoginResponse(@Header("Authorization") String token);

    @GET("themTruyenVaoGio/{idUser}/{idTruyen}")
    Call<ArrayList<TruyenItem>> themTruyenVaoGio(@Path("idUser")String idUser, @Path("idTruyen") String idTruyen);
}
