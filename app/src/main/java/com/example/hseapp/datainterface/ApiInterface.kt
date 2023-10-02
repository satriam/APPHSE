package com.example.hseapp.datainterface

import com.example.hseapp.dao.AnswerEntity
import com.example.hseapp.dao.DumpingEntity
import com.example.hseapp.dao.HaulingEntity
import com.example.hseapp.dataclass.DataMe
import com.example.hseapp.dataclass.Loading
import com.example.hseapp.dataclass.SignInBody
import com.example.hseapp.dataclass.safetycampaign
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiInterface {

    @FormUrlEncoded
    @POST("/Login")
    fun signin(
        @Field("email_or_username") email_or_username: String,
        @Field("password") password: String
    ): Call<SignInBody>

    @GET("/me")
    fun getUserLogin(): Call<List<DataMe>>

    @GET("/safety")
    fun getsafetycampaign(): Call<List<safetycampaign>>

    @Multipart
    @POST("/loading")
    fun uploadDataWithImage(
        @Part("data") data: List<AnswerEntity>,
        @Part gambar1: MultipartBody.Part,
        @Part gambar2: MultipartBody.Part
    ): Call<Void>

    @GET("/Loading/tampil")
    fun getrecent(): Call<ArrayList<Loading>>

    @Multipart
    @POST("/dumping")
    fun Dumpingupload(
        @Part("data") data: List<DumpingEntity>,
        @Part gambar1: MultipartBody.Part,
        @Part gambar2: MultipartBody.Part
    ): Call<Void>

    @GET("/Dumping/tampil")
    fun getDumping(): Call<ArrayList<Loading>>

    @Multipart
    @POST("/hauling")
    fun Haulingupload(
        @Part("data") data: List<HaulingEntity>,
        @Part gambar1: MultipartBody.Part,
        @Part gambar2: MultipartBody.Part
    ): Call<Void>

    @GET("/Hauling/tampil")
    fun getHauling(): Call<ArrayList<Loading>>



}