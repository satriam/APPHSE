package com.example.hseapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText

class PengaduanActivity : AppCompatActivity() {

    private lateinit var  nama:TextInputEditText
    private lateinit var  Lokasi:TextInputEditText
    private lateinit var  Kronologi:EditText
    private lateinit var  Nmperusahaan:TextInputEditText
    private lateinit var  Nmunit:TextInputEditText
    private lateinit var  Nmorg:TextInputEditText
    private lateinit var  gambar:ImageView
    private lateinit var  spinner:Spinner
    private lateinit var  submit:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pengaduan)

        init()
        submit.setOnClickListener {
            validation()
        }
    }


    fun init(){
        nama = findViewById(R.id.namaet)
        Lokasi = findViewById(R.id.lokasiet)
        Kronologi = findViewById(R.id.kronologiet)
        Nmperusahaan = findViewById(R.id.nmperusahaanet)
        Nmunit = findViewById(R.id.nmunitet)
        Nmorg = findViewById(R.id.nmoranget)
        gambar = findViewById(R.id.gmbrpengaduan)
        spinner = findViewById(R.id.spinner)
        submit = findViewById(R.id.Submit)
    }

    private fun validation(){
        val nm = nama.text.toString()
        val lokasi = Lokasi.text.toString()
        val kronologi = Kronologi.text.toString()
        val nmperusahaan = Nmperusahaan.text.toString()
        val nmunit = Nmunit.text.toString()
        val nmorg = Nmorg.text.toString()

        if (nm.isEmpty()){
            nama.setError("Nama Tidak boleh Kosong")
        }else if (lokasi.isEmpty()){
            Lokasi.setError("Lokasi Tidak boleh Kosong")
        }else if (kronologi.isEmpty()){
            Kronologi.setError("Kronologi Tidak boleh Kosong")
        }else if (nmperusahaan.isEmpty()){
            Nmperusahaan.setError("Nama Perusahaan Tidak boleh Kosong")
        }else if (nmunit.isEmpty()){
            Nmunit.setError("Nama Unit Tidak boleh Kosong")
        }else if (nmorg.isEmpty()){
            Nmorg.setError("Nama Orang Tidak boleh Kosong")
        }else{
            action()
        }
    }

    private fun action(){
        Toast.makeText(this, "BERHASIL MELAKUKAN PENGADUAN", Toast.LENGTH_SHORT).show()
    }
}