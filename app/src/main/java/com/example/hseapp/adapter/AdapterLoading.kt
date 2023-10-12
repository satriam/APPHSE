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

        holder.tanggal.text = data.tanggal
        holder.lokasi.text = data.nama_lokasi
        holder.card.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.biru))

        holder.status.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))


        // Dapatkan URL gambar dari atribut yang sesuai dalam data
        val imageUrl = RetrofitInstance.BASE_URL + data.img_url_1
        val widthInPixels = 80  // Gantilah dengan ukuran lebar yang Anda inginkan dalam piksel
        val heightInPixels = 80 // Gantilah dengan ukuran tinggi yang Anda inginkan dalam piksel

        Picasso.get()
            .load(Uri.parse(imageUrl)) // Konversi URL menjadi Uri jika perlu
            .resize(widthInPixels, heightInPixels) // Atur ukuran lebar dan tinggi di sini
            .centerCrop() // Atur cara tampilan gambar (misalnya: centerCrop, fit, dsb.)
            .into(holder.img)


//        holder.itemView.setOnClickListener{
//            val ctx = holder.context
//            val intent = Intent(ctx,DetailInspeksi::class.java)

//            intent.putExtra("id_loading", data.id_loadings.toString())
//            intent.putExtra("id_dumping", data.id_dumpings.toString())
//            intent.putExtra("id_hauling", data.id_haulings.toString())
//            intent.putExtra("nama",data.nama_lokasi.toString())
//            intent.putExtra("jenis",data.created_at.toString())
//            intent.putExtra("email",data.img_url_1.toString())
//            intent.putExtra("role_id",item.role_id.toString())
//            ctx.startActivity(intent)
//        }
    }

    override fun getItemCount(): Int = dataList.size

    class ViewHolderData(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tanggal: TextView = itemView.findViewById(R.id.tanggal)
        val lokasi: TextView = itemView.findViewById(R.id.tv_lokasi)
        val img :ImageView = itemView.findViewById(R.id.tv_img)
        val status : TextView = itemView.findViewById(R.id.tv_status)
        val card : CardView = itemView.findViewById(R.id.card)
//        val context = itemView.context
    }
}