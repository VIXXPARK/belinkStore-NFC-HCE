package com.example.belinkstore

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.NfcAdapter.CreateNdefMessageCallback
import android.nfc.NfcEvent
import android.os.Bundle
import android.os.Messenger
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.belinkstore.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var mBinding: ActivityMainBinding?=null
    private val binding get() = mBinding!!

    var nfcAdapter: NfcAdapter? = null
    var myHost: Intent? = null

    private lateinit var auto: SharedPreferences

    private lateinit var mAPDUMessenger: Messenger



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auto =getSharedPreferences("auto", Activity.MODE_PRIVATE)!!

        val intent = Intent(this,MyHostApduService::class.java)
        intent.putExtra("StoreID",auto.getString("databaseStoreId",""))
        startService(intent)

        val actionbar = actionBar
        actionbar?.hide()


        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (nfcAdapter == null) {
            Toast.makeText(this, "Does not support NFC", Toast.LENGTH_LONG).show()
            finish()
            return
        } else {
            myHost = Intent(this, MyHostApduService::class.java)
            startService(myHost)

        }

    }





    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    override fun onDestroy() {
        mBinding=null
        super.onDestroy()
    }


}