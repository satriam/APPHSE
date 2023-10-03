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

import android.Manifest
import android.content.pm.PackageManager
import android.media.Image
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    private val PERMISSION_CODE = 123
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sessionManager = SessionManager(this)
        //data user
        getpref()
        fiturout()
        slider()
        fitur()

        val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                // Jika belum memiliki izin MANAGE_EXTERNAL_STORAGE, buka layar pengaturan
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.data = Uri.parse("package:$packageName")
                startActivity(intent)
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, permissions, PERMISSION_CODE)
            }
        }

    }

    private fun fiturout() {
        //out
        val keluar = findViewById<ImageView>(R.id.logout)
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
        // Slider
        val apiClient = RetrofitInstance.Create(this)
        val call = apiClient.getsafetycampaign()


        call.enqueue(object : Callback<List<safetycampaign>> { // Updated the callback type to List<SafetyCampaign>
            override fun onResponse(call: Call<List<safetycampaign>>, response: Response<List<safetycampaign>>) {
                if (response.isSuccessful) {
                    val safetyCampaignList = response.body() // Get the list of SafetyCampaign

                    val imageslider = findViewById<ImageSlider>(R.id.slider)
                    val imageList = ArrayList<SlideModel>()

                    safetyCampaignList?.forEach { campaignData ->
                        val imageUrl = RetrofitInstance.BASE_URL + campaignData.img_url
                        imageList.add(SlideModel(imageUrl, campaignData.keterangan))
                    }

                    imageslider.setImageList(imageList)
                } else {
                    // Handle unsuccessful response
                }
            }

            override fun onFailure(call: Call<List<safetycampaign>>, t: Throwable) {
                // Handle failure
            }
        })
    }

    private fun fitur() {
        //hauling road
        val llhr = findViewById<LinearLayout>(R.id.LLHR)
        llhr.setOnClickListener{
            val intent = Intent(this, RVHauling::class.java)
            startActivity(intent)
        }
        //Dumping Point
        val lldp = findViewById<LinearLayout>(R.id.LLDP)
        lldp.setOnClickListener{
            val intent = Intent(this, RVDumping::class.java)
            startActivity(intent)
        }
        //loading
        val lllp = findViewById<LinearLayout>(R.id.LLLP)
        lllp.setOnClickListener{
            val intent = Intent(this,RVLoading::class.java)
            startActivity(intent)
        }
        val llst = findViewById<ConstraintLayout>(R.id.LLST)
        llst.setOnClickListener{
            val intent = Intent(this,safetyTalkActivity::class.java)
            startActivity(intent)
        }
        val llp = findViewById<ConstraintLayout>(R.id.LLP)
        llp.setOnClickListener{
            val intent = Intent(this,PengaduanActivity::class.java)
            startActivity(intent)
        }


    }
    private fun getpref(){
        val apiClient = RetrofitInstance.Create(this)
        val apiService = apiClient.getUserLogin()
        val Nama = findViewById<TextView>(R.id.tvNama)
        val Pegawai = findViewById<TextView>(R.id.tvNoPeg)
        apiService.enqueue(object : Callback<List<DataMe>> {
            override fun onResponse(call: Call<List<DataMe>>, response: Response<List<DataMe>>) {
                // Tangani respons sukses
                if (response.isSuccessful) {
                    val dataMeList = response.body()
                    val dataMe = dataMeList?.get(0)
                    Log.d("datame",dataMe.toString())
                    if (dataMe != null) {
                        val nama = dataMe.nama
                        val nopeg = dataMe.nomor_pegawai
                        Nama.text = nama
                        Pegawai.text = nopeg
                        val imageUrl = RetrofitInstance.BASE_URL + dataMe.path_gambar
                        val profilePictureImageView = findViewById<ImageView>(R.id.imageView3)
                        Picasso.get().load(imageUrl).into(profilePictureImageView)
                    } else {
                    }
                } else {
                    val errorMessage = response.message()
                }
            }

            override fun onFailure(call: Call<List<DataMe>>, t: Throwable) {
                Log.d("datame",t.toString())
            }
        })
    }
}