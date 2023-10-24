package com.example.hseapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.hseapp.Loading.AccSpvFragment
import com.example.hseapp.dataclass.response
import com.example.hseapp.retrofit.RetrofitInstance
import com.example.hseapp.retrofit.SessionManager
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class DetailInspeksiOnline : AppCompatActivity() {
    private lateinit var  etanggal: TextView
    private lateinit var  epengawas: TextView
    private lateinit var  elokasi: TextView
    private lateinit var  eshift: TextView
    private lateinit var  egrup: TextView
    private lateinit var  epembuat: TextView
    private lateinit var  egambar1: ImageView
    private lateinit var  egambar2: ImageView
    private lateinit var  eqr1: ImageView
    private lateinit var  eqr2: ImageView
    private lateinit var  epengawasmitra: TextView
    private lateinit var  epengawaslap: TextView
    private lateinit var  esupervisor: TextView
    private lateinit var  edetaillokasi: TextView
    private lateinit var  ehapus: Button
    private lateinit var  eupdate: Button
    private lateinit var sessionManager: SessionManager
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
        epembuat= findViewById(R.id.namapembuat)
        egambar1= findViewById(R.id.gambar1)
        egambar2= findViewById(R.id.gambar2)
        eqr1= findViewById(R.id.qr1)
        eqr2= findViewById(R.id.qr2)
        epengawasmitra= findViewById(R.id.pengawasmitra)
        epengawaslap= findViewById(R.id.pengawaslap)
        esupervisor= findViewById(R.id.supervisor)
        edetaillokasi= findViewById(R.id.lokasidetail)
        ehapus= findViewById(R.id.hapus)
        eupdate= findViewById(R.id.update)

    }

    private fun intent() {

        val tanggal = intent.getStringExtra("tanggal")
        val lokasi = intent.getStringExtra("lokasi")
        val pengawas = intent.getStringExtra("pengawas")
        val shift = intent.getStringExtra("shift")
        val grup = intent.getStringExtra("grup")
        val pembuat = intent.getStringExtra("nama")
        val gambar = intent.getStringExtra("gambar1")
        val gambar2 = intent.getStringExtra("gambar2")
        val qr1 = intent.getStringExtra("qr_mitra")
        val qr2 = intent.getStringExtra("qr_pengawas")
        val spv = intent.getStringExtra("supervisor")
        val status = intent.getStringExtra("status")
        val detailloading = intent.getStringExtra("detailloading")
        val detaildumping = intent.getStringExtra("detaildumping")
        val detailhauling = intent.getStringExtra("detailhauling")
        val loading = intent.getIntExtra("id_loading", 0)
        val dumping = intent.getIntExtra("id_dumping", 0)
        val hauling = intent.getIntExtra("id_hauling", 0)

        etanggal.text = tanggal
        elokasi.text = lokasi
        epengawas.text = pengawas
        eshift.text = shift
        egrup.text = grup
        epembuat.text = pembuat
        epengawasmitra.text = pengawas
        epengawaslap.text = pembuat
        esupervisor.text = spv


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

        val imageqr1 = RetrofitInstance.BASE_URL + qr1
        Picasso.get()
            .load(imageqr1)
            .resize(widthInPixels, heightInPixels)
            .centerCrop()
            .into(eqr1)

        val imageqr2 = RetrofitInstance.BASE_URL + qr2
        Picasso.get()
            .load(imageqr2)
            .resize(widthInPixels, heightInPixels)
            .centerCrop()
            .into(eqr2)

        if(detailloading!=null){
            edetaillokasi.text = detailloading
        }else if (detaildumping!=null){
            edetaillokasi.text = detaildumping
        }else{
            edetaillokasi.text = detailhauling
        }

        sessionManager = SessionManager(this)
        if (sessionManager.getRole() == "admin") {
            ehapus.visibility = View.VISIBLE
            eupdate.visibility = View.VISIBLE
        } else if (sessionManager.getRole() == "Supervisor") {
            if (status == "Acc") {
                ehapus.visibility = View.GONE
                eupdate.visibility = View.GONE
            } else {
                if (loading != 0) {
                    ehapus.visibility = View.VISIBLE
                    eupdate.visibility = View.VISIBLE
                    update()
                }else if(dumping!= 0 ){
                    ehapus.visibility = View.VISIBLE
                    eupdate.visibility = View.VISIBLE
                    updateDumping()
                }else{
                    ehapus.visibility = View.VISIBLE
                    eupdate.visibility = View.VISIBLE
                    updateHauling()
                }
            }
        }
    }

    private fun update() {
        val id = intent.getIntExtra("id_loading", 0)
        val tanggal = intent.getStringExtra("tanggal")
        val spv = intent.getStringExtra("supervisor")

        eupdate.setOnClickListener {

            val apiClient = RetrofitInstance.Create(this)

            val sendData = apiClient.UpdateAccLoading(
                id?.toInt(),
                tanggal.toString(),
                spv.toString()
            )

            Log.d("datasend",sendData.toString())
            sendData.enqueue(object : Callback<response> {
                override fun onResponse(
                    call: Call<response>,
                    response: Response<response>
                ) {
                    Log.d("datasend",response.toString())
                    if (response.isSuccessful) {
                        Toast.makeText(this@DetailInspeksiOnline, "Berhasil Mengubah Data", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }

                override fun onFailure(call: Call<response>, t: Throwable) {
                    Toast.makeText(this@DetailInspeksiOnline, t.toString(), Toast.LENGTH_SHORT).show()
                }

            })
            finish()
        }
        }

    private fun updateDumping() {
        val id = intent.getIntExtra("id_dumping", 0)
        val tanggal = intent.getStringExtra("tanggal")
        val spv = intent.getStringExtra("supervisor")

        eupdate.setOnClickListener {

            val apiClient = RetrofitInstance.Create(this)

            val sendData = apiClient.UpdateAccDumping(
                id?.toInt(),
                tanggal.toString(),
                spv.toString()
            )

            Log.d("datasend",sendData.toString())
            sendData.enqueue(object : Callback<response> {
                override fun onResponse(
                    call: Call<response>,
                    response: Response<response>
                ) {
                    Log.d("datasend",response.toString())
                    if (response.isSuccessful) {
                        Toast.makeText(this@DetailInspeksiOnline, "Berhasil Mengubah Data", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }

                override fun onFailure(call: Call<response>, t: Throwable) {
                    Toast.makeText(this@DetailInspeksiOnline, t.toString(), Toast.LENGTH_SHORT).show()
                }

            })
            finish()
        }
    }

    private fun updateHauling() {
        val id = intent.getIntExtra("id_hauling", 0)
        val tanggal = intent.getStringExtra("tanggal")
        val spv = intent.getStringExtra("supervisor")

        eupdate.setOnClickListener {

            val apiClient = RetrofitInstance.Create(this)

            val sendData = apiClient.UpdateAccHauling(
                id?.toInt(),
                tanggal.toString(),
                spv.toString()
            )

            Log.d("datasend",sendData.toString())
            sendData.enqueue(object : Callback<response> {
                override fun onResponse(
                    call: Call<response>,
                    response: Response<response>
                ) {
                    Log.d("datasend",response.toString())
                    if (response.isSuccessful) {
                        Toast.makeText(this@DetailInspeksiOnline, "Berhasil Mengubah Data", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }

                override fun onFailure(call: Call<response>, t: Throwable) {
                    Toast.makeText(this@DetailInspeksiOnline, t.toString(), Toast.LENGTH_SHORT).show()
                }

            })
            finish()
        }
    }
    }
