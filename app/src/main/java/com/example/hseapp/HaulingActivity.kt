package com.example.hseapp

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.hseapp.dao.DBHelper
import com.example.hseapp.retrofit.SessionManager
import com.google.android.material.checkbox.MaterialCheckBox
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HaulingActivity : AppCompatActivity() {
    private val checkBoxList = mutableListOf<CheckBox>()
    private val kdEditTextList = mutableListOf<EditText>()
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
        setContentView(R.layout.activity_hauling)

        imageView = findViewById(R.id.imageView)
        imageView.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }
        imageView2 = findViewById(R.id.imageView2)
        imageView2.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
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
            var path = it.getString(columnIndex)

            // Hapus "file:/" jika ada dalam path
            if (path.startsWith("file:/")) {
                path = path.substring("file:/".length)
            }

            return path
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
        for (i in 1..30) {
            val ckId = resources.getIdentifier("ck$i", "id", packageName)
            val kdId = resources.getIdentifier("kd$i", "id", packageName)

            val ck = findViewById<CheckBox>(ckId)
            val kd = findViewById<EditText>(kdId)

            checkBoxList.add(ck)
            kdEditTextList.add(kd)

            ck.setOnCheckedChangeListener { _, isChecked ->
                kd.isEnabled = !isChecked
            }
        }

        btn.setOnClickListener {

            val dbHelper = DBHelper(this)
            val db = dbHelper.writableDatabase
            val kondisi = arrayOf(
                R.id.ck1, R.id.ck2, R.id.ck3, R.id.ck4, R.id.ck5,
                R.id.ck6, R.id.ck7, R.id.ck8, R.id.ck9, R.id.ck10,
                R.id.ck11, R.id.ck12, R.id.ck13, R.id.ck14, R.id.ck15,
                R.id.ck16, R.id.ck17, R.id.ck18, R.id.ck19, R.id.ck20,
                R.id.ck21, R.id.ck22, R.id.ck23, R.id.ck24, R.id.ck25, R.id.ck26,
                R.id.ck27,R.id.ck28,R.id.ck29,R.id.ck30
            )

            val kode = arrayOf(
                R.id.kd1, R.id.kd2, R.id.kd3, R.id.kd4, R.id.kd5,
                R.id.kd6, R.id.kd7, R.id.kd8, R.id.kd9, R.id.kd10,
                R.id.kd11, R.id.kd12, R.id.kd13, R.id.kd14, R.id.kd15,
                R.id.kd16, R.id.kd17, R.id.kd18, R.id.kd19, R.id.kd20,
                R.id.kd21, R.id.kd22, R.id.kd23, R.id.kd24, R.id.kd25, R.id.kd26,
                R.id.kd27,R.id.kd28,R.id.kd29,R.id.kd30
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
            val ethauling = findViewById<EditText>(R.id.NamaHauling)
            val hauling = ethauling.text.toString()
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



            if (hauling.isEmpty()) {
                ethauling.setError("Nama Hauling Tidak boleh Kosong")
            } else if (pengawas.isEmpty()) {
                etpengawas.setError("Nama Pengawas Tidak boleh Kosong")
            } else if (imageUri==null) {
                Toast.makeText(this, "Gambar 1 tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (imageUri2==null) {
                Toast.makeText(this, "Gambar 2 tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else {

                //content values
                contentValues.put("Created_at", currentDateTime)
                contentValues.put("shift", shift)
                contentValues.put("nama_lokasi", lokasi)
                contentValues.put("grup", grup)
                contentValues.put("nama_hauling", hauling)
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


                for (i in 0 until kode.size) {
                    val editText = findViewById<EditText>(kode[i])
                    val userInput = editText.text.toString()

                    // Menambahkan input ke kolom "kode"
                    contentValues.put("Kode_bahaya${i + 1}", userInput)
                }


                // Simpan data ke tabel
                val result = db.insert("hauling", null, contentValues)
                db.close()

                if (result != -1L) {
                    // Penyimpanan berhasil, tampilkan pesan toast
                    val intent = Intent(this@HaulingActivity,HistoryHauling::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Penyimpanan gagal, tampilkan pesan toast
//                    Toast.makeText(this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show()
                    Log.d("ERROR",result.toString())
                }
            }
        }
    }
    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Apakah Anda yakin ingin keluar?")
        builder.setCancelable(false)

        builder.setPositiveButton("Ya") { dialog, which ->
            // Tindakan yang akan diambil jika pengguna menekan "Ya"
            val intent = Intent(this@HaulingActivity,HistoryHauling::class.java)
            startActivity(intent)
            finish()
        }

        builder.setNegativeButton("Tidak") { dialog, which ->
            // Tidak melakukan apa-apa jika pengguna menekan "Tidak"
            dialog.dismiss() // Menutup dialog
        }

        val dialog = builder.create()
        dialog.show()
    }
}
