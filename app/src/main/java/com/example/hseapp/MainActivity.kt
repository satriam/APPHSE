package com.example.hseapp


import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.example.hseapp.dao.DBHelper

import com.example.hseapp.dataclass.DataMe
import com.example.hseapp.dataclass.safetycampaign
import com.example.hseapp.retrofit.RetrofitInstance
import com.example.hseapp.retrofit.SessionManager
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioAttributes
import android.media.Image
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.Calendar
import android.text.format.DateFormat
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.hseapp.dataclass.notif


class MainActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    var animTv: Animation? = null
    var hariIni: String? = null
    var dat: TextView? = null
    private val PERMISSION_CODE = 123
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
//    private val CHANNEL_ID = "my_channel"
//    private val NOTIFICATION_ID = 101
//    private lateinit var notificationManager: NotificationManagerCompat
//    private val REQUEST_CODE_PERMISSION = 101

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var dateNow = Calendar.getInstance().time
        var dat = findViewById<TextView>(R.id.tvDate)
        sessionManager = SessionManager(this)
        //data user
        getpref()
        fiturout()
        slider()
        fitur()
//        notif()


        //test

//        notificationManager = NotificationManagerCompat.from(this)

        val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                // Jika belum memiliki izin MANAGE_EXTERNAL_STORAGE, buka layar pengaturan
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.data = Uri.parse("package:$packageName")
                startActivity(intent)
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, permissions, PERMISSION_CODE)
            }
        }

        swipeRefreshLayout = findViewById(R.id.swipe)
        swipeRefreshLayout.setOnRefreshListener {
            getpref()
            slider()
            swipeRefreshLayout.isRefreshing=false
        }

        animTv = AnimationUtils.loadAnimation(this,R.anim.anim_tv)
        hariIni = DateFormat.format("EEEE", dateNow) as String
        dat.setAnimation(animTv)


        if (hariIni.equals("sunday", ignoreCase = true)) {
            hariIni = "Minggu"
        } else if (hariIni.equals("monday", ignoreCase = true)) {
            hariIni = "Senin"
        } else if (hariIni.equals("tuesday", ignoreCase = true)) {
            hariIni = "Selasa"
        } else if (hariIni.equals("wednesday", ignoreCase = true)) {
            hariIni = "Rabu"
        } else if (hariIni.equals("thursday", ignoreCase = true)) {
            hariIni = "Kamis"
        } else if (hariIni.equals("friday", ignoreCase = true)) {
            hariIni = "Jumat"
        } else if (hariIni.equals("saturday", ignoreCase = true)) {
            hariIni = "Sabtu"
        }

        val tanggal = DateFormat.format("d", dateNow) as String
        val monthNumber = DateFormat.format("M", dateNow) as String
        val year = DateFormat.format("yyyy", dateNow) as String
        val month = monthNumber.toInt()
        var bulan: String? = null
        if (month == 1) {
            bulan = "Januari"
        } else if (month == 2) {
            bulan = "Februari"
        } else if (month == 3) {
            bulan = "Maret"
        } else if (month == 4) {
            bulan = "April"
        } else if (month == 5) {
            bulan = "Mei"
        } else if (month == 6) {
            bulan = "Juni"
        } else if (month == 7) {
            bulan = "Juli"
        } else if (month == 8) {
            bulan = "Agustus"
        } else if (month == 9) {
            bulan = "September"
        } else if (month == 10) {
            bulan = "Oktober"
        } else if (month == 11) {
            bulan = "November"
        } else if (month == 12) {
            bulan = "Desember"
        }
        val tgll = "$tanggal $bulan $year"
        var formatFix: String = hariIni + ", " + tgll
        dat.setText(formatFix)
}

    private fun fiturout() {
        //out
        val keluar = findViewById<ImageView>(R.id.logout)
        keluar.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Logout")
            builder.setMessage("Apakah Anda yakin ingin logout?")

            builder.setPositiveButton("Ya") { dialog, which ->
                sessionManager.removeData()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

            builder.setNegativeButton("Batal") { dialog, which ->
            }

            builder.show()
        }
    }

    private fun slider() {
        // Slider
        val apiClient = RetrofitInstance.Create(this)
        val call = apiClient.getsafetycampaign()


        call.enqueue(object : Callback<List<safetycampaign>> { // Updated the callback type to List<SafetyCampaign>
            override fun onResponse(call: Call<List<safetycampaign>>, response: Response<List<safetycampaign>>) {
                if (response.isSuccessful) {
                    val safetyCampaignList = response.body() // Get the list of SafetyCampaign
                    Log.d("Safety",safetyCampaignList.toString())
                    val imageslider = findViewById<ImageSlider>(R.id.slider)
                    val imageList = ArrayList<SlideModel>()

                    safetyCampaignList?.forEach { campaignData ->
                        val imageUrl = RetrofitInstance.BASE_URL + campaignData.img_url
                        imageList.add(SlideModel(imageUrl, campaignData.keterangan))
                    }

                    imageslider.setImageList(imageList)
                } else {
                    // Handle unsuccessful response
                }
            }

            override fun onFailure(call: Call<List<safetycampaign>>, t: Throwable) {
                // Handle failure
            }
        })
    }

    private fun fitur() {
        //hauling road
        val llhr = findViewById<LinearLayout>(R.id.LLHR)
        llhr.setOnClickListener{
            val intent = Intent(this, RVHauling::class.java)
            startActivity(intent)
        }
        //Dumping Point
        val lldp = findViewById<LinearLayout>(R.id.LLDP)
        lldp.setOnClickListener{
            val intent = Intent(this, RVDumping::class.java)
            startActivity(intent)
        }
        //loading
        val lllp = findViewById<LinearLayout>(R.id.LLLP)
        lllp.setOnClickListener{
            val intent = Intent(this,RVLoading::class.java)
            startActivity(intent)
        }
        val llst = findViewById<ConstraintLayout>(R.id.LLST)
        llst.setOnClickListener{
            val intent = Intent(this,safetyTalkActivity::class.java)
            startActivity(intent)
        }
        val llp = findViewById<ConstraintLayout>(R.id.LLP)
        llp.setOnClickListener{
            val intent = Intent(this,PengaduanActivity::class.java)
            startActivity(intent)
        }
        val llt = findViewById<ConstraintLayout>(R.id.LLT)
        llt.setOnClickListener{
            val intent = Intent(this,InspeksiBulananActivity::class.java)
            startActivity(intent)
        }
        val lli = findViewById<ConstraintLayout>(R.id.LLI)
        lli.setOnClickListener{
            val intent = Intent(this,InspeksiHarianActivity::class.java)
            startActivity(intent)
        }
        val lla = findViewById<ConstraintLayout>(R.id.LLA)
        lla.setOnClickListener{
            val intent = Intent(this,AccidentActivity::class.java)
            startActivity(intent)
        }
        if (sessionManager.getRole()!= "admin"){
            val llpr = findViewById<ConstraintLayout>(R.id.LLPR)
            llpr.setOnClickListener {
                Toast.makeText(this@MainActivity, "Hanya Admin", Toast.LENGTH_SHORT).show()
            }
        }else {
            val llpr = findViewById<ConstraintLayout>(R.id.LLPR)
            llpr.setOnClickListener {
                val intent = Intent(this, PromotifActivity::class.java)
                startActivity(intent)
            }
        }

    }
    private fun getpref(){
        val apiClient = RetrofitInstance.Create(this)
        val apiService = apiClient.getUserLogin()
        val Nama = findViewById<TextView>(R.id.tvNama)
        val Pegawai = findViewById<TextView>(R.id.tvNoPeg)
        apiService.enqueue(object : Callback<List<DataMe>> {
            override fun onResponse(call: Call<List<DataMe>>, response: Response<List<DataMe>>) {
                // Tangani respons sukses
                if (response.isSuccessful) {
                    val dataMeList = response.body()
                    val dataMe = dataMeList?.get(0)
                    Log.d("datame",dataMe.toString())
                    if (dataMe != null) {
                        val nama = dataMe.nama
                        val nopeg = dataMe.nomor_pegawai
                        Nama.text = nama
                        Pegawai.text = nopeg
                        val imageUrl = RetrofitInstance.BASE_URL + dataMe.path_gambar
                        val profilePictureImageView = findViewById<ImageView>(R.id.imageView3)
                        Picasso.get().load(imageUrl).into(profilePictureImageView)
                    } else {
                    }
                } else {
                    val errorMessage = response.message()
                }
            }

            override fun onFailure(call: Call<List<DataMe>>, t: Throwable) {
                Log.d("datame",t.toString())
            }
        })
    }


