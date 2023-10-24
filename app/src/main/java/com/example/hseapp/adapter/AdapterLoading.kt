package com.example.hseapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.hseapp.DetailInspeksi
import com.example.hseapp.DetailInspeksiOnline
import com.example.hseapp.R
import com.example.hseapp.dao.AnswerEntity
import com.example.hseapp.dataclass.Loading
import com.example.hseapp.retrofit.RetrofitInstance
import com.example.hseapp.retrofit.SessionManager
import com.squareup.picasso.Picasso
import retrofit2.Callback

class AdapterLoading(private val dataList: ArrayList<Loading> , private val sessionManager: SessionManager) : RecyclerView.Adapter<AdapterLoading.ViewHolderData>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderData {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.recent_loading, parent, false)
        return ViewHolderData(layout)
    }

    override fun onBindViewHolder(holder: ViewHolderData, position: Int) {
        val data = dataList[position]


        holder.tanggal.text = data.tanggal
        holder.lokasi.text = data.nama_lokasi
        holder.pengawas.text = data.nama_pengawas
        holder.card.setCardBackgroundColor(
            ContextCompat.getColor(
                holder.itemView.context,
                R.color.biru
            )
        )
        if (sessionManager.getRole()=="admin"){
            holder.nama.visibility = View.VISIBLE
            holder.nama.text = data.nama
        }

        holder.status.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
        holder.status.text = data.status

        val imageUrl = RetrofitInstance.BASE_URL + data.img_url_1
        val widthInPixels = 80
        val heightInPixels = 80

        Picasso.get()
            .load(Uri.parse(imageUrl))
            .resize(widthInPixels, heightInPixels)
            .centerCrop()
            .into(holder.img)

        holder.itemView.setOnClickListener {
                    val ctx = holder.context
                    val intent = Intent(ctx, DetailInspeksiOnline::class.java)


                    intent.putExtra("id_loading", data.id_loadings)
                    intent.putExtra("id_hauling", data.id_haulings)
                    intent.putExtra("id_dumping", data.id_dumpings)
                    intent.putExtra("tanggal", data.tanggal.toString())
                    intent.putExtra("pengawas", data.nama_pengawas.toString())
                    intent.putExtra("shift", data.shift.toString())
                    intent.putExtra("grup", data.grup.toString())
                    intent.putExtra("lokasi", data.nama_lokasi.toString())
                    intent.putExtra("gambar1", data.img_url_1)
                    intent.putExtra("gambar2", data.img_url_2)
                    intent.putExtra("nama", data.nama)
                    intent.putExtra("qr_mitra", data.qr_mitra)
                    intent.putExtra("qr_pengawas", data.qr_pengawas)
                    intent.putExtra("supervisor", data.nama_supervisor)
                    intent.putExtra("status", data.status)
                    intent.putExtra("detailloading", data.nama_loading)
                    intent.putExtra("detaildumping", data.nama_dumping)
                    intent.putExtra("detailhauling", data.nama_hauling)
                    ctx.startActivity(intent)

            }
        }


    override fun getItemCount(): Int = dataList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newDataList: ArrayList<Loading>) {
        dataList.clear()
        dataList.addAll(newDataList)
        notifyDataSetChanged()
    }

    class ViewHolderData(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tanggal: TextView = itemView.findViewById(R.id.tanggal)
        val lokasi: TextView = itemView.findViewById(R.id.tv_lokasi)
        val img: ImageView = itemView.findViewById(R.id.tv_img)
        val status: TextView = itemView.findViewById(R.id.tv_status)
        val card: CardView = itemView.findViewById(R.id.card)
        val pengawas: TextView = itemView.findViewById(R.id.pengawas)
        val nama: TextView = itemView.findViewById(R.id.postby)
        val context = itemView.context
    }
}
