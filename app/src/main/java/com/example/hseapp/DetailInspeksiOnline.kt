package com.example.hseapp

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.hseapp.retrofit.RetrofitInstance
import com.example.hseapp.retrofit.SessionManager
import com.squareup.picasso.Picasso
import java.io.File

class DetailInspeksiOnline : AppCompatActivity() {
    private lateinit var  etanggal: TextView
    private lateinit var  epengawas: TextView
    private lateinit var  elokasi: TextView
    private lateinit var  eshift: TextView
    private lateinit var  egrup: TextView
    private lateinit var  epembuat: TextView
    private lateinit var  egambar1: ImageView
    private lateinit var  egambar2: ImageView
    private lateinit var  ehapus: Button
    private lateinit var  eupdate: Button
    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_inspeksi_online)
        init()
        intent()
    }

    private fun init(){
        etanggal= findViewById(R.id.tanggal)
        elokasi= findViewById(R.id.lokasi)
        epengawas= findViewById(R.id.pengawas)
        eshift= findViewById(R.id.shift)
        egrup= findViewById(R.id.grup)
        epembuat= findViewById(R.id.namapembuat)
        egambar1= findViewById(R.id.gambar1)
        egambar2= findViewById(R.id.gambar2)
        ehapus= findViewById(R.id.hapus)
        eupdate= findViewById(R.id.update)
    }

    private fun intent() {

        val tanggal = intent.getStringExtra("tanggal")
        val lokasi = intent.getStringExtra("lokasi")
        val pengawas = intent.getStringExtra("pengawas")
        val shift = intent.getStringExtra("shift")
        val grup = intent.getStringExtra("grup")
        val pembuat = intent.getStringExtra("nama")
        val gambar = intent.getStringExtra("gambar1")
        val gambar2 = intent.getStringExtra("gambar2")

        Log.d("gambar2", gambar2.toString())


        etanggal.text = tanggal
        elokasi.text = lokasi
        epengawas.text = pengawas
        eshift.text = shift
        egrup.text = grup
        epembuat.text = pembuat

        val widthInPixels = 100
        val heightInPixels = 100

        val imageUri = RetrofitInstance.BASE_URL + gambar

        Picasso.get()
            .load(imageUri)
            .resize(widthInPixels, heightInPixels)
            .centerCrop()
            .into(egambar1)
        val imageUri2 = RetrofitInstance.BASE_URL + gambar2

        Picasso.get()
            .load(imageUri2)
            .resize(widthInPixels, heightInPixels)
            .centerCrop()
            .into(egambar2)

        Log.d("GAMBAR2",imageUri2.toString())
        sessionManager = SessionManager(this)
        if (sessionManager.getRole() == "admin"){
            ehapus.visibility = View.VISIBLE
            eupdate.visibility = View.VISIBLE
        }



    }

}