package com.example.hseapp

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.example.hseapp.dao.DBHelper

class DumpingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dumping)
        displayImageFromSQLite()


    }
    fun displayImageFromSQLite() {
        val dbHelper = DBHelper(this)
        val dataList = dbHelper.getAllData() // Mengambil data dari database
        val imageView = findViewById<ImageView>(R.id.imagetest) // Ganti dengan ID ImageView Anda

        if (dataList.isNotEmpty()) {
            val imageData = dataList[0].Image1 // Mengambil data gambar dari entitas pertama (Anda dapat menyesuaikan ini sesuai dengan kebutuhan Anda)

            if (imageData != null && !imageData.isEmpty()) {
                val bitmap = BitmapFactory.decodeFile(imageData) // Mengkonversi data gambar ke Bitmap
                imageView.setImageBitmap(bitmap) // Menampilkan gambar di ImageView
            } else {
                // Tindakan yang diambil jika data gambar tidak ada atau kosong
                Toast.makeText(this, "Data gambar tidak ditemukan", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Tindakan yang diambil jika tidak ada data yang ditemukan di database
            Toast.makeText(this, "Tidak ada data yang ditemukan di database", Toast.LENGTH_SHORT).show()
        }
    }
}