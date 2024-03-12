package ru.easycode.zerotoheroandroidtdd.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface SimpleService {

//    @GET("{link}")
//    suspend fun fetch(
//        @Path(value="link", encoded=true) url: String
//    ): SimpleResponse

    @GET
    suspend fun fetch(
        @Url url: String,
    ): SimpleResponse
}