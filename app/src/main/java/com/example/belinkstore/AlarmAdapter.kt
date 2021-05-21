package com.example.belinkstore

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class AlarmAdapter(val context: Context, val DataList:List<Message>): RecyclerView.Adapter<AlarmViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val info = LayoutInflater.from(context).inflate(R.layout.custom_list,parent,false)
        return AlarmViewHolder(info)
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        holder.tv_customer_value.setText(DataList[position].msg)
    }

    override fun getItemCount(): Int {
        return DataList.size
    }

}