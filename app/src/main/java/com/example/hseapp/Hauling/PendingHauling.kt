package com.example.hseapp.Hauling

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.hseapp.HaulingActivity
import com.example.hseapp.LoadingActivity
import com.example.hseapp.R
import com.example.hseapp.adapter.AdapterHauling
import com.example.hseapp.adapter.AdapterPending
import com.example.hseapp.dao.DBHelper
import com.example.hseapp.dao.HaulingEntity
import com.example.hseapp.dataclass.response
import com.example.hseapp.datainterface.ApiInterface
import com.example.hseapp.retrofit.RetrofitInstance
import com.google.android.material.floatingactionbutton.FloatingActionButton
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PendingHauling : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_pending_hauling, container, false)

        recyclerView = rootView.findViewById(R.id.rv_datahauling)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        sendSQLiteDataToApi()
        loadData()

        swipeRefreshLayout = rootView.findViewById(R.id.swipehauling) // Ganti dengan ID yang benar
        swipeRefreshLayout.setOnRefreshListener {
            // Implement refresh action here
            loadData()
            sendSQLiteDataToApi()

        }
        val actionbutton = rootView.findViewById<FloatingActionButton>(R.id.fabhauling)

        actionbutton.setOnClickListener {
            val intent = Intent(requireContext(), HaulingActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        return rootView
    }

    private fun loadData() {
        // You can access swipeRefreshLayout2 here
        val dbHelper = DBHelper(requireContext())
        val dataList = dbHelper.getAllDataHauling()

        Log.d("dataaaaa", dataList.toString())
        val adapterData = AdapterHauling(dataList)
        recyclerView.adapter = adapterData
    }

    private fun sendSQLiteDataToApi() {
        // You can access swipeRefreshLayout2 here
        val dbHelper = DBHelper(requireContext())
        val db = dbHelper.readableDatabase
        val dataList = dbHelper.getAllDataHauling()
        val idList = dbHelper.getAllIdsHauling()
        Log.d("DATA",dataList.toString())


        if (dataList.isNotEmpty()) {
            val apiClient = RetrofitInstance.Create(requireContext())

            // Buat fungsi rekursif untuk mengirim data satu per satu
            sendDataRecursively(apiClient, dataList, idList, dbHelper, 0)
        } else {
            Toast.makeText(requireContext(), "Data sudah diupdate", Toast.LENGTH_SHORT).show()
            swipeRefreshLayout.isRefreshing = false
            db.close()
        }

    }



    private fun sendDataRecursively(apiClient: ApiInterface, dataList: List<HaulingEntity>, idList: List<Int>, dbHelper: DBHelper, index: Int) {
        if (index >= dataList.size) {
            // Semua data telah dikirim, tutup database dan selesai
            dbHelper.close()
            return
        }

        val data = dataList[index]
        val imageFile = File(data.gambar1)
        val imageFile2 = File(data.gambar2)
        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), imageFile)
        val requestFile2 = RequestBody.create("image/*".toMediaTypeOrNull(), imageFile2)
        val imageBody = MultipartBody.Part.createFormData("gambar1", imageFile.name, requestFile)
        val imageBody2 = MultipartBody.Part.createFormData("gambar2", imageFile2.name, requestFile2)

        val call = apiClient.Haulingupload(listOf(data), imageBody, imageBody2)
        call.enqueue(object : retrofit2.Callback<response> {
            override fun onResponse(call: retrofit2.Call<response>, response: retrofit2.Response<response>) {
                val responseBody = response.body()
                if (responseBody != null) {
                    if (response.isSuccessful && responseBody.message == "berhasil menambahkan data" && responseBody.status == 200) {
                        Toast.makeText(requireContext(), "Data berhasil diupdate, silahkan refresh kembali", Toast.LENGTH_SHORT).show()
                        dbHelper.deleteDataByIdHauling(idList[index])
                        swipeRefreshLayout.isRefreshing = false
                    } else {
                        val errorBody = response.errorBody()?.string()
                        val errorMessage = if (!errorBody.isNullOrEmpty()) {
                            errorBody
                        } else {
                            "Tidak ada pesan kesalahan yang tersedia."
                        }
                        Toast.makeText(
                            requireContext(),
                            "Gagal mengirim data ke API. Pesan kesalahan: $errorMessage",
                            Toast.LENGTH_SHORT
                        ).show()
                        swipeRefreshLayout.isRefreshing = false
                    }
                }

                // Lanjutkan ke data berikutnya
                sendDataRecursively(apiClient, dataList, idList, dbHelper, index + 1)
            }

            override fun onFailure(call: retrofit2.Call<response>, t: Throwable) {
                Toast.makeText(requireContext(), "Terjadi kesalahan dalam permintaan: ${t.message}", Toast.LENGTH_SHORT).show()
                swipeRefreshLayout.isRefreshing = false
                Log.e("tolong",t.toString())
                dbHelper.close()
            }
        })
    }
}



