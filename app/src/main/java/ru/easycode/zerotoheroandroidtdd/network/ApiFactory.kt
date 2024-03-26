package ru.easycode.zerotoheroandroidtdd.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {

    private const val BASE_URL = "https://raw.githubusercontent.com/JohnnySC/ZeroToHeroAndroidTDD/"

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: SimpleService = retrofit.create(SimpleService::class.java)
}