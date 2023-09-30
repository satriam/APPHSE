package com.example.hseapp.adapter

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hseapp.R
import com.example.hseapp.dao.AnswerEntity
import com.example.hseapp.dataclass.Loading
import com.example.hseapp.retrofit.RetrofitInstance
import com.squareup.picasso.Picasso
import retrofit2.Callback

class AdapterLoading(private val dataList: ArrayList<Loading>): RecyclerView.Adapter<AdapterLoading.ViewHolderData>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderData {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.recent_loading, parent, false)
        return ViewHolderData(layout)
    }

    override fun onBindViewHolder(holder: ViewHolderData, position: Int) {
        val data = dataList[position]

        holder.tanggal.text = data.created_at
        holder.lokasi.text = data.nama_lokasi

        // Dapatkan URL gambar dari atribut yang sesuai dalam data
        val imageUrl = RetrofitInstance.BASE_URL + data.img_url_1
        val widthInPixels = 80  // Gantilah dengan ukuran lebar yang Anda inginkan dalam piksel
        val heightInPixels = 80 // Gantilah dengan ukuran tinggi yang Anda inginkan dalam piksel

        Picasso.get()
            .load(Uri.parse(imageUrl)) // Konversi URL menjadi Uri jika perlu
            .resize(widthInPixels, heightInPixels) // Atur ukuran lebar dan tinggi di sini
            .centerCrop() // Atur cara tampilan gambar (misalnya: centerCrop, fit, dsb.)
            .into(holder.img)
    }

    override fun getItemCount(): Int = dataList.size

    class ViewHolderData(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tanggal: TextView = itemView.findViewById(R.id.tanggal)
        val lokasi: TextView = itemView.findViewById(R.id.tv_lokasi)
        val img :ImageView = itemView.findViewById(R.id.tv_img)
    }
}