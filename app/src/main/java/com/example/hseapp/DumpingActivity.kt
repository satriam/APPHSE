package com.example.hseapp


import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.hseapp.dao.AnswerEntity
import com.example.hseapp.dao.DBHelper
import com.example.hseapp.retrofit.SessionManager
import com.google.android.material.checkbox.MaterialCheckBox
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import java.io.FileNotFoundException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DumpingActivity : AppCompatActivity() {

    private val checkBoxList = mutableListOf<CheckBox>()
    private val kdEditTextList = mutableListOf<EditText>()
    private val ktEditTextList = mutableListOf<EditText>()
    private lateinit var sessionManager: SessionManager
    lateinit var imageView: ImageView
    lateinit var imageView2: ImageView
    private val pickImage = 100
    private val pickImage2 = 101
    private var imageUri: Uri? = null
    private var imageUri2: Uri? = null
    private var imagePath: String? = null
    private var imagePath2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dumping)

        imageView = findViewById(R.id.imageView)
        imageView.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }
        imageView2 = findViewById(R.id.imageView2)
        imageView2.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage2)
        }

        save()
    }

    fun getRealPathFromUri(uri: Uri): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, projection, null, null, null)

        cursor?.use {
            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            it.moveToFirst()
            return it.getString(columnIndex)
        }

        return null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            when (requestCode) {
                pickImage -> {
                    data?.data?.let { uri ->
                        imageUri = uri
                        imageView.setImageURI(imageUri)
                        imagePath = getRealPathFromUri(uri)

                    }
                }
                pickImage2 -> {
                    data?.data?.let { uri ->
                        imageUri2 = uri
                        imageView2.setImageURI(imageUri2)
                        imagePath2 = getRealPathFromUri(uri)

                    }
                }
            }
        }
    }



    private fun save() {
        val btn = findViewById<Button>(R.id.btnSubmit)
        for (i in 1..20) {
            val ckId = resources.getIdentifier("ck$i", "id", packageName)
            val kdId = resources.getIdentifier("kd$i", "id", packageName)
            val ktId = resources.getIdentifier("kt$i", "id", packageName)

            val ck = findViewById<CheckBox>(ckId)
            val kd = findViewById<EditText>(kdId)
            val kt = findViewById<EditText>(ktId)

            checkBoxList.add(ck)
            kdEditTextList.add(kd)
            ktEditTextList.add(kt)

            ck.setOnCheckedChangeListener { _, isChecked ->
                kd.isEnabled = !isChecked
                kt.isEnabled = !isChecked
            }
        }

        btn.setOnClickListener {

            val dbHelper = DBHelper(this)
            val db = dbHelper.writableDatabase
            val kondisi = arrayOf(
                R.id.ck1, R.id.ck2, R.id.ck3, R.id.ck4, R.id.ck5,
                R.id.ck6, R.id.ck7, R.id.ck8, R.id.ck9, R.id.ck10,
                R.id.ck11, R.id.ck12, R.id.ck13, R.id.ck14, R.id.ck15,
                R.id.ck16, R.id.ck17, R.id.ck18, R.id.ck19, R.id.ck20
            )
            val keterangan = arrayOf(
                R.id.kt1, R.id.kt2, R.id.kt3, R.id.kt4, R.id.kt5,
                R.id.kt6, R.id.kt7, R.id.kt8, R.id.kt9, R.id.kt10,
                R.id.kt11, R.id.kt12, R.id.kt13, R.id.kt14, R.id.kt15,
                R.id.kt16, R.id.kt17, R.id.kt18, R.id.kt19, R.id.kt20
            )
            val kode = arrayOf(
                R.id.kd1, R.id.kd2, R.id.kd3, R.id.kd4, R.id.kd5,
                R.id.kd6, R.id.kd7, R.id.kd8, R.id.kd9, R.id.kd10,
                R.id.kd11, R.id.kd12, R.id.kd13, R.id.kd14, R.id.kd15,
                R.id.kd16, R.id.kd17, R.id.kd18, R.id.kd19, R.id.kd20
            )
            //tanggal
            val contentValues = ContentValues()
            val currentDateTime =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(
                    Date()
                )
            //lokasi
            val etlokasi = findViewById<SearchableSpinner>(R.id.lokasi)
            val lokasi = etlokasi.selectedItem.toString()
            //shift
            val etshift = findViewById<SearchableSpinner>(R.id.Shift)
            val shift = etshift.selectedItem.toString()
            //grup
            val etgrup = findViewById<SearchableSpinner>(R.id.grup)
            val grup = etgrup.selectedItem.toString()
            //loading
            val etdumping = findViewById<EditText>(R.id.NamaDumping)
            val dumping = etdumping.text.toString()
            //pengawas
            val etpengawas = findViewById<EditText>(R.id.NamaPengawas)
            val pengawas = etpengawas.text.toString()
            //input id
            sessionManager = SessionManager(this)
            val nama = sessionManager.getid()
            //input id
            sessionManager = SessionManager(this)
            val pathgambar = imagePath.toString()
            val pathgambar2 = imagePath2.toString()
            //tindakan
            val ettindakan = findViewById<EditText>(R.id.tindakan)
            val tindakan = ettindakan.text.toString()
            val ettindakan2 = findViewById<EditText>(R.id.tindakan2)
            val tindakan2 = ettindakan2.text.toString()



            if (dumping.isEmpty()) {
                etdumping.setError("Nama Dumping Tidak boleh Kosong")
            } else if (pengawas.isEmpty()) {
                etpengawas.setError("Nama Pengawas Tidak boleh Kosong")
            } else {

                //content values
                contentValues.put("Created_at", currentDateTime)
                contentValues.put("shift", shift)
                contentValues.put("nama_lokasi", lokasi)
                contentValues.put("grup", grup)
                contentValues.put("nama_dumping", dumping)
                contentValues.put("nama_pengawas", pengawas)
                contentValues.put("created_by_id", nama)
                contentValues.put("Image1",pathgambar)
                contentValues.put("Image2",pathgambar2)
                contentValues.put("Tindakan1",tindakan)
                contentValues.put("Tindakan2",tindakan2)

                for (i in 0 until kondisi.size) {
                    val checkBox = findViewById<MaterialCheckBox>(kondisi[i])

                    // Mendapatkan status checkbox dan menyimpannya ke dalam array
                    val checkboxStatus = checkBox.isChecked

                    // Menambahkan status checkbox ke dalam ContentValues
                    contentValues.put("kondisi${i + 1}", checkboxStatus.toString())
                }
                for (i in 0 until keterangan.size) {
                    val editText = findViewById<EditText>(keterangan[i])
                    val userInput = editText.text.toString()

                    // Menambahkan input ke kolom "keterangan"
                    contentValues.put("Keterangan${i + 1}", userInput)
                }

                for (i in 0 until kode.size) {
                    val editText = findViewById<EditText>(kode[i])
                    val userInput = editText.text.toString()

                    // Menambahkan input ke kolom "kode"
                    contentValues.put("Kode_bahaya${i + 1}", userInput)
                }


                // Simpan data ke tabel


                val result = db.insert("dumping", null, contentValues)
                db.close()

                if (result != -1L) {
                    // Penyimpanan berhasil, tampilkan pesan toast
                    Toast.makeText(this, "Data berhasil disimpan", Toast.LENGTH_SHORT)
                        .show()
                    finish()
                } else {
                    // Penyimpanan gagal, tampilkan pesan toast
                    Toast.makeText(this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}


