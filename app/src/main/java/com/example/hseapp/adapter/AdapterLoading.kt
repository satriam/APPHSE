package com.example.hseapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hseapp.R
import com.example.hseapp.dao.AnswerEntity
import com.example.hseapp.dataclass.Data
import com.example.hseapp.dataclass.Loading
import retrofit2.Callback

class AdapterLoading(private val dataList: List<Data>): RecyclerView.Adapter<AdapterLoading.ViewHolderData>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderData {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.recent_loading, parent, false)
        return ViewHolderData(layout)
    }

    override fun onBindViewHolder(holder: ViewHolderData, position: Int) {
        val data = dataList[position]

        holder.tanggal.text = data.attributes.tanggal
        holder.lokasi.text = "User ID: ${data.attributes.userId}"
    }

    override fun getItemCount(): Int = dataList.size

    class ViewHolderData(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val context = itemView.context
        val tanggal: TextView = itemView.findViewById(R.id.tanggal)
        val lokasi: TextView = itemView.findViewById(R.id.tv_lokasi)
        val status: TextView = itemView.findViewById(R.id.tv_status)
    }
}
