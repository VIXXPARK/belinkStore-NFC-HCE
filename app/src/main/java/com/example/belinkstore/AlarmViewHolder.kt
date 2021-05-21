package com.example.belinkstore

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class Message(val msg:String)

class AlarmViewHolder(v: View): RecyclerView.ViewHolder(v) {
    val tv_customer_value = v.findViewById<TextView>(R.id.tv_customer_value)
}