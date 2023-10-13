package com.example.hseapp

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.hseapp.retrofit.RetrofitInstance
import com.squareup.picasso.Picasso
import java.io.File

class DetailInspeksiOnline : AppCompatActivity() {
    private lateinit var  etanggal: TextView
    private lateinit var  epengawas: TextView
    private lateinit var  elokasi: TextView
    private lateinit var  eshift: TextView
    private lateinit var  egrup: TextView
    private lateinit var  egambar1: ImageView
    private lateinit var  egambar2: ImageView
    private lateinit var  hapus: Button
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
        egambar1= findViewById(R.id.gambar1)
        egambar2= findViewById(R.id.gambar2)
        hapus= findViewById(R.id.hapus)
    }

    private fun intent() {

        val tanggal = intent.getStringExtra("tanggal")
        val lokasi = intent.getStringExtra("lokasi")
        val pengawas = intent.getStringExtra("pengawas")
        val shift = intent.getStringExtra("shift")
        val grup = intent.getStringExtra("grup")
        val gambar = intent.getStringExtra("gambar1")
        val gambar2 = intent.getStringExtra("gambar2")


        etanggal.text = tanggal
        elokasi.text = lokasi
        epengawas.text = pengawas
        eshift.text = shift
        egrup.text = grup

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

    }

}