//    private fun notif() {
//        val apiClient = RetrofitInstance.Create(this)
//        val apiService = apiClient.getnotif()
//        apiService.enqueue(object : Callback<List<notif>> {
//            override fun onResponse(call: Call<List<notif>>, response: Response<List<notif>>) {
//                // Tangani respons sukses
//                if (response.isSuccessful) {
//                    val notifications = response.body()
//                    Log.d("DATA NOTIF", notifications.toString())
//                    notifications?.let {
//                        for ((index, notif) in it.withIndex()) {
//                            // Ganti title dan message sesuai dengan data notifikasi
//                            val title = notif.title
//                            val message = notif.body
//
//                            val builder = NotificationCompat.Builder(
//                                this@MainActivity,
//                                BaseApp.CHANNEL_ID_1
//                            )
//                                .setSmallIcon(R.mipmap.icon)
//                                .setContentTitle(title)
//                                .setContentText(message)
//                                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
//
//                            val notification = builder.build()
//                            val notificationId = index + 1 // Gunakan indeks sebagai ID unik
//                            notificationManager.notify(notificationId, notification)
//
//                            // Hapus notifikasi setelah ditampilkan
//                            notificationManager.cancel(notificationId)
//                        }
//                    }
//                } else {
//                    val errorMessage = response.message()
//                    Log.d("DATA NOTIF", errorMessage.toString())
//                }
//            }
//
//            override fun onFailure(call: Call<List<notif>>, t: Throwable) {
//                Log.d("datame", t.toString())
//            }
//        })
//    }



}