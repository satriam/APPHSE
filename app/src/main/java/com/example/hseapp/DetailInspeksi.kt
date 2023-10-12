package com.example.hseapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.hseapp.Dumping.PendingDumping
import com.example.hseapp.Hauling.PendingHauling
import com.example.hseapp.Loading.PendingFragment
import com.example.hseapp.dao.DBHelper
import com.google.android.material.textfield.TextInputEditText
import com.squareup.picasso.Picasso
import java.io.File

class DetailInspeksi : AppCompatActivity() {
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
        setContentView(R.layout.activity_detail_inspeksi)

        init()
        intent()

        hapus = findViewById(R.id.hapus)
        hapus.setOnClickListener{
            val hauling = intent.getIntExtra("id_hauling", 0)
            val loading = intent.getIntExtra("id_loading", 0)
            val dumping = intent.getIntExtra("id_dumping", 0)

            Log.d("loading",loading.toString())
            Log.d("loading",hauling.toString())
            Log.d("loading",dumping.toString())
            if (hauling != 0 ){
            val dbHelper = DBHelper(this)
            val db = dbHelper.readableDatabase
            val deleted = dbHelper.deleteDataByIdHauling(hauling)
            if (deleted == true) {
                // Penghapusan berhasil
                Toast.makeText(this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()

                // Jalankan intent atau tindakan selanjutnya di sini
                // Misalnya, kembali ke aktivitas sebelumnya
                val intent = Intent(this, HistoryHauling::class.java)
                startActivity(intent)
                finish() // Optional, jika Anda ingin menutup aktivitas saat ini
            } else {
                // Penghapusan gagal
                Toast.makeText(this, "Gagal menghapus data", Toast.LENGTH_SHORT).show()
            }
        }else if(dumping!=0){
                val dbHelper = DBHelper(this)
                val db = dbHelper.readableDatabase
                val deleted = dbHelper.deleteDataByIdDumping(dumping)
                if (deleted == true) {
                    // Penghapusan berhasil
                    Toast.makeText(this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()

                    // Jalankan intent atau tindakan selanjutnya di sini
                    // Misalnya, kembali ke aktivitas sebelumnya
                    val intent = Intent(this, HistoryDumping::class.java)
                    startActivity(intent)
                    finish() // Optional, jika Anda ingin menutup aktivitas saat ini
                } else {
                    // Penghapusan gagal
                    Toast.makeText(this, "Gagal menghapus data", Toast.LENGTH_SHORT).show()
                }
            } else if(loading!=0){
                val dbHelper = DBHelper(this)
                val db = dbHelper.readableDatabase
                val deleted = dbHelper.deleteDataById(loading)
                if (deleted == true) {
                    // Penghapusan berhasil
                    Toast.makeText(this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()

                    // Jalankan intent atau tindakan selanjutnya di sini
                    // Misalnya, kembali ke aktivitas sebelumnya
                    val intent = Intent(this, RVLoading::class.java)
                    startActivity(intent)
                    finish() // Optional, jika Anda ingin menutup aktivitas saat ini
                } else {
                    // Penghapusan gagal
                    Toast.makeText(this, "Gagal menghapus data", Toast.LENGTH_SHORT).show()
                }
            }
        }

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

    private fun intent(){

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

        val imageUri = Uri.fromFile(File(gambar))

        Picasso.get()
            .load(imageUri)
            .resize(widthInPixels, heightInPixels)
            .centerCrop()
            .into(egambar1)



        val imageUri2 = Uri.fromFile(File(gambar2))

        Picasso.get()
            .load(imageUri2)
            .resize(widthInPixels, heightInPixels)
            .centerCrop()
            .into(egambar2)
    }


}