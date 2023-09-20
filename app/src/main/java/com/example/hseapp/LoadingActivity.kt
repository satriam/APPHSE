package com.example.hseapp
import android.annotation.TargetApi
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.example.hseapp.dao.AnswerEntity
import com.example.hseapp.dao.DBHelper
import com.example.hseapp.dataclass.safetycampaign
import com.example.hseapp.retrofit.RetrofitInstance
import com.example.hseapp.retrofit.SessionManager
import com.github.gcacace.signaturepad.views.SignaturePad
import com.google.android.material.checkbox.MaterialCheckBox
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.sql.Types.NULL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class LoadingActivity : AppCompatActivity() {

    private val checkBoxList = mutableListOf<CheckBox>()
    private val kdEditTextList = mutableListOf<EditText>()
    private val ktEditTextList = mutableListOf<EditText>()
    private lateinit var sessionManager: SessionManager
    lateinit var imageView: ImageView
    private val pickImage = 100
    private var imageUri: Uri? = null
    private var imagePath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        imageView = findViewById(R.id.imageView)
        imageView.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
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
            }
        }
    }

            private fun save() {
                val btn = findViewById<Button>(R.id.btnSubmit)
                for (i in 1..26) {
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
                        // Ketika CheckBox dicentang, nonaktifkan EditText
                        // Ketika CheckBox dinonaktifkan, aktifkan EditText
                        kd.isEnabled = !isChecked
                        kt.isEnabled = !isChecked
                    }
                }

                btn.setOnClickListener {
                    if (imagePath != null) {
                        // Gunakan imagePath untuk penyimpanan gambar ke database atau tugas lainnya
                        Log.d("INFO", "imagePath: $imagePath")
                    }else{
                        Log.d("INFO", "tidak ada data")
                    }
                    val dbHelper = DBHelper(this)
                    val db = dbHelper.writableDatabase
                    val kondisi = arrayOf(
                        R.id.ck1, R.id.ck2, R.id.ck3, R.id.ck4, R.id.ck5,
                        R.id.ck6, R.id.ck7, R.id.ck8, R.id.ck9, R.id.ck10,
                        R.id.ck11, R.id.ck12, R.id.ck13, R.id.ck14, R.id.ck15,
                        R.id.ck16, R.id.ck17, R.id.ck18, R.id.ck19, R.id.ck20,
                        R.id.ck21, R.id.ck22, R.id.ck23, R.id.ck24, R.id.ck25, R.id.ck26
                    )
                    val keterangan = arrayOf(
                        R.id.kt1, R.id.kt2, R.id.kt3, R.id.kt4, R.id.kt5,
                        R.id.kt6, R.id.kt7, R.id.kt8, R.id.kt9, R.id.kt10,
                        R.id.kt11, R.id.kt12, R.id.kt13, R.id.kt14, R.id.kt15,
                        R.id.kt16, R.id.kt17, R.id.kt18, R.id.kt19, R.id.kt20,
                        R.id.kt21, R.id.kt22, R.id.kt23, R.id.kt24, R.id.kt25, R.id.kt26
                    )
                    val kode = arrayOf(
                        R.id.kd1, R.id.kd2, R.id.kd3, R.id.kd4, R.id.kd5,
                        R.id.kd6, R.id.kd7, R.id.kd8, R.id.kd9, R.id.kd10,
                        R.id.kd11, R.id.kd12, R.id.kd13, R.id.kd14, R.id.kd15,
                        R.id.kd16, R.id.kd17, R.id.kd18, R.id.kd19, R.id.kd20,
                        R.id.kd21, R.id.kd22, R.id.kd23, R.id.kd24, R.id.kd25, R.id.kd26
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
                    val etloading = findViewById<EditText>(R.id.NamaLoading)
                    val loading = etloading.text.toString()
                    //pengawas
                    val etpengawas = findViewById<EditText>(R.id.NamaPengawas)
                    val pengawas = etpengawas.text.toString()
                    //input id
                    sessionManager = SessionManager(this)
                    val nama = sessionManager.getid()
                    //input id
                    sessionManager = SessionManager(this)
                    val update = sessionManager.getid()




                    if (loading.isEmpty()) {
                        etloading.setError("Nama Loading Tidak boleh Kosong")
                    } else if (pengawas.isEmpty()) {
                        etpengawas.setError("Nama Pengawas Tidak boleh Kosong")
                    } else {
                        //content values
                        contentValues.put("Created_at", currentDateTime)
                        contentValues.put("shift", shift)
                        contentValues.put("nama_lokasi", lokasi)
                        contentValues.put("grup", grup)
                        contentValues.put("nama_loading", loading)
                        contentValues.put("created_by_id", nama)
                        contentValues.put("updated_by_id", nama)
                        contentValues.put("Image1",imagePath)

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
                        val result = db.insert("jawaban", null, contentValues)
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
