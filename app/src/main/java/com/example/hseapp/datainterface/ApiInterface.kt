package com.example.hseapp.datainterface

import com.example.hseapp.dao.AnswerEntity
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
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @FormUrlEncoded
    @POST("/api/auth/local")
    fun signin(
        @Field("identifier") identifier: String,
        @Field("password") password: String
    ): Call<SignInBody>

    @GET("/api/users/me?populate=*")
    fun getUserLogin(): Call<DataMe>

    @GET("/api/jenispertanyaans?populate[gambar][fields][1]=url")
    fun getsafetycampaign(): Call<safetycampaign>

    @Multipart
    @POST("/api/loadings")
    fun uploadDataWithImage(
        @Part("data") data: List<AnswerEntity>,
        @Part gambar1: MultipartBody.Part,
        @Part gambar2: MultipartBody.Part
    ): Call<Void>

//    @GET("/api/loadings?populate=*&[filters][createdBy][id][%24eq]=ids")
    @GET("/api/loadings?populate=*")
    fun getrecent(): Call<Loading>



}