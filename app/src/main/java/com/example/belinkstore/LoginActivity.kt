package com.example.belinkstore

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.belinkstore.R
import com.example.belinkstore.RetrofitClient
import com.example.belinkstore.RetrofitService
import com.example.belinkstore.databinding.ActivityLoginBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class LoginActivity : AppCompatActivity(){
    private var mBinding: ActivityLoginBinding?=null
    private val binding get() = mBinding!!

    private var storeId:String?=null
    private var storeToken:String?=null
    private var storeName:String?=null
    private var storeAddress:String?=null

    private lateinit var retrofit : Retrofit
    private lateinit var supplementService : RetrofitService

    private lateinit var auto: SharedPreferences
    private lateinit var autoLogin: SharedPreferences.Editor

    private var TOKEN=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auto =getSharedPreferences("auto", Activity.MODE_PRIVATE)!!
        autoLogin=auto.edit()

        initRetrofit()


        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("태그", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            TOKEN = token.toString();

        })

        var actionBar = supportActionBar
        actionBar?.hide()

        binding.btnLoginSignup.setOnClickListener {
            getEditString()
            autoLogin.apply()
            signup(storeId!!, storeAddress!!, storeName!!)
        }


    }

    fun getEditString(){
        storeId=binding.etStoreId.text.toString()
        storeAddress=binding.etStoreAddress.text.toString()
        storeName=binding.etStoreName.text.toString()
        // storeToken=
        autoLogin.clear()
        println("$storeId,$storeAddress,$storeName")
        println("*************************getEditString**************************************")
    }

    fun signup(storeId: String, storeAddress: String, storeName: String){
        println("Retrofit 들어가기 전에")
        println("$storeId + $storeAddress + $storeName")
        supplementService.signUp(companyNum=storeId, address = storeAddress, name=storeName,token = TOKEN).enqueue(object : Callback<Data>{
            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                println("*************************signup**************************************")
                val loginResponse = response.body()
                autoLogin.putString("inputStoreId", storeId)
                autoLogin.putString("inputStoreAddress", storeAddress)
                autoLogin.putString("inputStoreName", storeName)

                Log.d("Id", storeId)
                Log.d("Address", storeAddress)
                Log.d("Name", response.message())
                Log.d("스토어 아이디",response.body()!!.data.id)
                autoLogin.putString("databaseStoreId",response.body()!!.data.id)
                autoLogin.apply()
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                this@LoginActivity.finish()

            }
            override fun onFailure(call: Call<Data>, t: Throwable) {
                Log.d("fail", "$t")
                println("*************************signupFail**************************************")
            }
        })
    }

    private fun initRetrofit() {
        retrofit = RetrofitClient.getInstance(this)
        supplementService = retrofit.create(RetrofitService::class.java)
    }

    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }


}