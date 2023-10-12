package com.example.hseapp.retrofit

import android.content.Context
import com.example.hseapp.datainterface.ApiInterface
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {
    const val BASE_URL= "https://rehandling.my.id"

    var gson = GsonBuilder()
        .setLenient()
        .create()
    fun Create(context: Context): ApiInterface {
        val retrofit= Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okhttpClient(context))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofit.create(ApiInterface::class.java)
    }


    private fun okhttpClient(context: Context):OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context))
            .build()
    }


}