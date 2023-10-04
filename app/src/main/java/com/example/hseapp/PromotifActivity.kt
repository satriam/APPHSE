package com.example.hseapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import com.example.hseapp.retrofit.RetrofitInstance
import com.example.hseapp.retrofit.SessionManager
import com.google.android.material.textfield.TextInputEditText
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class PromotifActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var  submit: Button
    private lateinit var  Keterangan: EditText
    private lateinit var  spinner: Spinner
    lateinit var imageView: ImageView
    private val pickImage = 100
    private var imageUri: Uri? = null
    private var imagePath: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promotif)
        init()

        imageView.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }
        submit.setOnClickListener {
            validation()
        }
    }

    fun init(){
        Keterangan = findViewById(R.id.keteranganPromotif)
        spinner = findViewById(R.id.spinnermitra)
        imageView = findViewById(R.id.Promotif)
        submit = findViewById(R.id.btnSubmitpromotif)
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


    private fun validation(){
        val gmbr = imagePath.toString()
        val keterangan = Keterangan.text.toString()
        val mitra = spinner.selectedItem.toString()

       if (gmbr.isEmpty()){
           Toast.makeText(this@PromotifActivity, "Gambar Tidak Boleh Kosong", Toast.LENGTH_SHORT).show()
        }else if (keterangan.isEmpty()){
            Keterangan.setError("Keterangan Tidak Boleh Kosong")
        }else if(mitra=="Mitra"){
            Toast.makeText(this@PromotifActivity, "Pilih Mitra", Toast.LENGTH_SHORT).show()
        }else{
            action()
        }
    }

    private fun action(){
        val imageFile = File(imagePath)
        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), imageFile)
        val gambar = MultipartBody.Part.createFormData("gambar1", imageFile.name, requestFile)
        val keterangan = RequestBody.create("text/plain".toMediaTypeOrNull(), Keterangan.text.toString())
        val mitra = RequestBody.create("text/plain".toMediaTypeOrNull(), spinner.selectedItem.toString())

        val apiClient = RetrofitInstance.Create(this)
        val sendData = apiClient.uploadsafety(keterangan,mitra,gambar)

        sendData.enqueue(object : retrofit2.Callback<Void> {
            override fun onResponse(call: retrofit2.Call<Void>, response: retrofit2.Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@PromotifActivity, "BERHASIL MELAKUKAN INPUT DATA", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@PromotifActivity, MainActivity::class.java).also {
                            it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        }
                        startActivity(intent)
                        finish()

                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = if (!errorBody.isNullOrEmpty()) {
                        errorBody
                    } else {
                        "Tidak ada pesan kesalahan yang tersedia."
                    }
                    Toast.makeText(this@PromotifActivity, "Gagal mengirim data ke API. Pesan kesalahan: $errorMessage", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: retrofit2.Call<Void>, t: Throwable) {
                Toast.makeText(this@PromotifActivity, "Terjadi kesalahan dalam permintaan: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

    }

}