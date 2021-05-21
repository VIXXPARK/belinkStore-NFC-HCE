package com.example.belinkstore

import java.util.*

data class Sign(
    val id:String,
    val storeName:String,
    val storeLocation:String,
    val storeType:String,
    val companyNum:String,
    val updatedAt: Date = Date(),
    val createdAt: Date = Date()
)
