package com.example.hseapp


import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.example.hseapp.dao.DBHelper

import com.example.hseapp.dataclass.DataMe
import com.example.hseapp.dataclass.safetycampaign
import com.example.hseapp.retrofit.RetrofitInstance
import com.example.hseapp.retrofit.SessionManager
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sessionManager = SessionManager(this)
        //data user
        getpref()
        fiturout()
        slider()
        fitur()



    }

    private fun fiturout() {
        //out
        val keluar = findViewById<ConstraintLayout>(R.id.LLI)
        keluar.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Logout")
            builder.setMessage("Apakah Anda yakin ingin logout?")

            builder.setPositiveButton("Ya") { dialog, which ->
                sessionManager.removeData()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

            builder.setNegativeButton("Batal") { dialog, which ->
            }

            builder.show()
        }
    }

    private fun slider() {
        //slider
        val apiClient = RetrofitInstance.Create(this)
        val call = apiClient.getsafetycampaign() // Menggunakan Retrofit untuk mendapatkan data dari API

        call.enqueue(object : Callback<safetycampaign> {
            override fun onResponse(call: Call<safetycampaign>, response: Response<safetycampaign>) {
                if (response.isSuccessful) {
                    val safetyCampaign = response.body() // Mendapatkan data dari respons
                    Log.d("testing",safetyCampaign.toString())
                    val imageslider = findViewById<ImageSlider>(R.id.slider)
                    val imageList = ArrayList<SlideModel>()

                    // Mengisi imageList dengan data dari API
                    for (campaignData in safetyCampaign?.data.orEmpty()) {
                        val imageUrl =RetrofitInstance.BASE_URL + campaignData.attributes.gambar.data.attributes.url
                        imageList.add(SlideModel(imageUrl))
                    }

                    imageslider.setImageList(imageList)
                } else {
                }
            }

            override fun onFailure(call: Call<safetycampaign>, t: Throwable) {

            }
        })
    }

    private fun fitur() {
        //hauling road
        val llhr = findViewById<LinearLayout>(R.id.LLHR)
        llhr.setOnClickListener{
            val intent = Intent(this, HaulingActivity::class.java)
            startActivity(intent)
        }
        //Dumping Point
        val lldp = findViewById<LinearLayout>(R.id.LLDP)
        lldp.setOnClickListener{
            val intent = Intent(this, DumpingActivity::class.java)
            startActivity(intent)
        }
        //loading
        val lllp = findViewById<LinearLayout>(R.id.LLLP)
        lllp.setOnClickListener{
            val intent = Intent(this,RVLoading::class.java)
            startActivity(intent)
        }
        val llst = findViewById<LinearLayout>(R.id.LLST)
        llst.setOnClickListener{
            val intent = Intent(this,safetyTalkActivity::class.java)
            startActivity(intent)
        }

    }
    private fun getpref(){
        val apiClient = RetrofitInstance.Create(this)
        val apiService = apiClient.getUserLogin()
        val Nama = findViewById<TextView>(R.id.tvNama)
        apiService.enqueue(object : Callback<DataMe> {
            override fun onResponse(call: Call<DataMe>, response: Response<DataMe>) {
                // Tangani respons sukses
                if (response.isSuccessful) {
                    val dataMe = response.body()
                    if (dataMe != null) {
                        val nama = dataMe.Nama
                        Nama.text = nama

                        val imageUrl = RetrofitInstance.BASE_URL + dataMe.ProfilePicture.url
                        val profilePictureImageView = findViewById<ImageView>(R.id.imageView3)
                        Picasso.get().load(imageUrl).into(profilePictureImageView)
                    } else {
                    }
                } else {
                    val errorMessage = response.message()
                }
            }

            override fun onFailure(call: Call<DataMe>, t: Throwable) {
            }
        })
    }
}