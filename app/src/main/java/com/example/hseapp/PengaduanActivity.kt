package com.example.hseapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import java.util.Calendar
import androidx.annotation.RequiresApi
import com.example.hseapp.dataclass.response
import com.example.hseapp.retrofit.RetrofitInstance
import com.example.hseapp.retrofit.SessionManager
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class PengaduanActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var sessionManager: SessionManager
    private lateinit var  nama:TextInputEditText
    private lateinit var  Lokasi:TextInputEditText
    private lateinit var  Kronologi:EditText
    private lateinit var  Nmperusahaan:TextInputEditText
    private lateinit var  Nmunit:TextInputEditText
    private lateinit var  Nmorg:TextInputEditText
    private lateinit var  NomorHp:TextInputEditText
    private lateinit var  spinner:Spinner
    private lateinit var  submit:Button
    private lateinit var selectDate: EditText
    private lateinit var selectTime: EditText

    lateinit var btnFile : Button
    private val pickImage = 1
    private val pickVideo = 2
    private var mediaUri: Uri? = null
    private var imagePath: String? = null

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

        btnFile = findViewById(R.id.btnfile)
        btnFile.setOnClickListener{
            showChooseMediaDialog()
        }


    }


    private fun showChooseMediaDialog() {
        val options = arrayOf("Pilih Gambar", "Pilih Video")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Pilih Media")
        builder.setItems(options) { _, which ->
            when (which) {
                0 -> pickImageFromGallery()
                1 -> pickVideoFromGallery()
            }
        }
        builder.show()
    }
    fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, pickImage)
    }

    fun pickVideoFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, pickVideo)
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

    fun getRealPathFromVideoUri(uri: Uri): String? {
        val projection = arrayOf(MediaStore.Video.Media.DATA)
        val cursor = contentResolver.query(uri, projection, null, null, null)

        cursor?.use {
            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
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
                        mediaUri = uri
                        val mediaPreview = findViewById<EditText>(R.id.urlet)
                        imagePath = getRealPathFromUri(uri)
                        mediaPreview.setText(imagePath)

                    }
                }
                pickVideo -> {
                    data?.data?.let { videoUri ->
                        mediaUri = videoUri
                        val mediaPreview = findViewById<EditText>(R.id.urlet)
                        imagePath = getRealPathFromVideoUri(videoUri)
                        mediaPreview.setText(imagePath)
                    }
                }
            }
        }
    }


    fun init(){
        nama = findViewById(R.id.namaet)
        Lokasi = findViewById(R.id.lokasiet)
        Kronologi = findViewById(R.id.kronologiet)
        Nmperusahaan = findViewById(R.id.nmperusahaanet)
        Nmunit = findViewById(R.id.nmunitet)
        Nmorg = findViewById(R.id.nmoranget)
        NomorHp = findViewById(R.id.NomorHp)
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
        val NomorHp = NomorHp.text.toString()
        val grup = spinner.selectedItem.toString()

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
        }else if(grup=="Jenis Pengaduan"){
            Toast.makeText(this@PengaduanActivity, "Pilih Jenis Pengaduan", Toast.LENGTH_SHORT).show()
        }else{
            action()
        }
    }

    private fun action(){
        val layoutprogress = findViewById<LinearLayout>(R.id.progresslayout)
        val overlayView = findViewById<View>(R.id.overlayView)

        val selectDateText = selectDate.text.toString()
        val selectTimeText = selectTime.text.toString()

// Ubah format tanggal menjadi 'yyyy-MM-dd'
        val formattedDate = formatDate(selectDateText)
        val formattedTime = formatTime(selectTimeText)

// Gabungkan tanggal dan waktu ke dalam format yang diharapkan oleh API
        val dateTimeString = "$formattedDate $formattedTime"

        val imageFile = File(imagePath)
        val requestFile = RequestBody.create("*/*".toMediaTypeOrNull(), imageFile)
        val imageBody = MultipartBody.Part.createFormData("file", imageFile.name, requestFile)
        val apiClient = RetrofitInstance.Create(this)

        val nama = RequestBody.create("text/plain".toMediaTypeOrNull(), nama.text.toString())
        val lokasi = RequestBody.create("text/plain".toMediaTypeOrNull(), Lokasi.text.toString())
        val kronologi = RequestBody.create("text/plain".toMediaTypeOrNull(), Kronologi.text.toString())
        val perusahaan = RequestBody.create("text/plain".toMediaTypeOrNull(), Nmperusahaan.text.toString())
        val unit = RequestBody.create("text/plain".toMediaTypeOrNull(), Nmunit.text.toString())
        val orang = RequestBody.create("text/plain".toMediaTypeOrNull(), Nmorg.text.toString())
        val hp = RequestBody.create("text/plain".toMediaTypeOrNull(), NomorHp.text.toString())
        val jenis = RequestBody.create("text/plain".toMediaTypeOrNull(), spinner.selectedItem.toString())
        val tanggal = RequestBody.create("text/plain".toMediaTypeOrNull(), dateTimeString)

        submit.isEnabled = false

        // Tampilkan ProgressBar
        overlayView.visibility = View.VISIBLE
        layoutprogress.visibility = View.VISIBLE
        submit.isEnabled=false

        val sendData = apiClient.Pengaduan(
            nama, lokasi,kronologi,perusahaan,unit,orang,hp,jenis,tanggal, imageBody
        )
        sessionManager = SessionManager(this)
        sendData.enqueue(object : retrofit2.Callback<response> {
            override fun onResponse(call: retrofit2.Call<response>, response: retrofit2.Response<response>) {
                val responseBody = response.body()
                if (responseBody != null) {
                    if (response.isSuccessful && responseBody.message == "berhasil menambahkan data" && responseBody.status == 200) {
                        overlayView.visibility = View.GONE
                        layoutprogress.visibility = View.GONE
                        Toast.makeText(
                            this@PengaduanActivity,
                            "BERHASIL MELAKUKAN PENGADUAN",
                            Toast.LENGTH_SHORT
                        ).show()
                        if (sessionManager.isLogin()!!) {

                            val intent =
                                Intent(this@PengaduanActivity, MainActivity::class.java).also {
                                    it.flags =
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                }
                            startActivity(intent)
                            finish()
                        } else {
                            val intent =
                                Intent(this@PengaduanActivity, LoginActivity::class.java).also {
                                    it.flags =
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                }
                            startActivity(intent)
                            finish()
                        }
                    } else {
                        val errorBody = response.errorBody()?.string()
                        val errorMessage = if (!errorBody.isNullOrEmpty()) {
                            errorBody
                        } else {
                            "Tidak ada pesan kesalahan yang tersedia."
                        }
                        Toast.makeText(
                            this@PengaduanActivity,
                            "Gagal mengirim data ke API. Pesan kesalahan: $errorMessage",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            override fun onFailure(call: retrofit2.Call<response>, t: Throwable) {
                Toast.makeText(this@PengaduanActivity, "Cek Koneksi!", Toast.LENGTH_SHORT).show()
                overlayView.visibility = View.GONE
                layoutprogress.visibility = View.GONE
                submit.isEnabled=true

            }
        })

    }

    fun formatDate(date: String): String {
        val parts = date.split("-")
        if (parts.size == 3) {
            val year = parts[0]
            val month = parts[1].padStart(2, '0') // Tambahkan nol jika perlu
            val day = parts[2].padStart(2, '0') // Tambahkan nol jika perlu
            return "$year-$month-$day"
        }
        return date // Kembalikan as is jika format tidak sesuai
    }

    // Fungsi untuk mengubah format waktu menjadi 'HH:mm:ss'
    fun formatTime(time: String): String {
        val parts = time.split(":")
        if (parts.size == 2) {
            val hour = parts[0].padStart(2, '0') // Tambahkan nol jika perlu
            val minute = parts[1].padStart(2, '0') // Tambahkan nol jika perlu
            return "$hour:$minute:00"
        }
        return time // Kembalikan as is jika format tidak sesuai
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
                    val formattedDate = String.format("%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth)
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
                    val formattedTime = String.format("%02d:%02d:00", hourOfDay, minute)
                    selectTime.setText(formattedTime)
                },
                mHour,
                mMinute,
                true
            )
            timePickerDialog.show()
        }
    }

}