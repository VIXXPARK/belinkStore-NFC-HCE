package com.example.belinkstore

import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {
    @FormUrlEncoded
    @POST("/api/store/signup")
    fun signUp(
        @Field("address")address:String,@Field("name")name: String,@Field("companyNum")companyNum:String,@Field("token")token:String
    ):Call<Data>
}