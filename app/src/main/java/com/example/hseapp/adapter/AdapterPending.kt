package com.example.hseapp.adapter

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.hseapp.DetailInspeksi
import com.example.hseapp.R
import com.example.hseapp.dao.AnswerEntity
import com.example.hseapp.dao.HaulingEntity
import com.example.hseapp.dataclass.Loading
import com.example.hseapp.retrofit.RetrofitInstance
import com.squareup.picasso.Picasso
import retrofit2.Callback
import java.io.File

class AdapterPending(private val dataList: List<AnswerEntity>): RecyclerView.Adapter<AdapterPending.ViewHolderData>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderData {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.recent_loading, parent, false)
        return ViewHolderData(layout)
    }

    override fun onBindViewHolder(holder: ViewHolderData, position: Int) {
        val data = dataList[position]

        holder.tanggal.text = data.tanggal
        holder.lokasi.text = data.namalokasi
        holder.status.text = "Pending"
        holder.card.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.oren))

        holder.status.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))


        // Dapatkan URL gambar dari atribut yang sesuai dalam data
        val imageUrl = data.gambar1
        val widthInPixels = 80  // Gantilah dengan ukuran lebar yang Anda inginkan dalam piksel
        val heightInPixels = 80 // Gantilah dengan ukuran tinggi yang Anda inginkan dalam piksel

        val imageUri = Uri.fromFile(File(imageUrl))

        Picasso.get()
            .load(imageUri)
            .resize(widthInPixels, heightInPixels)
            .centerCrop()
            .into(holder.img)


        holder.itemView.setOnClickListener{
            val ctx = holder.context
            val intent = Intent(ctx, DetailInspeksi::class.java)

            intent.putExtra("id_loading", data.id_answer)
            intent.putExtra("tanggal",data.tanggal.toString())
            intent.putExtra("pengawas",data.pengawas.toString())
            intent.putExtra("shift",data.shift.toString())
            intent.putExtra("grup",data.grup.toString())
            intent.putExtra("lokasi",data.namalokasi.toString())
            intent.putExtra("gambar1",data.gambar1)
            intent.putExtra("gambar2",data.gambar2)
            ctx.startActivity(intent)
        }

    }

    override fun getItemCount(): Int = dataList.size

    class ViewHolderData(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tanggal: TextView = itemView.findViewById(R.id.tanggal)
        val lokasi: TextView = itemView.findViewById(R.id.tv_lokasi)
        val img : ImageView = itemView.findViewById(R.id.tv_img)
        val context = itemView.context
        val status : TextView = itemView.findViewById(R.id.tv_status)
        val card : CardView = itemView.findViewById(R.id.card)
    }
}
