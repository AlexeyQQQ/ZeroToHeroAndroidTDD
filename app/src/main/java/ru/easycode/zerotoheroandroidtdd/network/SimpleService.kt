package ru.easycode.zerotoheroandroidtdd.network

import retrofit2.http.GET
import retrofit2.http.Path

interface SimpleService {

    @GET("{link}")
    suspend fun fetch(
        @Path(value="link", encoded=true) url: String
    ): SimpleResponse
}