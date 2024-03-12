package ru.easycode.zerotoheroandroidtdd.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {

    private const val BASE_URL = "https://raw.githubusercontent.com/JohnnySC/ZeroToHeroAndroidTDD/"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    val apiService: SimpleService = retrofit.create(SimpleService::class.java)
}