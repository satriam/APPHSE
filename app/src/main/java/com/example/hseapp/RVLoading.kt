package com.example.hseapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.hseapp.adapter.AdapterLoading
import com.example.hseapp.dao.DBHelper
import com.example.hseapp.dataclass.Data
import com.example.hseapp.dataclass.Loading
import com.example.hseapp.retrofit.RetrofitInstance
import com.google.android.material.floatingactionbutton.FloatingActionButton
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class RVLoading : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdapterLoading
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rvloading)
        getpref()
        sendSQLiteDataToApi()
        swipeRefreshLayout = findViewById(R.id.swipe)
        swipeRefreshLayout.setOnRefreshListener {
            getpref()
            sendSQLiteDataToApi()
            swipeRefreshLayout.isRefreshing=false
        }
        
        val actionbutton = findViewById<FloatingActionButton>(R.id.fab)

        actionbutton.setOnClickListener {
            val intent = Intent(this, LoadingActivity::class.java)
            startActivity(intent)
        }

    }

    private fun sendSQLiteDataToApi() {
        val dbHelper = DBHelper(this)
        val db = dbHelper.readableDatabase

        val dataList = dbHelper.getAllData()
        Log.d("DATA LIST",dataList.toString())
        val idList = dbHelper.getAllIds()

        if (dataList.isNotEmpty()) {
            val apiClient = RetrofitInstance.Create(this)

            for (data in dataList) {
                Log.d("SQLite Data", data.toString()) // Tambahkan log ini
                val imageFile = File(data.Image1) // Ubah sesuai dengan properti yang sesuai di objek DataPayload
                val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), imageFile)
                val imageBody = MultipartBody.Part.createFormData("files.gambar1", imageFile.name, requestFile)
                Log.d("body",imageFile.toString())


                val call = apiClient.uploadDataWithImage(dataList,imageBody)
                call.enqueue(object : retrofit2.Callback<Void> {
                    override fun onResponse(call: retrofit2.Call<Void>, response: retrofit2.Response<Void>) {
                        if (response.isSuccessful) {
                            for (id in idList) {
                                val deleted = dbHelper.deleteDataById(id)
                                Log.d("deleted", deleted.toString())
                            }
                        } else {
                            val errorBody = response.errorBody()?.string()
                            val errorMessage = if (!errorBody.isNullOrEmpty()) {
                                errorBody
                            } else {
                                "Tidak ada pesan kesalahan yang tersedia."
                            }
                            Toast.makeText(this@RVLoading, "Gagal mengirim data ke API. Pesan kesalahan: $errorMessage", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: retrofit2.Call<Void>, t: Throwable) {
                        Toast.makeText(this@RVLoading, "Terjadi kesalahan dalam permintaan: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        } else {
            Toast.makeText(this, "Data sudah Update", Toast.LENGTH_SHORT).show()
        }
        db.close()

    }



    private fun getpref(){
        val apiClient = RetrofitInstance.Create(this)
        val apiService = apiClient.getrecent()
        val listData = ArrayList<Data>() // Menggunakan List<Data> bukan List<Loading>
        recyclerView = findViewById(R.id.rv_data)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        apiService.enqueue(object : Callback<Loading> {
            override fun onResponse(call: Call<Loading>, response: Response<Loading>) {
                // Tangani respons sukses
                if (response.isSuccessful) {
                    val dataMe = response.body()
                    Log.e("datame",dataMe.toString())

                    if (dataMe != null) {
                        // Tambahkan semua objek Data ke dalam listData
                        listData.addAll(dataMe.data)
                        val adapterData = AdapterLoading(listData) // Gunakan List<Data>
                        recyclerView.adapter = adapterData
                    } else {
                    }
                } else {
                    val errorMessage = response.message()
                }
            }

            override fun onFailure(call: Call<Loading>, t: Throwable) {
            }
        })
    }
}
