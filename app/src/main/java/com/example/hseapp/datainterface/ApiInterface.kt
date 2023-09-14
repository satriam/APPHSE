package com.example.hseapp.datainterface

import com.example.hseapp.dao.AnswerEntity
import com.example.hseapp.dao.DataPayload
import com.example.hseapp.dataclass.DataMe
import com.example.hseapp.dataclass.Loading
import com.example.hseapp.dataclass.SignInBody
import com.example.hseapp.dataclass.safetycampaign
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

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

    @POST("/api/loadings")
    fun sendDataToApi(@Body data: DataPayload): Call<Void>

    @GET("/api/loadings")
    fun getrecent(): Call<Loading>



}