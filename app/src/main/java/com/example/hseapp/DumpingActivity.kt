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
import com.squareup.picasso.Picasso
import java.io.FileNotFoundException

class DumpingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dumping)

// Assuming you have an ImageView with the ID "imageView"
        val imageView = findViewById<ImageView>(R.id.imagetest)

// Query the database to retrieve the image path (Image1)
        val dbHelper = DBHelper(this)
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT Image1 FROM jawaban", null)

        if (cursor.moveToFirst()) {
            val imagePath = cursor.getString(cursor.getColumnIndex("Image1"))
            Log.e("TEST",imagePath.toString())

            // Load and display the image using Picasso
            Picasso.get()
                .load("file://$imagePath") // Use "file://" to specify a local file path
                .into(imageView)
        }

        cursor.close()
        db.close()
    }

}