package com.example.hseapp.datainterface

import com.example.hseapp.dao.AnswerEntity
import com.example.hseapp.dao.DumpingEntity
import com.example.hseapp.dao.HaulingEntity
import com.example.hseapp.dataclass.DataMe
import com.example.hseapp.dataclass.Loading
import com.example.hseapp.dataclass.SignInBody
import com.example.hseapp.dataclass.notif
import com.example.hseapp.dataclass.response
import com.example.hseapp.dataclass.safetycampaign
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.time.LocalDateTime

interface ApiInterface {

    @FormUrlEncoded
    @POST("/Login")
    fun signin(
        @Field("email_or_username") email_or_username: String,
        @Field("password") password: String
    ): Call<SignInBody>

    @GET("/me")
    fun getUserLogin(): Call<List<DataMe>>

    @GET("/Notification")
    fun getnotif(): Call<List<notif>>

    @GET("/safety")
    fun getsafetycampaign(): Call<List<safetycampaign>>

    @Multipart
    @POST("/Safety")
    fun uploadsafety(
        @Part("keterangan") tanggal: RequestBody,
        @Part("mitra") mitra: RequestBody,
        @Part gambar1: MultipartBody.Part
    ): Call<Void>
//    @Headers("Content-Type: application/json")
    @Multipart
    @POST("/loading")
    fun uploadDataWithImage(
        @Part("data") data: List<AnswerEntity>,
        @Part gambar1: MultipartBody.Part,
        @Part gambar2: MultipartBody.Part
    ): Call<response>

    @GET("/Loading/tampil")
    fun getrecent(): Call<ArrayList<Loading>>

    @Multipart
    @POST("/dumping")
    fun Dumpingupload(
        @Part("data") data: List<DumpingEntity>,
        @Part gambar1: MultipartBody.Part,
        @Part gambar2: MultipartBody.Part
    ): Call<response>

    @GET("/Dumping/tampil")
    fun getDumping(): Call<ArrayList<Loading>>

    @Multipart
    @POST("/hauling")
    fun Haulingupload(
        @Part("data") data: List<HaulingEntity>,
        @Part gambar1: MultipartBody.Part,
        @Part gambar2: MultipartBody.Part
    ): Call<response>

    @GET("/Hauling/tampil")
    fun getHauling(): Call<ArrayList<Loading>>

    @Multipart
    @POST("/pengaduan")
    fun Pengaduan(
        @Part("nama") nama: RequestBody,
        @Part("lokasi_kejadian") lokasi: RequestBody,
        @Part("kronologi") kronologi: RequestBody,
        @Part("perusahaan") perusahaan: RequestBody,
        @Part("unit") unit: RequestBody,
        @Part("nama_orang") nama_orang: RequestBody,
        @Part("email") nomorhp: RequestBody,
        @Part("jenis") jenis: RequestBody,
        @Part("tanggal") tanggal: RequestBody,
        @Part file: MultipartBody.Part
    ): Call<response>


}