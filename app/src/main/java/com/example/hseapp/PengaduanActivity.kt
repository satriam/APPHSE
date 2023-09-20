package com.example.hseapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hseapp.dao.DBHelper
import com.google.android.material.textfield.TextInputEditText
import java.util.Calendar

class PengaduanActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var  nama:TextInputEditText
    private lateinit var  Lokasi:TextInputEditText
    private lateinit var  Kronologi:EditText
    private lateinit var  Nmperusahaan:TextInputEditText
    private lateinit var  Nmunit:TextInputEditText
    private lateinit var  Nmorg:TextInputEditText
    private lateinit var  gambar:ImageView
    private lateinit var  spinner:Spinner
    private lateinit var  submit:Button
    private lateinit var selectDate: EditText
    private lateinit var selectTime: EditText
    lateinit var imageView: ImageView
    private val pickImage = 100
    private var imageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pengaduan)

        init()
        submit.setOnClickListener {
            validation()
        }

        selectDate=findViewById(R.id.date);
        selectTime=findViewById(R.id.time);

        selectDate.setOnClickListener(this);
        selectTime.setOnClickListener(this);

        imageView = findViewById(R.id.gmbrpengaduan)
        imageView.setOnClickListener{
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }
    }

    val databaseHelper = DBHelper(this)
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == RESULT_OK) {
//            when (requestCode) {
//                pickImage -> {
//                    imageUri = data?.data
//                    imageView.setImageURI(imageUri)
//
//                    if (imageUri != null) {
//                        val insertedId = databaseHelper.insertImage(imageUri.toString())
//                        if (insertedId > 0) {
//                            // Gambar berhasil disimpan
//                            Toast.makeText(this, "Gambar berhasil disimpan", Toast.LENGTH_SHORT).show()
//                        } else {
//                            // Terjadi kesalahan saat menyimpan gambar
//                            Toast.makeText(this, "Terjadi kesalahan saat menyimpan gambar", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                }
//
//
//            }
//        }
//    }

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

    override fun onClick(view: View) {
        if (view == selectDate) {
            val c = Calendar.getInstance()
            val mYear = c.get(Calendar.YEAR)
            val mMonth = c.get(Calendar.MONTH)
            val mDay = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    val formattedDate = "$dayOfMonth-${monthOfYear + 1}-$year"
                    selectDate.setText(formattedDate)
                },
                mYear,
                mMonth,
                mDay
            )
            datePickerDialog.show()
        }
        if (view == selectTime) {
            val c = Calendar.getInstance()
            val mHour = c.get(Calendar.HOUR_OF_DAY)
            val mMinute = c.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(
                this,
                TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    val formattedTime = "$hourOfDay:$minute"
                    selectTime.setText(formattedTime)
                },
                mHour,
                mMinute,
                false
            )
            timePickerDialog.show()
        }
    }

}