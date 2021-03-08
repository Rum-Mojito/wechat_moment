package com.android_practice.wechatmoment.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://thoughtworks-mobile-2018.herokuapp.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(LiveDataCallAdapterFactory())
        .build()

    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }

}