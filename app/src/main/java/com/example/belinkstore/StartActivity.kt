package com.example.belinkstore

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.belinkstore.LoginActivity
import com.example.belinkstore.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.*

class StartActivity : AppCompatActivity() {
    private lateinit var retrofit: Retrofit
    private lateinit var supplementService: RetrofitService

    private lateinit var auto: SharedPreferences
    private lateinit var autoLogin: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        initRetrofit()

        auto =getSharedPreferences("auto", Activity.MODE_PRIVATE)!!
        autoLogin=auto.edit()

        var actionBar = supportActionBar
        actionBar?.hide()

        val storeID = auto.getString("inputStoreId",null)
        val storeADDRESS = auto.getString("inputStoreAddress", null)
        val storeNAME = auto.getString("inputStoreName",null)

        if(storeID.isNullOrBlank()){
            val intent = Intent(this@StartActivity,LoginActivity::class.java)
            startActivity(intent)

        }else{
            val intent = Intent(this@StartActivity,MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun initRetrofit() {
        retrofit = RetrofitClient.getInstance(this)
        supplementService = retrofit.create(RetrofitService::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()

    }

}