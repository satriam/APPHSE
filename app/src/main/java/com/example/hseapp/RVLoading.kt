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
import com.example.hseapp.dao.AnswerEntity
import com.example.hseapp.dao.DBHelper
import com.example.hseapp.dao.DataPayload
import com.example.hseapp.dataclass.Data
import com.example.hseapp.dataclass.Loading
import com.example.hseapp.retrofit.RetrofitInstance
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RVLoading : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdapterLoading
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rvloading)
        getpref()
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
        val db = dbHelper.writableDatabase

        val dataList = dbHelper.getAllData()
        val idList = dbHelper.getAllIds()

        if (dataList.isNotEmpty()) {
            val apiClient = RetrofitInstance.Create(this)

            for (data in dataList) {
                val dataPayload = DataPayload(data)
                val call = apiClient.sendDataToApi(dataPayload)
                call.enqueue(object : retrofit2.Callback<Void> {
                    override fun onResponse(call: retrofit2.Call<Void>, response: retrofit2.Response<Void>) {
                        if (response.isSuccessful) {
                            for (id in idList) {
                                val deleted = dbHelper.deleteDataById(id)
                                Log.d("deleted",deleted.toString())
                            }
                        } else {
                            val errorBody = response.errorBody()?.string()
                            val errorMessage = if (!errorBody.isNullOrEmpty()) {
                                errorBody
                            } else {
                                "Tidak ada pesan kesalahan yang tersedia."
                            }
                            Log.e("LoadingActivity", "Gagal mengirim data ke API. Pesan kesalahan: $errorMessage")
                        }
                    }

                    override fun onFailure(call: retrofit2.Call<Void>, t: Throwable) {
                        Log.e("LoadingActivity", "Terjadi kesalahan dalam permintaan: ${t.message}")
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
