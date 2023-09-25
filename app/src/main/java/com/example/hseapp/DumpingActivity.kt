package com.example.hseapp


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.example.hseapp.dao.AnswerEntity
import com.example.hseapp.dao.DBHelper
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.io.FileNotFoundException

class DumpingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dumping)
    }
}


