package com.example.oneshop;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JsonPlaceHolder {


    @GET("getShopData.php")
    Call<List<fetchRecordModel>> getlist();

    //Electronics
    @GET("getElectronicData.php")
    Call<List<fetchElectronicModel>> getElectroniclist();

    @FormUrlEncoded
    @POST("uploadElectronic.php")
    Call<responsemessage> uploadElectronicData(
            @Field("image") String image,
            @Field("name") String name,
            @Field("quality") String qaulity,
            @Field("warrenty") String warrenty,
            @Field("price") String Price);

    //Electronics

    @FormUrlEncoded
    @POST("oneShop.php")
    Call<responsemessage> Register(@Field("name") String Name,
                                   @Field("email") String Email,
                                   @Field("password") String Password);

    @FormUrlEncoded
    @POST("login.php")
    Call<responsemessage> login(@Field("name") String Name,
                                @Field("email") String Email,
                                @Field("password") String Password);

    @FormUrlEncoded
    @POST("uploadData.php")
    Call<responsemessage> uploadData(
                                     @Field("image") String image,
                                     @Field("name") String Email,
                                     @Field("detail") String Password,
                                     @Field("pri") String Price);

    @FormUrlEncoded
    @POST("UpdateData.php")
    Call<responsemessage> updateData(@Field("id") String id,
                                     @Field("name") String Email,
                                     @Field("detail") String Password,
                                     @Field("price") String Price);
    @FormUrlEncoded
    @POST("DeleteData.php")
    Call<responsemessage> DeleteData(@Field("id") String id);
}
